<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/plugins/jqueryform/jQueryForm.js"></script>
    <script src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script>
        $(function () {
            //点击事件 触发 提交表单
            $(".btn_reload").click(function () {
                showartDialog("加载权限需要耗费较长时间,确定要重新加载吗",function () {
                    //发送ajax请求
                    $.get("/permission/reload.do",function (data) {
                        if(data.success){
                            showartDialog("加载成功",function () {
                                location.reload();//刷新当前界面
                            });
                        }else {
                            location.href = "/nopermission.do";
                        }
                    });
                },true);
            });
        });
    </script>
    <title>WMS-权限管理</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
</head>
<body>
<form id="searchForm" action="/permission/list.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_bottom">
                        <input type="button" value="加载权限"
                               class="ui_input_btn01 btn_reload"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0"
                       width="100%" align="center" border="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all"/></th>
                        <th>编号</th>
                        <th>权限名称</th>
                        <th>权限编码</th>
                        <th></th>
                    </tr>
                    <c:forEach var="item" items="${result.data}" varStatus="vs">
                        <tr>
                            <td><input type="checkbox" class="acb"/></td>
                            <td>${vs.count}</td>
                            <td>${item.name}</td>
                            <td>${item.expression}</td>
                            <td>
                                <a href="javascript:" class="btn_delete"
                                   data-url="/permission/delete.do?id=${item.id}">删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <%--引入分页操作--%>
            <jsp:include page="/WEB-INF/views/common/page.jsp"/>
        </div>
    </div>
</form>
</body>
</html>