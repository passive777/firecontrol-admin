// $(function(){
// 		var worldMapContainer1 = document.getElementById('distribution_map');
// 		var myChart = echarts.init(worldMapContainer1);
// 		var option = {
// 			tooltip: {
// 				trigger: 'item'
// 			},
// 			legend: {
// 				orient: 'vertical',
// 				x: 'left',
// 				y: 'bottom',
// 				data: [
// 					'已安装设备'
// 				],
// 				textStyle: {
// 					color: '#ccc'
// 				}
// 			},
// 			visualMap: {
// 				min: 0,
// 				max: 2500,
// 				left: 'right',
// 				top: 'bottom',
// 				text: ['高', '低'], // 文本，默认为数值文本
// 				calculable: true,
// 				//		color: ['#26cfe4', '#f2b600', '#ec5845'],
// 				textStyle: {
// 					color: '#fff'
// 				}
// 			},
// 			series: [{
// 					name: '已安装设备',
// 					type: 'map',
// 					aspectScale: 0.75,
// 					zoom: 1.2,
// 					mapType: 'china',
// 					roam: false,
// 					label: {
//                         normal: {
//                             show: true,//显示省份标签
//                             textStyle:{color:"#c71585"}//省份标签字体颜色
//                         },
//                         emphasis: {//对应的鼠标悬浮效果
//                             show: true,
//                             textStyle:{color:"#800080"}
//                         }
// 					},
// 					itemStyle: {
//                         normal: {
//                             borderWidth: .5,//区域边框宽度
//                             borderColor: '#009fe8',//区域边框颜色
//                             areaColor:"#ffffff",//区域颜色
//                         },
//                         emphasis: {
//                             borderWidth: .5,
//                             borderColor: '#4b0082',
//                             areaColor:"#ffdead",
//                         }
//                     },
// 					data: function() {
// 						var serie = [];
// 						for(var i = 0; i < vm.map.length; i++) {
// 							var item = {
// 								name: vm.map[i].area,
// 								value: vm.map[i].cnt
// 							};
// 							serie.push(item);
// 						}
// 						return serie;
// 					}()
//
// 				}
// 			]
// 		};
//
// 		myChart.setOption(option);
//
// 		// 使用刚指定的配置项和数据显示图表。
// 		myChart.setOption(option);
// 		myChart.on('click', function (params) {//点击事件
// 		    if (params.componentType === 'series') {
// 		    }
// 		})
// 	}
// )

// $(function(){
//     initMap();
// })
// //地图界面高度设置
//
//
//
// // //加载地图
// function initMap(){
// // 百度地图API功能
//     var map = new BMap.Map("distribution_map");    // 创建Map实例
//     map.centerAndZoom(new BMap.Point(111.295292,23.513766), 11);  // 初始化地图,设置中心点坐标和地图级别
//     //添加地图类型控件
//     var size1 = new BMap.Size(10, 50);
//     map.addControl(new BMap.MapTypeControl({
//         offset: size1,
//         mapTypes:[
//             BMAP_NORMAL_MAP,
//             BMAP_HYBRID_MAP,
//
//         ]}));
//     // 编写自定义函数,创建标注
//     function addMarker(point){
//         var marker = new BMap.Marker(point);
//         map.addOverlay(marker);
//     }
//     // 随机向地图添加25个标注
//     var bounds = map.getBounds();
//     var sw = bounds.getSouthWest();
//     var ne = bounds.getNorthEast();
//     var lngSpan = Math.abs(sw.lng - ne.lng);
//     var latSpan = Math.abs(ne.lat - sw.lat);
//     // TODO:发送请求获取设备的坐标
//     for (var i = 0; i < 45; i ++) {
//         var point = new BMap.Point(sw.lng + lngSpan * (Math.random() * 0.7), ne.lat - latSpan * (Math.random() * 0.7));
//         addMarker(point);
//     };
//
//     map.setCurrentCity("梧州");          // 设置地图显示的城市 此项是必须设置的
//     map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
//     //设备地图颜色
//     var mapStyle={
//         style:"midnight"
//     };
//     map.setMapStyle(mapStyle);
//
//
//
//
//
// //加载城市控件
//     var size = new BMap.Size(10, 50);
//     map.addControl(new BMap.CityListControl({
//         anchor: BMAP_ANCHOR_TOP_LEFT,
//         offset: size,
//
//
//     }));
// }
//
