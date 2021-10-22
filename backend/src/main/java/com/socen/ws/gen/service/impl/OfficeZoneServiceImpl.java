package com.socen.ws.gen.service.impl;

import com.socen.ws.gen.entity.OfficeZone;
import com.socen.ws.gen.dao.OfficeZoneMapper;
import com.socen.ws.gen.service.IOfficeZoneService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author Socen
 */
@Service
public class OfficeZoneServiceImpl extends ServiceImpl<OfficeZoneMapper, OfficeZone> implements IOfficeZoneService {

    @Override
    public void deleteOfficeZoneByOfficeId(String[] officeIds) {
        Arrays.stream(officeIds).forEach(id ->{
            baseMapper.deleteByOfficeId(Long.valueOf(id));
        });
    }

    @Override
    public void deleteOfficeZoneByZoneId(String[] zoneIds) {
        Arrays.stream(zoneIds).forEach(id->{
            baseMapper.deleteByZoneId(Long.valueOf(id));
        });
    }
}
