package com.socen.ws.gen.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *
 *
 * @author Socen
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_inspection_record")
@ApiModel(value="InspectionRecord对象", description="")
public class InspectionRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "检查记录id")
            @TableId(value = "Record_id", type = IdType.AUTO)
    private Long recordId;

    @ApiModelProperty(value = "报表id")
        @TableField("Report_id")
    private Long reportId;

    @ApiModelProperty(value = "检查方式 1 查资料 2 现场测试 3 询问技术专家")
        @TableField("Record_mode")
    private String recordMode;

    @ApiModelProperty(value = "隐患分类 1 符合项 2 一般隐患 3 重大隐患")
        @TableField("Record_class")
    private String recordClass;

    @ApiModelProperty(value = "现场情况描述")
        @TableField("Record_desc")
    private String recordDesc;

    @ApiModelProperty(value = "条款")
        @TableField("Record_terms")
    private String recordTerms;

    @ApiModelProperty(value = "检查内容")
        @TableField("Record_content")
    private String recordContent;

    @ApiModelProperty(value = "检查依据")
        @TableField("Record_basis")
    private String recordBasis;

    @ApiModelProperty(value = "检查时间")
        @TableField("CREATE_TIME")
    private Date createTime;


}
