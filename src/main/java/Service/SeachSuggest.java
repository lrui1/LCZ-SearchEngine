package Service;

import ES.Search;
import ES.impl.ESearch;
import com.alibaba.fastjson.JSON;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/SearchSuggest")
public class SeachSuggest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String input = request.getParameter("input");
        if(input != null) {
            Search search = new ESearch();
            List<String> searchSuggest = search.getSearchSuggest(input);
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println(JSON.toJSONString(searchSuggest));
            out.close();
            search.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
