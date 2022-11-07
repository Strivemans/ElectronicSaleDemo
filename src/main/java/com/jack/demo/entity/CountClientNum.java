package com.jack.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * \* User: jack
 * \* Date: 2022/10/27
 * \* Time: 10:30
 * \* Description: 用于统计用户数
 */
@Data
@ApiModel("记录用户时间数")
public class CountClientNum {
    @ApiModelProperty("时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date date;
    @ApiModelProperty("数量")
    private Integer num;
}
