package com.socen.ws.gen.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.socen.ws.gen.entity.Company;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author Socen
 */
@Repository
public interface CompanyMapper extends BaseMapper<Company> {

    /**
     * 获取企业信息详情，包括检查单元
     * @param page
     * @param company
     * @return
     */
    IPage<Company> findCompanyDetail(Page page, @Param("company") Company company);

    /**
     * 通过区域Id查询该区域下的所有企业
     * @param zoneId
     * @return
     */
    List<Company> findCompanyByZonId(String zoneId);
}
