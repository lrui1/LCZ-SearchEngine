import Bean.ResultEntry;
import ES.Search;
import ES.impl.ESearch;

import java.util.List;

public class SearchSuggestTest {
    public static void main(String[] args) {
        Search search = new ESearch();
        List<String> suggest = search.getSearchSuggest("西游记");
        System.out.println(suggest);
    }
}
