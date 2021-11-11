package com.firecontrol.web.controller.module;

import com.firecontrol.common.core.controller.BaseController;
import com.firecontrol.common.utils.DateUtils;
import com.firecontrol.framework.util.ShiroUtils;
import com.firecontrol.module.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计分析
 * 
 * @author Fire
 * @date 2020-03-23
 */
@Controller
@RequestMapping("/module/statistic")
public class StatisticController extends BaseController
{
    private String prefix = "module/statistic";
	
	@Autowired
	private IGroupService groupService;
	@Autowired
	private IAlarmService alarmService;
	@Autowired
	private IAlarmDisposeService alarmDisposeService;

	/**
	 * 打开设备报警统计
	 */
	@GetMapping()
	public String alarmStatisitic(ModelMap map)
	{
		return prefix + "/alarmStatistic";
	}

	/**
	 * 获取单位十五天内单位设备报警信息
	 * @return
	 */
	@GetMapping("/statistic")
	@ResponseBody
	public Map statistic(){
		Map<String, Object> map = new HashMap<String, Object>();
		Date fif = DateUtils.beforNumberDay(DateUtils.getNowDate(), -14);
		List<String> fifArray = DateUtils.findDates(fif, DateUtils.getNowDate());
		String[] days = fifArray.toArray(new String[fifArray.size()]);
		int[] counts = new int[15];
		for(int i = 0; i < days.length; i++){
			//查询单位每天报警数量
			counts[i] = alarmDisposeService.alarmByEachDay(days[i], ShiroUtils.getSysUser().getDeptId());
		}

		map.put("days", days);
		map.put("counts", counts);
		return map;
	}

}
