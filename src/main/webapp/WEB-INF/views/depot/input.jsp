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
            $(".btn_btn").click(function () {
                //把表单方式改成ajax提交
                $("#editForm").ajaxSubmit(function (data) {
                    showartDialog("操作成功",function () {
                        //资源路径要写死 无法从事件源获取
                        location.href = "/depot/list.do";
                    },true);
                });
            });
        });
    </script>
</head>
<body>
<form id="editForm" action="/depot/saveOrUpdate.do" method="post">
    <input type="hidden" name="id" value="${entity.id}">
    <div id="container">
        <div id="nav_links">
            <span style="color: #1A5CC6;">仓库编辑</span>
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
                    <td class="ui_text_rt" width="140">仓库名称</td>
                    <td class="ui_text_lt">
                        <input name="name" value="${entity.name}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">仓库地址</td>
                    <td class="ui_text_lt">
                        <input name="location" value="${entity.location}" class="ui_input_txt02"/>
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