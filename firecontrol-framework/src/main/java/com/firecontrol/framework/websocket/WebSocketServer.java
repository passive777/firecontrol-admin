package com.firecontrol.framework.websocket;
import com.firecontrol.framework.web.service.TokenService;
import com.firecontrol.module.domain.Equipment;
import com.firecontrol.module.domain.Group;
import com.firecontrol.system.domain.SysUser;
import com.google.gson.Gson;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * webSocket即时通讯长连接，用于消防警告的浏览器实时通信
 * @version 2019.12.19
 */
@ServerEndpoint("/websocket_server")
@Component
public class WebSocketServer implements InitializingBean {
    //静态变量，用来记录当前大屏数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    public static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    public static WebSocketServer webSocketServer;

    @Autowired
    private  TokenService tokenService;

    @PostConstruct
    public void init(){
        System.out.println("执行了init方法！！！");
        webSocketServer = this;
        webSocketServer.tokenService = this.tokenService;
    }

    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     * @version 2019.12.19
     */
    @OnOpen
    public void onOpen(Session session){
        System.out.println("执行了onOpen方法！！！");
        //SysUser user1 = (SysUser) SecurityUtils.getSubject().getPrincipal();
        //System.out.println(user1);
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //大屏数加1

        //取出授权对象sysuser用于redis记录sessionId值与userId进行保存
        try{
            SysUser user = new Gson().fromJson(checkUserJson(session.getUserPrincipal().getName()), SysUser.class);
            webSocketServer.tokenService.saveWebSocketSession(session.getId(),new Gson().toJson(user));
        }catch (Exception e){
            e.printStackTrace();
        }

        String message = "连接已建立";
        //返回信息至客户端
        this.sendMessage(message);
    }
    /**
     * 连接关闭调用的方法
     * @version 2019.12.19
     */
    @OnClose
    public void onClose(){
        System.out.println("执行了onClose方法！！！");
        webSocketSet.remove(this);  //从set中删除

        subOnlineCount();  //大屏数减1

        SysUser user = new Gson().fromJson(checkUserJson(this.session.getUserPrincipal().getName()), SysUser.class);   //从授权对象中获取用户信息

        webSocketServer.tokenService.delWebSocketSession(new Gson().toJson(user),this.session.getId());   //移除redis缓存

    }
    /**
     * 收到客户端消息后调用的方法（暂不使用）
     * @param message 客户端发送过来的消息
     * @version 2019.12.19
     */
    @OnMessage
    public void onMessage(String message) {
        System.out.println("执行了onMessage方法！！！");
        //群发消息
        for(WebSocketServer item: webSocketSet){
            if(message.equals("ping")){
                item.sendMessage("ping");
            }else{
                item.sendMessage(message);
            }
        }
    }

    /**
     *  用于向客户端发送指定消息
     *  实现服务器主动推送
     * @throws IOException
     * @version 2019.12.19
     */
    private void sendMessage(String message) {
        System.out.println("执行了sendMessage方法！！！");
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     * @version 2019.12.19
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("执行了onError方法！！！");
        System.out.println("发生错误");
    }

    /**
     * 自定义警报时会调用此方法向正在保存实时通信的dept单位客户端发送报警广播
     * @param equipment 报警的设备信息
     * @param group 报警设备所在的集群
     * @version 2019.12.19
     */
    public static void sendAalarmMessage(Equipment equipment, Group group) {
        System.out.println("执行了sendAalarmMessage方法！！！");
        //打包报警数据
        Map<String,Object> respMap = new HashMap<>();
        respMap.put("equipment",equipment);
        respMap.put("group",group);
        respMap.put("message","alarm");
        try{
            //查询当前报警集群所有正在通讯的sessionId
            List<String> sessionIdList = webSocketServer.tokenService.checkGroupWebSocketSession(String.valueOf(equipment.getDeptId()));

            for (int i = 0; i < sessionIdList.size(); i++) {
                //循环向所有需要报警通讯的发送消息
                for (WebSocketServer item : webSocketSet) {
                    if (sessionIdList.get(i).equals(item.session.getId())){
                        item.sendMessage(new Gson().toJson(respMap));
                        break;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 自定义解除警报时会调用此方法向正在保存实时通信的dept单位客户端发送解除报警广播
     * @param equipment 报警的设备信息
     * @param group 报警设备所在的集群
     * @version 2019.12.19
     */
    public static void sendRelieveAalarmMessage(Equipment equipment, Group group) {
        //打包报警数据
        Map<String,Object> respMap = new HashMap<>();
        respMap.put("equipment",equipment);
        respMap.put("group",group);
        respMap.put("message","relieveAlarm");
        try{
            //查询当前报警集群所有正在通讯的sessionId
            List<String> sessionIdList = webSocketServer.tokenService.checkGroupWebSocketSession(String.valueOf(equipment.getDeptId()));
            for (int i = 0; i < sessionIdList.size(); i++) {
                //循环向所有需要解除报警通讯的发送消息
                for (WebSocketServer item : webSocketSet) {
                    if (sessionIdList.get(i).equals(item.session.getId())){
                        item.sendMessage(new Gson().toJson(respMap));
                        break;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }
    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }
    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
    @Override
    public void afterPropertiesSet() throws Exception {
    }
    public  WebSocketServer() {
    }

    /**
     * 抽取授权对象字符创中userId和deptId的JSON数据
     * @param userJson
     * @version 2019.12.19
     */
    public String checkUserJson(String userJson){
        System.out.println("执行了checkUserJson方法！！！");
        userJson = session.getUserPrincipal().getName().substring(7).replace(" ", "");
        int parentId = userJson.indexOf("parentId");
        userJson = userJson.substring(0, parentId - 1);
        userJson+="}";
        userJson = userJson.replace("{", "{\"");
        userJson = userJson.replace(",", "\",\"");
        userJson = userJson.replace("}", "\"}");
        userJson = userJson.replace("=", "\":\"");
        return userJson;
    }
}