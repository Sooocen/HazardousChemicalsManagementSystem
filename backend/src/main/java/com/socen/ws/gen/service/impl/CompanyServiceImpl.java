package com.socen.ws.gen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.socen.ws.common.domain.QueryRequest;
import com.socen.ws.common.domain.WsConstant;
import com.socen.ws.common.utils.SortUtil;
import com.socen.ws.gen.dao.CompanyUnitMapper;
import com.socen.ws.gen.entity.Company;
import com.socen.ws.gen.dao.CompanyMapper;
import com.socen.ws.gen.entity.CompanyUnit;
import com.socen.ws.gen.service.ICompanyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.socen.ws.gen.service.ICompanyUnitService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Socen
 */
@Slf4j
@Service("ICompanyService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements ICompanyService {

    @Autowired
    private CompanyUnitMapper companyUnitMapper;
    @Autowired
    private ICompanyUnitService companyUnitService;

    /**
     * 根据企业名、创建时间查找企业（表）
     *
     * @param request
     * @param company
     * @return
     */
    @Override
    public List<Company> findCompanies(QueryRequest request, Company company) {

        QueryWrapper<Company> queryWrapper = new QueryWrapper<>();
        List<Company> list = null;
        try {
            if (StringUtils.isNotBlank(company.getCompanyName())) {
                queryWrapper.lambda().eq(Company::getCompanyName, company.getCompanyName());
            }
            if (StringUtils.isNotBlank(company.getCreateTimeFrom()) && StringUtils.isNotBlank(company.getCreateTimeTo())) {
                queryWrapper.lambda()
                        .ge(Company::getCreateTime, company.getCreateTimeFrom())
                        .le(Company::getCreateTime, company.getCreateTimeTo());
            }
            list = this.baseMapper.selectList(queryWrapper);

        } catch (Exception e) {
            log.error("获取企业列表失败", e);
            list = null;

        }
        return list;
    }

    @Override
    public IPage<Company> findCompanyDetail(Company company, QueryRequest request) {
        try {
            Page<Company> page = new Page<>();
            SortUtil.handlePageSort(request,page,"companyId", WsConstant.ORDER_ASC,false);
            return this.baseMapper.findCompanyDetail(page,company);
        }catch (Exception e){
            log.error("查询用户异常",e);
            return null;
        }
    }

    /**
     * 增加企业
     *
     * @param company
     */
    @Override
    public void createCompany(Company company) {
        company.setCreateTime(new Date());
        company.setModifyTime(new Date());
        this.save(company);
    }

    @Override
    public void updateCompany(Company company) {
        company.setModifyTime(new Date());
        this.baseMapper.updateById(company);
    }

    @Override
    public void deleteCompanies(String[] companyIds) {
        this.removeByIds(Arrays.asList(companyIds));
    }

    @Override
    public void addUnits(Company company) {
        //根据企业id删除删除所有的单元企业关联
        companyUnitMapper.delete(new LambdaQueryWrapper<CompanyUnit>()
                .eq(CompanyUnit::getCompanyId, company.getCompanyId()));
        //添加企业单元关联
        setCompanyUnits(company);
    }

    @Override
    public List<Company> findCompanyByZoneId(String zoneId) {
       return this.baseMapper.findCompanyByZonId(zoneId);
    }

    /**
     * 添加企业单元项
     * @param company //企业
     */
    private void setCompanyUnits(Company company) {
        if (company.getUnitId() != null) {
            String[] unitIds = company.getUnitId().split(StringPool.COMMA);
            Arrays.stream(unitIds).forEach(unitId -> {
                CompanyUnit cu = new CompanyUnit();
                cu.setCompanyId(company.getCompanyId());
                cu.setUnitId(Long.valueOf(unitId));
                this.companyUnitMapper.insert(cu);
            });
        }
    }


}
