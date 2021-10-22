package com.socen.ws.gen.service;

import com.socen.ws.gen.entity.InspectionRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Socen
 */
public interface IInspectionRecordService extends IService<InspectionRecord> {

    /**
     * 添加检查记录
     * @param record
     */
    void createRecord(InspectionRecord record);

    /**
     * 通过报表Id查询检查结果
     * @param reportId
     * @return
     */
    List<InspectionRecord> getRecordsByReportId(String reportId);
}
