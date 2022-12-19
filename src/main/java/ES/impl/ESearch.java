package ES.impl;

import ES.ESInt;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import entry.ResultEntry;
import utils.ESUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ESearch implements ESInt {
    ElasticsearchClient client;

    public ESearch(ElasticsearchClient client) {
        this.client = client;
    }

    @Override
    public IndexResponse add(ResultEntry entry) {
        IndexResponse response = null;
        try {
            response = client.index(i -> i
                    .index(ESUtil.index).document(entry));
        } catch (IOException e) {

        }
        return response;
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
    public void close() {
        ESUtil.release();
    }
}
