import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import utils.ESUtil;

import java.io.IOException;

public class CreateIndexTest {
    public static void main(String[] args) throws IOException {
        ElasticsearchClient client = ESUtil.getConnect();
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
