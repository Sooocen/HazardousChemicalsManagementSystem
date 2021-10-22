package com.socen.ws.gen.controller;


import com.socen.ws.common.annotation.Log;
import com.socen.ws.common.domain.QueryRequest;
import com.socen.ws.common.exception.WsException;
import com.socen.ws.gen.entity.InspectionReport;
import com.socen.ws.gen.service.IInspectionReportService;
import com.wuwenze.poi.ExcelKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.socen.ws.common.controller.BaseController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Socen
 */
@Slf4j
@Validated
@RestController
@RequestMapping("report")
@Api(tags = "检查报表业务",description = "检查报表业务")
public class InspectionReportController extends BaseController {

    @Autowired
    private IInspectionReportService reportService;

    private String message ;

    @Log("获取检查报表列表")
    @GetMapping
    @RequiresPermissions("report:view")
    @ApiOperation("获取检查报表列表")
    public Map<String,Object> reportList(QueryRequest request, InspectionReport report){
        Map<String,Object> result = new HashedMap<>();
        List<InspectionReport> list = null;
        try{
            list = this.reportService.findReports(request,report);
            result.put("rows",list);
            result.put("total",list.size());
        }catch (Exception e){
            message = "查询检查报表列表失败";
            log.error(message,e);
            result.put("rows",null);
            result.put("total",0);
        }
        return result;
    }

    @Log("新增检查报表")
    @PostMapping
    @RequiresPermissions("report:add")
    @ApiOperation("新增检查报表")
    public Map<String,Object> addReport(@Valid InspectionReport report) throws WsException{
        Map<String,Object> result = new HashMap<>();
        try{
            Long reportId = this.reportService.getNextId();
            report.setReportId(reportId);
            this.reportService.createReport(report);
            result.put("reportId",reportId);
        }catch (Exception e){
            message = "添加检查报表";
            log.error(message,e);
            result.put("reportId",null);
            throw new WsException(message);
        }
        return result;
    }

    @Log("导出Excel")
    @PostMapping("excel")
    @RequiresPermissions("report:export")
    @ApiOperation("导出Excel")
    public void exportReport(QueryRequest request, InspectionReport report, HttpServletResponse response) throws WsException{
        try{
            List<InspectionReport> reports = this.reportService.findReports(request, report);
            ExcelKit.$Export(InspectionReport.class,response).downXlsx(reports,false);
        }catch (Exception e){
            message = "导出Excel失败";
            log.error(message,e);
            throw new WsException(message);
        }
    }
}
