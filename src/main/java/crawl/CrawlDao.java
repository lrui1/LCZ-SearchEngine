package crawl;

import java.util.Date;
import java.util.List;

public interface CrawlDao {
    List<String> crawlLinks();
    String crawlTitle();
    String crawlText();
    Date crwalDate();
}
