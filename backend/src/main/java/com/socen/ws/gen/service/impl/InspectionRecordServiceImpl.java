package com.socen.ws.gen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.socen.ws.gen.entity.InspectionRecord;
import com.socen.ws.gen.dao.InspectionRecordMapper;
import com.socen.ws.gen.service.IInspectionRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Socen
 */
@Service("IInspectionRecordService")
public class InspectionRecordServiceImpl extends ServiceImpl<InspectionRecordMapper, InspectionRecord> implements IInspectionRecordService {

    @Override
    public void createRecord(InspectionRecord record) {
        record.setCreateTime(new Date());
        this.save(record);
    }

    @Override
    public List<InspectionRecord> getRecordsByReportId(String reportId) {
        QueryWrapper<InspectionRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(StringUtils.isNotBlank(reportId),InspectionRecord::getReportId,Long.valueOf(reportId));
        return this.baseMapper.selectList(queryWrapper);
    }
}
