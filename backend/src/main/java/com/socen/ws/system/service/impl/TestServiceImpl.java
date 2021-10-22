package com.socen.ws.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.socen.ws.system.dao.TestMapper;
import com.socen.ws.system.domain.Test;
import com.socen.ws.system.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("testService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements TestService {

    @Value("${ws.max.batch.insert.num}")
    private int batchInsertMaxNum;

    @Override
    public List<Test> findTests() {
        try {
            return baseMapper.selectList(new QueryWrapper<Test>().orderByDesc("create_time"));
        } catch (Exception e) {
            log.error("获取信息失败", e);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public void batchInsert(List<Test> list) {
        saveBatch(list, batchInsertMaxNum);
    }
}
