package ES;

import Bean.ResultEntry;

import java.util.List;

public interface Search {
    ResultEntry add(ResultEntry entry);
    List<ResultEntry> search(String searchText); //全文检索，根据text查找, 待优化
    void close();   // 关闭资源
}