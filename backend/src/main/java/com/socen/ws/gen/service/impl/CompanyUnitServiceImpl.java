package com.socen.ws.gen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.socen.ws.gen.entity.CompanyUnit;
import com.socen.ws.gen.dao.CompanyUnitMapper;
import com.socen.ws.gen.entity.InspectionUnit;
import com.socen.ws.gen.service.ICompanyUnitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Socen
 */
@Service("ICompanyUnitService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CompanyUnitServiceImpl extends ServiceImpl<CompanyUnitMapper, CompanyUnit> implements ICompanyUnitService {

    @Override
    public void deleteCompanyUnitByCompanyId(String[] companyIds) {
        Arrays.stream(companyIds).forEach(id -> {
            baseMapper.deleteByCompanyId(Long.valueOf(id));
        });
    }

    @Override
    public void deleteCompanyUnitByUnitId(String[] unitIds) {
        Arrays.stream(unitIds).forEach(id -> {
            baseMapper.deleteByUnitId(Long.valueOf(id));
        });
    }

    @Override
    public List<String> findCompanyIdByUnitId(String[] unitIds) {
        List<CompanyUnit> list = baseMapper.selectList(new LambdaQueryWrapper<CompanyUnit>().in(CompanyUnit::getUnitId,
                String.join(StringPool.COMMA, unitIds)));
        return list.stream().map(userRole ->
                String.valueOf(userRole.getCompanyId())
        ).collect(Collectors.toList());
    }

    @Override
    public List<InspectionUnit> getUnitsCompanyId(Long companyId) {
        return this.baseMapper.getUnitsByCompanyId(companyId);
    }
}
