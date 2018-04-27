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
    <script src="/js/plugins/artDialog/iframeTools.js"></script>
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
            
            $(".chart").click(function () {
                var serialize = $("#searchForm").serialize();
                var url = $(this).data("url");
                $.dialog.open(url + "?" + serialize,{
                    title:"选择窗口",
                    width:"90%",
                    height:"90%",
                    top:"50%",
                    left:"50%",
                    lock:"true",
                    resize:"true"
                });
            });
        });
    </script>
    <title>WMS-销售报表管理</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
</head>
<body>
<form id="searchForm" action="/chart/sale.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        业务时间
                        <fmt:formatDate var="beginDate" value="${qo.beginDate}"/>
                        <input id="beginDate" type="text" class="ui_input_txt01"
                               value="${beginDate}" name="beginDate" /> -
                        <fmt:formatDate var="endDate" value="${qo.endDate}"/>
                        <input id="endDate" type="text" class="ui_input_txt01"
                               value="${endDate}" name="endDate" />
                        商品名称/编码
                        <input type="text" class="ui_input_txt01"
                               value="${qo.keyword}" name="keyword" />
                        客户
                        <select id="clientId" class="ui_select01" name="clientId">
                            <option value="-1">全部客户</option>
                            <c:forEach var="item" items="${clients}">
                                <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                        品牌
                        <select id="brandId" class="ui_select01" name="brandId">
                            <option value="-1">全部品牌</option>
                            <c:forEach var="item" items="${brands}">
                                <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                        类型
                        <select id="groupType" class="ui_select01" name="groupType">
                            <option value="e.name">销售员</option>
                            <option value="p.name">商品名称</option>
                            <option value="p.brandName">商品品牌</option>
                            <option value="c.name">客户</option>
                            <option value="DATE_FORMAT(sa.vdate,'%Y-%m')">销售月份</option>
                            <option value="DATE_FORMAT(sa.vdate,'%Y-%m-%d')">销售日期</option>
                        </select>
                    </div>
                    <script>
                        //回显 供应商 和 品牌 类型
                        $("#clientId option[value='${qo.clientId}']").prop("selected",true);
                        $("#brandId option[value='${qo.brandId}']").prop("selected",true);
                        $("#groupType option[value=\"${qo.groupType}\"]").prop("selected",true);
                    </script>
                    <div id="box_bottom">
                        <input type="button" value="查询"
                               class="ui_input_btn01 btn_page"/>
                        <input type="button" value="柱状图" class="left2right chart"
                               data-url="/chart/saleByBar.do">
                        <input type="button" value="饼状图" class="left2right chart"
                               data-url="/chart/saleByPie.do">
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0"
                       width="100%" align="center" border="0">
                    <tr>
                        <th>分组类型</th>
                        <th>销售总数</th>
                        <th>销售总额</th>
                        <th>利润</th>
                    </tr>
                    <c:forEach var="item" items="${list}">
                        <tr>
                            <td>${item.groupType}</td>
                            <td>${item.totalNumber}</td>
                            <td>${item.totalAmount}</td>
                            <td>${item.profit}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</form>
</body>
</html>
