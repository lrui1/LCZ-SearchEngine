package ES;

import Bean.ResultEntry;

import java.io.Reader;
import java.util.List;

public interface Search {
    boolean newIndex(Reader reader);
    boolean deleteIndex();
    ResultEntry add(ResultEntry entry);
    List<ResultEntry> search(String searchText); //全文检索，根据text查找, 待优化
    List<String> getSearchSuggest(String prefix);

    void close();   // 关闭资源
}