package com.jack.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * \* User: jack
 * \* Date: 2022/10/18
 * \* Time: 8:31
 * \* Description: 客户类
 */
@Data
@ApiModel("客户类")
@TableName("client")
@NoArgsConstructor
public class Client {
    @TableId(value = "client_id",type = IdType.AUTO)
    @ApiModelProperty("客户id")
    private Integer clientId;
    @ApiModelProperty("客户名")
    private String clientName;
    @ApiModelProperty("客户电话")
    private String clientTel;
    @ApiModelProperty("客户vip")
    private Integer clientRank;
    @ApiModelProperty("等级名")
    @TableField(exist = false)
    private String rankName;
    @ApiModelProperty("客户地址")
    private String clientAddress;
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createDate;

    public Client(String clientName, String clientTel, Integer clientRank, String clientAddress) {
        this.clientName = clientName;
        this.clientTel = clientTel;
        this.clientRank = clientRank;
        this.clientAddress = clientAddress;
    }
}
