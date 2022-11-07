package com.jack.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * \* User: jack
 * \* Date: 2022/10/27
 * \* Time: 10:59
 * \* Description: 统计日期的订单支付数
 */
@Data
@ApiModel("统计日期订单支付数")
public class CountOrderNum {
    @ApiModelProperty("日期")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date date;
    @ApiModelProperty("数量")
    private Integer num;
}
