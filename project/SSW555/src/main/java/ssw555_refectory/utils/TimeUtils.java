package ssw555_refectory.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author HaoxuanLi  Github:bestksl
 * @version created date：2019-09-24 13:58
 */
public class TimeUtils {
    private static Date parse(String strDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        return sdf.parse(strDate);
    }

    public static int getAge(String strDate) throws Exception {
        Date day = parse(strDate);
        Calendar cal = Calendar.getInstance();
        if (cal.before(day)) { //出生日期晚于当前时间，无法计算
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(day);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            } else {
                age--;//当前月份在生日之前，年龄减一

            }
        }
        return age;
    }
    public static long getDaysFromDate(String strDate) throws Exception {
        Date day = parse(strDate);
        Calendar cal = Calendar.getInstance();
        if (cal.before(day)) { //出生日期晚于当前时间，无法计算
            return -1;
        }
        System.out.println((cal.getTimeInMillis() - day.getTime()) / (60 * 60 * 1000 * 24));
        System.out.println(cal.getTime());
        return ((System.currentTimeMillis() - day.getTime()) / (60 * 60 * 1000 * 24));
    }
}
