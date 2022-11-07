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
 * \* Time: 20:18
 * \* Description: 销售员利润表
 */
@Data
@ApiModel("销售员利润表")
@TableName("saler_profit")
@NoArgsConstructor
public class SalerProfit {
    @ApiModelProperty("id")
    @TableId(value = "sprofit_id",type = IdType.AUTO)
    private Integer sprofitId;

    @ApiModelProperty("销售员id")
    private Integer salerId;
    @ApiModelProperty("订单id")
    private Integer orderId;
    @ApiModelProperty("利润")
    private Double profit;

    public SalerProfit(Integer salerId, Integer orderId, Double profit) {
        this.salerId = salerId;
        this.orderId = orderId;
        this.profit = profit;
    }

}
