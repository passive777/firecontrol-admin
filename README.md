智能安防管理系统 - PC端后台
 ============================  
#### 项目说明
>firecontrol

#### _2020年6月1日～5日参加广西鲲鹏大赛（训练营第一期）后，决定对项目进行重构：_
<ol>
    <li>
        <h4>平面图OBS上传下载（精确到管辖域、楼号、层号、房间号）</h4>
    </li>
    <li>
        <h4>pc端可视化渲染</h4>
    </li>
    <li>
        <h4>微信端安装点平面图导出添加点击事件</h4>
    </li>
    <li>
        <h4>华为云短信、语音通知</h4>
    </li>
    <li>
        <h4>数据库容灾备份</h4>
    </li>
    <li>
        <h4>欧拉ＯS服务器配置</h4>
    </li>
</ol>


## 部署流程

- 打包镜像
>docker  build . -t mikeyboom/firecontrol:v1.0.0

- 推送镜像
>docker push mikeyboom/firecontrol:v1.0.0

- 拉取镜像
>docker pull mikeyboom/firecontrol:v1.0.0

- 启动实例
>docker-compose -f up docker-compose.yaml -d