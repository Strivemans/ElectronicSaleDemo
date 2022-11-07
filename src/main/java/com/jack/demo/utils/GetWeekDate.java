package com.jack.demo.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * \* User: jack
 * \* Date: 2022/10/27
 * \* Time: 21:25
 * \* Description:
 */
public class GetWeekDate {
    public static String[] getWeekDays(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_WEEK, -1);
        }
        String[] dates = new String[7];
        for (int i = 0; i < 7; i++) {
            dates[i] = dateFormat.format(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }
}
