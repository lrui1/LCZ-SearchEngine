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
        String inputText = request.getParameter("inputText");
        System.out.println(inputText);
        request.setAttribute("String", inputText);
        response.setContentType("text/html;charset=utf-8");
        request.getRequestDispatcher("Servlet2").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
