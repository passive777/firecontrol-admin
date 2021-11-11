$(function() {
    var dom = document.getElementById("container2");
    var myChart = echarts.init(dom);
    var app = {};
    option = null;
    option = {
        
        legend: {
            x : 'center',
            y : 'bottom',
            itemWidth: 8,
            itemHeight: 8,
            textStyle:{//图例文字的样式
                color:'#fff',
                fontSize:12
            },
            data:['故障','正常','静音','低电量','低压','报警']
        },
        calculable : true,
        series : [
            {
                name:'面积模式',
                type:'pie',
                radius : [30, 100],
                center : ['50%', '45%'],
                roseType : 'area',
                data:[
                    {value:vm.online.v1, name:'故障',itemStyle:{normal:{color:'#ff7800'}}},
                    {value:vm.online.v2, name:'正常',itemStyle:{normal:{color:'#23eb6a'}}},
                    {value:vm.online.v3, name:'静音',itemStyle:{normal:{color:'#7627cb'}}},
                    {value:vm.online.v4, name:'低电量',itemStyle:{normal:{color:'#fffc00'}}},
                    {value:vm.online.v5, name:'低压',itemStyle:{normal:{color:'#46afdb'}}},
                    {value:vm.online.v6, name:'报警',itemStyle:{normal:{color:'#ff0000'}}}
                ]
            }
        ]
    };
    ;
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
});