package crawl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class getMessageFunctions {
    public static List<ResultEntry> getMessage(Document connection, String selection) throws IOException {//解析表面一层的链接
        List<ResultEntry>backList=new ArrayList<>();
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
                e.setDeclearTime(declearTime);
                backList.add(e);
            }catch (Exception E){
                continue;
            }
        }
        return backList;
    }
    public  static List<ResultEntry> getNestMessage(Document connection, String selection, String target, String repleacement) throws IOException {
       //解析深层次的链接内容
        List<ResultEntry>backList=new ArrayList<>();
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
                e.setDeclearTime(declearTime);
                e.setText(text);
                backList.add(e);
            }catch (Exception E){
                continue;
            }
        }
        return backList;
    }

    public static List<ResultEntry>getPageTurnMsg(String href) throws IOException {//获取全部翻页的内容
        List<ResultEntry>backList=new ArrayList<>();
        List<ResultEntry>list=new ArrayList<>();
        int index=href.indexOf(".htm");
        int number=Integer.parseInt(href.substring(5, index));
        String prefix = href.substring(0, 5);//换页的前缀
        String prefixHref;
        if(prefix.equals("xyxw/"))
            prefixHref="http://cec.jmu.edu.cn/xwtz/";
        else if (prefix.equals("tztg/"))
            prefixHref="http://cec.jmu.edu.cn/bksjy/";
        else if (prefix.equals("xyfc/"))
            prefixHref="http://cec.jmu.edu.cn/xwtz/";
        else if (prefix.equals("xssw/")) {
            prefixHref = "http://cec.jmu.edu.cn/";
        } else prefixHref="http://cec.jmu.edu.cn/kyj/";
        for (int i=number;i>=1;i--){
            Document document = Jsoup.connect(prefixHref + prefix + i + ".htm").get();
            if(prefix.equals("xssw/")||prefix.equals("xwtz/")) {
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
        if (indexOf != -1) return (text.substring(indexOf + 3));
        else return null;
    }
    public static void print(List<ResultEntry>list)
    {
        for (ResultEntry resultEntry : list) {
            System.out.println(resultEntry.toString());
        }
    }
    public static List<ResultEntry>addToPrintList(List<ResultEntry>printList, List<ResultEntry>addList){
        for (ResultEntry resultEntry : addList) {
            printList.add(resultEntry);
        }
        return printList;
    }
}
