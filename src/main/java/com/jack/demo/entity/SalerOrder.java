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
 * \* Date: 2022/10/20
 * \* Time: 19:56
 * \* Description: 销售员 与 订单的关联表
 */
@Data
@ApiModel("销售员与订单关联表")
@TableName("saler_order")
@NoArgsConstructor
public class SalerOrder {
    @ApiModelProperty("id")
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("销售员id")
    private Integer salerId;
    @ApiModelProperty("订单id")
    private Integer orderId;

    @ApiModelProperty("销售员名")
    @TableField(exist = false)
    private String salerName;

    @ApiModelProperty("提成")
    @TableField(exist = false)
    private Double profit;

    @ApiModelProperty("时间")
    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date  createTime;


    public SalerOrder(Integer salerId, Integer orderId) {
        this.salerId = salerId;
        this.orderId = orderId;
    }
}
