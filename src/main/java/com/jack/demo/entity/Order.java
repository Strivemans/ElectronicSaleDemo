package com.jack.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * \* User: jack
 * \* Date: 2022/10/18
 * \* Time: 8:14
 * \* Description: 订单实体类
 */
@Data
@ApiModel("订单类")
@TableName("`order`")
@NoArgsConstructor
public class Order implements Serializable {
    @TableId(value = "order_id",type = IdType.AUTO)
    @ApiModelProperty("订单id")
    private Integer orderId;
    @ApiModelProperty("订单状态")
    private String orderState;
    @ApiModelProperty("订单金额")
    private Double orderMoney;
    @ApiModelProperty("客户Id")
    private Integer clientId;

    @ApiModelProperty("客户名")
    @TableField(exist = false)
    private String clientName;

    @ApiModelProperty("订单产品关联")
    @TableField(exist = false)
    private List<GoodOrder> goodOrderList;

    @ApiModelProperty("产品列表")
    @TableField(exist = false)
    private List<Good> goodList;

    @ApiModelProperty("销售人名")
    private String salerName;
    @ApiModelProperty("销售人id")
    @TableField(exist = false)
    private Integer salerId;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",timezone = "GMT+8")
    private Date orderDate;

    @ApiModelProperty("订单利润")
    @TableField(exist = false)
    private Double profit;

    public Order(Double orderMoney, Integer clientId, String salerName, Date orderDate) {
        this.orderMoney = orderMoney;
        this.clientId = clientId;
        this.salerName = salerName;
        this.orderDate = orderDate;
    }
}
