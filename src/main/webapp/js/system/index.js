$(function () {
    // 加载日期
    loadDate();
    // ======================================
    // 显示隐藏侧边栏
    $("#show_hide_btn").click(function () {
        switchSysBar();
    });
    // ======================================
});

//加载当前日期
function loadDate() {
    var time = new Date();
    var myYear = time.getFullYear();
    var myMonth = time.getMonth() + 1;
    var myDay = time.getDate();
    if (myMonth < 10) {
        myMonth = "0" + myMonth;
    }
    document.getElementById("day_day").innerHTML = myYear + "." + myMonth + "."
        + myDay;
}

$(function () {
    //点击按钮跳转模块
    $("#left_menu li").click(function () {
        //复位操作
        $.each($("#left_menu li"), function (index, ele) {
            //重置样式
            $(ele).removeClass("selected");
            //重置图片
            $(ele).children("img").prop("src", "/images/common/" + (index + 1) + ".jpg")
        });

        //拿到当前事件源index索引
        var index = $(this).index() + 1;
        //把当前面板的图片全部换成_hover
        $(this).children("img").prop("src", "/images/common/" + index + "_hover.jpg")
        //加上样式
        $(this).addClass("selected");
        //展示模块名字的图片
        $("#nav_module img").prop("src", "/images/common/module_" + index + ".png");

        //=================zTree节点树==================
        var sn = $(this).data("rootmenu");
        //调用函数
        loadMenu(sn)
    });

    //调用函数一开始就默认这个版块
    loadMenu("business");
});

//定义一个加载菜单的函数
function loadMenu(sn) {
    //加载菜单
    $.fn.zTree.init($("#dleft_tab1"), setting, nodes[sn]);
}

var setting = {
    //发生异步请求
    async:{
        enable:true,
        url:"/systemMenu/getMenusBySn.do",//发送请求资源路径
        autoParam:["sn=menuSn"]//发送请求参数
    },
    data: {
        //启动简单JSON格式
        simpleData: {
            enable: true
        }
    },
    //设置节点点击事件
    callback:{
        onClick:function (e,treeId,node) {
            //如果当前节点有父节点
            if(node.getParentNode()){
                var msg = "当前位置："+node.getParentNode().name+"&nbsp;>&nbsp;" + node.name;
                $("here_area").html(msg);
                //跳转界面
                $("#rightMain").prop("src","/"+node.action+".do");
            }
        }
    }
};

var nodes = {
    business: [
        {id: 2, pId: 0, name: "业务模块", sn: "business", isParent: true}
    ],
    systemManage: [
        {id: 1, pId: 0, name: "系统模块", sn: "system", isParent: true}
    ],
    charts: [
        {id: 3, pId: 0, name: "报表模块", sn: "chart",isParent: true}
    ]
};

/**
 * 隐藏或者显示侧边栏
 *
 */
function switchSysBar(flag) {
    var side = $('#side');
    var left_menu_cnt = $('#left_menu_cnt');
    if (flag == true) { // flag==true
        left_menu_cnt.show(500, 'linear');
        side.css({
            width: '280px'
        });
        $('#top_nav').css({
            width: '77%',
            left: '304px'
        });
        $('#main').css({
            left: '280px'
        });
    } else {
        if (left_menu_cnt.is(":visible")) {
            left_menu_cnt.hide(10, 'linear');
            side.css({
                width: '60px'
            });
            $('#top_nav').css({
                width: '100%',
                left: '60px',
                'padding-left': '28px'
            });
            $('#main').css({
                left: '60px'
            });
            $("#show_hide_btn").find('img').attr('src',
                '/images/common/nav_show.png');
        } else {
            left_menu_cnt.show(500, 'linear');
            side.css({
                width: '280px'
            });
            $('#top_nav').css({
                width: '77%',
                left: '304px',
                'padding-left': '0px'
            });
            $('#main').css({
                left: '280px'
            });
            $("#show_hide_btn").find('img').attr('src',
                '/images/common/nav_hide.png');
        }
    }
}
