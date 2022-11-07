package com.jack.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * \* User: jack
 * \* Date: 2022/10/23
 * \* Time: 10:48
 * \* Description: 更新账号信息用
 */
@Data
@ApiModel("个人中心更新")
public class UpdateAccount {
    @ApiModelProperty("销售员id")
    private Integer salerId;
    @ApiModelProperty("账号id")
    private Integer accountId;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("签名")
    private String signatur;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("电话")
    private String telephone;
    @ApiModelProperty("用户名")
    private String accountName;
    @ApiModelProperty("密码")
    private String password;
}
