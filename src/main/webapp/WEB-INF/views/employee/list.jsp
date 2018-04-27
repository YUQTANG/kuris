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
    <title>WMS-员工管理</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
</head>
<body>
<form id="searchForm" action="/employee/list.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        姓名/邮箱
                        <input type="text" class="ui_input_txt02"
                                    value="${qo.keyword}" name="keyword" />
                        所属部门
                        <select id="deptId" class="ui_select01" name="deptId">
                            <option value="-1">所有部门</option>
                            <c:forEach var="item" items="${depts}">
                                <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div id="box_bottom">
                        <input type="button" value="查询" class="ui_input_btn01 btn_page"/>
                        <input type="button" value="新增" class="ui_input_btn01 btn_input"
                               data-url="/employee/input.do"/>
                    </div>
                    <script>
                        $(function () {
                            //部门回显数据
                            $("#deptId option[value='${qo.deptId}']").prop("selected",true);
                        });
                    </script>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0"
                       width="100%" align="center" border="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all" /></th>
                        <th>编号</th>
                        <th>用户名</th>
                        <th>EMAIL</th>
                        <th>年龄</th>
                        <th>所属部门</th>
                        <th></th>
                    </tr>
                    <c:forEach var="item" items="${result.data}" varStatus="vs">
                        <tr>
                            <td><input type="checkbox" class="acb" /></td>
                            <td>${vs.count}</td>
                            <td>${item.name}</td>
                            <td>${item.email}</td>
                            <td>${item.age}</td>
                            <td>${item.dept.name}</td>
                            <td>
                                <a href="/employee/input.do?id=${item.id}">编辑</a>
                                <a href="javascript:" class="btn_delete"
                                   data-url="/employee/delete.do?id=${item.id}">删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <jsp:include page="/WEB-INF/views/common/page.jsp"/>
        </div>
    </div>
</form>
</body>
</html>
