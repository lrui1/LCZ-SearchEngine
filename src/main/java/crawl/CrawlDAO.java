package crawl;

import java.util.List;
/**
 * @author 开架大飞机
 * @description 初始化后端数据
 * @date: 2023/1/6
 */
public interface CrawlDAO {
    /**
     * 获取网站的网址
     * @return 返回结果为字符串数组
     */
    List<String> crawlLinks();
    /**
     * 获取文章标题的字符串
     * @return   结果为字符串，类型为String
     */

    String crawlTitle();

    /**
     *获取文章内容
     * @return  文章内容
     */
    String crawlText();

    /**
     *获取发布时间
     * @return   发布时间
     */
    String declareTime();

}
