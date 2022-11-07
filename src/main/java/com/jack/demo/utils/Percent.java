package com.jack.demo.utils;

import java.text.NumberFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * \* User: jack
 * \* Date: 2022/10/29
 * \* Time: 10:53
 * \* Description: 用于计算百分比
 */
public class Percent {
    public static List<String> percent(List<Integer> list){
        // 计算集合数据的总数
        int sum = list.stream().mapToInt(value -> value).sum();
        List<String> collect = list.stream().map(a -> getPercent(a, sum)).collect(Collectors.toList());
        return collect;
    }
    public static String getPercent(int x, int y) {
        double d1 = x * 1.0;
        double d2 = y * 1.0;
        NumberFormat percentInstance = NumberFormat.getPercentInstance();
        // 设置保留几位小数
        percentInstance.setMinimumFractionDigits(0);
        return percentInstance.format(d1 / d2);
    }

}
