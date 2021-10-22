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
@TableName("t_inspection_unit")
@ApiModel(value = "InspectionUnit对象", description = "检查单元")
@Excel("检查单元")
public class InspectionUnit implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = " 单元id")
    @TableId(value = "Unit_id", type = IdType.AUTO)
    private Long unitId;

    @ExcelField(value = "单元编号", writeConverterExp = "")
    @ApiModelProperty(value = "单元编号")
    @TableField("Unit_Num")
    private String unitNum;

    @ExcelField(value = "单元名称", writeConverterExp = "")
    @ApiModelProperty(value = "单元名称")
    @TableField("Unit_name")
    private String unitName;

    @ExcelField(value = "权重", writeConverterExp = "")
    @ApiModelProperty(value = "权重")
    @TableField("Unit_weight")
    private Double unitWeight;

    @ExcelField(value = "分数",writeConverterExp = "")
    @ApiModelProperty(value = "分数")
    @TableField("Unit_score")
    private Double unitScore;

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
