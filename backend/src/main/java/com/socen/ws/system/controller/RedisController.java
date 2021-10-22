package com.socen.ws.system.controller;


import com.socen.ws.common.domain.RedisInfo;
import com.socen.ws.common.domain.WsResponse;
import com.socen.ws.common.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("redis")
@Api(tags = "Redis信息",description = "Redis信息")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @GetMapping("info")
    @ApiOperation("获取Redis信息")
    public WsResponse getRedisInfo() throws Exception {
        List<RedisInfo> infoList = this.redisService.getRedisInfo();
        return new WsResponse().data(infoList);
    }

    @GetMapping("keysSize")
    @ApiOperation("获取RedisKey")
    public Map<String, Object> getKeysSize() throws Exception {
        return redisService.getKeysSize();
    }

    @GetMapping("memoryInfo")
    @ApiOperation("获取Redis内存信息")
    public Map<String, Object> getMemoryInfo() throws Exception {
        return redisService.getMemoryInfo();
    }
}
