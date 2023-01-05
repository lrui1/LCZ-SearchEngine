package ES.impl;

import ES.Search;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.*;
import Bean.ResultEntry;
import utils.ESUtil;

import java.io.IOException;
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
//        SearchResponse<TestEntry> search = null;
//        try {
//            client.search(se -> se
//                            .index(ESUtil.index)
//                            .suggest(su -> su
//                                    .suggesters(key, sg -> {
//                                        sg.prefix(prefix);
//                                        sg.completion(co -> co
//                                                .field(field));
//                                        return sg;
//                                    }))
//                    , TestEntry.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

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

//        Reader queryJson = new StringReader("{\n" +
//                "  \"query\": {\n" +
//                "    \"match\": {\n" +
//                "      \"title\": "+prefix+"\n" +
//                "    }\n" +
//                "  }\n" +
//                "}");
//
//        SearchRequest request = new SearchRequest.Builder()
//                .withJson(queryJson)
//                .index(ESUtil.index)
//                .build();
//
//        try {
//            search = client.search(request, TestEntry.class);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        List<String> ret = new ArrayList<>();

        List<Hit<ResultEntry>> hits = search.hits().hits();
        for(Hit<ResultEntry> hit : hits) {
            ret.add(hit.source().getTitle());
        }
        return ret;
    }

    @Override
    public void close() {
        ESUtil.release();
    }
}
