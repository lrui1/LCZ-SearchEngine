import bean.ResultEntry;
import es.Search;
import es.impl.EsSearch;

import java.util.List;

public class SearchByTime {
    public static void main(String[] args) {
        Search search = new EsSearch();
        List<ResultEntry> search1 = search.search("计算机", 1, "2022-05-01");
        System.out.println(search1);
        search.close();
    }
}
