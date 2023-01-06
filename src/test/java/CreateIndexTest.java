import ES.Search;
import ES.impl.ESearch;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import utils.ESUtil;

import java.io.IOException;

public class CreateIndexTest {
    public static void main(String[] args) throws IOException {
        Search search = new ESearch();

        search.close();
    }
}
