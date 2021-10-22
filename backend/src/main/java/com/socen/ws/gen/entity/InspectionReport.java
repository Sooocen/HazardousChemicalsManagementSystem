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
@TableName("t_inspection_report")
@ApiModel(value = "检查报表对象", description = "检查报表")
@Excel("检查报表")
public class InspectionReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "报表id")
    @TableId(value = "Report_id", type = IdType.INPUT)
    private Long reportId;

    @ApiModelProperty(value = "企业id")
    @TableField("Company_id")
    private Long companyId;

    @ExcelField(value = "企业")
    private transient String companyName;

    @ApiModelProperty(value = "检查机构")
    @TableField("Office_id")
    private String officeId;

    @ExcelField("检查机构")
    private transient String officeName;

    @ApiModelProperty(value = "检查类型 1 例行检查 2 突击检查 3 特殊日期检查")
    @TableField("Report_type")
    @ExcelField(value = "检查类型")
    private String reportType;

    @ApiModelProperty(value = "是否自查 1 是 2 否")
    @TableField("Report_self")
    @ExcelField("是否自查")
    private String reportSelf;

    @ApiModelProperty(value = "检查人")
    @TableField("Report_person")
    @ExcelField(value = "检查人")
    private String reportPerson;

    @ApiModelProperty(value = "最后得分")
    @TableField("Report_score")
    @ExcelField(value = "得分")
    private Double reportScore;

    @ApiModelProperty(value = "检查时间")
    @TableField("Create_Time")
    @ExcelField(value = "检查时间")
    private Date createTime;

    private transient String createTimeFrom;

    private transient String createTimeTo;

}
