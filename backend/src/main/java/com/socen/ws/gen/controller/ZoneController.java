package com.socen.ws.gen.controller;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.socen.ws.common.annotation.Log;
import com.socen.ws.common.domain.QueryRequest;
import com.socen.ws.common.exception.WsException;
import com.socen.ws.gen.entity.Zone;
import com.socen.ws.gen.entity.model.Option;
import com.socen.ws.gen.service.IZoneService;
import com.wuwenze.poi.ExcelKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.socen.ws.common.controller.BaseController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Socen
 */
@Slf4j
@Validated
@RestController
@RequestMapping("zone")
@Api(tags = "区域管理",description = "区域管理")
public class ZoneController extends BaseController {

    private String message;

    @Autowired
    private IZoneService zoneService;

    @Log("获取区域列表")
    @GetMapping
    @RequiresPermissions("Zone:view")
    @ApiOperation("获取区域列表")
    public Map<String,Object> zoneList(QueryRequest request, Zone zone){
        return this.zoneService.findZones(request,zone);
    }

    @Log("获取区域列表")
    @GetMapping("/select")
    @RequiresPermissions("Zone:view")
    @ApiOperation("获取区域列表(组件)")
    public Map<String,Object> zoneSelectList(QueryRequest request,Zone zone) throws WsException{
        Map<String,Object> result = new HashMap<>();
        List<Zone> list = null;
        try{
            list = this.zoneService.findZones(zone,request);
            result.put("rows",list);
            result.put("total",list.size());
        }catch (Exception e){
            message = "获取区域列表失败";
            log.error(message,e);
            result.put("rows",null);
            result.put("total",0);
            throw new WsException(message);
        }
        return result;
    }

    @Log("添加区域")
    @PostMapping
    @RequiresPermissions("Zone:add")
    @ApiOperation("添加区域")
    public void addZone(@Valid Zone zone) throws WsException{
        try{
            this.zoneService.createZone(zone);
        }catch (Exception e){
            message = "新增区域失败";
            log.error(message,e);
            throw new WsException(message);
        }
    }

    @Log("删除区域")
    @DeleteMapping("/{zoneIds}")
    @RequiresPermissions("zone:delete")
    @ApiOperation("删除区域")
    public void deleteZones(@NotBlank(message = "{required}") @PathVariable String zoneIds) throws WsException{
        try {
            String[] ids = zoneIds.split(StringPool.COMMA);
            this.zoneService.deleteZones(ids);
        }catch (Exception e){
            message = "删除区域失败";
            log.error(message,e);
            throw new WsException(message);
        }
    }

    @Log("修改地区")
    @PutMapping
    @RequiresPermissions("zone:update")
    @ApiOperation("修改地区")
    public void updateZone(Zone zone) throws WsException{
        try{
            this.zoneService.updateZone(zone);
        }catch (Exception e){
            message = "修改企业地区失败";
            log.error(message,e);
            throw new WsException(message);
        }
    }

    @Log("导出Excel")
    @PostMapping("excel")
    @RequiresPermissions("zone:export")
    @ApiOperation("导出Excel")
    public void export(Zone zone, QueryRequest request, HttpServletResponse response) throws WsException{
        try{
            List<Zone> zones = this.zoneService.findZones(zone,request);
            ExcelKit.$Export(Zone.class,response).downXlsx(zones,false);
        }catch (Exception e){
            message = "导出Excel失败";
            log.error(message,e);
            throw new WsException(message);
        }
    }

    @Log("导出")
    @PostMapping("zc")
    @ApiOperation("通过用户名或许区域公司二级联动")
    public List<Option> getZoneCompanyByUsername(String username){
        return this.zoneService.findZoneCompanyOptionByUsername(username);
    }
}
