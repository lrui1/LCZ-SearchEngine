package ES.impl;

import ES.Search;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.*;
import Bean.ResultEntry;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.json.JsonData;

import utils.ESUtil;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ESearch implements Search {
    ElasticsearchClient client;
    private long searchCount = 0;

    public ESearch() {
        client = ESUtil.getConnect();
    }

    @Override
    public long getSearchCount() {
        return searchCount;
    }

    @Override
    public ResultEntry add(ResultEntry entry) {
        IndexResponse response;
        try {
            response = client.index(i -> i
                    .index(ESUtil.index).document(entry));
        } catch (IOException e) {
            return null;
        }
        return entry;
    }

    @Override
    public List<ResultEntry> search(String searchText) {
        SearchResponse<ResultEntry> response = null;
        try {
            response = client.search(s -> s
                    .index(ESUtil.index)
                            .query(q -> q
                                    .match(m -> m
                                            .field("text")
                                            .query(searchText)
                                    )
                            ),
                    ResultEntry.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dealSearchResponse(response);
    }

    @Override
    public List<ResultEntry> search(String searchText, int page) {
        int value = (page - 1) * 10;// 页数从0开始编号
        SearchResponse<ResultEntry> search = null;

        try {
            search = client.search(s -> s
                            .index(ESUtil.index)
                            .query(q -> q
                                    .multiMatch(m -> m
                                            .query(searchText)
                                            .fields("title", "text")))
                            .highlight(h -> h
                                    .preTags("<font>")
                                    .postTags("</font>")
                                    .fields("title", builder -> builder)
                                    .fields("text", builder -> builder))
                            .from(value)
                            .size(10)
                    , ResultEntry.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dealSearchResponse(search);
    }

    @Override
    public List<ResultEntry> search(String searchText, int page, String beginDate) {
        int value = (page - 1) * 10;// 页数从0开始编号
        JsonData jsonBeginDate = JsonData.of(beginDate);
        SearchResponse<ResultEntry> search = null;
        try {
            search = client.search(s -> s
                            .index(ESUtil.index)
                            .query(q -> q
                                    .bool(b -> b
                                            .must(b1 -> b1
                                                    .multiMatch(b2 -> b2
                                                            .query(searchText)
                                                            .fields("title", "text")))
                                            .filter(b3 -> b3
                                                    .range(b4 -> b4
                                                            .field("declareTime")
                                                            .gte(jsonBeginDate)))))
                            .highlight(h -> h
                                    .preTags("<font>")
                                    .postTags("</font>")
                                    .fields("title", builder -> builder)
                                    .fields("text", builder -> builder))
                            .from(value)
                            .size(10)
                    , ResultEntry.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dealSearchResponse(search);
    }

    @Override
    public List<ResultEntry> search(String searchText, int page, String beginDate, String endDate) {
        int value = (page - 1) * 10;// 页数从0开始编号
        JsonData jsonBeginDate = JsonData.of(beginDate);
        JsonData jsonEndDate = JsonData.of(endDate);
        SearchResponse<ResultEntry> search = null;
        try {
            search = client.search(s -> s
                            .index(ESUtil.index)
                            .query(q -> q
                                    .bool(b -> b
                                            .must(b1 -> b1
                                                    .match(b2 -> b2
                                                            .field("text")
                                                            .query(searchText)))
                                            .filter(b3 -> b3
                                                    .range(b4 -> b4
                                                            .field("declareTime")
                                                            .gte(jsonBeginDate)
                                                            .lte(jsonEndDate)))))
                            .from(value)
                            .size(10)
                    , ResultEntry.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dealSearchResponse(search);
    }


    @Override
    public List<String> getSearchSuggest(String prefix) {
        SearchResponse<ResultEntry> search = null;

        try {
            search = client.search(s -> s
                            .index(ESUtil.index)
                            .query(q -> q
                                    .match(m -> m
                                            .field("title")
                                            .query(prefix)))
                    , ResultEntry.class);
        } catch (IOException e) {
            close();
            e.printStackTrace();
        }

        if(search != null) {
            List<String> ret = new ArrayList<>();
            List<Hit<ResultEntry>> hits = search.hits().hits();
            for(Hit<ResultEntry> hit : hits) {
                ret.add(hit.source().getTitle());
            }
            return ret;
        } else {
            return null;
        }
    }
    @Override
    public boolean newIndex(Reader reader) {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest.Builder()
                .withJson(reader)
                .index(ESUtil.index)
                .build();

        CreateIndexResponse response = null;
        try {
            response = client.indices().create(createIndexRequest);
        } catch (Exception e) {

        }
        if(response != null) {
            return response.acknowledged();
        }
        else {
            return false;
        }
    }

    @Override
    public boolean deleteIndex() {
        DeleteIndexResponse deleteIndexResponse = null;
        try {
            deleteIndexResponse = client.indices().delete(d -> d
                    .index(ESUtil.index));
        } catch (Exception e) {

        }
        if(deleteIndexResponse == null) {
            return false;
        } else {
            return deleteIndexResponse.acknowledged();
        }
    }

    @Override
    public void close() {
        ESUtil.release();
    }

    private List<ResultEntry> dealSearchResponse(SearchResponse<ResultEntry> searchResponse) {
        if(searchResponse != null) {
            List<ResultEntry> resultEntryList = new ArrayList<>();
            List<Hit<ResultEntry>> hits = searchResponse.hits().hits();
            searchCount = searchResponse.hits().total().value();
            for(Hit<ResultEntry> hit : hits) {
                ResultEntry source = hit.source();
                Map<String, List<String>> highlight = hit.highlight();
                if(highlight != null) {
                    List<String> titles = highlight.get("title");
                    List<String> texts = highlight.get("text");
                    if(titles != null && titles.size() > 0) {
                        source.setTitle(titles.get(0));
                    }
                    if(texts != null && texts.size() > 0) {
                        source.setText(texts.get(0));
                    }
                }
                resultEntryList.add(source);
            }
            return resultEntryList;
        } else {
            return null;
        }
    }
}
