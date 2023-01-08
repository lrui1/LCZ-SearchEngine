package es;

import bean.ResultEntry;

import java.io.Reader;
import java.util.List;

/**
 * @author 开架大飞机
 * @description 搜索引擎功能接口
 * @date: 2022/12/17
 */
public interface Search {
    /**
     * 获取搜素结果数量
     * @return 搜索结果数量，类型为long
     */
    long getSearchCount();

    /**
     * 新建索引
     * @param reader 一个Reader类型的字符串，ResultFul风格的索引类型信息
     * @return 新建成功返回true，失败返回false
     */
    boolean newIndex(Reader reader);

    /**
     * 删除索引，目标为ESUtil的index
     * @return 删除成功返回true，失败返回false
     */
    boolean deleteIndex();

    /**
     * 传入一个条目，将条目插入到对应的Search中
     * @param entry 被插入的条目
     * @return 插入成功返回该条目，否则返回null
     */
    ResultEntry add(ResultEntry entry);

    /**
     * 全文检索，根据text查找, 待优化
     * @param searchText 待搜索文本
     * @return 搜索结果放在List集合中
     */
    List<ResultEntry> search(String searchText);

    /**
     * 全文检索，先对searchText进行分词，结果按匹配度评分降序返回对应的页数内容
     * @param searchText 待搜索文本
     * @param page 页数
     * @return page页的searchText的搜索结果
     */
    List<ResultEntry> search(String searchText, int page);

    /**
     * 全文检索，对searchText分词，返回在最早发布日期后、对应的页数的结果
     * @param searchText 待搜索文本
     * @param page 页数
     * @param beginDate 最早发布日期
     * @return 对应的搜索结果
     */
    List<ResultEntry> search(String searchText, int page, String beginDate);

    /**
     * 全文检索，对searchText进行分词，返回在最早发布日期和最晚发布日期间对应页数的结果
     * @param searchText 待搜索文本
     * @param page 页数
     * @param beginDate 最早发布日期
     * @param endDate 最晚发布日期
     * @return 对应的搜索结果
     */
    List<ResultEntry> search(String searchText, int page, String beginDate, String endDate);

    /**
     * 获取搜索建议，传入前缀，返回匹配前缀的字符串集合
     * @param prefix 字符串前缀
     * @return 匹配的字符串集合
     */
    List<String> getSearchSuggest(String prefix);
    /**
     * 释放Search的资源
     */
    void close();   // 关闭资源
}