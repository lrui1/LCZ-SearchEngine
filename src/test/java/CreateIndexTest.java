import es.Search;
import es.impl.EsSearch;

import java.io.IOException;

public class CreateIndexTest {
    public static void main(String[] args) throws IOException {
        Search search = new EsSearch();

        search.close();
    }
}
