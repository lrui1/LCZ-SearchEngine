package crawl;
import bean.ResultEntry;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * @author xiudian
 */
public class GetJmuCecMessage extends GetMessageFunctions {
    public static List<ResultEntry> backJmuCecMessage() throws IOException {
//爬取class类部分
        Document document = Jsoup.connect("http://cec.jmu.edu.cn/").get();
        List<ResultEntry> printList = new ArrayList<>();
        Set<String> classSet = new HashSet<String>();
        Elements div = document.getElementsByTag("div");
        for (Element element : div) {
            String aClass = element.attr("class");
            if (aClass != ""){ classSet.add(aClass);}
        }
        List<ResultEntry> message = new ArrayList<>();
        List<ResultEntry> nestMessage = new ArrayList<>();
        for (String s : classSet) {
            message = getMessage(document, "div." + s + ">a");
            for (ResultEntry resultEntry : message) {
                Document connect = Jsoup.connect(resultEntry.getUrl()).get();
                nestMessage = getNestMessage(connect, "div.er_right_new>ul>li", "..", "http://cec.jmu.edu.cn");
                addToPrintList(printList, nestMessage);
            }
        }
        //测试爬取计算机工程主页的各个id的值，couter内容的爬取
        Elements span = document.getElementsByTag("span");
        for (Element element : span) {
            Elements a = element.getElementsByTag("a");
            String href = "http://cec.jmu.edu.cn/" + a.attr("href");
            Document document1 = Jsoup.connect(href).get();
            //将每个部分的第一页数据导入数组，每个部分可以获得此模块总共多少页，从而实现每页都访问
            List<ResultEntry> list = getNestMessage(document1, "div.er_right_new>ul>li", "..", "http://cec.jmu.edu.cn");
            addToPrintList(printList, list);
            Elements select = document1.select("div.page");
            for (Element element1 : select) {
                //提供翻页的第一页
                String pageTurnHref = element1.getElementsByTag("a").attr("href");
                List<ResultEntry> pageTurnMsg = getPageTurnMsg(pageTurnHref);
                addToPrintList(printList, pageTurnMsg);
            }
        }
        return printList;
    }
}

