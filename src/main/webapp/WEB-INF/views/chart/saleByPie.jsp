<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
    <script src="/js/plugins/echart/echarts.min.js"></script>
</head>
<body>
<div id="main" style="width: 900px;height:400px;"></div>
<script type="text/javascript">
    //基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById("main"));

    //指定图表的配置项和数据
    var option = {
        title : {
            text: "${groupType}",
            subtext: "销售员",
            x:"center"
        },
        tooltip : {
            trigger: "item",
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: "vertical",
            left: "left",
            data: ${x}
        },
        series : [
            {
                name: "访问来源",
                type: "pie",
                radius : "60%",
                center: ["50%", "60%"],
                data: ${y},
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: "rgba(0, 0, 0, 0.5)"
                    }
                }
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
</script>
</body>
</html>
