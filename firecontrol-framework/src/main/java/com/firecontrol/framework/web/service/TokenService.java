package com.firecontrol.framework.web.service;

import com.firecontrol.common.json.JSON;
import com.firecontrol.common.utils.RedisUtil;
import com.firecontrol.system.domain.SysUser;
import com.google.gson.Gson;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * token业务
 * @date 2019-11-08 15:06:58
 **/
@Service("tokenService")
public class TokenService {
    //微信普通的token缓存生效时间
    private static final long EXPIRETIME=2*60*60;
    @Autowired
    private RedisUtil redisUtil;

    public RedisUtil getRedisUtil(){
        return redisUtil;
    }

    public TokenService(){
        init();
    }

    private void init() {
        // ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        //redisUtil = new RedisUtil();
    }

    //生成token(格式为token:加密的用户名-时间-六位随机数)
    public String generateToken(String username){
        StringBuilder token = new StringBuilder();
        //加加密的用户名
        token.append(DigestUtils.md5Hex(username) + "-");
        //加时间
        token.append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "-");
        //加六位随机数111111-999999
        token.append(new Random().nextInt((999999 - 111111 + 1)) + 111111);
        return token.toString();
    }

    //保存token
    public void saveToken(String token, SysUser user){
        try {
            redisUtil.set(token,JSON.marshal(user),2*60*60);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 校验token
     * @date 2019-06-08 15:01:46
     **/
    public boolean checkToken(String token){
        boolean flag=false;
        if (redisUtil.hasKey(token)){
            redisUtil.expire(token,EXPIRETIME);
            flag=true;
        }
        return flag;
    }

    /**
     * 删除token
     * @date 2019-06-14 16:40:16
     **/
    public boolean delToken(String token){
        boolean flag=false;
        try{
            redisUtil.del(token);
            flag=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 保存websocket sessionId至redis缓存
     * @param sessionId websocket sessionId
     * @param userInfo 用户数据字符串
     * @version 2019.08.19
     */
    public void saveWebSocketSession(String sessionId, String userInfo) {
        try {
            SysUser sysUser = new Gson().fromJson(userInfo, SysUser.class);
            redisUtil.set(JSON.marshal(sysUser.getUserId()+"-websocket-"+sessionId), sessionId);

            //分配集群实时通讯缓存
            List<Object> groupList =new ArrayList<Object>();
            groupList.add(sysUser.getUserId()+"-websocket-"+sessionId);
            redisUtil.lSet(JSON.marshal(sysUser.getDeptId() + "-dept-websocket"),groupList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 移除websocket sessionId至redis缓存
     * @param userInfo 用户编号
     * @version 2019.08.19
     */
    public void delWebSocketSession(String userInfo,String sessionId) {
        try {
            SysUser sysUser = new Gson().fromJson(userInfo, SysUser.class);
            redisUtil.del(JSON.marshal(sysUser.getUserId()+"-websocket-"+sessionId));

            redisUtil.lRemove(JSON.marshal(sysUser.getDeptId() + "-dept-websocket"),0,sysUser.getUserId()+"-websocket-"+sessionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检索指定deptId在redis中的websocket缓存
     * @param deptId
     * @version 2019.08.19
     */
    public List<String> checkGroupWebSocketSession(String deptId) {
        try {
            System.out.println("deptId:"+deptId);
            List<Object> objects = redisUtil.lGet(JSON.marshal(deptId + "-dept-websocket"), 0, -1);
            System.out.println("objects:"+new Gson().toJson(objects));
            List<String> sessionIdList =new ArrayList<String>();
            for (int i = 0; i < objects.size(); i++) {
                String sessionId = checkUserWebSocketSession(String.valueOf(objects.get(i)));
                System.out.println("sessionId:"+sessionId);
                if (sessionId!=null){
                    sessionIdList.add(sessionId);
                }
            }
            return sessionIdList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 检索指定userId在redis中的sessionId缓存（deptId检索完后会得到多个userId再根据该方法检索出所有需要报警客户端的sessionId）
     * @param userId
     * @version 2019.08.19
     */
    private String checkUserWebSocketSession(String userId) {
        try {
            return String.valueOf(redisUtil.get(JSON.marshal(userId)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
