<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script src="/js/plugins/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script>
        $(function () {
            //处理开始时间
            $("#beginDate").click(function () {
                WdatePicker({
                    readOnly: true,
                    maxDate: new Date()
                });
            });
            //处理开始时间
            $("#endDate").click(function () {
                WdatePicker({
                    readOnly: true,
                    minDate: $("#beginDate").val(),
                    maxDate: new Date()
                });
            });

            //审核功能
            $(".btn_audit").click(function () {
                var url = $(this).data("audit");
                showartDialog("确定要审核吗?",function () {
                    //发送ajax请求
                    $.get(url,function (data) {
                        showartDialog("审核成功",function () {
                            //刷新界面
                            location.reload();
                        })
                    },"json");
                },true);
            });
        });
    </script>
    <title>WMS-采购入库管理</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
</head>
<body>
<form id="searchForm" action="/stockIncomeBill/list.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        业务时间
                        <fmt:formatDate var="beginDate" value="${qo.beginDate}"/>
                        <input id="beginDate" type="text" class="ui_input_txt02"
                               value="${beginDate}" name="beginDate" /> -
                        <fmt:formatDate var="endDate" value="${qo.endDate}"/>
                        <input id="endDate" type="text" class="ui_input_txt02"
                               value="${endDate}" name="endDate" />
                        仓库
                        <select id="depotId" class="ui_select01" name="depotId">
                            <option value="-1">全部仓库</option>
                            <c:forEach var="item" items="${depots}">
                                <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                        状态
                        <select id="status" class="ui_select01" name="status">
                            <option value="-1">全部状态</option>
                            <option value="1">未审核</option>
                            <option value="2">已审核</option>
                        </select>
                    </div>
                    <script>
                        //回显 供应商 和 状态
                        $("#depotId option[value='${qo.depotId}']").prop("selected",true);
                        $("#status option[value='${qo.status}']").prop("selected",true);
                    </script>
                    <div id="box_bottom">
                        <input type="button" value="查询"
                               class="ui_input_btn01 btn_page"/>
                        <input type="button" value="新增"
                               class="ui_input_btn01 btn_input"
                               data-url="/stockIncomeBill/input.do"/>
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
                        <th>入库单号</th>
                        <th>业务时间</th>
                        <th>仓库</th>
                        <th>总数量</th>
                        <th>总金额</th>
                        <th>录入人</th>
                        <th>审核人</th>
                        <th>状态</th>
                        <th></th>
                    </tr>
                    <c:forEach var="item" items="${result.data}" varStatus="vs">
                        <tr>
                            <td><input type="checkbox" class="acb"/></td>
                            <td>${item.sn}</td>
                            <td><fmt:formatDate value="${item.vdate}" pattern="yyyy-MM-dd"/> </td>
                            <td>${item.depot.name}</td>
                            <td>${item.totalNumber}</td>
                            <td>${item.totalAmount}</td>
                            <td>${item.inputUser.name}</td>
                            <td>${item.auditor.name}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${item.status == 1}">
                                        <span style="color: green">未审核</span>
                                    </c:when>
                                    <c:when test="${item.status == 2}">
                                        <span style="color: red">已审核</span>
                                    </c:when>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${item.status == 1}">
                                        <a href="/stockIncomeBill/input.do?id=${item.id}">编辑</a>
                                        <a href="javascript:" class="btn_delete"
                                           data-url="/stockIncomeBill/delete.do?id=${item.id}">删除</a>
                                        <a href="javascript:" class="btn_audit"
                                           data-audit="/stockIncomeBill/audit.do?id=${item.id}">审核</a>
                                    </c:when>
                                    <c:when test="${item.status == 2}">
                                        <a href="/stockIncomeBill/view.do?id=${item.id}">查看</a>
                                    </c:when>
                                </c:choose>
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
