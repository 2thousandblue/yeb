package com.blue.server.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * HR登录实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain=true)
@ApiModel(value = "HRLogin对象")
public class HrLoginData {
    @ApiModelProperty(value = "用户名",required = true)
    private String username;
    @ApiModelProperty(value = "密码",required = true)
    private String password;

    @ApiModelProperty(value = "验证码",required = true)
    private String code;
}
