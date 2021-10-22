package com.socen.ws.gen.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.Date;

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
@TableName("t_inspection_entry")
@ApiModel(value = "InspectionEntry对象", description = "检查条目")
@Excel("检查条目")
public class InspectionEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "条目id")
    @TableId(value = "Entry_id", type = IdType.AUTO)
    private Long entryId;

    @ApiModelProperty(value = "单元id")
    @TableField("Unit_id")
    private Long unitId;

    @ExcelField("单元")
    private transient String unitName;

    @ExcelField(value = "条目编号")
    @ApiModelProperty(value = "条目编号")
    @TableField("Entry_num")
    private String entryNum;

    @ExcelField(value = "检查内容")
    @ApiModelProperty(value = "检查内容")
    @TableField("Entry_content")
    private String entryContent;

    @ExcelField(value = "检查依据")
    @ApiModelProperty(value = "检查依据")
    @TableField("Entry_basis")
    private String entryBasis;

    @ExcelField(value = "分数")
    @ApiModelProperty(value = "分数")
    @TableField("Entry_score")
    private Double entryScore;

    @ExcelField(value = "是否否决 1 否 0 是")
    @ApiModelProperty(value = "是否否决 1 否 0 是")
    @TableField("Entry_veto")
    private String entryVeto;

    @ExcelField(value = "状态 1 可用 0 过期")
    @ApiModelProperty(value = "状态 1 可用 0 过期")
    @TableField("Entry_status")
    private String entryStatus;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("MODIFY_TIME")
    private Date modifyTime;

    private transient String createTimeFrom;

    private transient String createTimeTo;
}
