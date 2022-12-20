package org.example;

import ES.Search;
import ES.impl.ESearch;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import crawl.CrawlTask;
import entry.ResultEntry;
import utils.ESUtil;

import java.util.*;

public class InitUtil {

    public static void InitSQL() {
        // 将 url, title, text 从一个网站上扒下来
        final String URL = "http://cec.jmu.edu.cn/";
        CrawlTask crawlTask = new CrawlTask(URL);
        Set<ResultEntry> resultEntrySet = crawlTask.crawl();
        System.out.println("一共爬取了"+resultEntrySet.size()+"条数据");
        // 将resultEntrySet -> Elasticsearch
        System.out.println("正在将数据装填至数据库······");
        ElasticsearchClient client = ESUtil.getConnect();
        Search search = new ESearch();
        for(ResultEntry resultEntry : resultEntrySet) {
            search.add(resultEntry);
        }
        search.close();
        System.out.println("装填完毕!");
        System.exit(0); // 不知道为啥子jvm不退出，反正先杀了，摆烂
    }

    public static void main(String[] args) {
//        InitUtil.InitSQL();
        Search search = new ESearch();
        String text = "篮球赛";
        List<ResultEntry> searchResult1 = search.search(text);
        for(ResultEntry resultEntry : searchResult1) {
            System.out.println(resultEntry);
        }
        search.close();
    }
}