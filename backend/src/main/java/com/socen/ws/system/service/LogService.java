package com.socen.ws.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.socen.ws.common.domain.QueryRequest;
import com.socen.ws.system.domain.SysLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;

public interface LogService extends IService<SysLog> {

    IPage<SysLog> findLogs(QueryRequest request, SysLog sysLog);

    void deleteLogs(String[] logIds);

    @Async
    void saveLog(ProceedingJoinPoint point, SysLog log) throws JsonProcessingException;
}
