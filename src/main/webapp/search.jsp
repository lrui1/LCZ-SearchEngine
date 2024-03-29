<%@ page import="es.impl.EsSearch" %>
<%@ page import="es.Search" %>
<%@ page import="bean.ResultEntry" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: 开架大飞机
  Date: 2023/1/3
  Time: 15:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="bootstrap5/css/bootstrap.min.css" rel="stylesheet">
    <!--    引入jquery-ui 时，一定要先引入jquery-->
    <!--    静态引入失败，只能cdn引入 -->
    <script src="js/jquery-3.6.3.min.js"></script>
    <link rel="stylesheet" href="css/jquery-ui.min.css">
    <script src="js/jquery-ui.min.js"></script>
    <script src="js/jq-paginator.min.js"></script>
    <link rel="stylesheet" href="css/my_css.css">
    <link rel="shortcut icon" href="img/favicon.png">
    <title>LCZ-SEARCH</title>
</head>
<body>

<%
    /**
     * 接受表单请求, 分页请求，日期选择请求，
     * 未来将封装至servlet 返回json，通过setAttribute
     */
    request.setCharacterEncoding("utf-8");
    String inputText = request.getParameter("inputText");
    String page1 = request.getParameter("page");
    String beginDate = request.getParameter("beginDate");
    String endDate = request.getParameter("endDate");

    // 给currentPage, beginDate, endDate初始值
    if(page1 == null) {
        page1 = "1";
    }
    int currentPage = Integer.parseInt(page1);

    // 去后台搜索结果
    Search search = new EsSearch();
    List<ResultEntry> searchResult = new ArrayList<>();
    if(inputText != null) {
        if(beginDate == null && endDate == null) {
            searchResult = search.search(inputText, currentPage);
        } else if (endDate == null) {
            searchResult = search.search(inputText, currentPage, beginDate);
        }
    }
    long searchCount = search.getSearchCount();

%>

<script src="js/my-js.js"></script>

<div class="container-fluid mt-4">
    <div id="wrapper" class="">
        <form action="search.jsp" method="get">
            <div class="row">
                <div class="input-group col">
                    <a href="index.html"><img src="img/logo.png" alt="logo" width="50" height="45"></a>
                    <input type="search"
                           class="form-control search-input ms-3"
                           placeholder="Enter content"
                           name="inputText">
                    <button type="submit" class="btn btn-primary">搜索一下</button>
                </div>
                <div class="col-lg-6"></div>
            </div>
        </form>
    </div>
</div>

<div class="container mt-1">
    <div id="tool" class="row">
        <div class="col-9 col-lg-5">
            <%
                out.println("<small id=\"searchNumTool\">"+"开架大飞机为您搜到"+searchCount+"条记录"+"</small>");
            %>
        </div>
        <div class="col">
            <span id="dateTool" class="dropdown">
            <button type="button" id="drop-button1" class="btn btn-primary btn-sm dropdown-toggle" data-bs-toggle="dropdown">
                时间不限
            </button>
            <ul class="dropdown-menu">
                <li><a id="range-all" class="dropdown-item" href="#">时间不限</a></li>
                <li><a id="range-week" class="dropdown-item" href="#">一周内</a></li>
                <li><a id="range-month" class="dropdown-item" href="#">一月内</a></li>
                <li><a id="range-year" class="dropdown-item" href="#">一年内</a></li>
            </ul>
            </span>
        </div>
    </div>
</div>

<div class="container">
    <div class="result">
        <%
            for(ResultEntry resultEntry : searchResult) {
                out.println("<div class=\"col col-lg-7 mt-3\">");
                out.println("<a class=\"address\" href="+resultEntry.getUrl()+" target=\"_blank\">"+resultEntry.getTitle()+"</a>");
                // 获取content需要输出的区间
                int front = resultEntry.getText().indexOf("<span");
                int tail = resultEntry.getText().lastIndexOf("span>");
                front -= 10; tail += 25;
                if(front < 0) {
                    front = 0;
                }
                if(tail > resultEntry.getText().length() - 1) {
                    tail = resultEntry.getText().length() - 1;
                    do {
                        if(!"\"".equals(resultEntry.getText().charAt(tail))) {
                            tail++;
                            break;
                        }
                        tail--;
                    }while (tail > 0);
                }
                String content = resultEntry.getText().substring(front, tail);
                out.println("<div class=\"content\">"+content+"</div>");
                out.println("<a href="+resultEntry.getUrl()+" style=\"font-size: small; color: gray\">"+resultEntry.getUrl()+"</a>");
                out.println("</div>");
            }
        %>
    </div>
</div>

<div class="container">

</div>

<div class="container mt-5">
    <small id="my-pagination-text">当前第1页</small>
    <ul id="my-pagination" class="pagination">

    </ul>
</div>


<script>
    /**
     * 分页功能
     */
    $("#my-pagination").jqPaginator({
        totalPages: <%=searchCount/10+1%>,
        visiblePages: 10,
        currentPage: <%=currentPage%>,
        first: '<li class="first page-item"><a class="page-link" href="javascript:;">首页</a></li>',
        prev: '<li class="prev page-item"><a class="page-link" href="javascript:;">上一页</a></li>',
        next: '<li class="next page-item"><a class="page-link" href="javascript:;">下一页</a></li>',
        last: '<li class="last page-item"><a class="page-link" href="javascript:;">末页</a></li>',
        page: '<li class="page page-item"><a class="page-link" href="javascript:;">{{page}}</a></li>',
        onPageChange: function (num, type) {
            $('#my-pagination-text').html('当前第' + num + '页');
            if("change" == type) { // 换页触发的
                let inputText = getQueryVariable("inputText");
                let beginDate = getQueryVariable("beginDate");
                // console.log("search.jsp?inputText="+inputText+"&page="+num);
                if(beginDate != "") {
                    window.location.href = "search.jsp?inputText="+inputText+"&page="+num+"&beginDate="+beginDate;
                } else {
                    window.location.href = "search.jsp?inputText="+inputText+"&page="+num;
                }
            }
        }
    });
</script>
<script src="js/my-js.js"></script>
<script src="bootstrap5/js/bootstrap.bundle.min.js" ></script>
</body>
</html>

<%
    /**
     * 释放资源
     */
    search.close();
%>