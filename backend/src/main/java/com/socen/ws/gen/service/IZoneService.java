package com.socen.ws.gen.service;

import com.socen.ws.common.domain.QueryRequest;
import com.socen.ws.common.exception.WsException;
import com.socen.ws.gen.entity.Zone;
import com.baomidou.mybatisplus.extension.service.IService;
import com.socen.ws.gen.entity.model.Option;

import java.util.List;
import java.util.Map;

/**
 * @author Socen
 */
public interface IZoneService extends IService<Zone> {

    /**
     * 根据条件查询所有地区
     * @param request
     * @param zone
     * @return
     */
    Map<String, Object> findZones(QueryRequest request,Zone zone);

    /**
     * 根据条件查询所有地区
     * @param zone
     * @param request
     * @return
     */
    List<Zone> findZones(Zone zone,QueryRequest request);

    /**
     * 添加地区
     * @param zone
     */
    void createZone(Zone zone);

    /**
     * 修改地区
     * @param zone
     */
    void updateZone(Zone zone);

    /**
     * 删除区域
     * @param zoneIds
     * @throws WsException
     */
    void deleteZones(String[] zoneIds) throws WsException;

    /**
     * 通过用户名查询地区
     * @param username
     * @return
     */
    List<Zone> findZoneByUsername(String username);

    /**
     * 构建区域企业联动数据
     * @param username
     * @return
     */
    List<Option> findZoneCompanyOptionByUsername(String username);
}
