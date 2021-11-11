package com.firecontrol.web.controller.system;

import com.firecontrol.common.core.controller.BaseController;
import com.firecontrol.framework.cloud.voice.alibaba.AliVoice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * 为物联网提供的接口数据 控制器
 */
@RestController
@RequestMapping("/xf-api")
public class SysApiController extends BaseController {

    @RequestMapping("/test")
    @ResponseBody
    public List<String> testMethod(){
        List<String> list = new ArrayList<String>();
        for(int i=0;i<10;i++){
            list.add(UUID.randomUUID().toString());
        }
        return list;
    }

    @ResponseBody
    @RequestMapping("/jingbao")
    public java.util.Map<String,Object> callPhone(String address, String number){
        HashMap<String,Object> result = new HashMap<>();
        AliVoice aliVoice = new AliVoice();
        aliVoice.callAlarm(number, "{\"address\":\"" + address + "\"}");
        result.put("code", "ok");
        return result;
    }

}
