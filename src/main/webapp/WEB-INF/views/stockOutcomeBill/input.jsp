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
    <script type="text/javascript" src="/js/plugins/jqueryform/jQueryForm.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script src="/js/plugins/artDialog/iframeTools.js"></script>
    <script src="/js/plugins/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script>
        $(function () {
            //点击事件 触发 提交表单
            $("#editForm").ajaxForm(function (data) {
                showartDialog("操作成功",function () {
                    //资源路径要写死 无法从事件源获取
                    location.href = "/stockOutcomeBill/list.do";
                },true);
            });

            $(".Wdate").click(function () {
                WdatePicker({
                    readOnly: true,
                    maxDate: new Date()
                });
            });

            //克隆明细 在tbody中追加一行
            $(".appendRow").click(function () {
                var tr = $("#edit_table_body tr:first").clone();
                //清空里面的数据
                tr.find("input").val("");
                tr.find("span").html("");
                //然后再tbody中追加一个tr
                tr.appendTo("#edit_table_body");
            });

            //统一绑定事件
            $(" #edit_table_body").on("click",".searchproduct",function () {
                var tr = $(this).closest("tr");
                $.dialog.open("/product/view.do",{
                    title:"选择窗口",
                    width:"90%",
                    height:"90%",
                    top:"50%",
                    left:"50%",
                    lock:"true",
                    resize:"true",
                    close: function () {
                        var data = $.dialog.data("data");
                        console.log(data);
                        if(data){
                            tr.find("[tag='name']").val(data.name);
                            tr.find("[tag='pid']").val(data.id);
                            tr.find("[tag='salePrice']").val(data.salePrice);
                            tr.find("[tag='number']").val(1);
                            tr.find("[tag='brand']").html(data.brandName);
                            tr.find("[tag='amount']").html(data.salePrice.toFixed(2));
                        }
                    }
                });
            }).on("blur","input[tag='salePrice'],input[tag='number']",function () {
                var tr = $(this).closest("tr");
                var salePrice = tr.find("[tag='salePrice']").val() || 0;
                var number = tr.find("[tag='number']").val() || 0;
                var amount = salePrice * number;
                tr.find("[tag='amount']").html(amount.toFixed(2));
            }).on("click",".removeItem",function () {
                var tr = $(this).closest("tr");
                //如果tbody中只剩下最后一个tr 只需要清空数据即可
                if ($("#edit_table_body tr").size() == 1) {
                    tr.find("input").val("");
                    tr.find("span").html("");
                    //结束
                    return;
                }
                //删除所在的行
                tr.remove();
            });

            $(".btn_btn").click(function () {
                //表单提交之前把明细的属性改变
                $.each($("#edit_table_body tr"),function (index, ele) {
                    $(ele).find("[tag='pid']").prop("name","items["+index+"].product.id");
                    $(ele).find("[tag='salePrice']").prop("name","items["+index+"].salePrice");
                    $(ele).find("[tag='number']").prop("name","items["+index+"].number");
                    $(ele).find("[tag='remark']").prop("name","items["+index+"].remark");
                });
                $("#editForm").submit();
            });
        });
    </script>
</head>
<body>
<form id="editForm" action="/stockOutcomeBill/saveOrUpdate.do" method="post">
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
                        <input name="sn" value="${entity.sn}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">仓库</td>
                    <td class="ui_text_lt">
                        <select id="depotId" name="depot.id" class="ui_select03">
                            <c:forEach var="item" items="${depots}">
                                <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">客户</td>
                    <td class="ui_text_lt">
                        <select id="clientId" name="client.id" class="ui_select03">
                            <c:forEach var="item" items="${clients}">
                                <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <script>
                    $("#depotId option[value='${entity.depot.id}']").prop("selected",true);
                    $("#clientId option[value='${entity.client.id}']").prop("selected",true);
                </script>
                <tr>
                    <td class="ui_text_rt" width="140">业务时间</td>
                    <td class="ui_text_lt">
                        <fmt:formatDate var="vdate" value="${entity.vdate}" pattern="yyyy-MM-dd"/>
                        <input name="vdate" value="${vdate}" class="ui_input_txt02 Wdate"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">明细</td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <input type="button" value="添加明细" class="ui_input_btn01 appendRow"/>
                        <table class="edit_table" cellspacing="0" cellpadding="0" border="0">
                            <thead>
                            <tr>
                                <th width="10"></th>
                                <th width="170">货品</th>
                                <th width="100">品牌</th>
                                <th width="80">价格</th>
                                <th width="80">数量</th>
                                <th width="100">金额小计</th>
                                <th width="180">备注</th>
                                <th width="120"></th>
                            </tr>
                            </thead>
                            <tbody id="edit_table_body">
                                <c:choose>
                                    <%--如果ID为空 则进入这个界面--%>
                                    <c:when test="${empty entity.id}">
                                        <tr>
                                            <td></td>
                                            <td>
                                                <input disabled readonly class="ui_input_txt01" tag="name"/>
                                                <img src="/images/common/search.png" class="searchproduct"/>
                                                <input type="hidden" tag="pid"/>
                                            </td>
                                            <td><span tag="brand"></span></td>
                                            <td><input type="number" tag="salePrice" class="ui_input_txt01"/></td>
                                            <td><input type="number" tag="number" class="ui_input_txt01"/></td>
                                            <td><span tag="amount"></span></td>
                                            <td><input tag="remark" class="ui_input_txt01"/></td>
                                            <td>
                                                <a href="javascript:;" class="removeItem">删除明细</a>
                                            </td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="item" items="${entity.items}">
                                            <tr>
                                                <td></td>
                                                <td>
                                                    <input disabled readonly class="ui_input_txt01" value="${item.product.name}" tag="name"/>
                                                    <img src="/images/common/search.png" class="searchproduct"/>
                                                    <input type="hidden" value="${item.product.id}" tag="pid"/>
                                                </td>
                                                <td><span tag="brand">${item.product.brandName}</span></td>
                                                <td><input type="number" value="${item.salePrice}" tag="salePrice" class="ui_input_txt01"/></td>
                                                <td><input type="number" value="${item.number}" tag="number" class="ui_input_txt01"/></td>
                                                <td><span tag="amount">${item.amount}</span></td>
                                                <td><input tag="remark" value="${item.remark}" class="ui_input_txt01"/></td>
                                                <td>
                                                    <a href="javascript:;" class="removeItem">删除明细</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
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