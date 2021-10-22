package com.socen.ws.gen.controller;


import com.socen.ws.common.annotation.Log;
import com.socen.ws.common.exception.WsException;
import com.socen.ws.gen.entity.InspectionRecord;
import com.socen.ws.gen.service.IInspectionRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.socen.ws.common.controller.BaseController;

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
@RequestMapping("record")
@Api(tags = "检查记录",description = "检查记录")
public class InspectionRecordController extends BaseController {

    private String message;

    @Autowired
    private IInspectionRecordService recordService;

    @Log("新增检查记录")
    @PostMapping
    @RequiresPermissions("record:add")
    @ApiOperation("新增检查记录")
    public void addRecord(@Valid InspectionRecord record) throws WsException{
        try {
            this.recordService.createRecord(record);
        }catch (Exception e){
            message = "添加检查记录失败";
            log.error(message,e);
            throw new WsException(message);
        }
    }

    @Log("根据报表Id查询检查记录")
    @GetMapping("/getRecords/{reportId}")
    @RequiresPermissions("report:getRecordByReportId")
    @ApiOperation("根据报表Id查询检查记录")
    public Map<String,Object> getRecordByReportId(@NotBlank(message = "{required}" ) @PathVariable String reportId) throws WsException{
        Map<String,Object> result = new HashMap<>();
        List<InspectionRecord> list = null;
        try{
            list = this.recordService.getRecordsByReportId(reportId);
            result.put("rows",list);
            result.put("total",list.size());
        }catch (Exception e){
            message = "根据报表Id查询检查记录失败";
            log.error(message,e);
            result.put("total",0);
            result.put("rows",null);
            throw new WsException(message);
        }
        return result;
    }

}

