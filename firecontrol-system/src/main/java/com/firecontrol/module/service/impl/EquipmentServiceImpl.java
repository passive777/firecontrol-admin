package com.firecontrol.module.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.firecontrol.common.utils.bean.R;
import com.firecontrol.module.domain.EquipmentType;
import com.firecontrol.module.domain.Group;
import com.firecontrol.module.service.IEquipmentTypeService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.firecontrol.module.mapper.EquipmentMapper;
import com.firecontrol.module.domain.Equipment;
import com.firecontrol.module.service.IEquipmentService;
import com.firecontrol.common.core.text.Convert;
import org.springframework.web.multipart.MultipartFile;

/**
 * 设备 服务层实现
 * 
 * @author Fire
 * @date 2019-12-04
 */
@Service
public class EquipmentServiceImpl implements IEquipmentService 
{
	@Autowired
	private EquipmentMapper equipmentMapper;

	@Autowired
	private IEquipmentTypeService iEquipmentTypeService;
	/**
     * 查询设备信息
     * 
     * @param id 设备ID
     * @return 设备信息
     */
    @Override
	public Equipment selectEquipmentById(Long id)
	{
	    return equipmentMapper.selectEquipmentById(id);
	}
	
	/**
     * 查询设备列表
     * 
     * @param equipment 设备信息
     * @return 设备集合
     */
	@Override
	public List<Equipment> selectEquipmentList(Equipment equipment)
	{

	    return equipmentMapper.selectEquipmentList(equipment);
	}

	/**
	 * 根据集群以及本单位查询设备情况
	 */
	public List<Equipment> forPlat(Group group){
		return equipmentMapper.forPlat(group);
	}
	
    /**
     * 新增设备
     * 
     * @param equipment 设备信息
     * @return 结果
     */
	@Override
	public int insertEquipment(Equipment equipment)
	{
	    return equipmentMapper.insertEquipment(equipment);
	}
	
	/**
     * 修改设备
     * 
     * @param equipment 设备信息
     * @return 结果
     */
	@Override
	public int updateEquipment(Equipment equipment)
	{
	    return equipmentMapper.updateEquipment(equipment);
	}

	/**
     * 删除设备对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteEquipmentByIds(String ids)
	{
		return equipmentMapper.deleteEquipmentByIds(Convert.toStrArray(ids));
	}

	/**
	 * 查询设备列表根据集群
	 */
	@Override
	public List<Equipment> groupEqumentList(Integer id, Equipment equipment) {
		return equipmentMapper.groupEqumentList(id, equipment);
	}


	/**
	 * 查询指定IMEI号的设备信息
	 * @param imei 设备IMEI号
	 * @return 设备安装信息
	 */
	@Override
	public Equipment selectEquipmentByImei(String imei) {

		Equipment equipment = null;
		try{
			equipment = equipmentMapper.selectEquipmentByImei(imei);
		}catch (Exception e){
			e.printStackTrace();
		}
		return equipment;
	}

