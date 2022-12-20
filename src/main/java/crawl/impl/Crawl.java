package crawl.impl;

import crawl.CrawlDao;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Crawl implements CrawlDao {
    private String URL; //爬虫的目标
    private Document doc;

    public Crawl(String URL) throws IOException {
        this.URL = URL;
        doc = Jsoup.connect(URL).get();
    }

    public List<String> crawlLinks() {
        List<String> ret = new ArrayList<>();
        Elements links = doc.select("a");
        for(Element link : links) {
            ret.add(link.attr("abs:href"));
        }
        return ret;
    }

    public String crawlTitle() {
        return doc.title();
    }

    public String crawlText() {
        StringBuilder sb = new StringBuilder();;
        Elements texts = doc.select("body").select("p");// body里的p
        for(Element text : texts) {
            sb.append(text.text());
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        // 从URL加载文档
        final String url = "http://cec.jmu.edu.cn/info/1048/5230.htm"; // 不能使用https 会报错
        CrawlDao cr;
        try {
            cr = new Crawl(url);
            String text = cr.crawlText();
            List<String> links = cr.crawlLinks();
            String title = cr.crawlTitle();
            System.out.println(cr.crawlText());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
