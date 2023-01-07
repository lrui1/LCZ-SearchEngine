import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import utils.ESUtil;


public class DeleteIndex {
    public static void main(String[] args) {
        ElasticsearchClient client = ESUtil.getConnect();
        DeleteIndexResponse deleteIndexResponse = null;
        try {
            deleteIndexResponse = client.indices().delete(d -> d
                    .index("link-repo2"));
        } catch (Exception e) {

        }
        if(deleteIndexResponse == null) {
            System.out.println(false);
        } else {
            System.out.println(deleteIndexResponse.acknowledged());
        }
        ESUtil.release();
    }
}
