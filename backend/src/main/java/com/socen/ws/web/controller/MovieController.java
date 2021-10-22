package com.socen.ws.web.controller;

import com.socen.ws.common.domain.WsConstant;
import com.socen.ws.common.domain.WsResponse;
import com.socen.ws.common.exception.WsException;
import com.socen.ws.common.utils.HttpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@Slf4j
@Validated
@RestController
@RequestMapping("movie")
@Api(tags = "电影信息",description = "电影信息")
public class MovieController {

    private String message;

    @GetMapping("hot")
    @ApiOperation("最热电影获取")
    public WsResponse getMoiveHot() throws WsException {
        try {
            String data = HttpUtil.sendSSLPost(WsConstant.TIME_MOVIE_HOT_URL, "locationId=328");
            return new WsResponse().data(data);
        } catch (Exception e) {
            message = "获取热映影片信息失败";
            log.error(message, e);
            throw new WsException(message);
        }
    }

    @GetMapping("coming")
    @ApiOperation("正在上映电影获取")
    public WsResponse getMovieComing() throws WsException {
        try {
            String data = HttpUtil.sendSSLPost(WsConstant.TIME_MOVIE_COMING_URL, "locationId=328");
            return new WsResponse().data(data);
        } catch (Exception e) {
            message = "获取即将上映影片信息失败";
            log.error(message, e);
            throw new WsException(message);
        }
    }

    @GetMapping("detail")
    @ApiOperation("获取影片详情")
    public WsResponse getDetail(@NotBlank(message = "{required}") String id) throws WsException {
        try {
            String data = HttpUtil.sendSSLPost(WsConstant.TIME_MOVIE_DETAIL_URL, "locationId=328&movieId=" + id);
            return new WsResponse().data(data);
        } catch (Exception e) {
            message = "获取影片详情失败";
            log.error(message, e);
            throw new WsException(message);
        }
    }

    @GetMapping("comments")
    @ApiOperation("获取影片评论")
    public WsResponse getComments(@NotBlank(message = "{required}") String id) throws WsException {
        try {
            String data = HttpUtil.sendSSLPost(WsConstant.TIME_MOVIE_COMMENTS_URL, "movieId=" + id);
            return new WsResponse().data(data);
        } catch (Exception e) {
            message = "获取影片评论失败";
            log.error(message, e);
            throw new WsException(message);
        }
    }
}

