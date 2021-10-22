package com.socen.ws.gen.service;

import com.socen.ws.gen.entity.OfficeZone;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Socen
 */
public interface IOfficeZoneService extends IService<OfficeZone> {

    /**
     * 通过安监局Id删除所管理区域关系
     * @param officeIds
     */
    void deleteOfficeZoneByOfficeId(String[] officeIds);

    /**
     * 通过地区Id删除所属安监局关系
     * @param zoneIds
     */
    void deleteOfficeZoneByZoneId(String[] zoneIds);
}
