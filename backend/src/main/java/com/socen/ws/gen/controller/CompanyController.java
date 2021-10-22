package com.socen.ws.gen.controller;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.socen.ws.common.annotation.Log;
import com.socen.ws.common.domain.QueryRequest;
import com.socen.ws.common.exception.WsException;
import com.socen.ws.gen.entity.*;
import com.socen.ws.gen.service.ICompanyService;
import com.socen.ws.gen.service.ICompanyUnitService;
import com.socen.ws.gen.service.IInspectionEntryService;
import com.socen.ws.gen.entity.Company;
import com.socen.ws.gen.entity.InspectionEntry;
import com.socen.ws.gen.entity.InspectionUnit;
import com.wuwenze.poi.ExcelKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.socen.ws.common.controller.BaseController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Socen
 */
@Slf4j
@Validated
@RestController
@RequestMapping("company")
@Api(tags = "企业管理",description = "企业管理")
public class CompanyController extends BaseController {

    private String message;

    @Autowired
    private ICompanyUnitService companyUnitService;
    @Autowired
    private ICompanyService companyService;
    @Autowired
    private IInspectionEntryService entryService;

    @Log("获取企业列表")
    @GetMapping
    @RequiresPermissions("company:view")
    @ApiOperation("获取企业列表")
    public Map<String,Object> companyList(QueryRequest request, Company company){
        return getDataTable(companyService.findCompanyDetail(company,request));
    }

    @Log("新增企业")
    @PostMapping("/addCompany")
    @RequiresPermissions("company:add")
    @ApiOperation("新增企业")
    public void addCompany(@Valid Company company) throws WsException{
        try{
            this.companyService.createCompany(company);
        } catch (Exception e){
            message = "新增企业失败";
            log.error(message,e);
            throw new WsException(message);
        }
    }




    @Log("为企业添加检查单元")
    @PostMapping("/addUnit")
    @RequiresPermissions("company:addUnit")
    @ApiOperation("为企业添加检查单元")
    public void addUnit( Company company) throws WsException{
        try{
            this.companyService.addUnits(company);
        }catch (Exception e){
            message = "添加检查单元失败";
            log.error(message,e);
            throw new WsException(message);
        }
    }

    @Log("删除企业")
    @DeleteMapping("/{companyIds}")
    @RequiresPermissions("company:delete")
    @ApiOperation("删除企业")
    public void deleteCompanies(@NotBlank(message = "{required}") @PathVariable String companyIds) throws WsException{
        try {
            String[] ids = companyIds.split(StringPool.COMMA);
            this.companyService.deleteCompanies(ids);
        }catch (Exception e){
            message = "删除企业失败";
            log.error(message,e);
            throw new WsException(message);
        }
    }

    @Log("修改企业")
    @PutMapping
    @RequiresPermissions("company:update")
    @ApiOperation("修改企业")
    public void update(Company company) throws WsException{
        try{
            this.companyService.updateCompany(company);
        }catch (Exception e){
            message = "修改企业失败";
            log.error(message,e);
            throw new WsException(message);
        }
    }



    @Log("导出")
    @PostMapping("excel")
    @RequiresPermissions("company:export")
    @ApiOperation("导出")
    public void export(Company company, QueryRequest request, HttpServletResponse response) throws WsException{
        try{
            List<Company> list = this.companyService.findCompanies(request,company);
            ExcelKit.$Export(Company.class,response).downXlsx(list,false);
        }catch (Exception e){
            message = "导出Excel失败";
            log.error(message,e);
            throw new WsException(message);
        }
    }

    @Log("根据企业id获取总记录数")
    @GetMapping("/getRecordSum/{companyId}")
    @RequiresPermissions("company:recordSum")
    @ApiOperation("根据企业id获取总记录数")
    public Map<String,Object> getRecordSum(@NotBlank(message = "{required}") @PathVariable String companyId) throws WsException{
        Map<String,Object> result = new HashMap<>();
        int recordSum = 0;
        try {
            List<InspectionUnit> units = companyUnitService.getUnitsCompanyId(Long.valueOf(companyId));
            for (int i = 0; i < units.size(); i++) {
                List<InspectionEntry> entries = entryService.getEntriesByUnitId(units.get(i).getUnitId().toString().trim());
                if (entries != null){
                    recordSum += entries.size();
                }
            }
            result.put("recordNum",recordSum);
        }catch (Exception e){
            message = "获取失败";
            log.error(message,e);
            result.put("recordNum",0);
            throw new WsException(message);
        }
        return result;
    }

    @Log("根据企业id企业详情")
    @GetMapping("/getCompany/{companyId}")
    @RequiresPermissions("company:detail")
    @ApiOperation("根据企业id企业详情")
    public Map<String,Object> getCompany(@NotBlank(message = "{required}") @PathVariable String companyId) throws WsException{
        Map<String,Object> result = new HashMap<>();
        try {
            Company company = companyService.getBaseMapper().selectById(companyId);
            result.put("company",company);
        }catch (Exception e){
            message = "获取失败";
            log.error(message,e);
            result.put("recordNum",null);
            throw new WsException(message);
        }
        return result;
    }
}
