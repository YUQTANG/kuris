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
        title: {
            text: "${groupType}",
            subtext:"销售员",
            left: "center"
        },
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:["销售总额"],
            left: "left"
        },
        toolbox: {
            show : true,
            feature : {
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        xAxis: {
            data: ${x}
        },
        yAxis: {},
        series: [{
            name: "销售总额",
            type: "bar",
            data: ${y},
            markPoint : {
                data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            },
            markLine : {
                data : [
                    {type : 'average', name: '平均值'}
                ]
            }
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
</script>
</body>
</html>
