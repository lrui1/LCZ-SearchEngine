import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Suggestion;
import utils.EsUtil;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

public class SearchSuggestTest {
    public static void main(String[] args) throws IOException {
        ElasticsearchClient client = EsUtil.getConnect();
        SearchResponse<Music> response = null;
//        try {
//            response = client.search(s -> s
//                            .index("music")
//                            .suggest(b -> b
//                                    .suggesters("song-suggest", builder -> builder
//                                            .completion(builder1 -> builder1
//                                                    .field("suggest"))))
//                    , Music.class);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        Reader reader = new StringReader("{\n" +
                "  \"suggest\": {\n" +
                "    \"song-suggest\": {\n" +
                "      \"prefix\": \"zh\",\n" +
                "      \"completion\": {\n" +
                "          \"field\": \"suggest\"  \n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}");

        SearchRequest request = new SearchRequest.Builder()
                .withJson(reader)
                .index("music")
                        .build();

        response = client.search(request, Music.class);

        if(response != null) {
            Map<String, List<Suggestion<Music>>> suggest = response.suggest();
            List<Suggestion<Music>> suggestions = suggest.get("song-suggest");
            System.out.println(suggestions);
        }
        EsUtil.release();
    }
}
class Music {
    private String title;
    private String suggest;

    public Music() {
        super();
    }
}