<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="ui_tb_h30">
    <div class="ui_flt" style="height: 30px; line-height: 30px;">
        共有
        <span class="ui_txt_bold04">${result.totalCount}</span>
        条记录，当前第
        <span class="ui_txt_bold04">${result.currentPage}/${result.endPage}</span>
        页
    </div>
    <div class="ui_frt">
        <input type="button" value="首页" class="ui_input_btn01 btn_page"
               data-page="1"/>
        <input type="button" value="上一页" class="ui_input_btn01 btn_page"
               data-page="${result.prevPage}"/>
        <input type="button" value="下一页" class="ui_input_btn01 btn_page"
               data-page="${result.nextPage}"/>
        <input type="button" value="尾页" class="ui_input_btn01 btn_page"
               data-page="${result.endPage}"/>

        <select name="pageSize" class="ui_select02 btn_pageSize">
            <option value="5">5</option>
            <option value="10">10</option>
            <option value="15">15</option>
            <option value="20">20</option>
        </select>
        转到第<input type="text" name="currentPage" value="${qo.currentPage}"
                  class="ui_input_txt01"/>页
        <input type="button" class="ui_input_btn01 btn_page" value="跳转"/>
        <script>
            $(function () {
                //改变数据容量提交表单
                $(".btn_pageSize").change(function () {
                    $("input[name='currentPage']").val(1);
                    //提交表单
                    $("#searchForm").submit();
                });

                //回显页面容量
                $(".btn_pageSize option[value='${qo.pageSize}']").prop("selected",true);
            });
        </script>
    </div>
</div>
</body>
</html>
