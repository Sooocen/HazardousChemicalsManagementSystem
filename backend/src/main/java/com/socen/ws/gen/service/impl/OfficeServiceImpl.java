package com.socen.ws.gen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.socen.ws.common.domain.QueryRequest;
import com.socen.ws.gen.entity.Office;
import com.socen.ws.gen.dao.OfficeMapper;
import com.socen.ws.gen.service.IOfficeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
@Service("IOfficeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class OfficeServiceImpl extends ServiceImpl<OfficeMapper, Office> implements IOfficeService {

    /**
     * 查询安检部门
     * @param request
     * @param office
     * @return
     */
    @Override
    public List<Office> findOffices(QueryRequest request, Office office) {
        QueryWrapper<Office> queryWrapper = new QueryWrapper<>();
        List<Office> list = null;
        try{
            if (StringUtils.isNotBlank(office.getOfficeName())){
                queryWrapper.lambda().eq(Office::getOfficeName,office.getOfficeNum());
            }
            if (StringUtils.isNotBlank(office.getCreateTimeFrom()) && StringUtils.isNotBlank(office.getCreateTimeTo())){
                queryWrapper.lambda()
                        .ge(Office::getCreateTime,office.getCreateTimeFrom())
                        .le(Office::getCreateTime,office.getCreateTimeTo());
            }
            list = this.baseMapper.selectList(queryWrapper);

        } catch (Exception e){
            log.error("获取安检局列表失败");
            list = null;
        }
        return list;
    }

    @Override
    public void createOffice(Office office) {
        office.setCreateTime(new Date());
        office.setModifyTime(new Date());
        this.save(office);
    }

    @Override
    public void updateOffice(Office office) {
        office.setModifyTime(new Date());
        this.baseMapper.updateById(office);
    }

    @Override
    public void deleteOffices(String[] officeIds) {
        this.removeByIds(Arrays.asList(officeIds));
    }


}
