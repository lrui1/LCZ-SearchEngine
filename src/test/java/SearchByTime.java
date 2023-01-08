import Bean.ResultEntry;
import ES.Search;
import ES.impl.ESearch;

import java.util.List;

public class SearchByTime {
    public static void main(String[] args) {
        Search search = new ESearch();
        List<ResultEntry> search1 = search.search("计算机", 1, "2022-05-01");
        System.out.println(search1);
        search.close();
    }
}
