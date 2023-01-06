import ES.Search;
import ES.impl.ESearch;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import utils.ESUtil;

public class ConnectTest {
    public static void main(String[] args) {
        Search search = new ESearch();
        search.close();
    }
}
