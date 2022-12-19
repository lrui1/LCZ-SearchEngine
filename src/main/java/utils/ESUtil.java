package utils;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

import java.io.IOException;

public class ESUtil {
    private static final String URL = "localhost";
    private static final int port = 9200;
    private static final String username = "elastic";
    private static final String password = "jmu_zuicaide";

    private static RestClient restClient;
    private static ElasticsearchTransport transport;

    public static final String index = "link-repo";
    public static ElasticsearchClient getConnect() {
        // 创建许可证
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                AuthScope.ANY, new UsernamePasswordCredentials(username, password));
        // 导入许可证
        RestClientBuilder builder = RestClient.builder(new HttpHost(URL, port))
                .setHttpClientConfigCallback(httpAsyncClientBuilder -> httpAsyncClientBuilder
                        .setDefaultCredentialsProvider(credentialsProvider));
        // 建立连接
        restClient = builder.build();
        transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());
        ElasticsearchClient client = new ElasticsearchClient(transport);
        return client;
    }

    public static void release() {
        try {
            if(transport != null) {
                transport.close();
            }
            if(restClient != null) {
                restClient.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

