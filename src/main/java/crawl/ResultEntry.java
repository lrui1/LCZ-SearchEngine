package crawl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ResultEntry implements CrawlDao{
    private String title;
    private String declearTime;
    private String url;
    private String text;
    public String getDeclearTime() {
        return declearTime;
    }
    public void setDeclearTime(String declearTime) {
        this.declearTime = declearTime;
    }



    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Text{" +
                "title='" + title + '\'' +
                ", declearTime='" + declearTime + '\'' +
                ", url='" + url + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultEntry resultEntry1 = (ResultEntry) o;
        return Objects.equals(title, resultEntry1.title) && Objects.equals(url, resultEntry1.url) && Objects.equals(text, resultEntry1.text) && Objects.equals(declearTime, resultEntry1.declearTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, url, text, declearTime);
    }

    @Override
    public List<String> crawlLinks() {
        List<String>href=new ArrayList<>();
        href.add(this.url);
        return href;
    }

    @Override
    public String crawlTitle() {
        return this.title;
    }

    @Override
    public String crawlText() {
        return this.text;
    }

    @Override
    public Date crwalDate() {
        return null;
    }
}
