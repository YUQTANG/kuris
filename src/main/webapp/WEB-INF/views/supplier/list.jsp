<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <title>WMS-供应商管理</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
</head>
<body>
<form id="searchForm" action="/supplier/list.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_bottom">
                        <input type="button" value="新增"
                               class="ui_input_btn01 btn_input"
                               data-url="/supplier/input.do"/>
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
                        <th>供应商名称</th>
                        <th>供应商电话</th>
                        <th>供应商地址</th>
                        <th></th>
                    </tr>
                    <c:forEach var="item" items="${result.data}" varStatus="vs">
                        <tr>
                            <td><input type="checkbox" class="acb"/></td>
                            <td>${vs.count}</td>
                            <td>${item.name}</td>
                            <td>${item.phone}</td>
                            <td>${item.address}</td>
                            <td>
                                <a href="/supplier/input.do?id=${item.id}">编辑</a>
                                <a href="javascript:" class="btn_delete"
                                        data-url="/supplier/delete.do?id=${item.id}">删除</a>
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
