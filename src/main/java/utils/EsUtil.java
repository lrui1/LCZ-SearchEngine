package utils;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
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

/**
 * @author 开架大飞机
 * @description ES连接工具类
 * @date: 2022/12/19
 */
public class EsUtil {
    private static final String URL = "localhost";
    private static final int PORT = 9200;
    private static final String USERNAME = "elastic";
    private static final String PASSWORD = "jmu_zuicaide";

    private static RestClient restClient;
    private static ElasticsearchTransport transport;

    public static String index = "link-repo2";
    public static ElasticsearchClient getConnect() {
        // 创建许可证
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                AuthScope.ANY, new UsernamePasswordCredentials(USERNAME, PASSWORD));
        // 导入许可证
        RestClientBuilder builder = RestClient.builder(new HttpHost(URL, PORT))
                .setHttpClientConfigCallback(httpAsyncClientBuilder -> httpAsyncClientBuilder
                        .setDefaultCredentialsProvider(credentialsProvider));
        // 建立连接
        restClient = builder.build();
        transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
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