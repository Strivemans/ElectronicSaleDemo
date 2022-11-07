package com.jack.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * \* User: jack
 * \* Date: 2022/10/15
 * \* Time: 15:50
 * \* Description: 专门用于接收 注册信息的对象
 */
@Data
@ApiModel("登录数据对象")
@AllArgsConstructor
@NoArgsConstructor
public class Register {
    @ApiModelProperty("账号名")
    private String accountName;
    @ApiModelProperty("销售员名")
    private String salerName;
    @ApiModelProperty("销售员地址")
    private String salerAddress;
    @ApiModelProperty("账号密码")
    private String accountPwd;
    @ApiModelProperty("销售员电话")
    private String salerTel;
}
