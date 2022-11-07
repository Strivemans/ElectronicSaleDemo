package com.jack.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * \* User: jack
 * \* Date: 2022/10/20
 * \* Time: 14:35
 * \* Description: 产品订单关联类
 */
@Data
@ApiModel("产品订单关联类")
@TableName("good_order")
@NoArgsConstructor
public class GoodOrder {
    @ApiModelProperty("id")
    @TableId(value = "go_id",type = IdType.AUTO)
    private Integer goId;

    @ApiModelProperty("产品id")
    private Integer goodId;
    @ApiModelProperty("订单id")
    private Integer orderId;
    @ApiModelProperty("每个产品购买的数量")
    private Integer num;
    @ApiModelProperty("缺货数额")
    private Integer stockOut;
    @ApiModelProperty("每个产品购买的数额")
    private Double price;

    public GoodOrder(Integer goodId, Integer orderId, Integer num, Double price) {
        this.goodId = goodId;
        this.orderId = orderId;
        this.num = num;
        this.price = price;
    }

    public GoodOrder(Integer goodId, Integer orderId, Integer num, Integer stockOut, Double price) {
        this.goodId = goodId;
        this.orderId = orderId;
        this.num = num;
        this.stockOut = stockOut;
        this.price = price;
    }
}
