package com.socen.ws.gen.dao;

import com.socen.ws.gen.entity.InspectionReport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Socen
 */
@Repository
public interface InspectionReportMapper extends BaseMapper<InspectionReport> {

    /**
     * 过滤获取报表列表
     * @param report
     * @return
     */
    List<InspectionReport> findReports(@Param("report") InspectionReport report);

    /**
     * 获取主键最大值
     * @return
     */
    Long getMaxId();
}
