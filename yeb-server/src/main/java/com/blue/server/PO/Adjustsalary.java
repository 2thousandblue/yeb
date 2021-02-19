package com.blue.server.PO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 *
 * </p>
 *
 * @author blue
 * @since 2021-02-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Adjustsalary对象", description="")
public class Adjustsalary implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer eid;

    @ApiModelProperty(value = "调薪日期")
    private LocalDate asDate;

    @ApiModelProperty(value = "调前薪资")
    private Integer beforeSalary;

    @ApiModelProperty(value = "调后薪资")
    private Integer afterSalary;

    @ApiModelProperty(value = "调薪原因")
    private String reason;

    @ApiModelProperty(value = "备注")
    private String remark;


}
