/** table鼠标悬停换色* */
$(function () {
    // 如果鼠标移到行上时，执行函数
    $(".table tr").mouseover(function () {
        $(this).css({
            background: "#CDDAEB"
        });
        $(this).children('td').each(function (index, ele) {
            $(ele).css({
                color: "#1D1E21"
            });
        });
    }).mouseout(function () {
        $(this).css({
            background: "#FFF"
        });
        $(this).children('td').each(function (index, ele) {
            $(ele).css({
                color: "#909090"
            });
        });
    });
});


//点击跳转页面
$(function () {
    $(".btn_input").click(function () {
        location.href = $(this).data("url");
    });
});

//翻页操作
$(function () {
    $(".btn_page").click(function () {
        var page = $(this).data("page") || $("input[name='currentPage']").val();
        $("input[name='currentPage']").val(page);
        //提交表单
        $("#searchForm").submit();
    });
});

//超级管理员
$(function () {
    //先克隆一份role菜单 找到最近tr 然后事先克隆一份出来
    var cp = $(".role").closest("tr").clone(true);
    //如果是超级管理员 就不需要显示角色挑选框
    $("#admin").click(function () {
        if (this.checked) {
            //删除离role 最近tr 的所有内容
            $(".role").closest("tr").remove();
        } else {
            //添加到离事件源最近tr 的后面添加克隆出来的内容
            $(this).closest("tr").after(cp);
        }
    });
    //如果事先就是超级管理员 就删除挑选角色框
    if ($("#admin").prop("checked")) {
        //删除离role 最近tr 的所有内容
        $(".role").closest("tr").remove();
    }
});

//显示对话框
function showartDialog(content, ok, cancel) {
    $.artDialog({
        title: "温馨提示",
        content: content,
        ok: ok || true,
        cancel: cancel,
        icon: "face-smile"
    });
}

$(function () {
    //删除功能
    $(".btn_delete").click(function () {
        var url = $(this).data("url");
        //发送请求
        showartDialog("您确定要删除吗?", function () {
            //发送ajax请求
            $.get(url, function (data) {
                console.log(data);
                showartDialog("删除成功",function () {
                    location.reload();
                })
            }, "json");
        }, true);
    });
});