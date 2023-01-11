/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import bean.ResultEntry;
import es.Search;
import es.impl.EsSearch;

import java.util.List;
import java.util.Scanner;

/**
 *
 * @author 123456
 */
public class Input {
    private static List<ResultEntry> results;
    private static String input;
    public static void read(String text){
        Search search = new EsSearch();
        results = search.search(text);
        search.close();
        input = text;

    }
    public static String getText(){

        return input;
    }

    public static List<ResultEntry> getResults() {
        return results;
    }
}
