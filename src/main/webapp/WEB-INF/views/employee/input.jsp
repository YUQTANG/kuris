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
    <script type="text/javascript" src="/js/plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="/js/plugins/validate/messages_cn.js"></script>
    <script type="text/javascript" src="/js/plugins/jqueryform/jQueryForm.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script>
        $(function () {
            $("#editForm").validate({
                rules:{
                    name: {
                        required:true,
                        minlength: 2
                    },
                    password: {
                        required: true
                    },
                    repassword: {
                        equalTo:"#pwd"
                    },
                    email:{
                        required:true,
                        email:true
                    },
                    age:{
                        digits:true,
                        range:[16,65]
                    }
                }
            });

            //点击事件 触发 提交表单
            $(".btn_btn").click(function () {
                //把表单方式改成ajax提交
                $("#editForm").ajaxSubmit(function (data) {
                    showartDialog("操作成功",function () {
                        //资源路径要写死 无法从事件源获取
                        location.href = "/employee/list.do";
                    },true);
                });
            });

            //全部右移
            $("#selectAll").click(function () {
                $(".all_option option").appendTo(".selected_option");
            });
            //选择右移
            $("#select").click(function () {
                $(".all_option option:selected").appendTo(".selected_option");
            });
            //全部左移
            $("#deselectAll").click(function () {
                $(".selected_option option").appendTo(".all_option");
            });
            //选择左移
            $("#deselect").click(function () {
                $(".selected_option option:selected").appendTo(".all_option");
            });

            //去重
            //右边存在的
            var arr = $.map($(".selected_option option"),function (ele) {
                return ele.value;
            });
            //迭代操作
            $.each(arr,function (index, ele) {
                $.each($(".all_option option"),function (i, e) {
                    if (e.value == ele){
                        $(e).remove();
                    }
                })
            });
        });
    </script>
</head>
<body>
<form id="editForm" action="/employee/saveOrUpdate.do" method="post">
    <input type="hidden" name="id" value="${entity.id}">
    <div id="container">
        <div id="nav_links">
            <span style="color: #1A5CC6;">员工编辑</span>
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
                    <td class="ui_text_rt" width="140">用户名</td>
                    <td class="ui_text_lt">
                        <input name="name" value="${entity.name}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <c:if test="${empty entity.id}">
                    <tr>
                        <td class="ui_text_rt" width="140">密码</td>
                        <td class="ui_text_lt">
                            <input type="password" name="password" id="pwd" class="ui_input_txt02"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="ui_text_rt" width="140">验证密码</td>
                        <td class="ui_text_lt">
                            <input name="repassword" type="password" class="ui_input_txt02" />
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td class="ui_text_rt" width="140">Email</td>
                    <td class="ui_text_lt">
                        <input name="email" value="${entity.email}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">年龄</td>
                    <td class="ui_text_lt">
                        <input name="age" value="${entity.age}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">所属部门</td>
                    <td class="ui_text_lt">
                        <select name="dept.id" class="ui_select03">
                            <c:forEach var="item" items="${depts}">
                                <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">超级管理员</td>
                    <td class="ui_text_lt">
                        <input id="admin" type="checkbox" name="admin" class="ui_checkbox01"
                                ${entity.admin?"checked":""}/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">角色</td>
                    <td class="ui_text_lt role">
                        <table>
                            <tr>
                                <td>
                                    <select multiple="true" class="ui_multiselect01 all_option">
                                        <c:forEach var="ele" items="${roles}">
                                            <option value="${ele.id}">${ele.name}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td align="center">
                                    <input type="button" id="select" value="-->" class="left2right"/><br/>
                                    <input type="button" id="selectAll" value="==>" class="left2right"/><br/>
                                    <input type="button" id="deselect" value="<--" class="left2right"/><br/>
                                    <input type="button" id="deselectAll" value="<==" class="left2right"/>
                                </td>
                                <td>
                                    <select name="roleIds" multiple="true" class="ui_multiselect01 selected_option">
                                        <c:forEach var="ele" items="${entity.roles}">
                                            <option value="${ele.id}">${ele.name}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
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