	/**
	 * 批量导入设备
	 * @date 2019-12-01 20:52:42
	 **/
	@Override
	public List<Equipment> importEquipment(MultipartFile file, boolean updateSupport, Long deptId) {
		List<Equipment> equipmentList = new ArrayList<Equipment>();
		try {
			Workbook book = null;
			try {
				book = new XSSFWorkbook(file.getInputStream());
			} catch (Exception ex) {
				book = new HSSFWorkbook(new POIFSFileSystem(file.getInputStream()));
			}
			int numberOfSheets = book.getNumberOfSheets();
			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheet = book.getSheetAt(i);
				int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
				for (int j = 0; j < physicalNumberOfRows; j++) {
					if (j == 0) {
						continue;//标题行
					}
					Equipment equipment = new Equipment();
					Row row = sheet.getRow(j);//获得当前行数据
					Cell cell = row.getCell(0);
					cell.setCellType(CellType.STRING);
					System.out.println("cell.getStringCellValue():"+cell.getStringCellValue());
					equipment.setImei(cell.getStringCellValue());
					cell = row.getCell(1);
					cell.setCellType(CellType.STRING);
					equipment.setEquipmentName(cell.getStringCellValue());
					cell = row.getCell(2);
					cell.setCellType(CellType.STRING);
					equipment.setImportStatus(cell.getStringCellValue());
					cell = row.getCell(3);
					cell.setCellType(CellType.STRING);
					equipment.setCommunication(cell.getStringCellValue());
					cell = row.getCell(4);
					cell.setCellType(CellType.STRING);
					equipment.setManufacturer(cell.getStringCellValue());
					cell = row.getCell(5);
					cell.setCellType(CellType.STRING);
					equipment.setImsi(cell.getStringCellValue());
					cell = row.getCell(6);
					cell.setCellType(CellType.STRING);
					equipment.setHardwareVersion(cell.getStringCellValue());
					cell = row.getCell(7);
					cell.setCellType(CellType.STRING);
					equipment.setFirmwareVersion(cell.getStringCellValue());
					cell = row.getCell(8);
					cell.setCellType(CellType.STRING);
					//补全部门ID和状态
					equipment.setDeptId(Math.toIntExact(deptId));
					equipmentList.add(equipment);
				}
			}
			for (int i = 0; i <equipmentList.size() ; i++) {
				Equipment equipment = equipmentList.get(i);
				String imei = equipment.getImei();
				if (imei==null||imei.equals("")){
					equipmentList.get(i).setImportStatus("设备IMEI为空");
				}else {
					//查询该设备是否已存在
					Equipment equipment2 = selectEquipmentByImei(imei);

					if (equipment2==null){
						//设备不存在，查询对应类型是否存在
						EquipmentType equipmentType=new EquipmentType();
						String type = equipment.getImportStatus();
						if (type==null||type.equals("")){
							equipmentList.get(i).setImportStatus("设备类型为空");
						}else{
							equipmentType.setName(type);
							List<EquipmentType> equipmentTypes = iEquipmentTypeService.selectEquipmentTypeList(equipmentType);
							if (equipmentTypes.size()==0){
								//类型不存在
								equipmentList.get(i).setImportStatus("设备类型不存在");
							}else {
								Integer typeId = equipmentTypes.get(0).getId();
								equipment.setType(typeId);
								equipmentList.get(i).setType(typeId);
								int num = insertEquipment(equipment);
								if (num == 0) {
									//存储成功
									equipmentList.get(i).setImportStatus("失败");
								}else {
									equipmentList.get(i).setImportStatus("成功");
								}
							}
						}
					}else{
						//设备已存在
						equipmentList.get(i).setImportStatus("设备已存在");
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return equipmentList;
	}
	@Override
	public R queryEquipmentByDeptId(Map<String, Object> params) {
		Gson gson = new Gson();
		String json = gson.toJson(params);
		Equipment equipment = gson.fromJson(json, Equipment.class);
		Page<?> objects = null;
		Integer start = 1;
		Integer size = 10;
		try{
			start = (Integer)params.get("start");
			size = (Integer) params.get("size");
			objects = PageHelper.startPage(start, size);
		}catch (Exception e){
			objects = PageHelper.startPage(1,10);
		}
		List<Equipment> list = equipmentMapper.selectEquipmentList(equipment);
		return R.ok().put("data", list).put("hasMore", objects.getTotal() > (start * size) );
	}


	/**
	 * 微信小程序新增安装点时向对应imei设备置入deptId + status
	 * @param equipment 设备信息
	 * @return 结果
	 * @version 2019.12.29
	 */
	@Override
	public int updateEquipmentDeptIdByImei(Equipment equipment)
	{
		return equipmentMapper.updateEquipmentDeptIdByImei(equipment);
	}
}
