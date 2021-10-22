package com.socen.ws.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.socen.ws.common.controller.BaseController;
import com.socen.ws.common.domain.QueryRequest;
import com.socen.ws.common.domain.WsResponse;
import com.socen.ws.common.exception.WsException;
import com.socen.ws.system.domain.Test;
import com.socen.ws.system.service.TestService;
import com.wuwenze.poi.ExcelKit;
import com.wuwenze.poi.handler.ExcelReadHandler;
import com.wuwenze.poi.pojo.ExcelErrorField;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Slf4j
@RestController
@RequestMapping("test")
@Api(tags = "导入导出测试",description = "导入导出测试")
public class TestController extends BaseController {

    private String message;

    @Autowired
    private TestService testService;

    @GetMapping
    @ApiOperation("显示所有测试数据列")
    public Map<String, Object> findTests(QueryRequest request) {
        Page<Test> page = new Page<>(request.getPageNum(), request.getPageSize());
        return getDataTable(testService.page(page, null));
    }

    /**
     * 生成 Excel导入模板
     */
    @PostMapping("template")
    @ApiOperation("生成Excel导入模板")
    public void generateImportTemplate(HttpServletResponse response) {
        // 构建数据
        List<Test> list = new ArrayList<>();
        IntStream.range(0, 20).forEach(i -> {
            Test test = new Test();
            test.setField1("字段1");
            test.setField2(i + 1);
            test.setField3("mrbird" + i + "@gmail.com");
            list.add(test);
        });
        // 构建模板
        ExcelKit.$Export(Test.class, response).downXlsx(list, true);
    }

    /**
     * 导入Excel数据，并批量插入 T_TEST表
     */
    @PostMapping("import")
    @ApiOperation("导入Excel数据")
    public WsResponse importExcels(@RequestParam("file") MultipartFile file) throws WsException {
        try {
            if (file.isEmpty()) {
                throw new WsException("导入数据为空");
            }
            String filename = file.getOriginalFilename();
            if (!StringUtils.endsWith(filename, ".xlsx")) {
                throw new WsException("只支持.xlsx类型文件导入");
            }
            // 开始导入操作
            long beginTimeMillis = System.currentTimeMillis();
            final List<Test> data = Lists.newArrayList();
            final List<Map<String, Object>> error = Lists.newArrayList();
            ExcelKit.$Import(Test.class).readXlsx(file.getInputStream(), new ExcelReadHandler<Test>() {
                @Override
                public void onSuccess(int sheet, int row, Test test) {
                    // 数据校验成功时，加入集合
                    test.setCreateTime(new Date());
                    data.add(test);
                }
                @Override
                public void onError(int sheet, int row, List<ExcelErrorField> errorFields) {
                    // 数据校验失败时，记录到 error集合
                    error.add(ImmutableMap.of("row", row, "errorFields", errorFields));
                }
            });
            if (!data.isEmpty()) {
                // 将合法的记录批量入库
                this.testService.batchInsert(data);
            }
            long time = ((System.currentTimeMillis() - beginTimeMillis));
            ImmutableMap<String, Object> result = ImmutableMap.of(
                    "time", time,
                    "data", data,
                    "error", error
            );
            return new WsResponse().data(result);
        } catch (Exception e) {
            message = "导入Excel数据失败," + e.getMessage();
            log.error(message);
            throw new WsException(message);
        }
    }

    /**
     * 导出 Excel
     */
    @PostMapping("export")
    @ApiOperation("导出Excel")
    public void export(HttpServletResponse response) throws WsException {
        try {
            List<Test> list = this.testService.findTests();
            ExcelKit.$Export(Test.class, response).downXlsx(list, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new WsException(message);
        }
    }
}
