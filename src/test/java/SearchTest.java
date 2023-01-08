import ES.Search;
import ES.impl.ESearch;
import Bean.ResultEntry;
import utils.ESUtil;

import java.io.IOException;
import java.util.List;

public class SearchTest {
    public static void main(String[] args) throws IOException {
        Search search = new ESearch();
        List<ResultEntry> results = search.search("计算机");
        System.out.println(results);

        search.close();
    }
}
