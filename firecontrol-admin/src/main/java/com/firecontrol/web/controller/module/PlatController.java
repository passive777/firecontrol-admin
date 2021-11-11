package com.firecontrol.web.controller.module;
import com.firecontrol.module.service.IEquipmentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.firecontrol.common.core.controller.BaseController;

/**
 * 地图查看
 * @date 2020-02-08 18:02:32
 **/
@Controller
@RequestMapping("/module/plat")
public class PlatController extends BaseController
{

    @Autowired
    private IEquipmentService equipmentService;

    private String prefix = "module/plat";

    /**
     * 打开管地图界面
     * @return
     */
    @RequiresPermissions("module:plat:view")
    @GetMapping()
    public String plat(ModelMap map)
    {
        return prefix + "/plat";
    }

    /**
     * 打开管辖域地理位置选择地图界面
     * @date 2019-12-07 17:53:48
     **/
    @GetMapping("/selectPlat")
    public String selectPlat()
    {
        return prefix + "/selectPlat";
    }
}
