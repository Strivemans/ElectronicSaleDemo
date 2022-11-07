package com.jack.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * \* User: jack
 * \* Date: 2022/10/22
 * \* Time: 13:54
 * \* Description: 售后类
 */
@Data
@ApiModel("售后详情类")
public class AfterDetail implements Serializable {
    @ApiModelProperty("售后id")
    private Integer afterId;
    @ApiModelProperty("售后信息")
    private AfterSales afterSales;
    @ApiModelProperty("产品信息")
    private List<Good> goodList;
    @ApiModelProperty("客户信息")
    private Client client;
    @ApiModelProperty("处理订单的销售员名")
    private String orderSalerName;
}
