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
import utils.ESUtil;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class ESearch implements Search {
    ElasticsearchClient client;

    public ESearch() {
        client = ESUtil.getConnect();
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
            return null;
        }

        List<ResultEntry> ret = new ArrayList<>();

        List<Hit<ResultEntry>> lists = response.hits().hits();
        for(Hit<ResultEntry> resultEntryHit : lists) {
            ret.add(resultEntryHit.source());
        }
        return ret;
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

        List<String> ret = new ArrayList<>();
        List<Hit<ResultEntry>> hits = search.hits().hits();
        for(Hit<ResultEntry> hit : hits) {
            ret.add(hit.source().getTitle());
        }
        return ret;
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
//        System.exit(0);//强制杀死进程
    }
}
