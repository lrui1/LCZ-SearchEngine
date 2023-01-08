import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import utils.EsUtil;


public class DeleteIndex {
    public static void main(String[] args) {
        ElasticsearchClient client = EsUtil.getConnect();
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
        EsUtil.release();
    }
}
