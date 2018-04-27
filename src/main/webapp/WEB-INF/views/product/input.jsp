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
    <script type="text/javascript" src="/js/plugins/jqueryform/jQueryForm.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script>
        $(function () {
            //点击事件 触发 提交表单

            $("#editForm").ajaxForm(function (data) {
                showartDialog("操作成功",function () {
                    //资源路径要写死 无法从事件源获取
                    location.href = "/product/list.do";
                },true);
            });

            $(".btn_btn").click(function () {
                //把表单方式改成ajax提交
                $("#editForm").submit();
            });

        });
    </script>
</head>
<body>
<form id="editForm" action="/product/saveOrUpdate.do" method="post" enctype="multipart/form-data">
    <input type="hidden" name="id" value="${entity.id}">
    <div id="container">
        <div id="nav_links">
            <span style="color: #1A5CC6;">商品编辑</span>
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
                    <td class="ui_text_rt" width="140">商品名称</td>
                    <td class="ui_text_lt">
                        <input name="name" value="${entity.name}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">商品编码</td>
                    <td class="ui_text_lt">
                        <input name="sn" value="${entity.sn}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">成本价</td>
                    <td class="ui_text_lt">
                        <input name="costPrice" value="${entity.costPrice}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">零售价</td>
                    <td class="ui_text_lt">
                        <input name="salePrice" value="${entity.salePrice}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">商品品牌</td>
                    <td class="ui_text_lt">
                        <select id="brandId" name="brandId" class="ui_select03">
                            <c:forEach var="ele" items="${brands}">
                                <option value="${ele.id}">${ele.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <script>
                    //品牌回显
                    $("#brandId option[value='${entity.brandId}']").prop("selected",true);
                </script>
                <tr>
                    <td class="ui_text_rt" width="140">商品图片</td>
                    <td class="ui_text_lt">
                        <input name="pic" type="file" class="ui_file"/>
                        <c:if test="${ not empty entity.imagePath}">
                            <img src="${entity.imagePath}" class="list_img_min">
                            <input type="hidden" name="imagePath" value="${entity.imagePath}">
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">商品简介</td>
                    <td class="ui_text_lt">
                        <textarea name="intro" class="ui_input_txtarea"></textarea>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td class="ui_text_lt">
                        &nbsp;<input type="button" value="确定保存"
                                     class="ui_input_btn01 btn_btn"/>
                        &nbsp;<input id="cancelbutton" type="button" value="重置"
                                     class="ui_input_btn01"/>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</form>
</body>
</html>