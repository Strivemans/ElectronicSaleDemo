package com.jack.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * \* User: jack
 * \* Date: 2022/10/15
 * \* Time: 15:26
 * \* Description: 员工类
 */
@Data
@ApiModel(value = "员工对象",description = "员工")
@TableName(value = "saler")
@NoArgsConstructor
public class Saler {
    @TableId(value = "saler_id",type = IdType.AUTO)
    @ApiModelProperty("id")
    private Integer salerId;
    @ApiModelProperty("姓名")
    private String  salerName;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("签名")
    private String signatur;
    @ApiModelProperty("账户id")
    private Integer accountId;
    @ApiModelProperty("电话")
    private String salerTel;
    @ApiModelProperty("权限id")
    private Integer permissionId;
    @ApiModelProperty("职位")
    @TableField(exist = false)
    private String position;
    @ApiModelProperty("头像地址")
    private String avatarUrl;

    public Saler(String salerName,String address,String salerTel,Integer permissionId){
        this.salerName = salerName;
        this.address = address;
        this.salerTel = salerTel;
        this.permissionId = permissionId;
    }
}
