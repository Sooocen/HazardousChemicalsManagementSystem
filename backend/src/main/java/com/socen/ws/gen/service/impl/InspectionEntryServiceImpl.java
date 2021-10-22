package com.socen.ws.gen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.socen.ws.common.domain.QueryRequest;
import com.socen.ws.gen.entity.InspectionEntry;
import com.socen.ws.gen.dao.InspectionEntryMapper;
import com.socen.ws.gen.service.IInspectionEntryService;
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
@Service("IInspectionEntryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class InspectionEntryServiceImpl extends ServiceImpl<InspectionEntryMapper, InspectionEntry> implements IInspectionEntryService {

    @Override
    public List<InspectionEntry> findEntries(QueryRequest request, InspectionEntry entry) {
        QueryWrapper<InspectionEntry> queryWrapper = new QueryWrapper<>();
        List<InspectionEntry> list = null;
        try {
            if (entry.getUnitId() != null){
                queryWrapper.lambda().eq(InspectionEntry::getUnitId,entry.getUnitId());
            }
            if (StringUtils.isNotBlank(entry.getEntryNum())){
                queryWrapper.lambda().eq(InspectionEntry::getEntryNum,entry.getEntryNum());
            }
            if (StringUtils.isNotBlank(entry.getCreateTimeFrom()) || StringUtils.isNotBlank(entry.getCreateTimeTo())){
                queryWrapper.lambda()
                        .ge(InspectionEntry::getCreateTime,entry.getCreateTimeFrom())
                        .le(InspectionEntry::getCreateTime,entry.getCreateTimeTo());
            }
            list = this.baseMapper.selectList(queryWrapper);
        }catch (Exception e){
            log.error("获取条目列表失败",e);
            list = null;
        }
        return list;
    }

    @Override
    public void createEntry(InspectionEntry entry) {
        entry.setCreateTime(new Date());
        entry.setModifyTime(new Date());
        this.save(entry);
    }

    @Override
    public void updateEntry(InspectionEntry entry) {
        entry.setModifyTime(new Date());
        this.baseMapper.updateById(entry);
    }

    @Override
    public void deleteEntries(String[] entryIds) {
        this.removeByIds(Arrays.asList(entryIds));
    }

    @Override
    public List<InspectionEntry> getEntriesByUnitId(String unitId) {
        QueryWrapper<InspectionEntry> queryWrapper = new QueryWrapper<>();
        List<InspectionEntry> list = null;
        try{
            queryWrapper.lambda().eq(StringUtils.isNotBlank(unitId),InspectionEntry::getUnitId,Long.valueOf(unitId));
            queryWrapper.lambda().eq(InspectionEntry::getEntryStatus,"1");
            list = this.baseMapper.selectList(queryWrapper);
        }catch (Exception e){
            log.error("获取条目列表失败",e);
            list = null;
        }
        return list;

    }
}
