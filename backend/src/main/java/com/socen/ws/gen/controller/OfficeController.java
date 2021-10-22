package com.socen.ws.gen.controller;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.socen.ws.common.annotation.Log;
import com.socen.ws.common.domain.QueryRequest;
import com.socen.ws.common.exception.WsException;
import com.socen.ws.gen.entity.Office;
import com.socen.ws.gen.service.IOfficeService;
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
@RequestMapping("office")
@Api(tags = "安检部门管理",description = "安检部门管理")
public class OfficeController extends BaseController {

    private String message;

    @Autowired
    private IOfficeService officeService;

    @Log("获取安监局列表")
    @GetMapping
    @RequiresPermissions("office:view")
    @ApiOperation("获取安监局列表")
    public Map<String,Object> officeList(QueryRequest request, Office office){
        Map<String,Object> result = new HashedMap<>();
        List<Office> list = null;
        try {
            list = this.officeService.findOffices(request,office);
            result.put("rows",list);
            result.put("total", list.size());
        }catch (Exception e){
            log.error("查询安检部门成功",e);
            result.put("rows",null);
            result.put("total", 0);
        }
        return result;
    }

    @Log("新增安监局")
    @PostMapping
    @RequiresPermissions("office:add")
    @ApiOperation("新增企业")
    public void addOffice(@Valid Office office) throws WsException{
        try{
            this.officeService.createOffice(office);
        } catch (Exception e){
            message = "添加安监局失败";
            log.error(message,e);
            throw new WsException(message);
        }
    }

    @Log("修改安监局")
    @PutMapping
    @RequiresPermissions("office:update")
    @ApiOperation("修改安监局")
    public void updateOffice(@Valid Office office) throws WsException{
        try{
            this.officeService.updateOffice(office);
        }catch (Exception e){
            message = "修改安监局失败";
            log.error(message,e);
            throw new WsException(message);
        }
    }

    @Log("删除安检局")
    @DeleteMapping("/{officeIds}")
    @RequiresPermissions("office:delete")
    @ApiOperation("删除安监局")
    public void deleteOffice(@NotBlank(message = "{required}") @PathVariable String officeIds) throws WsException{
        try{
            String[] ids = officeIds.split(StringPool.COMMA);
            this.officeService.deleteOffices(ids);
        }catch (Exception e){
            message = "删除安监局失败";
            log.error(message,e);
            throw new WsException(message);
        }
    }

    @Log("导出Excel")
    @PostMapping("excel")
    @RequiresPermissions("office:export")
    @ApiOperation("导出Excel")
    public void exportOffice(QueryRequest request, Office office, HttpServletResponse response) throws WsException{
        try{
            List<Office> offices = this.officeService.findOffices(request,office);
            ExcelKit.$Export(Office.class,response).downXlsx(offices,false);
        }catch (Exception e){
            message = "导出Excel失败";
            log.error(message,e);
            throw new WsException(message);
        }
    }
}
