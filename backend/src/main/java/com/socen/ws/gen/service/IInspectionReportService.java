package com.socen.ws.gen.service;

import com.socen.ws.common.domain.QueryRequest;
import com.socen.ws.gen.entity.InspectionReport;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Socen
 */
public interface IInspectionReportService extends IService<InspectionReport> {

    /**
     * 根据条件获取检查报表
     * @param request
     * @param report
     * @return
     */
    List<InspectionReport> findReports(QueryRequest request, InspectionReport report);

    /**
     * 添加报表
     * @param report
     */
    void createReport(InspectionReport report);

    /**
     * 修改报表
     * @param report
     */
    void updateReport(InspectionReport report);

    /**
     * 删除报表（批量）
     * @param reportIds
     */
    void deleteReport(String[] reportIds);

    /**
     * 获取下一个主键
     * @return
     */
    Long getNextId();
}
