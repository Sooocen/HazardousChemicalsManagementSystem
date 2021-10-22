package com.socen.ws.gen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.socen.ws.common.domain.QueryRequest;
import com.socen.ws.gen.entity.InspectionUnit;
import com.socen.ws.gen.dao.InspectionUnitMapper;
import com.socen.ws.gen.service.IInspectionUnitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Socen
 */
@Slf4j
@Service("IInspectionUnitService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class InspectionUnitServiceImpl extends ServiceImpl<InspectionUnitMapper, InspectionUnit> implements IInspectionUnitService {

    @Override
    public List<InspectionUnit> findUnits(QueryRequest request, InspectionUnit unit) {
        QueryWrapper<InspectionUnit> queryWrapper = new QueryWrapper<>();
        List<InspectionUnit> list = null;
        try{
            if (StringUtils.isNotBlank(unit.getUnitNum())){
                queryWrapper.lambda().eq(InspectionUnit::getUnitNum,unit.getUnitNum());
            }
            if (StringUtils.isNotBlank(unit.getUnitName())){
                queryWrapper.lambda().eq(InspectionUnit::getUnitName,unit.getUnitName());
            }
            list = this.baseMapper.selectList(queryWrapper);
        }catch (Exception e){
            log.error("获取检查单元失败");
            list = null;
        }
        return list;
    }

    @Override
    public void createUnit(InspectionUnit unit) {
        unit.setCreateTime(new Date());
        unit.setModifyTime(new Date());
        this.save(unit);
    }

    @Override
    public void updateUnit(InspectionUnit unit) {
        unit.setModifyTime(new Date());
        this.baseMapper.updateById(unit);
    }

    @Override
    public void deleteUnits(String[] unitIds) {
        this.removeByIds(Arrays.asList(unitIds));
    }
}
