<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <link href="/js/plugins/fancyBox/jquery.fancybox.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script src="/js/plugins/fancyBox/jquery.fancybox.js?v=2.1.5"></script>
    <script src="/js/plugins/fancyBox/jquery.fancybox.pack.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script>
        $(function () {
            //图片弹出
            $(".fancybox").fancybox();
        });
    </script>
    <title>WMS-商品管理</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
</head>
<body>
<form id="searchForm" action="/product/list.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        商品名称/编码
                        <input type="text" class="ui_input_txt02"
                               value="${qo.keyword}" name="keyword" />
                        商品品牌
                        <select id="brandId" class="ui_select01" name="brandId">
                            <option value="-1">全部品牌</option>
                            <c:forEach var="item" items="${brands}">
                                <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <script>
                        $("#brandId option[value='${qo.brandId}']").prop("selected",true);
                    </script>
                    <div id="box_bottom">
                        <input type="button" value="查询"
                               class="ui_input_btn01 btn_page"/>
                        <input type="button" value="新增"
                               class="ui_input_btn01 btn_input"
                               data-url="/product/input.do"/>
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
                        <th>商品图片</th>
                        <th>商品名称</th>
                        <th>商品编码</th>
                        <th>商品品牌</th>
                        <th>成本价</th>
                        <th>零售价</th>
                        <th></th>
                    </tr>
                    <c:forEach var="item" items="${result.data}" varStatus="vs">
                        <tr>
                            <td><input type="checkbox" class="acb"/></td>
                            <td>${vs.count}</td>
                            <td>
                                <a class="fancybox" href="${item.imagePath}" data-fancybox-group="gallery"
                                   title="${item.name}">
                                    <img src="${item.smallImagePath}" class="list_img">
                                </a>

                            </td>
                            <td>${item.name}</td>
                            <td>${item.sn}</td>
                            <td>${item.brandName}</td>
                            <td>${item.costPrice}</td>
                            <td>${item.salePrice}</td>
                            <td>
                                <a href="/product/input.do?id=${item.id}">编辑</a>
                                <a href="javascript:" class="btn_delete"
                                        data-url="/product/delete.do?id=${item.id}&imagePath=${item.imagePath}">删除</a>
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
