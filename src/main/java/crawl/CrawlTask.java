package crawl;

import crawl.impl.Crawl;
import entry.ResultEntry;

import java.io.IOException;
import java.util.*;

public class CrawlTask{
    private final String URL;

    public CrawlTask(String URL) {
        this.URL = URL;
    }

    public Set<ResultEntry> crawl() {
        Set<ResultEntry> resultEntrySet = new HashSet<>();
        Set<String> urlSet = new HashSet<>();
        Queue<String> waitCrawlQueue = new ArrayDeque<>();
        waitCrawlQueue.add(URL);

        while (!waitCrawlQueue.isEmpty()) {
            try {
                String url = waitCrawlQueue.poll();
                if(urlSet.contains(url)) {
                    continue;
                }
                urlSet.add(url);
                // new record
//                Thread.sleep(1000);
                CrawlDao cr = new Crawl(url);
                String title = cr.crawlTitle();
                String text = cr.crawlText();
                ResultEntry resultEntry = new ResultEntry(url, title, text);
                resultEntrySet.add(resultEntry);
                System.out.println(resultEntry);
                // new waitCrawl
                List<String> links = cr.crawlLinks();
                for(String s : links) {
                    // 判断是计算机工程学院下的网站
                    if(s.contains("cec.jmu.edu.cn")) {
                        waitCrawlQueue.add(s);
                    }
                }
            }catch (IllegalArgumentException e) {
                // 该链接不是htm

                continue;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {

            }
        }

        return resultEntrySet;
    }
}
