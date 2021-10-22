package com.socen.ws.gen.service;

import com.socen.ws.common.domain.QueryRequest;
import com.socen.ws.gen.entity.Office;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Socen
 */
public interface IOfficeService extends IService<Office> {

    /**
     * 根据条件获取安检部门
     * @param request
     * @param office
     * @return
     */
    List<Office> findOffices(QueryRequest request, Office office);

    /**
     * 添加安检部门
     * @param office
     */
    void createOffice(Office office);

    /**
     * 修改安检部门
     * @param office
     */
    void updateOffice(Office office);

    /**
     * 删除安检部门（批量）
     * @param officeIds
     */
    void deleteOffices(String [] officeIds);
}
