package com.socen.ws.gen.dao;

import com.socen.ws.gen.entity.Zone;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author Socen
 */

public interface ZoneMapper extends BaseMapper<Zone> {

    /**
     * 通过用户名查找所管理地区
     * @param username
     * @return
     */
    List<Zone> findZoneByUsername(String username);
}
