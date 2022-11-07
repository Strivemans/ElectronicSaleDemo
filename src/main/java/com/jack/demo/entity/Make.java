package com.jack.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * \* User: jack
 * \* Date: 2022/10/20
 * \* Time: 12:16
 * \* Description: 用于下订单时传输的类型
 */
@Data
@ApiModel("下订单类")
@NoArgsConstructor
@AllArgsConstructor
public class Make {
    @ApiModelProperty("客户名")
    private String clientName;
    @ApiModelProperty("客户地址")
    private String clientAddress;
    @ApiModelProperty("客户电话")
    private String clientTel;
    @ApiModelProperty("客户vip等级")
    private Integer clientRank;
    @ApiModelProperty("产品列表")
    private List<Good> goodList;
    @ApiModelProperty("销售员名")
    private String salerName;

    public Make(List<Good> goodList) {
        this.goodList = goodList;
    }
}
