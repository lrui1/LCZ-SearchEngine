package crawl;

import java.util.List;

public interface CrawlDAO {
    List<String> crawlLinks();
    String crawlTitle();
    String crawlText();
}
