/**
 * 从url中获取 根据变量名获取匹配值
 */
function getQueryVariable(variable)
{
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable){return decodeURI(pair[1]);}
    }
    return("");
}

/**
 * 获取时间格式: xxxx-xx-xx
 */
function myGetDate(date) {
    let year=date.getFullYear();//年
    let month=(date.getMonth()+1)<10?"0"+(date.getMonth()+1):(date.getMonth()+1);//月
    let day=date.getDate()<10?"0"+date.getDate():date.getDate();//日
    return year+"-"+month+"-"+day;
}

/**
 * 给input部件添加初值
 */
$(function () {
    $(".search-input").val(getQueryVariable("inputText"));
})

/**
 * 自动填充——搜索提示
 */
//参考实例2——https://www.runoob.com/jqueryui/api-autocomplete.html#option-source
$(function () {
    $(".search-input").autocomplete({
        source: function( request, response ) {
            var input = $(".search-input").val();
            var source = "";
            $.ajax({
                type : "get",
                url : "SearchSuggest",
                datatype : "json",
                data: {"input": input},
                async : false,
                error : function() {
                    console.error("Load recommand data failed!");
                },
                success : function(data) {
                    source = data;
                }
            });
            response(JSON.parse(decodeURI(source)));
        }
    })
})

/**
 * 时间选择
 */
$(function () {
    $("#range-all").click(function () {
        let inputText = getQueryVariable("inputText");
        window.location.href = "search.jsp?inputText="+inputText;
    });
    $("#range-week").click(function () {
        // 获取当前选择的日期
        let myDate = new Date();
        myDate.setDate(myDate.getDate() - 7);
        let beginDate = myGetDate(myDate);
        // 跳转页面
        let inputText = getQueryVariable("inputText");
        window.location.href = "search.jsp?inputText="+inputText+"&beginDate="+beginDate;
    });
    $("#range-month").click(function () {
        // 获取当前选择的日期
        let myDate = new Date();
        myDate.setMonth(myDate.getMonth() - 1);
        let beginDate = myGetDate(myDate);
        // 跳转页面
        let inputText = getQueryVariable("inputText");
        window.location.href = "search.jsp?inputText="+inputText+"&beginDate="+beginDate;
    });
    $("#range-year").click(function () {
        // 获取当前选择的日期
        let myDate = new Date();
        myDate.setFullYear(myDate.getFullYear() - 1);
        let beginDate = myGetDate(myDate);
        // 跳转页面
        let inputText = getQueryVariable("inputText");
        window.location.href = "search.jsp?inputText="+inputText+"&beginDate="+beginDate;
    });
});

/**
 *  时间选择按钮内容的初始化
 */
$(function () {
    // 做初始化
    let beginDate = getQueryVariable("beginDate");
    if(beginDate == "") {
        $("#drop-button1").html("时间不限");
    } else {
        let dateWeek = new Date();
        let dateMonth = new Date();
        dateWeek.setDate(dateWeek.getDate() - 7);
        dateMonth.setMonth(dateMonth.getMonth() - 1);
        let dateWeekString = myGetDate(dateWeek);
        let dateMonthString = myGetDate(dateMonth);
        let buttonVal = "";
        if(dateWeekString == beginDate) {
            buttonVal = "一周内";
        } else if(dateMonthString == beginDate) {
            buttonVal = "一月内";
        } else {
            buttonVal = "一年内";
        }
        $("#drop-button1").html(buttonVal);
    }
})