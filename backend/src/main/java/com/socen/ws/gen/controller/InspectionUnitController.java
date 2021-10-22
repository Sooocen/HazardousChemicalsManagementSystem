package com.socen.ws.gen.controller;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.socen.ws.common.annotation.Log;
import com.socen.ws.common.domain.QueryRequest;
import com.socen.ws.common.exception.WsException;
import com.socen.ws.gen.entity.InspectionUnit;
import com.socen.ws.gen.service.ICompanyUnitService;
import com.socen.ws.gen.service.IInspectionUnitService;
import com.wuwenze.poi.ExcelKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.socen.ws.common.controller.BaseController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author Socen
 */
@Slf4j
@Validated
@RestController
@RequestMapping("unit")
@Api(tags = "检查单元管理",description = "检查单元管理")
public class InspectionUnitController extends BaseController {

    private String message;

    @Autowired
    private IInspectionUnitService unitService;
    @Autowired
    private ICompanyUnitService companyUnitService;

    @Log("获取检查单元列表")
    @GetMapping
    @RequiresPermissions("unit:view")
    @ApiOperation("获取检查单元列表")
    public Map<String, Object> unitList(QueryRequest request, InspectionUnit unit){
        Map<String, Object> result = new HashedMap<>();
        List<InspectionUnit> list = null;
        try{
            list = this.unitService.findUnits(request,unit);
            result.put("rows",list);
            result.put("total",list.size());
        }catch (Exception e){
            message = "查询检查单元列表失败";
            log.error(message,e);
            result.put("rows",null);
            result.put("total",0);
        }
        return result;
    }

    @Log("新增检查单元")
    @PostMapping
    @RequiresPermissions("unit:add")
    @ApiOperation("新增检查单元")
    public void addUnit(@Valid InspectionUnit unit) throws WsException{
        try{
            this.unitService.createUnit(unit);
        }catch (Exception e){
            message = "添加检查单元失败";
            log.error(message,e);
            throw new WsException(message);
        }
    }

    @Log("修改检查单元")
    @PutMapping
    @RequiresPermissions("unit:update")
    @ApiOperation("修改检查单元")
    public void updateUnit(@Valid InspectionUnit unit) throws WsException{
        try {
            this.unitService.updateUnit(unit);
        }catch (Exception e){
            message = "修改检查单元失败";
            log.error(message,e);
            throw new WsException(message);
        }
    }

    @Log("删除检查单元")
    @DeleteMapping("/{unitIds}")
    @RequiresPermissions("unit:delete")
    @ApiOperation("删除检查单元")
    public void deleteUnit(@NotBlank(message = "{required}") @PathVariable String unitIds) throws WsException{
        try {
            String[] ids = unitIds.split(StringPool.COMMA);
            this.unitService.deleteUnits(ids);
        }catch (Exception e){
            message = "删除检查单元失败";
            log.error(message,e);
            throw new WsException(message);
        }
    }

    @Log("导出Excel")
    @PostMapping("excel")
    @RequiresPermissions("unit:export")
    @ApiOperation("导出Excel")
    public void exportUnit(QueryRequest request, InspectionUnit unit, HttpServletResponse response) throws Exception{
        try {
            List<InspectionUnit> units = this.unitService.findUnits(request,unit);
            ExcelKit.$Export(InspectionUnit.class,response).downXlsx(units,false);
        }catch (Exception e){
            message = "导出Excel失败";
            log.error(message,e);
            throw new WsException(message);
        }
    }

    @Log("根据企业Id查询该企业的检查单元")
    @GetMapping("/getUnits/{companyId}")
    @RequiresPermissions("unit:getUnitsByCompanyId")
    @ApiOperation("根据企业Id查询该企业的检查单元")
    public Map<String, Object> getCompanyUnits (@NotBlank(message = "{required}") @PathVariable String companyId) throws WsException{
        Map<String, Object> result = new HashedMap<>();
        List<InspectionUnit> list = null;
        try{
            list = companyUnitService.getUnitsCompanyId(Long.valueOf(companyId));
            result.put("rows",list);
            result.put("total",list.size());
        }catch (Exception e){
            message = "根据企业Id获取检查单元失败";
            log.error(message,e);
            result.put("rows",null);
            result.put("total",0);
            throw new WsException(message);
        }
        return result;
    }
}
