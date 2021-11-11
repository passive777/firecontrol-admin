var scn_data = {
    alarm: {alarm: 10, fault: 10},
    dtu: {on: 110, off: 150},
    plc: {on: 10, off: 10},
    industy: {v1: 10, v2: 11, v3: 12, v3: 14, v4: 15, v5: 17, v6: 18},
    online: {v1: 10, v2: 11, v3: 12, v3: 14, v4: 15, v5: 17, v6: 18},
    almMsg: [{msg: "5月4日梧州学院实验楼3楼走廊烟雾报警"},
        {msg: "6月1日梧州学院实验楼1楼软件实验室烟雾报警"},
        {msg: "6月3日梧州学院实验楼3楼物联网实验室烟雾报警"},
        {msg: "6月6日梧州学院实验楼4楼软件开发中心走廊烟雾报警"},
        {msg: "5月4日实验楼3楼软件开发中心走廊烟雾报警"},
        {msg: "2020年5月4日梧州学院实验楼3楼软件开发中心走廊烟雾报警"},
        {msg: "2020年5月4日梧州学院实验楼3楼软件开发中心走廊烟雾报警"}
    ],
    msgCnt: [{msg: 100, alm: 20},
        {msg: 200, alm: 40},
        {msg: 300, alm: 50},
        {msg: 400, alm: 35},
        {msg: 400, alm: 40},
        {msg: 400, alm: 11},
        {msg: 400, alm: 66},
        {msg: 100, alm: 77},
        {msg: 200, alm: 88},
        {msg: 300, alm: 22},
        {msg: 400, alm: 99},
        {msg: 400, alm: 100},
        {msg: 400, alm: 111},
        {msg: 400, alm: 222},
        {msg: 100, alm: 333},
        {msg: 200, alm: 11},
        {msg: 300, alm: 33},
        {msg: 400, alm: 55},
        {msg: 400, alm: 77},
        {msg: 400, alm: 90}
    ],
    map: [{area: "山东", cnt: 20},
        {area: "浙江", cnt: 40},
        {area: "江苏", cnt: 50},
        {area: "辽宁", cnt: 50}
    ],
    factoryHeader: [
        {"categories": "区域"},
        {"categories": "位置"},
        {"categories": "报警时间"},
        {"categories": "离报警点的浓度"},
        {"categories": "报警状态"}
    ],
    factory: [
        {"company": "宝钢", "dtuCnt": 200, "plcCnt": 400, "dataCnt": 5000, "alarm": "无"},
        {"company": "造纸厂", "dtuCnt": 3000, "plcCnt": 2000, "dataCnt": 1000, "alarm": "无"},
        {"company": "锅炉厂", "dtuCnt": 1500, "plcCnt": 1000, "dataCnt": 500, "alarm": "无"},
        {"company": "锅炉二厂", "dtuCnt": 1500, "plcCnt": 300, "dataCnt": 1200, "alarm": "温度上限报警>120"},
        {"company": "锅炉三厂", "dtuCnt": 1000, "plcCnt": 800, "dataCnt": 200, "alarm": "无"},
        {"company": "锅炉三厂", "dtuCnt": 1000, "plcCnt": 800, "dataCnt": 200, "alarm": "无"},
        {"company": "锅炉三厂", "dtuCnt": 1000, "plcCnt": 800, "dataCnt": 200, "alarm": "无"},
        {"company": "锅炉三厂", "dtuCnt": 1000, "plcCnt": 800, "dataCnt": 200, "alarm": "无"},
        {"company": "锅炉三厂", "dtuCnt": 1000, "plcCnt": 800, "dataCnt": 200, "alarm": "无"},
        {"company": "锅炉三厂", "dtuCnt": 1000, "plcCnt": 800, "dataCnt": 200, "alarm": "无"},
        {"company": "锅炉三厂", "dtuCnt": 1000, "plcCnt": 800, "dataCnt": 200, "alarm": "无"}]
};
var prefix = "module/bigdata";//用于下面异步请求的url
var vm = new Vue({
    el: '#content',
    data: scn_data,
    beforeCreate() {
        $.ajax({
            data: {},
            url: "/module/bigdata/eq_on_off",
            type: "GET",
            dataType: "text",
            async: false,
            success: function (data) {
                let map = JSON.parse(data)
                console.log(map)
                scn_data.plc.on = map.equment[0].status
                scn_data.plc.off = 10
                scn_data.dtu.on = map.equment[1].status
                scn_data.dtu.off = 10
                //设备状态
                map.alarms.forEach((v)=>{
                    if (v.alarmStatus=="1")scn_data.online.v1++;
                        if (v.alarmStatus=="2")scn_data.online.v2++;
                            if (v.alarmStatus=="3")scn_data.online.v3++;
                                if (v.alarmStatus=="4")scn_data.online.v4++;
                                    if (v.alarmStatus=="5")scn_data.online.v5++;
                                        if (v.alarmStatus=="6")scn_data.online.v6++;
                })
                //
                scn_data.alarm.fault = map.alarms.length
                scn_data.alarm.alarm = map.alarmDisposes.length
                //地图描点
                // map.installs.forEach((v)=>{
                //     var point = new BMap.Point(v.longitude, v.latitude);
                //     addMarker(point);
                // })

                //预警信息
                // let tmp =  []
                // map.alarmDisposes.forEach((v)=>{
                //     console.log(v)
                //     tmp.push({"company": v.installName, "dtuCnt": v.installName, "plcCnt": v.installName, "dataCnt": v.installName, "alarm": v.installName})
                // })
                // scn_data.factory = tmp
                scn_data.factory = map.alarmDisposes
                console.log(scn_data.factory)

            },
            error: function (data) {
                alert("请求失败！");
            }
        });
        // this.details()
    }
})

