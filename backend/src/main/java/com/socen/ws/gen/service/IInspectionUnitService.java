package com.socen.ws.gen.service;

import com.socen.ws.common.domain.QueryRequest;
import com.socen.ws.gen.entity.InspectionUnit;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Socen
 */
public interface IInspectionUnitService extends IService<InspectionUnit> {

    /**
     * 根据条件获取检查单元列表
     * @param request
     * @param unit
     * @return
     */
    List<InspectionUnit> findUnits(QueryRequest request, InspectionUnit unit);

    /**
     * 添加检查单元
     * @param unit
     */
    void createUnit(InspectionUnit unit);

    /**
     * 修改检查单元
     * @param unit
     */
    void updateUnit(InspectionUnit unit);

    /**
     * 删除检查单元（批量）
     * @param unitIds
     */
    void deleteUnits(String [] unitIds);
}
