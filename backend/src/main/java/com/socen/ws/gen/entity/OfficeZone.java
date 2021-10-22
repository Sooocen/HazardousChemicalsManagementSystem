package com.socen.ws.gen.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
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
@TableName("t_office_zone")
@ApiModel(value="OfficeZone对象", description="")
public class OfficeZone implements Serializable {

    private static final long serialVersionUID = 1L;

        @TableField("OFFICE_ID")
    private Long officeId;

        @TableField("ZONE_ID")
    private Long zoneId;


}
