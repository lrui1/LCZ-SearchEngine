package crawl;

import java.util.List;

public interface CrawlInt {
    List<String> crawlLinks();
    String crawlTitle();
    String crawlText();
}
