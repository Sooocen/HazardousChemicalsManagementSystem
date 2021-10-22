package com.socen.ws.gen.dao;

import com.socen.ws.gen.entity.CompanyUnit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.socen.ws.gen.entity.InspectionUnit;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Socen
 */
@Repository
public interface CompanyUnitMapper extends BaseMapper<CompanyUnit> {

    /**
     * 根据企业Id删除该企业的检查单元关系
     * @param companyId
     * @return
     */
    Boolean deleteByCompanyId(@Param("companyId") Long companyId);

    /**
     * 根据检查单元Id删除该检查单元的企业关系s
     * @param unitId
     * @return
     */
    Boolean deleteByUnitId(@Param("unitId") Long unitId);

    /**
     * 根据企业Id查询检查单元
     * @param companyId
     * @return
     */
    @Select("SELECT tu.* FROM t_company_unit cu," +
            "t_inspection_unit tu WHERE cu.Company_id = #{company} and cu.Unit_id = tu.Unit_id")
    List<InspectionUnit> getUnitsByCompanyId(@Param("companyId") Long companyId);
}
