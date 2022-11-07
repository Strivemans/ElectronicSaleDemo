package com.jack.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * \* User: jack
 * \* Date: 2022/10/17
 * \* Time: 14:28
 * \* Description:产品实体类
 */
@Data
@ApiModel("产品类")
@TableName("good")
public class Good {
    @TableId(value = "good_id",type = IdType.AUTO)
    @ApiModelProperty("产品id")
    private Integer goodId;
    @ApiModelProperty("产品名")
    private String goodName;
    @ApiModelProperty("产品库存")
    private Integer goodStock;
    @ApiModelProperty("产品销售量")
    private Integer goodInventory;

    @ApiModelProperty("产品价格")
    private Double goodPrice;
    @ApiModelProperty("每个订单购买的产品的金额")
    @TableField(exist = false)
    private Double totalPrice;

    @ApiModelProperty("产品利润")
    private Double goodProfit;
    @ApiModelProperty("分类id")
    private Integer speciesId;

    @TableField(exist = false)
    @ApiModelProperty("分类名")
    private String categoryName;

    @TableField(exist = false)
    @ApiModelProperty("前端修改产品信息所用")
    private Boolean editable;

    @TableField(exist = false)
    @ApiModelProperty("前端修改产品信息所用")
    private Integer key;

    @TableField(exist = false)
    @ApiModelProperty("订单购买产品数量")
    private Integer num;

    @ApiModelProperty("缺货信息")
    @TableField(exist = false)
    private String stockOutInfo;
}
