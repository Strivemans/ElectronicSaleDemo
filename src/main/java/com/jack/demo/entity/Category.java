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
 * \* Time: 14:35
 * \* Description: 产品分类实体类
 */
@Data
@ApiModel("产品实体类")
@TableName("good_species")
public class Category {
    @TableId(value = "species_id",type = IdType.AUTO)
    @ApiModelProperty("分类id")
    private Integer speciesId;
    @ApiModelProperty("分类名")
    private String speciesName;
    @ApiModelProperty("销售类额所占白分比")
    @TableField(exist = false)
    private String percent;
    @ApiModelProperty("分类所占数量")
    @TableField(exist = false)
    private Integer num;
}
