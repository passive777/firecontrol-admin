package com.firecontrol.module.service.impl;

import java.util.List;

import com.firecontrol.common.utils.DateUtils;
import com.firecontrol.module.domain.Equipment;
import com.firecontrol.module.service.IEquipmentService;
import com.firecontrol.system.domain.SysOperLog;
import com.firecontrol.system.service.ISysUserService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.firecontrol.module.mapper.EquipmentLogMapper;
import com.firecontrol.module.domain.EquipmentLog;
import com.firecontrol.module.service.IEquipmentLogService;
import com.firecontrol.common.core.text.Convert;

/**
 * 操作日志记录 服务层实现
 * 
 * @author Fire
 * @date 2019-12-20
 */
@Service
public class EquipmentLogServiceImpl implements IEquipmentLogService 
{
	@Autowired
	private EquipmentLogMapper equipmentLogMapper;
	@Autowired
	private ISysUserService iSysUserService;
	@Autowired
	private IEquipmentService iEquipmentService;

	/**
     * 查询操作日志记录信息
     * 
     * @param operId 操作日志记录ID
     * @return 操作日志记录信息
     */
    @Override
	public EquipmentLog selectEquipmentLogById(Long operId)
	{
	    return equipmentLogMapper.selectEquipmentLogById(operId);
	}
	
	/**
     * 查询操作日志记录列表
     * 
     * @param equipmentLog 操作日志记录信息
     * @return 操作日志记录集合
     */
	@Override
	public List<EquipmentLog> selectEquipmentLogList(EquipmentLog equipmentLog)
	{
	    return equipmentLogMapper.selectEquipmentLogList(equipmentLog);
	}
	
    /**
     * 新增操作日志记录
     * 
     * @param equipmentLog 操作日志记录信息
     * @return 结果
     */
	@Override
	public int insertEquipmentLog(EquipmentLog equipmentLog)
	{
	    return equipmentLogMapper.insertEquipmentLog(equipmentLog);
	}
	
	/**
     * 修改操作日志记录
     * 
     * @param equipmentLog 操作日志记录信息
     * @return 结果
     */
	@Override
	public int updateEquipmentLog(EquipmentLog equipmentLog)
	{
	    return equipmentLogMapper.updateEquipmentLog(equipmentLog);
	}

	/**
     * 删除操作日志记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteEquipmentLogByIds(String ids)
	{
		return equipmentLogMapper.deleteEquipmentLogByIds(Convert.toStrArray(ids));
	}

	/**
	 * 实体类转换，保存操作日志
	 * @date 2019-12-20 15:04:28
	 **/
	@Override
	public void insertEquipmentLogForOperLog(SysOperLog operLog) {
		EquipmentLog equipmentLog=new EquipmentLog();
		equipmentLog.setDeptName(operLog.getDeptName());
		equipmentLog.setErrorMsg(operLog.getErrorMsg());
		equipmentLog.setMethod(operLog.getMethod());
		equipmentLog.setOperatorType(operLog.getOperatorType());
		equipmentLog.setOperId(operLog.getOperId());
		equipmentLog.setOperIp(operLog.getOperIp());
		equipmentLog.setOperLocation(operLog.getOperLocation());
		equipmentLog.setOperName(operLog.getOperName());
		equipmentLog.setOperTime(DateUtils.getNowDate());
		equipmentLog.setOperUrl(operLog.getOperUrl());
		equipmentLog.setStatus(operLog.getStatus());
		String operParam = operLog.getOperParam();
		JsonParser jsonParser=new JsonParser();
		JsonObject asJsonObject = jsonParser.parse(operParam).getAsJsonObject();
		Integer businessType = operLog.getBusinessType();
		equipmentLog.setBusinessType(businessType);
		String title = operLog.getTitle();
		equipmentLog.setTitle(title);
		if (title.equals("用户管理")){
            if (businessType==3){
            	//删除业务
				String idList = asJsonObject.get("ids").getAsString();
				String[] ids = idList.split(",");
				String userList="";
				for (int i = 0; i <ids.length ; i++) {
					userList+=iSysUserService.selectUserById(Long.valueOf(ids[i])).getUserName();
					if (i!=ids.length-1){
						userList+=",";
					}
				}
				operParam=userList;
			}else {
            	//新增和修改操作
				return;
			}
		}else if (title.equals("设备删除")){
			if (businessType==3){
				//删除业务
				String imei = asJsonObject.get("imei").getAsString();
				String name = asJsonObject.get("equipmentName").getAsString();
				operParam=imei+name;
			}else{
				return;
			}
		}else if (title.equals("设备解绑")){
			if (businessType==3){
				String imei=asJsonObject.get("imei").getAsString();
				Equipment equipment = iEquipmentService.selectEquipmentByImei(imei);
				operParam=equipment.getImei()+equipment.getEquipmentName();
			}else {
				return;
			}
		}
		equipmentLog.setOperParam(operParam);
		equipmentLogMapper.insertEquipmentLog(equipmentLog);
	}

}
