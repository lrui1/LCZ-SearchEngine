package Controler;

import Bean.ResultEntry;
import ES.Search;
import ES.impl.ESearch;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/servlet1")
public class WatchDog extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String text = request.getParameter("inputText");
        Search search = new ESearch();
        List<ResultEntry> searchResult = search.search(text);
        request.setAttribute("list", searchResult);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
