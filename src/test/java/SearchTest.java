import es.Search;
import es.impl.EsSearch;
import bean.ResultEntry;

import java.io.IOException;
import java.util.List;

public class SearchTest {
    public static void main(String[] args) throws IOException {
        Search search = new EsSearch();
        List<ResultEntry> results = search.search("计算机");
        System.out.println(results);

        search.close();
    }
}
