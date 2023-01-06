package Service;

import Bean.ResultEntry;
import ES.Search;
import ES.impl.ESearch;
import crawl.getJmuCecMessage;

import utils.ESUtil;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Scanner;

public class Init {
    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        Search search = new ESearch();
        // 获取索引名
        System.out.print("请输入索引名:");
        String index = sc.next();
        ESUtil.index = index;
        System.out.println("索引名为:"+index+", 正在删除原有的"+index);
        // 删除索引库
        search.deleteIndex();
        System.out.println("删除"+index+"成功！");
        // 新建索引库
        System.out.println("正在新建·······");
        Reader reader = new StringReader("{\n" +
                "  \"mappings\": {\n" +
                "    \"properties\": {\n" +
                "      \"url\":{\"type\": \"keyword\"},\n" +
                "      \"title\":{\n" +
                "        \"type\": \"text\",\n" +
                "        \"analyzer\": \"ik_max_word\", \n" +
                "        \"fields\": {\n" +
                "          \"suggest\": {\n" +
                "            \"type\": \"completion\",\n" +
                "            \"analyzer\": \"ik_max_word\"\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      \"text\":{\n" +
                "        \"type\": \"text\",\n" +
                "        \"analyzer\": \"ik_max_word\"\n" +
                "      },\n" +
                "      \"date\": {\n" +
                "        \"type\": \"date\"\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}");
        search.newIndex(reader);
        System.out.println("新建成功!!");
        // 爬取数据
        System.out.println("正在爬取数据·······");
        Thread.sleep(1000);
        List<ResultEntry> printList = null;
        try {
            printList = getJmuCecMessage.backJmuCecMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 展示
        System.out.println("共爬取了"+printList.size()+"条数据");
        // 插入ES
        System.out.println("正在插入ES······");

        for(ResultEntry resultEntry : printList) {
            search.add(resultEntry);
        }
        // 返回提示成功
        System.out.println("插入ES成功！");
        search.close();
    }
}
