import bean.ResultEntry;
import es.Search;
import es.impl.EsSearch;

import java.util.List;

public class search1 {
    public static void main(String[] args) {
        Search search = new EsSearch();
        List<ResultEntry> results = search.search("出彩",1);
        for(ResultEntry x:results) {
            System.out.println(x);
        }
        search.close();
    }
}
