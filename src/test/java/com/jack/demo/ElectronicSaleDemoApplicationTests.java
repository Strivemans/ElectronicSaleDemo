package com.jack.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jack.demo.entity.Good;
import com.jack.demo.service.GoodService;
import com.jack.demo.service.OrderService;
import com.jack.demo.utils.GetWeekDate;
import com.jack.demo.utils.Percent;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
class ElectronicSaleDemoApplicationTests {
    @Resource
    private OrderService orderService;
    @Resource
    private GoodService goodService;
    @Test
    void contextLoads() {
        List<Integer> list = new ArrayList<>();
        list.add(15);
        list.add(21);
        list.add(41);
        List<String> percent = Percent.percent(list);
        System.out.println(percent);
    }
}
