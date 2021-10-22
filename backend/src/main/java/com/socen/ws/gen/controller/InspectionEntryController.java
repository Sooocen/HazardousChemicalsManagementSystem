package com.socen.ws.gen.controller;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.socen.ws.common.annotation.Log;
import com.socen.ws.common.domain.QueryRequest;
import com.socen.ws.common.exception.WsException;
import com.socen.ws.gen.entity.InspectionEntry;
import com.socen.ws.gen.service.IInspectionEntryService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Socen
 */
@Slf4j
@Validated
@RestController
@RequestMapping("entry")
@Api(tags = "检查条目管理",description = "检查条目管理")
public class InspectionEntryController extends BaseController {

    private String message;

    @Autowired
    private IInspectionEntryService entryService;

    @Log("获取检查条目列表")
    @GetMapping
    @RequiresPermissions("entry:view")
    @ApiOperation("获取检查条目列表")
    public Map<String, Object> entryList(QueryRequest request, InspectionEntry entry){
        Map<String, Object> result = new HashedMap<>();
        List<InspectionEntry> list = null;
        try{
            list = this.entryService.findEntries(request,entry);
            result.put("rows",list);
            result.put("total",list.size());
        }catch (Exception e){
            message = "查询检查条目列表失败";
            log.error(message,e);
            result.put("rows",null);
            result.put("total",0);
        }
        return result;
    }

    @Log("新增检查条目")
    @PostMapping
    @RequiresPermissions("entry:add")
    @ApiOperation("新增检查条目")
    public void addEntry(@Valid InspectionEntry entry) throws WsException {
        try{
            this.entryService.createEntry(entry);
        }catch (Exception e){
            message = "添加检查条目失败";
            log.error(message,e);
            throw new WsException(message);
        }
    }

    @Log("修改检查条目")
    @PutMapping
    @RequiresPermissions("entry:update")
    @ApiOperation("修改检查条目")
    public void updateEntry(@Valid InspectionEntry entry) throws WsException{
        try {
            this.entryService.updateEntry(entry);
        }catch (Exception e){
            message = "修改检查条目失败";
            log.error(message,e);
            throw new WsException(message);
        }
    }

    @Log("删除检查条目")
    @DeleteMapping("/{entryIds}")
    @RequiresPermissions("entry:delete")
    @ApiOperation("删除检查条目")
    public void deleteEntry(@NotBlank(message = "{required}") @PathVariable String entryIds) throws WsException{
        try {
            String[] ids = entryIds.split(StringPool.COMMA);
            this.entryService.deleteEntries(ids);
        }catch (Exception e){
            message = "删除检查条目失败";
            log.error(message,e);
            throw new WsException(message);
        }
    }

    @Log("导出Excel")
    @PostMapping("excel")
    @RequiresPermissions("entry:export")
    @ApiOperation("导出Excel")
    public void exportEntry(QueryRequest request, InspectionEntry entry, HttpServletResponse response) throws Exception{
        try {
            List<InspectionEntry> entries = this.entryService.findEntries(request,entry);
            ExcelKit.$Export(InspectionEntry.class,response).downXlsx(entries,false);
        }catch (Exception e){
            message = "导出Excel失败";
            log.error(message,e);
            throw new WsException(message);
        }
    }

    @Log("根据单元Id查询该单元下的所有条目")
    @GetMapping("/getEntries/{unitId}")
    @RequiresPermissions("entry:getEntriesByUnitId")
    @ApiOperation("根据单元Id查询该单元下的所有条目")
    public Map<String,Object> getEntryListByUnitId(@NotBlank(message = "{required}") @PathVariable String unitId) throws WsException{
        Map<String,Object> result = new HashMap<>();
        List<InspectionEntry> list = null;
        try{
            list = this.entryService.getEntriesByUnitId(unitId);
            result.put("rows",list);
            result.put("total",list.size());
        }catch (Exception e){
            message = "根据单元Id查询该单元下的所有条目失败";
            log.error(message,e);
            result.put("rows",null);
            result.put("total",0);
            throw new WsException(message);
        }
        return result;
    }
}
