<%@ page import="ES.impl.ESearch" %>
<%@ page import="ES.Search" %>
<%@ page import="Bean.ResultEntry" %>
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
    <link href="https://code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css" rel="Stylesheet"></link>
    <script src="https://code.jquery.com/ui/1.13.2/jquery-ui.min.js" ></script>
    <script src="js/jq-paginator.min.js"></script>
    <title>search</title>
</head>
<body>

<%
    /**
     * 接受表单请求
     */
    request.setCharacterEncoding("utf-8");
    String inputText = request.getParameter("inputText");
    String currentPage = request.getParameter("page");
    if(currentPage == null) {
        currentPage = "1";
    }

    Search search = new ESearch();
    List<ResultEntry> searchResult = new ArrayList<>();

    if(inputText != null) {
        searchResult = search.search(inputText, Integer.parseInt(currentPage));
    }
    long searchCount = search.getSearchCount();

%>

<script src="js/my-js.js"></script>

<div class="container-fluid mt-4">
    <div id="wrapper" class="">
        <form action="search.jsp" method="get">
            <div class="input-group w-50">
                <img src="img/small_logo.png" alt="logo" width="50" height="35">
                <input type="search"
                       class="form-control search-input ms-3"
                       placeholder="Enter content"
                       name="inputText">
                <button type="submit" class="btn btn-primary">搜索一下</button>
            </div>
        </form>
    </div>
</div>

<div class="container mt-1">
    <div id="tool" class="row">
        <div class="col-5">
            <%
                out.println("<small id=\"searchNumTool\">"+"开架大飞机为您搜到"+searchCount+"条记录"+"</small>");
            %>
        </div>
        <div class="col">
            <span id="dateTool" class="dropdown">
            <button type="button" class="btn btn-primary btn-sm dropdown-toggle" data-bs-toggle="dropdown">
                时间不限
            </button>
            <ul class="dropdown-menu">
                <li><a class="dropdown-item" href="#">一周内</a></li>
                <li><a class="dropdown-item" href="#">一月内</a></li>
                <li><a class="dropdown-item" href="#">一年内</a></li>
            </ul>
            </span>
        </div>
    </div>
</div>

<div class="container">
    <%
        for(ResultEntry resultEntry : searchResult) {
            out.println("<a class=\"address\" href="+resultEntry.getUrl()+">"+resultEntry.getTitle()+"</a>");
            int index1 = resultEntry.getText().indexOf(inputText);
            int front = index1-30, behind = index1+30;
            if(front < 0)
                front = 0;
            if(behind > resultEntry.getText().length() - 1)
                behind = resultEntry.getText().length();
            String content = resultEntry.getText().substring(front, behind);
            out.println("<p>"+content+"</p>");
        }
//        search.close(); //释放资源
    %>
</div>

<div class="container">
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
                var inputText = getQueryVariable("inputText");
                console.log("search.jsp?inputText="+inputText+"&page="+num);
                window.location.href = "search.jsp?inputText="+inputText+"&page="+num;
            }
        }
    });
</script>

<script>
<%--    给input部件增加初始值--%>
    $(".search-input").val(getQueryVariable("inputText"));
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