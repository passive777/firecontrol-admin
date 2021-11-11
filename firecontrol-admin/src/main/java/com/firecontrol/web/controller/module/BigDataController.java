package com.firecontrol.web.controller.module;


import com.alibaba.fastjson.JSONObject;
import com.firecontrol.common.core.controller.BaseController;
import com.firecontrol.common.core.page.TableDataInfo;
import com.firecontrol.common.utils.bean.R;
import com.firecontrol.module.domain.Alarm;
import com.firecontrol.module.service.IBigDataService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据可视化
 *
 * @author Janvan911
 * @date 2020-03-07
 */
@Controller
@RequestMapping("/module/bigdata")
public class BigDataController extends BaseController {

    private String prefix = "module/bigdata";

    @Autowired
    private IBigDataService bigDataService;

    @GetMapping("/bigdata")
    public String bigdata(HttpServletRequest request, HttpServletResponse response) {
        return prefix + "/bigdata";
    }

    /**
     * 查询设备在线\离线数量
     * @param
     * @return
     */
    @GetMapping("/eq_on_off")
    @ResponseBody
    public String eqOnOff()
    {
        return bigDataService.eqOnOff();
    }

    @GetMapping("/installs")
    @ResponseBody
    public String installs(){
        return bigDataService.installs();
    }
}
