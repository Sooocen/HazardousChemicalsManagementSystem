package com.socen.ws.gen.service;

import com.socen.ws.gen.entity.CompanyUnit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.socen.ws.gen.entity.InspectionUnit;

import java.util.List;

/**
 * @author Socen
 */
public interface ICompanyUnitService extends IService<CompanyUnit> {

    /**
     * 通过企业Id删除检查单元
     * @param companyIds
     */
    void deleteCompanyUnitByCompanyId(String[] companyIds);

    /**
     * 通过单元Id删除企业
     * @param unitIds
     */
    void deleteCompanyUnitByUnitId(String[] unitIds);

    /**
     * 通过单元Id查找企业id
     * @param unitIds
     * @return
     */
    List<String> findCompanyIdByUnitId(String[] unitIds);

    /**
     * 通过企业Id查找检查单元
     * @param companyId
     * @return
     */
    List<InspectionUnit> getUnitsCompanyId(Long companyId);
}
