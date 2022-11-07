package com.jack.demo.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author jack
 * @version 1.0
 * @date 2022/2/28 8:45
 *
 * 用于 对 实体类的自动填写时间进行配置
 * @TableField(fill = FieldFill.INSERT)
 * @TableField(fill = FieldFill.INSERT_UPDATE)
 */
@Component
public class MyMetaObjectHandle implements MetaObjectHandler {

    //增加数据库 时，进行设置 添加时间
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("order_date",new DateTime(),metaObject);
        this.setFieldValByName("order_date",new DateTime(),metaObject);
    }

    //修改student 时，进行修改时间
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("order_date",new Date(),metaObject);
    }
}
