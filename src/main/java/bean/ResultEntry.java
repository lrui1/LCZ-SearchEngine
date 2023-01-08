package bean;

import java.util.Objects;

/**
 * @author 开架大飞机
 * @description 爬虫爬取条目类
 * @date: 2022/12/17
 */
public class ResultEntry {

    private String url;
    private String title;
    private String text;
    private String declareTime;
    // 时间格式： xxxx-xx-xx
    /**
     * 需添加默认构造函数，否则Jackson会抛出异常，即无法正常完成反序列化
     */
    public ResultEntry() {
        super();
    }

    public ResultEntry(String url, String title, String text, String declareTime) {
        this.url = url;
        this.title = title;
        this.text = text;
        this.declareTime = declareTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDeclareTime() {
        return declareTime;
    }

    public void setDeclareTime(String declareTime) {
        this.declareTime = declareTime;
    }

    @Override
    public String toString() {
        return "ResultEntry{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", declareTime='" + declareTime + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResultEntry that = (ResultEntry) o;
        return Objects.equals(url, that.url) && Objects.equals(title, that.title) && Objects.equals(text, that.text) && Objects.equals(declareTime, that.declareTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, title, text, declareTime);
    }
}