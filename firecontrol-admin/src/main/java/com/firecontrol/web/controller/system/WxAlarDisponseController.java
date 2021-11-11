package com.firecontrol.web.controller.system;
import com.firecontrol.common.utils.bean.R;
import com.firecontrol.module.service.IAlarmDisposeService;
import com.firecontrol.web.controller.common.WxCommonController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * 微信小程序端报警信息控制器
 * @authorspring
 */
@RequestMapping("/wx")
@RestController
public class WxAlarDisponseController extends WxCommonController {

    @Autowired
    private IAlarmDisposeService alarmDisposeService;

    /**
     * 报警处理信息列表 --- 待处理的
     * @param params
     * @return
     * z作者：spring
     */
    @RequestMapping("/queryAlarmDispose0")
    public R queryAlarmDispose0(@RequestBody Map<String, Object> params){
        params.put("status","0");
        return analysisPostBeforeB(params) ? alarmDisposeService.queryAlarmDispose(params) : R.ok().put("data", null);
    }

    /**
     * 报警处理信息列表 --- 已处理的
     * @param params
     * @return
     * z作者：spring
     */
    @RequestMapping("/queryAlarmDispose1")
    public R queryAlarmDispose1(@RequestBody Map<String, Object> params){
        params.put("status","1");
        return analysisPostBeforeB(params) ? alarmDisposeService.queryAlarmDispose(params): R.ok().put("data", null).put("hasMore", false);
    }

    /**
     * 处理报警设备
     * @param params
     * @return
     * 作者 ： spring
     */
    @RequestMapping("/updateAlarmDis")
    public R updateAlarmDis(@RequestBody Map<String, Object> params){
        return analysisPostBeforeB(params)? alarmDisposeService.updateAlarmDisposeWx(params) : R.ok().put("data",  0);
    }

    /**
     * 查询指定id的报警设备信息
     * @param params
     * @return
     * 作者 ： spring
     */
    @RequestMapping("/queryAlarmDisById")
    public R queryAlarmDisById(@RequestBody Map<String, Object> params){
        Long id = Long.valueOf((Integer) params.get("id"));
        return analysisPostBeforeB(params) ? R.ok().put("data", alarmDisposeService.selectAlarmDisposeById(id)) : R.ok().put("data", null);
    }

}
