package com.jack.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * \* User: jack
 * \* Date: 2022/10/21
 * \* Time: 22:44
 * \* Description: 售后类
 */
@Data
@ApiModel("销售类")
@TableName("after_sales")
@NoArgsConstructor
public class AfterSales implements Serializable {
    @ApiModelProperty("售后id")
    @TableId(value = "after_id",type = IdType.AUTO)
    private Integer afterId;
    @ApiModelProperty("售后问题")
    private String afterProblem;
    @ApiModelProperty("售后状态")
    private String afterState;

    @ApiModelProperty("客户id")
    private Integer clientId;
    @ApiModelProperty("客户名")
    @TableField(exist = false)
    private String clientName;

    @ApiModelProperty("销售员id")
    private Integer salerId;
    @ApiModelProperty("销售员名")
    @TableField(exist = false)
    private String salerName;

    @ApiModelProperty("订单id")
    private Integer orderId;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",timezone = "GMT+8")
    private Date createTime;
    @ApiModelProperty("结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",timezone = "GMT+8")
    private Date endTime;

    public AfterSales(String afterProblem, String afterState, Integer clientId, String clientName, Integer salerId, String salerName, Integer orderId, Date createTime) {
        this.afterProblem = afterProblem;
        this.afterState = afterState;
        this.clientId = clientId;
        this.clientName = clientName;
        this.salerId = salerId;
        this.salerName = salerName;
        this.orderId = orderId;
        this.createTime = createTime;
    }
}
