<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>信息管理系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript"
            src="/js/plugins/jqueryform/jQueryForm.js"></script>
    <script type="text/javascript"
            src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script src="/js/plugins/artDialog/iframeTools.js"></script>
    <script src="/js/plugins/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script>
        $(function () {
            //设值整个界面为只读
            $("input").prop("readOnly",true);
            //跳转回到list界面
            $(".btn_btn").click(function () {
                location.href = "/stockOutcomeBill/list.do";
            });
        });
    </script>
</head>
<body>
<input type="hidden" name="id" value="${entity.id}">
<div id="container">
    <div id="nav_links">
        <span style="color: #1A5CC6;">出库单编辑</span>
        <div id="page_close">
            <a>
                <img src="/images/common/page_close.png" width="20"
                     height="20" style="vertical-align: text-top;"/>
            </a>
        </div>
    </div>
    <div class="ui_content">
        <table cellspacing="0" cellpadding="0" width="100%" align="left"
               border="0">
            <tr>
                <td class="ui_text_rt" width="140">出库单编码</td>
                <td class="ui_text_lt">
                    <input name="sn" value="${entity.sn}"
                           class="ui_input_txt02"/>
                </td>
            </tr>
            <tr>
                <td class="ui_text_rt" width="140">仓库</td>
                <td class="ui_text_lt">
                    <input name="depot.id" value="${entity.depot.name}"
                           class="ui_input_txt02"/>
                </td>
            </tr>
            <tr>
                <td class="ui_text_rt" width="140">仓库</td>
                <td class="ui_text_lt">
                    <input name="client.id" value="${entity.client.name}"
                           class="ui_input_txt02"/>
                </td>
            </tr>
            <tr>
                <td class="ui_text_rt" width="140">业务时间</td>
                <td class="ui_text_lt">
                    <fmt:formatDate var="vdate" value="${entity.vdate}"
                                    pattern="yyyy-MM-dd"/>
                    <input name="vdate" value="${vdate}"
                           class="ui_input_txt02 Wdate"/>
                </td>
            </tr>
            <tr>
                <td class="ui_text_rt" width="140">明细</td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <table class="edit_table" cellspacing="0" cellpadding="0"
                           border="0">
                        <thead>
                        <tr>
                            <th width="10"></th>
                            <th width="170">货品</th>
                            <th width="100">品牌</th>
                            <th width="80">价格</th>
                            <th width="80">数量</th>
                            <th width="100">金额小计</th>
                            <th width="180">备注</th>
                        </tr>
                        </thead>
                        <tbody id="edit_table_body">
                        <c:forEach var="item" items="${entity.items}">
                            <tr>
                                <td></td>
                                <td>
                                    <input disabled readonly
                                           class="ui_input_txt01"
                                           value="${item.product.name}"
                                           tag="name"/>
                                    <img src="/images/common/search.png"
                                         class="searchproduct"/>
                                    <input type="hidden"
                                           value="${item.product.id}"
                                           tag="pid"/>
                                </td>
                                <td><span
                                        tag="brand">${item.product.brandName}</span>
                                </td>
                                <td><input type="number"
                                           value="${item.salePrice}"
                                           tag="salePrice"
                                           class="ui_input_txt01"/></td>
                                <td><input type="number"
                                           value="${item.number}"
                                           tag="number"
                                           class="ui_input_txt01"/></td>
                                <td><span
                                        tag="amount">${item.amount}</span>
                                </td>
                                <td><input tag="remark"
                                           value="${item.remark}"
                                           class="ui_input_txt01"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td class="ui_text_lt">
                    &nbsp;<input type="button" value="返回主菜单"
                                 class="ui_input_btn01 btn_btn"/>
                </td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>