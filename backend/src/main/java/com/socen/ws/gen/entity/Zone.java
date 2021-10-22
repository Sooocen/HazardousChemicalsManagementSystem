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
@TableName("t_zone")
@ApiModel(value = "Zone对象", description = "地区实体类")
@Excel("地区信息表")
public class Zone implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "区域ID")
    @TableId(value = "ZONE_ID", type = IdType.AUTO)
    private Long zoneId;

    @ApiModelProperty(value = "上级区域id")
    @TableField("PARENT_ID")
    private Long parentId;

    @ExcelField(value = "区域名称")
    @ApiModelProperty(value = "区域名")
    @TableField("ZONE_NAME")
    private String zoneName;

    @ApiModelProperty(value = "排序")
    @TableField("ZONE_NUM")
    private Double zoneNum;

    @ExcelField(value = "创建时间", writeConverter = TimeConverter.class)
    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private Date createTime;

    @ExcelField(value = "修改时间", writeConverter = TimeConverter.class)
    @ApiModelProperty(value = "修改时间")
    @TableField("MODIFY_TIME")
    private Date modifyTime;

    @ExcelField(value = "区域范围坐标点的集合")
    @ApiModelProperty(value = "区域范围点的集合（以括号逗号为一组（p1,p2）,(..,..),...）")
    @TableField("POINTS")
    private String points;

    private transient String createTimeFrom;

    private transient String createTimeTo;
}
