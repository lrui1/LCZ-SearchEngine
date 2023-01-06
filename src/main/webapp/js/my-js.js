/**
 * 根据变量名获取匹配值
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