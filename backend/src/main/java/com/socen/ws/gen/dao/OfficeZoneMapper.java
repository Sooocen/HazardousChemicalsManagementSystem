package com.socen.ws.gen.dao;

import com.socen.ws.gen.entity.OfficeZone;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.lettuce.core.dynamic.annotation.Param;

/**
 * @author Socen
 */
public interface OfficeZoneMapper extends BaseMapper<OfficeZone> {

    /**
     * 根据安监局Id删除该安监局的管理区域关系
     * @param officeId
     * @return
     */
    Boolean deleteByOfficeId(@Param("officeId") Long officeId);

    /**
     * 根据地区Id删除与安监局关联的关系
     * @param zoneId
     * @return
     */
    Boolean deleteByZoneId(@Param("zoneId") Long zoneId);
}
