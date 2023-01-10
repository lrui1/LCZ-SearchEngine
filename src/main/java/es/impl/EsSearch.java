package es.impl;

import es.Search;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.*;
import bean.ResultEntry;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.json.JsonData;

import utils.EsUtil;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author 开架大飞机
 * @description ES实现搜索接口类
 * @date: 2022/12/17
 */
public class EsSearch implements Search {
    private ElasticsearchClient client;
    private long searchCount = 0;

    public EsSearch() {
        client = EsUtil.getConnect();
    }

    @Override
    public long getSearchCount() {
        return searchCount;
    }

    @Override
    public ResultEntry add(ResultEntry entry) {
        try {
            client.index(i -> i
                    .index(EsUtil.index).document(entry));
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
                    .index(EsUtil.index)
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
        // 页数从0开始编号
        int value = (page - 1) * 10;
        SearchResponse<ResultEntry> search = null;

        try {
            search = client.search(s -> s
                            .index(EsUtil.index)
                            .query(q -> q
                                    .multiMatch(m -> m
                                            .query(searchText)
                                            .fields("title", "text")
                                            .analyzer("ik_smart")))
                            .highlight(h -> h
                                    .preTags("<span class=\"hit-result\">")
                                    .postTags("</span>")
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
        // 页数从0开始编号
        int value = (page - 1) * 10;
        JsonData jsonBeginDate = JsonData.of(beginDate);
        SearchResponse<ResultEntry> search = null;
        try {
            search = client.search(s -> s
                            .index(EsUtil.index)
                            .query(q -> q
                                    .bool(b -> b
                                            .must(b1 -> b1
                                                    .multiMatch(b2 -> b2
                                                            .query(searchText)
                                                            .fields("title", "text")
                                                            .analyzer("ik_smart")))
                                            .filter(b3 -> b3
                                                    .range(b4 -> b4
                                                            .field("declareTime")
                                                            .gte(jsonBeginDate)))))
                            .highlight(h -> h
                                    .preTags("<span class=\"hit-result\">")
                                    .postTags("</span>")
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
        // 页数从0开始编号
        int value = (page - 1) * 10;
        JsonData jsonBeginDate = JsonData.of(beginDate);
        JsonData jsonEndDate = JsonData.of(endDate);
        SearchResponse<ResultEntry> search = null;
        try {
            search = client.search(s -> s
                            .index(EsUtil.index)
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
                            .index(EsUtil.index)
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
                ResultEntry source = hit.source();
                if(source != null) {
                    ret.add(source.getTitle());
                }
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
                .index(EsUtil.index)
                .build();

        CreateIndexResponse response = null;
        try {
            response = client.indices().create(createIndexRequest);
        } catch (Exception e) {
            // 创建索引引发的异常
        }
        if(response != null) {
            return Objects.requireNonNullElse(response.acknowledged(), false);
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteIndex() {
        DeleteIndexResponse deleteIndexResponse = null;
        try {
            deleteIndexResponse = client.indices().delete(d -> d
                    .index(EsUtil.index));
        } catch (Exception e) {
            // 删除索引引发的所有异常
        }
        if(deleteIndexResponse == null) {
            return false;
        } else {
            return deleteIndexResponse.acknowledged();
        }
    }

    @Override
    public void close() {
        EsUtil.release();
    }

    private List<ResultEntry> dealSearchResponse(SearchResponse<ResultEntry> searchResponse) {
        if(searchResponse != null) {
            List<ResultEntry> resultEntryList = new ArrayList<>();
            List<Hit<ResultEntry>> hits = searchResponse.hits().hits();
            if (searchResponse.hits().total() != null) {
                searchCount = searchResponse.hits().total().value();
            }
            for(Hit<ResultEntry> hit : hits) {
                ResultEntry source = hit.source();
                Map<String, List<String>> highlight = hit.highlight();
                if(highlight != null && source != null) {
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