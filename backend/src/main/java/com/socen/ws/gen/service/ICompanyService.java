package com.socen.ws.gen.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.socen.ws.common.domain.QueryRequest;
import com.socen.ws.gen.entity.Company;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Socen
 */
public interface ICompanyService extends IService<Company> {

    /**
     * 根据企业名、创建时间查找企业
     * @param request
     * @param company
     * @return
     */
    List<Company> findCompanies(QueryRequest request, Company company);

    /**
     * 查询公司详情，包括基本信息，检查单元
     * @param company
     * @param request
     * @return
     */
    IPage<Company> findCompanyDetail (Company company, QueryRequest request);



    /**
     * 增加企业
     * @param company
     */
    void createCompany(Company company);

    /**
     * 修改企业
     * @param company
     */
    void updateCompany(Company company);

    /**
     * 删除多个企业
     * @param companyIds
     */
    void deleteCompanies(String[] companyIds);

    /**
     * 为企业添加检查单元
     * @param company
     */
    void addUnits(Company company);

    /**
     * 通过区域id查找企业
     * @param zoneId
     * @return
     */
    List<Company> findCompanyByZoneId(String zoneId);

}
