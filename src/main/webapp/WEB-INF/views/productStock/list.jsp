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
    <title>WMS-即时库存管理</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
</head>
<body>
<form id="searchForm" action="/productStock/list.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        商品名称/编码
                        <input type="text" class="ui_input_txt02"
                               value="${qo.keyword}" name="keyword" />
                        仓库
                        <select id="depotId" class="ui_select01" name="depotId">
                            <option value="-1">全部仓库</option>
                            <c:forEach var="item" items="${depots}">
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
                        库存阈值
                        <input type="number" class="ui_input_txt02"
                               value="${qo.limitNum}" name="limitNum" />
                    </div>
                    <script>
                        //回显 供应商 和 状态
                        $("#depotId option[value='${qo.depotId}']").prop("selected",true);
                        $("#brandId option[value='${qo.brandId}']").prop("selected",true);
                    </script>
                    <div id="box_bottom">
                        <input type="button" value="查询"
                               class="ui_input_btn01 btn_page"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0"
                       width="100%" align="center" border="0">
                    <tr>
                        <th>仓库</th>
                        <th>商品编码</th>
                        <th>商品名称</th>
                        <th>商品品牌</th>
                        <th>库存价格</th>
                        <th>库存数量</th>
                        <th>总价值</th>
                    </tr>
                    <c:forEach var="item" items="${result.data}">
                        <tr>
                            <td>${item.d_name}</td>
                            <td>${item.p_sn}</td>
                            <td>${item.p_name}</td>
                            <td>${item.p_brandName}</td>
                            <td>${item.price}</td>
                            <td>${item.storeNumber}</td>
                            <td>${item.amount}</td>
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
