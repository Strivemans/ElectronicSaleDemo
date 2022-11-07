package com.jack.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * \* User: jack
 * \* Date: 2022/10/15
 * \* Time: 14:43
 * \* Description: 账户类
 */
@Data
@ApiModel(value = "账户对象",description = "账号")
@TableName(value = "account")
@NoArgsConstructor
public class Account {
    @TableId(value = "id",type = IdType.AUTO)
    @ApiModelProperty("用户id")
    private Integer Id;
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("客户id")
    private Integer salerId;

    public Account(String userName, String password, Integer salerId) {
        this.userName = userName;
        this.password = password;
        this.salerId = salerId;
    }
}
