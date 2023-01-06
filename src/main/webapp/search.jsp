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
    <title>search</title>
</head>
<body>

<%
    request.setCharacterEncoding("utf-8");
    String inputText = request.getParameter("inputText");
    List<ResultEntry> search1;
    if(inputText == null) {
        search1 = new ArrayList<>();
    }
    else {
        Search search = new ESearch();
        search1 = search.search(inputText);
    }
%>

<script src="js/my-js.js"></script>

<div class="container-fluid mt-4">
    <div id="wrapper" class="">
        <form action="search.jsp" method="get">
            <div class="input-group w-50">
                <img src="img/small_logo.ico" alt="logo" width="50" height="35">
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
                out.println("<small id=\"searchNumTool\">"+"开架大飞机为您搜到"+search1.size()+"条记录"+"</small>");
            %>
        </div>
        <div class="col">
            <span id="dateTool" class="dropdown">
            <button type="button" class="btn btn-primary btn-sm dropdown-toggle" data-bs-toggle="dropdown">
                时间不限
            </button>
            <ul class="dropdown-menu">
                <li><a class="dropdown-item" href="#">链接 1</a></li>
                <li><a class="dropdown-item" href="#">链接 2</a></li>
                <li><a class="dropdown-item" href="#">链接 3</a></li>
            </ul>
            </span>
        </div>
    </div>
</div>

<div class="container">
    <%
        for(ResultEntry resultEntry : search1) {
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
    %>
</div>

<div class="container">
    <ul class="pagination">
        <li class="page-item"><a class="page-link" href="#">上一页</a></li>
        <li class="page-item"><a class="page-link" href="#">1</a></li>
        <li class="page-item active"><a class="page-link" href="#">2</a></li>
        <li class="page-item"><a class="page-link" href="#">3</a></li>
        <li class="page-item"><a class="page-link" href="#">下一页</a></li>
    </ul>
</div>

<script>
<%--    给input部件增加初始值--%>
    $(".search-input").val(getQueryVariable("inputText"));

    $(function () {
        $("#datepicker").datepicker();
    })
</script>

<script src="bootstrap5/js/bootstrap.bundle.min.js" ></script>
</body>
</html>
