package com.sky.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author dlf
 * @date 2023/3/23 18:31
 */
public class DateUtils {



    private DateUtils() {
    }

    private final static SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
    private final static SimpleDateFormat longHourSdf = new SimpleDateFormat("yyyy-MM-dd HH");
    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");


    /**
     * 判断输入的时间是否小于当前时间 如果小于当前时间返回true,否则返回false
     *
     * @param date yyyy-MM-dd 输入日期
     * @param days 往后推几天
     */
    public static boolean isPastDate(Date date, int days) {

        boolean result = false;
        Date now = new Date();
        if (null != date) {
            result = now.after(getSpecifiedDayAfter(date, days));
        }
        return result;
    }

    /**
     * 获取输入日期后 N 天的日期
     *
     * @param specified 输入日期
     * @return date
     */
    public static Date getSpecifiedDayAfter(Date specified, int days) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.format(specified);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(specified);
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE, day + days);
        return calendar.getTime();
    }

    /**
     * 获取时间范围内的所有月份
     */
    public static List<String> getMothsBetween(Date startTime, Date endTime) {
        List<String> dateList = new ArrayList<>();
        DateUtil.rangeToList(startTime, endTime, DateField.MONTH).forEach(
                dateTime -> dateList.add(DateUtil.format(dateTime, "yyyy-MM"))
        );
        return dateList;
    }

    public static Date getQuarterStartTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date dt = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 6);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            dt = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00.000");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }

    /**
     * 当前季度的结束时间
     *
     * @return
     */
    public static Date getQuarterEndTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date dt = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(Calendar.MONTH, 2);
                c.set(Calendar.DATE, 31);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(Calendar.MONTH, 8);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            dt = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59.999");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }


    /**
     * 获取date所属年的所有季度列表及开始/结束时间 开始时间：date[0]，结束时间：date[1]
     *
     * @param date
     * @return
     */
    public static List<Date[]> yearQuarterList(Date date) {
        List<Date[]> result = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        Date starttm = DateUtil.beginOfYear(date);
        Date endtm = DateUtil.endOfYear(date);
        calendar.setTime(starttm);
        while (calendar.getTime().before(endtm)) {
            Date st = getQuarterStartTime(calendar.getTime());
            Date et = getQuarterEndTime(calendar.getTime());
            result.add(new Date[]{st, et});
            calendar.add(Calendar.MONTH, 3);
        }
        return result;
    }


    public static void main(String[] args) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DATE, -1);

        Date time = cal.getTime();
        System.out.println(sdf.format(time));

        System.err.println(isPastDate(time, 1));
        //获取指定时间范围内得所有月份
        List<String> dateList = getMothsBetween(DateUtil.date(), DateUtil.offset(DateUtil.date(), DateField.YEAR, 1));
        List<DateTime> dateTimes = DateUtil.rangeToList(DateUtil.date(), DateUtil.offset(DateUtil.date(), DateField.YEAR, 1), DateField.MONTH);

        System.err.println("当前日期:" + DateUtil.date() + "处理过后的时间范围:" + dateList);
        List<String> resultList = new ArrayList<>();
        System.out.println("今天2月之后得数据:" + DateUtil.offset(DateUtil.date(), DateField.MONTH, 2));
        //两月一传
       /* AtomicReference<DateTime> nextTime = new AtomicReference<>(new DateTime());
        dateTimes.forEach(data -> {
            String dateTime = DateUtil.format(data, shortSdf);
            nextTime.set(DateUtil.offset(data, DateField.MONTH, 2));
            resultList.add(dateTime + "~" + DateUtil.format(nextTime.get(), shortSdf));

        });*/
        //目的是获取范围数据
      /*  DateTime dateTime = new DateTime();
        for (int i = 0; i < dateTimes.size(); i++) {
            DateTime startTime = dateTimes.get(0);
            resultList.add(dateTime + "~" + DateUtil.format(dateTimes.get(i), shortSdf));
        }*/

        // [2022-09-23 14:43:06, 2022-10-23 14:43:06]
        System.out.println("range时间:" + dateTimes);
        DateTime aa = dateTimes.get(0);
        System.out.println("入参:" + aa);
        System.err.println("季度相关数据:" + DateUtil.yearAndQuarter(new Date(), DateUtil.offset(new Date(), DateField.YEAR, 1)));
        System.out.println("季度开始时间:" + DateUtil.quarter(new Date()));

        // todo new
        do {
            System.out.println("aa:" + aa);
            // offset 指定偏移量 2 偏移两个月 ，6偏移六个月
            resultList.addAll(getTestData(dateTimes, aa, 2));
            System.out.println("待处理数据:" + resultList);
            String[] strings = resultList.get(resultList.size() - 1).split("~");
            aa = DateUtil.parse(strings[strings.length - 1]);
            //break;
        } while (resultList.size() < 6);
        System.out.println("我们想要的最终结果:" + resultList);

        //季度数据
        System.out.println("当前季度开始时间:" + DateUtil.format(getQuarterStartTime(DateUtil.date()), sdf));

        // 开始季度数据计算
        List<String> quartList = new ArrayList<>();

        //测试一月一传
        // List<String> timeList = new ArrayList<>();
      /*  getMothsBetweenYYYYMMDDHHMMMSS(DateUtil.date(), DateUtil.offset(new Date(), DateField.YEAR, 1), 1).forEach(
                timeStr -> timeList.add(DateUtil.format(LocalDateTime.parse(timeStr), "YYYY-MM")));*/

        System.out.println("将DateTime转化过后的数据" +
                getMothsBetweenYYYYMMDDHHMMMSS(DateUtil.date(), DateUtil.offset(new Date(), DateField.YEAR, 1), 2));

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 业务变更，获取其实时间的月初天数

        System.err.println("月份开始时间:" + DateUtil.beginOfMonth(DateUtil.parseDate("2022-10-09")));
        LocalDate startTime = LocalDate.parse(
                DateUtil.beginOfMonth(DateUtil.parseDate("2022-10-09")).toString()
                , dateTimeFormatter);
        System.err.println("startTime:" + startTime);
    }

    public static JSONObject calculateQuart(List<DateTime> input) {
        JSONObject resultJson = new JSONObject();
        List<String> quartList = new ArrayList<>();
        List<String> result = new ArrayList<>();
        for (DateTime dateTime : input) {
            // 元素进来是有序的，获取季度得开始时间和结束时间
            DateTime startQuart = DateUtil.beginOfQuarter(dateTime);
            DateTime endQuart = DateUtil.endOfQuarter(dateTime);
            quartList.add(startQuart + "~" + endQuart);
            resultJson.put("quartList", quartList);
            break;
        }
        return resultJson;
    }

    /**
     * 获取规则时间范围内的区间数据得算法
     *
     * @param startTime 开始时间
     * @param endTime   截至时间
     * @param offset    偏移量 1:往后一个月。 2往后两个月 6:半年 3:三个月
     * @return List<String>DateTime</String>
     */
    public static List<String> getMothsBetweenYYYYMMDDHHMMMSS(Date startTime, Date endTime, int offset) {

        List<DateTime> dateTimes = DateUtil.rangeToList(startTime, endTime, DateField.MONTH);
        //默认递归次数
        int cycTimes = dateTimes.size();
        List<String> resultList = new ArrayList<>();
        System.out.println("时间范围:" + dateTimes);
        DateTime begin = dateTimes.get(0);

        do {
            System.out.println("入参:" + begin);
            ;
            resultList.addAll(getTestData(dateTimes, begin, offset));
            System.out.println("待处理数据:" + resultList);
            ;
            String[] strings = resultList.get(resultList.size() - 1).split("~");
            begin = DateUtil.parse(strings[strings.length - 1]);
        } while (resultList.size() < cycTimes / offset);
        return resultList;
    }

    /**
     * 数据处理
     */
    public static List<String> getTestData(List<DateTime> insert, DateTime startTime, int offset) {
        System.out.println("List入参:" + insert + "," + "初始时间入参:" + startTime);
        ;
        List<String> resultList = new ArrayList<>();
        for (DateTime dateTime : insert) {
            if (dateTime.isAfterOrEquals(startTime)) {
                System.out.println("计算过程中:" + startTime);

                resultList.add(startTime + "~" + DateUtil.format(dateTime.offset(DateField.MONTH, offset), sdf));
                break;
            }
        }
        return resultList;
    }

    public static Boolean checkYearValid(String yearStr) {
        try {
            Integer year = Integer.valueOf(yearStr);
            if (year > 2000) {
                return true;
            }
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return false;
    }

    /**
     * 根据时间 和时间格式 校验是否正确
     *
     * @param length 校验的长度
     * @param sDate  校验的日期
     * @param format 校验的格式
     * @return
     */
    public static boolean isLegalDate(int length, String sDate, String format) {
        int legalLen = length;
        if ((sDate == null) || (sDate.length() != legalLen)) {
            return false;
        }
        DateFormat formatter = new SimpleDateFormat(format);
        try {
            Date date = formatter.parse(sDate);
            return sDate.equals(formatter.format(date));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断当前日期是否大于某个日期
     *
     * @param date yyyy-MM-dd
     * @return
     */
    public static boolean afterDate(String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //把String转为LocalDate
        LocalDate localTime = LocalDate.parse(date, dtf);
        //判断当前日期是否大于指定日期
        return LocalDate.now().isAfter(localTime);
    }
}
