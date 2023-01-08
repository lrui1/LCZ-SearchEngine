package service.controler;

import bean.ResultEntry;
import crawl.getJmuCecMessage;
import es.Search;
import es.impl.EsSearch;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

/**
 * @author: lrui1
 * @description 初始化
 * @date: 2023/1/8
 */
public class Init {
    public static void main(String[] args) throws InterruptedException {
        Search search = new EsSearch();
        // 获取索引名
        String index = "link-repo2";
        // 删除索引库
        System.out.println("索引名为:"+index+", 正在删除原有的"+index);
        boolean deleteBool = search.deleteIndex();
        if(deleteBool) {
            System.out.println("删除"+index+"成功！");
        } else {
            System.out.println("删除"+index+"失败！");
            System.exit(0);
        }
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
                "      \"declareTime\": {\n" +
                "        \"type\": \"date\"\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}");
        boolean newBool = search.newIndex(reader);
        if(newBool) {
            System.out.println("新建成功!!");
        } else {
            System.out.println("新建失败!!");
            System.exit(0);
        }
        // 爬取数据
        System.out.println("正在爬取数据·······");
        Thread.sleep(1000);
        List<ResultEntry> printList = null;
        try {
            printList = getJmuCecMessage.backJmuCecMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(printList != null) {
            // 展示
            System.out.println("共爬取了"+printList.size()+"条数据");
            // 插入ES
            System.out.println("正在插入ES······");

            for(ResultEntry resultEntry : printList) {
                search.add(resultEntry);
            }
            // 返回提示成功
            System.out.println("插入ES成功！");
        }
        search.close();
    }
}
