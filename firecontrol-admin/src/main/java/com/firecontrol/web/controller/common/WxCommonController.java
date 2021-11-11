package com.firecontrol.web.controller.common;

import com.firecontrol.common.json.JSON;
import com.firecontrol.framework.web.service.TokenService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

public class WxCommonController {
    protected static final  String SUCCESSED="SUCCESSED";
    protected static final  String FALSE="FALSE";

    protected static final  String TOKEN="TOKEN";

    protected static final  String FLAG="FLAG";

    @Autowired
    private TokenService tokenService;

    public  Gson gson=new Gson();

    /**
     * 处理post请求的token验证并返回解析json结果
     * @date 2019-11-12 10:33:46
     **/
    public String  analysisPostBefore(Map<String,Object> map){
        String json="";
        if (tokenService.checkToken(String.valueOf(map.get(TOKEN)))){
              json = gson.toJson(map);
        }
        return json;
    }

    protected  boolean analysisPostBeforeB(Map<String,Object> map){
        String json = analysisPostBefore(map);
        return  (json!=null||!json.equals(""));
    }

}
