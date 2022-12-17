package crawl;

import crawl.impl.Crawl;
import entry.ResultEntry;

import java.io.IOException;
import java.util.List;

public class CrawlTask implements Runnable{
    private final String URL;

    public CrawlTask(String URL) {
        this.URL = URL;
    }

    @Override
    public void run() {
        try {
            Crawl cr = new Crawl(URL);
            String text = cr.crawlText();
            List<String> links = cr.crawlLinks();
            String title = cr.crawlTitle();
//            ResultEntry resultEntry = new ResultEntry()
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