$(function(){
    initMap();
})
//地图界面高度设置



// //加载地图
function initMap(){
// 百度地图API功能
    var map = new BMap.Map("distribution_map");    // 创建Map实例
    map.centerAndZoom(new BMap.Point(111.295292,23.513766), 11);  // 初始化地图,设置中心点坐标和地图级别
    //添加地图类型控件
    var size1 = new BMap.Size(10, 50);
    map.addControl(new BMap.MapTypeControl({
        offset: size1,
        mapTypes:[
            BMAP_NORMAL_MAP,
            BMAP_HYBRID_MAP,

        ]}));
    // 编写自定义函数,创建标注
    function addMarker(point){
        var marker = new BMap.Marker(point);
        map.addOverlay(marker);
    }
    // 随机向地图添加25个标注
    var bounds = map.getBounds();
    var sw = bounds.getSouthWest();
    var ne = bounds.getNorthEast();
    var lngSpan = Math.abs(sw.lng - ne.lng);
    var latSpan = Math.abs(ne.lat - sw.lat);
    //请求数据
    $.ajax({
        data: {},
        url: "/module/bigdata/installs",
        type: "GET",
        dataType: "text",
        async: false,
        success: function (data) {
            let tmp = JSON.parse(data)
            // console.log('data='+data)
            // //地图描点
            // for (var i = 0; i < installs.size; i ++) {
            //     var point = new BMap.Point(Number(installs[i].longitude),Number(installs[i].latitude));
            //     console.log(point)
            //     addMarker(point);
            // };

            tmp.installs.forEach((v)=>{
                var point = new BMap.Point(v.longitude, v.latitude);
                addMarker(point);
            })
        },
        error: function (data) {
            alert("请求失败！");
        }
    });
    // 临时测试数据
    for (var i = 0; i < 45; i ++) {
        var point = new BMap.Point(sw.lng + lngSpan * (Math.random() * 0.7), ne.lat - latSpan * (Math.random() * 0.7));
        addMarker(point);
    };

    map.setCurrentCity("梧州");          // 设置地图显示的城市 此项是必须设置的
    map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
    //设备地图颜色
    var mapStyle={
        style:"midnight"
    };
    map.setMapStyle(mapStyle);





//加载城市控件
    var size = new BMap.Size(10, 50);
    map.addControl(new BMap.CityListControl({
        anchor: BMAP_ANCHOR_TOP_LEFT,
        offset: size,


    }));
}

