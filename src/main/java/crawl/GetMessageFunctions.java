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
public class GetMessageFunctions {
    public static List<ResultEntry> getMessage(Document connection, String selection) throws IOException {
        Set<ResultEntry> backSet=new HashSet<>();
        Elements select = connection.select(selection);
        for (Element element : select) {
            Elements a = element.getElementsByTag("a");
            ResultEntry e=new ResultEntry();
            String href = a.attr("href");
            e.setUrl("http://cec.jmu.edu.cn/"+href);
            String title = a.attr("title");e.setTitle(title);
            try {
                String text = Jsoup.connect(e.getUrl()).get().text();e.setText(text);
                String declearTime = getDeclearTime(Jsoup.connect(e.getUrl()).get());
                e.setDeclareTime(declearTime);
                backSet.add(e);
            }catch (Exception ex){
                continue;
            }
        }
        List<ResultEntry>backList=new ArrayList<>(backSet);
        return backList;
    }
    public  static List<ResultEntry> getNestMessage(Document connection, String selection, String target, String repleacement) throws IOException {
       //解析深层次的链接内容
        Set<ResultEntry>backSet=new HashSet<>();
        Elements select = connection.select(selection);
        for (Element element : select) {
            Elements a = element.getElementsByTag("a");
            ResultEntry e=new ResultEntry();
            String href = a.attr("href").replace(target,repleacement);
            e.setUrl(href);
            String title = a.attr("title");e.setTitle(title);
            try {
                String text = Jsoup.connect(e.getUrl()).get().text();
                String declearTime = getDeclearTime(Jsoup.connect(e.getUrl()).get());
                e.setDeclareTime(declearTime);
                int textBeginIndex = text.indexOf("正文");
                e.setText(text.substring(textBeginIndex+2));
                backSet.add(e);
                System.out.println(e.toString());
            }catch (Exception ex){
                continue;
            }
        }
        List<ResultEntry>backList=new ArrayList<>(backSet);
        return backList;
    }
    public static List<ResultEntry>getPageTurnMsg(String href) throws IOException {//获取全部翻页的内容
        List<ResultEntry>backList=new ArrayList<>();
        List<ResultEntry>list=new ArrayList<>();
        int index=href.indexOf(".htm");
        int number=Integer.parseInt(href.substring(5, index));
        //换页的前缀
        String prefix = href.substring(0, 5);
        String prefixHref;
        String xyxw="xyxw/";String tztg="tztg/";String xyfc="xyfc/";String xssw="xssw/";String xwtz="xwtz";
        if(prefix.equals(xyxw)) {
        prefixHref="http://cec.jmu.edu.cn/xwtz/";}
        else if (prefix.equals(tztg)){
            prefixHref="http://cec.jmu.edu.cn/bksjy/";}
        else if (prefix.equals(xyfc)){
            prefixHref="http://cec.jmu.edu.cn/xwtz/";}
        else if (prefix.equals(xssw)) {
            prefixHref = "http://cec.jmu.edu.cn/";
        } else{ prefixHref="http://cec.jmu.edu.cn/kyj/";}
        for (int i=number;i>=1;i--){
            Document document = Jsoup.connect(prefixHref + prefix + i + ".htm").get();
            if(prefix.equals(xssw)||prefix.equals(xwtz)) {
                list = getNestMessage(document, "div.er_right_new>ul>li", "..", "http://cec.jmu.edu.cn");
                addToPrintList(backList,list);
            }
            list=getNestMessage(document,"div.er_right_new>ul>li","../..","http://cec.jmu.edu.cn");
            addToPrintList(backList,list);
        }
        return backList;
    }
    public static String getDeclearTime(Document connection) {
        String text = connection.select("div.er_right_xnew_date").text();
        int indexOf = text.indexOf("时间：");
        int suffixNum=3;
        if (indexOf != -1) {return (text.substring(indexOf + suffixNum));}
        else {return null;}
    }
    public static List<ResultEntry>addToPrintList(List<ResultEntry>printList, List<ResultEntry>addList){
        for (ResultEntry resultEntry : addList) {
            printList.add(resultEntry);
        }
        return printList;
    }
}
