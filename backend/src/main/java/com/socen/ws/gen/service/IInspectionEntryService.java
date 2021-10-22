package com.socen.ws.gen.service;

import com.socen.ws.common.domain.QueryRequest;
import com.socen.ws.gen.entity.InspectionEntry;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Socen
 */
public interface IInspectionEntryService extends IService<InspectionEntry> {

    /**
     * 根据条件获取条目
     * @param request
     * @param entry
     * @return
     */
    List<InspectionEntry> findEntries(QueryRequest request, InspectionEntry entry);

    /**
     * 添加条目
     * @param entry
     */
    void createEntry(InspectionEntry entry);

    /**
     * 修改条目
     * @param entry
     */
    void updateEntry(InspectionEntry entry);

    /**
     * 删除条目（批量）
     * @param entryIds
     */
    void deleteEntries(String [] entryIds);

    /**
     * 根据单元id查询检查条目
     * @return
     */
    List<InspectionEntry> getEntriesByUnitId(String unitId);
}
