package service.servlet;

import es.Search;
import es.impl.EsSearch;
import com.alibaba.fastjson.JSON;

import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author: lrui1
 * @description 向前端提供搜索建议
 * @date: 2023/1/6
 */
@WebServlet("/SearchSuggest")
public class SearchSuggest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        String input = request.getParameter("input");
        if(input != null) {
            Search search = new EsSearch();
            List<String> searchSuggest = search.getSearchSuggest(input);
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println(JSON.toJSONString(searchSuggest));
            out.close();
            search.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doGet(request, response);
    }
}
