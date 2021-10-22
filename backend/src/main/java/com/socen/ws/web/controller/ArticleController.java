package com.socen.ws.web.controller;

import com.socen.ws.common.domain.WsConstant;
import com.socen.ws.common.domain.WsResponse;
import com.socen.ws.common.exception.WsException;
import com.socen.ws.common.utils.HttpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("article")
@Api(tags = "文章获取",description = "文章获取")
public class ArticleController {

    @GetMapping
    @RequiresPermissions("article:view")
    @ApiOperation("文章获取")
    public WsResponse queryArticle(String date) throws WsException {
        String param;
        String data;
        try {
            if (StringUtils.isNotBlank(date)) {
                param = "dev=1&date=" + date;
                data = HttpUtil.sendSSLPost(WsConstant.MRYW_DAY_URL, param);
            } else {
                param = "dev=1";
                data = HttpUtil.sendSSLPost(WsConstant.MRYW_TODAY_URL, param);
            }
            return new WsResponse().data(data);
        } catch (Exception e) {
            String message = "获取文章失败";
            log.error(message, e);
            throw new WsException(message);
        }
    }
}
