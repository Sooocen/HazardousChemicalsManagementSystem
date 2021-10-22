package com.socen.ws.gen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;



import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.Date;

import com.socen.ws.common.converter.TimeConverter;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @author Socen
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_company")
@ApiModel(value = "Company对象", description = "企业实体类")
@Excel("企业信息表")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 企业性质
     */
    public static final String NATURE_ADMINISTRATIVE = "1";

    public static final String NATURE_PARTNERSHIP = "2";

    @TableId(value = "Company_id", type = IdType.AUTO)
    private Long companyId;


    @ApiModelProperty(value = "所属区域id")
    @TableField("Zone_id")
    private Long zoneId;
    @ExcelField(value = "所属区域",writeConverterExp = "xxx")
    private transient String zoneName;


    @ApiModelProperty(value = "安监安监局id")
    @TableField("Office_id")
    private Long officeId;
    @ExcelField(value = "所属安监局",writeConverterExp = "xxx")
    private transient String officeName;

    @ExcelField(value = "企业名称",writeConverterExp = "xxx")
    @ApiModelProperty(value = "企业名称")
    @TableField("Company_name")
    private String companyName;

    @ExcelField(value = "统一信用码",writeConverterExp = "xxx")
    @ApiModelProperty(value = "统一信用码")
    @TableField("Unified_credit_code")
    private String unifiedCreditCode;

    @ExcelField(value = "地址",writeConverterExp = "xxx")
    @ApiModelProperty(value = "地址")
    @TableField("Company_addr")
    private String companyAddr;

    @NotBlank(message = "{required}")
    @ExcelField(value = "企业性质",writeConverterExp = "xxx")
    @ApiModelProperty(value = "企业性质")
    @TableField("Company_nature")
    private String companyNature;

    @ExcelField(value = "法人代表名字",writeConverterExp = "xxx")
    @ApiModelProperty(value = "法人代表名字")
    @TableField("Company_Legal_person")
    private String companyLegalPerson;

    @ExcelField(value = "电话号码")
    @ApiModelProperty(value = "电话号码")
    @TableField("Company_te")
    private String companyTe;

    @ExcelField(value = "传真号码")
    @ApiModelProperty(value = "传真号码")
    @TableField("Company_fax")
    private String companyFax;

    @ExcelField(value = "电邮")
    @ApiModelProperty(value = "电邮")
    @TableField("Company_email")
    private String companyEmail;

    @ExcelField(value = "生产许可编码")
    @ApiModelProperty(value = "生产许可编码")
    @TableField("Company_license")
    private String companyLicense;

    @ExcelField(value = "企业人数")
    @ApiModelProperty(value = "企业人数")
    @TableField("Company_people")
    private Integer companyPeople;

    @ExcelField(value = "企业简介")
    @ApiModelProperty(value = "企业简介")
    @TableField("Company_memo")
    private String companyMemo;

    @ExcelField(value = "企业类别")
    @ApiModelProperty(value = "企业类别")
    @TableField("Company_type")
    private String companyType;

    @ExcelField(value = "经度")
    @ApiModelProperty(value = "经度")
    @TableField("Company_lon")
    private Double companyLon;

    @ExcelField(value = "维度")
    @ApiModelProperty(value = "维度")
    @TableField("Company_lat")
    private Double companyLat;

    @ExcelField(value = "创建时间" , writeConverter = TimeConverter.class)
    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private Date createTime;

    @ExcelField(value = "修改时间" , writeConverter = TimeConverter.class)
    @ApiModelProperty(value = "修改时间")
    @TableField("MODIFY_TIME")
    private Date modifyTime;


    private transient String unitId;

    @ExcelField(value = "检查单元")
    private transient String unitName;

    private transient String createTimeFrom;

    private transient String createTimeTo;

}
