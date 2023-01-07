import ES.Search;
import ES.impl.ESearch;
import com.alibaba.fastjson.JSON;

import java.util.List;

public class SearchSuggestTest {
    public static void main(String[] args) {
        Search search = new ESearch();
        List<String> suggest = search.getSearchSuggest("计算机");
        String s = JSON.toJSONString(suggest);
        System.out.println(s);
        search.close();
    }
}
