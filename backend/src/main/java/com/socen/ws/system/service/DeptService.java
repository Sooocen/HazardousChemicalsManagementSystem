package com.socen.ws.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.socen.ws.common.domain.QueryRequest;
import com.socen.ws.system.domain.Dept;

import java.util.List;
import java.util.Map;

public interface DeptService extends IService<Dept> {

    Map<String, Object> findDepts(QueryRequest request, Dept dept);

    List<Dept> findDepts(Dept dept, QueryRequest request);

    void createDept(Dept dept);

    void updateDept(Dept dept);

    void deleteDepts(String[] deptIds);
}
