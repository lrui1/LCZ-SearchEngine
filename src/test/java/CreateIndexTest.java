import ES.Search;
import ES.impl.ESearch;

import java.io.IOException;

public class CreateIndexTest {
    public static void main(String[] args) throws IOException {
        Search search = new ESearch();

        search.close();
    }
}
