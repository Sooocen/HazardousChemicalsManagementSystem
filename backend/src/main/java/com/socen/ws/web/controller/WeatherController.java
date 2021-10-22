package com.socen.ws.web.controller;

import com.socen.ws.common.domain.WsConstant;
import com.socen.ws.common.domain.WsResponse;
import com.socen.ws.common.exception.WsException;
import com.socen.ws.common.utils.HttpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@Slf4j
@Validated
@RestController
@RequestMapping("weather")
@Api(tags = "天气信息",description = "天气信息")
public class WeatherController {

    @GetMapping
    @RequiresPermissions("weather:view")
    @ApiOperation("天气查询")
    public WsResponse queryWeather(@NotBlank(message = "{required}") String areaId) throws WsException {
        try {
            String data = HttpUtil.sendPost(WsConstant.MEIZU_WEATHER_URL, "cityIds=" + areaId);
            return new WsResponse().data(data);
        } catch (Exception e) {
            String message = "天气查询失败";
            log.error(message, e);
            throw new WsException(message);
        }
    }
}

