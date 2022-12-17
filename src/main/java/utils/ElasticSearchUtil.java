package utils;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import java.io.IOException;

public class ElasticSearchUtil {
//    private static final String
    public static void main(String[] args) throws IOException {
        RestClient restClient = RestClient.builder(
                new HttpHost("localhost", 9200)).build();
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());
        ElasticsearchClient client = new ElasticsearchClient(transport);

        CreateIndexResponse createIndexResponse = client.indices().create(
                c ->
                        c.index("javaboy_book")
                                .settings(s ->
                                        s.numberOfShards("3")
                                                .numberOfReplicas("1"))
                                .mappings(m->
                                        m.properties("name", p -> p.text(f -> f.analyzer("ik_max_word")))
                                                .properties("birthday", p->p.date(d->d.format("yyyy-MM-dd"))))
                                .aliases("book_alias", f->f.isWriteIndex(true)));
        System.out.println("CreateIndexResponse.acknowledged() = "+createIndexResponse.acknowledged());
        System.out.println("createResponse.index() = " + createIndexResponse.index());
        System.out.println("createResponse.shardsAcknowledged() = " + createIndexResponse.shardsAcknowledged());
    }
}

