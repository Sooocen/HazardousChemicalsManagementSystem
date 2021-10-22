package com.socen.ws.system.controller;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.socen.ws.common.annotation.Log;
import com.socen.ws.common.controller.BaseController;
import com.socen.ws.common.domain.QueryRequest;
import com.socen.ws.common.exception.WsException;
import com.socen.ws.system.domain.SysLog;
import com.socen.ws.system.service.LogService;
import com.wuwenze.poi.ExcelKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping("log")
@Api(tags = "日志信息",description = "日志信息")
public class LogController extends BaseController {

    private String message;

    @Autowired
    private LogService logService;

    @GetMapping
    @RequiresPermissions("log:view")
    @ApiOperation("日志信息列表")
    public Map<String, Object> logList(QueryRequest request, SysLog sysLog) {
        return getDataTable(logService.findLogs(request, sysLog));
    }

    @Log("删除系统日志")
    @DeleteMapping("/{ids}")
    @RequiresPermissions("log:delete")
    @ApiOperation("删除系统日志")
    public void deleteLogss(@NotBlank(message = "{required}") @PathVariable String ids) throws WsException {
        try {
            String[] logIds = ids.split(StringPool.COMMA);
            this.logService.deleteLogs(logIds);
        } catch (Exception e) {
            message = "删除日志失败";
            log.error(message, e);
            throw new WsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("log:export")
    @ApiOperation("导出Excel")
    public void export(QueryRequest request, SysLog sysLog, HttpServletResponse response) throws WsException {
        try {
            List<SysLog> sysLogs = this.logService.findLogs(request, sysLog).getRecords();
            ExcelKit.$Export(SysLog.class, response).downXlsx(sysLogs, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new WsException(message);
        }
    }
}
