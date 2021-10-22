package com.socen.ws.gen.service.impl;

import com.socen.ws.common.domain.QueryRequest;
import com.socen.ws.gen.entity.InspectionReport;
import com.socen.ws.gen.dao.InspectionReportMapper;
import com.socen.ws.gen.service.IInspectionReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
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
@Service("IInspectionReportService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class InspectionReportServiceImpl extends ServiceImpl<InspectionReportMapper, InspectionReport> implements IInspectionReportService {

    @Override
    public List<InspectionReport> findReports(QueryRequest request, InspectionReport report) {
        List<InspectionReport> list = null;
        try {
            list = this.baseMapper.findReports(report);
        }catch (Exception e){
            log.error("获取报表列表失败",e);
            list = null;
        }
        return list;
    }

    @Override
    public void createReport(InspectionReport report) {
        report.setCreateTime(new Date());
        this.save(report);
    }

    @Override
    public void updateReport(InspectionReport report) {
        this.baseMapper.updateById(report);
    }

    @Override
    public void deleteReport(String[] reportIds) {
        this.removeByIds(Arrays.asList(reportIds));
    }

    @Override
    public Long getNextId() {
        Long id = this.baseMapper.getMaxId();
        if (id == null){
            return new Long(1);
        }
        return id + 1;
    }
}
