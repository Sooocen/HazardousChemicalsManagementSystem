package com.socen.ws.gen.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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

/**
 * @author Socen
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_office")
@ApiModel(value = "Office对象", description = "安检部门")
@Excel("安监部门信息")
public class Office implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "安监局ID")
    @TableId(value = "OFFICE_ID", type = IdType.AUTO)
    private Long officeId;

    @ExcelField(value = "安监局名称", writeConverterExp = "xxx")
    @ApiModelProperty(value = "安监局名称")
    @TableField("OFFICE_NAME")
    private String officeName;

    @ApiModelProperty(value = "排序")
    @TableField("OFFICE_NUM")
    private Double officeNum;

    @ExcelField(value = "创建时间", writeConverter = TimeConverter.class)
    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private Date createTime;

    @ExcelField(value = "修改时间", writeConverter = TimeConverter.class)
    @ApiModelProperty(value = "修改时间")
    @TableField("MODIFY_TIME")
    private Date modifyTime;

    private transient String createTimeFrom;

    private transient String createTimeTo;

}
