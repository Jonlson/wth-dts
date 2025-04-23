package wth.dts.utils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class DateUtils {
    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    public static Date getDate() {
        return new Date();
    }

    public static Date format(String time, String format) {
        if (StringUtils.isNotBlank(time)) {
            try {
                return new SimpleDateFormat(format).parse(time);
            } catch (ParseException e) {
                logger.error(e.getMessage(), e);
            }
        }

        return null;
    }

    public static String format(Date time, String format) {
        if (null != time) {
            return new SimpleDateFormat(format).format(time);
        }

        return "";
    }

    public static Date getTimeHour(Date time) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(time);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public static Date getDate000000(Date time) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(time);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public static Date getDate0000(Date time) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(time);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public static Date getYesterday() {

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DATE, -1);

        return calendar.getTime();
    }

    /**
     * 获取当天时间23:59:59
     */
    public static Date getDate235959(Date time) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(time);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    /**
     * 获取几天后时间23:59:59
     */
    public static Date getDate235959AfterDay(Date time, Integer day) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(time);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }


    /**
     * 获取几天后时间23:59:59
     */
    public static Date getDate000000AfterDay(Date time, Integer day) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(time);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取几天后当前时间
     */
    public static Date getDateAfterDay(Date time, Integer day) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(time);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    public static Date getMonthFirstDay000000() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getWeekDay000000() {

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DATE, -6);

        return calendar.getTime();
    }

    /**
     * 根据类型获取下一个时间的结点
     * statTime :开始时间节点
     * timeType :1->按日期查询，2->查询一个月，3->查询一年  ，4->查询一季  |
     */
    public static Map<String, Date> getDateByType(Date statTime, Date endTime, Integer timeType) {
        Date startTime = statTime == null ? DateUtils.getDate000000(new Date()) : DateUtils.getDate000000(statTime);
        if (Integer.valueOf(1).equals(timeType)) {
            if (null == endTime) {
                endTime = startTime;
            }
        } else if (Integer.valueOf(2).equals(timeType)) {
            //开始时间
            Calendar c = Calendar.getInstance();
            c.setTime(Objects.requireNonNull(startTime));
            c.add(Calendar.MONTH, 0);
            c.set(Calendar.DAY_OF_MONTH, 1);
            startTime = c.getTime();
            //结束时间
            c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
            endTime = c.getTime();
        } else if (Integer.valueOf(3).equals(timeType)) {
            //开始时间
            Calendar cal = Calendar.getInstance();
            cal.setTime(Objects.requireNonNull(startTime));

            Calendar calendar = Calendar.getInstance();
            calendar.clear();
            calendar.set(Calendar.YEAR, cal.get(Calendar.YEAR));
            startTime = calendar.getTime();
            //结束时间
            calendar.clear();
            calendar.set(Calendar.YEAR, cal.get(Calendar.YEAR));
            calendar.roll(Calendar.DAY_OF_YEAR, -1);
            endTime = calendar.getTime();
        } else if (Integer.valueOf(4).equals(timeType)) {
            //开始时间
            Calendar c = Calendar.getInstance();
            c.setTime(Objects.requireNonNull(startTime));
            // 获取当前时间c的月份, Calendar这个类获取的月份是当前月份 - 1, 比如现在是11月份, 但c.get(Calendar.MONTH)得到的是10月份
            int month = c.get(Calendar.MONTH);
            // Calendar在赋月份时, 会自动+1, 比如c.set(Calendar.MONTH,9), 但实际赋的是10
            c.set(Calendar.MONTH, month / 3 * 3);
            c.set(Calendar.DAY_OF_MONTH, 1);
            startTime = c.getTime();
            //结束时间
            c.add(Calendar.MONTH, 2);
            c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
            endTime = c.getTime();
        }
        Map<String, Date> map = new HashMap<>();
        map.put("startTime", startTime);
        map.put("endTime", DateUtils.getDate235959(endTime));
        return map;
    }

    //查询上个月开始时间和结束时间
    public static Map<String, Date> getLastMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        int month = calendar1.get(Calendar.MONTH);
        calendar1.set(Calendar.MONTH, month - 1);
        calendar1.set(Calendar.DAY_OF_MONTH,
                calendar1.getActualMaximum(Calendar.DAY_OF_MONTH));
        Map<String, Date> timeMap = new HashMap<>();
        //开始时间
        timeMap.put("startTime", calendar.getTime());
        //结束时间
        timeMap.put("endTime", calendar1.getTime());
        return timeMap;
    }

    /**
     * 获取上一年开始时间和结束时间
     *
     * @param date
     * @return
     */
    public static Map<String, Date> setLastYearRange(Date date) {
        Map<String, Date> map = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), 0, calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.add(Calendar.YEAR, -1);
        map.put("startTime", calendar.getTime());
        calendar.add(Calendar.YEAR, 1);
        calendar.set(Calendar.SECOND, -1);
        map.put("endTime", calendar.getTime());
        return map;
    }

    /**
     * 获得昨天零时零分零秒
     *
     * @return
     */
    public static Date yesterdayDateByDay(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DAY_OF_MONTH) - 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当天时间23:59:59
     */
    public static String getWeek(Date time) {
        String week = "";
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        int weekday = c.get(Calendar.DAY_OF_WEEK);
        if (weekday == 1) {
            week = "周日";
        } else if (weekday == 2) {
            week = "周一";
        } else if (weekday == 3) {
            week = "周二";
        } else if (weekday == 4) {
            week = "周三";
        } else if (weekday == 5) {
            week = "周四";
        } else if (weekday == 6) {
            week = "周五";
        } else if (weekday == 7) {
            week = "周六";
        }
        return week;
    }

    /**
     * 校验时间字符串是否合法
     *
     * @param dateStr the date str
     * @param pattern the pattern
     * @return the boolean
     */
    public static boolean validDateStr(String dateStr, String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        try {
            LocalDate.parse(dateStr, new DateTimeFormatterBuilder().appendPattern(pattern).parseStrict().toFormatter());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 将rfc3339格式的时间转换成Date类型时间
     * <p>
     * rfc3339格式为YYYY-MM-DDTHH:mm:ss+TIMEZONE,
     * YYYY-MM-DD表示年月日,
     * T出现在字符串中，表示time元素的开头，HH:mm:ss表示时分秒,
     * TIMEZONE表示时区（+08:00表示东八区时间，领先UTC 8小时，即北京时间）.
     * 例如：2015-05-20T13:29:35+08:00表示，北京时间2015年5月20日 13点29分35秒。
     *
     * @param dateStr rfc3339格式日期字符串
     * @param format  转换的日期格式
     * @return
     */
    public static Date rfc3339ToDate(String dateStr, String format) {
        if (StringUtils.isNotBlank(dateStr)) {
            dateStr = dateStr.substring(0, dateStr.indexOf("+"));
            dateStr = dateStr.replace("T", " ");
            return format(dateStr, format);
        }
        return null;
    }

    /**
     * 计算两个日期间隔时间，以字符串天 时 分 秒的格式返回
     * 开始时间和结束时间都不能为空否则返回null，并且获取的秒必须大于0，否则返回null
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static String calculateIntervalTime(LocalDateTime startTime, LocalDateTime endTime) {
        StringBuilder stringBuilder = new StringBuilder();
        if (null == startTime
                || null == endTime) {
            return null;
        }

        long day = ChronoUnit.DAYS.between(startTime, endTime);
        long hour = ChronoUnit.HOURS.between(startTime, endTime);
        long minutes = ChronoUnit.MINUTES.between(startTime, endTime);
        long seconds = ChronoUnit.SECONDS.between(startTime, endTime);
        if (seconds < 1) {
            return null;
        }

        if (day > 0) {
            stringBuilder.append(day).append("天");
        }

        if (hour > 0) {
            long hourNumber = hour - 24;
            stringBuilder.append(hourNumber).append("时");
        }

        if (minutes > 0) {
            long minutesNumber = ((seconds / 60) - hour * 60);
            stringBuilder.append(minutesNumber).append("分");
        }

        if (seconds > 0) {
            long minutesNumber = minutes * 60;
            long integer = seconds - minutesNumber;
            stringBuilder.append(integer).append("秒");
        }

        return stringBuilder.toString();
    }

    /**
     * 获取当月的第一天
     *
     * @param dt
     * @return
     */
    public static Date getFirstMonthDay(Date dt) {
        //获取当前月第一天：
        Calendar ca = Calendar.getInstance();
        ca.setTime(dt);
        ca.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        ca.set(Calendar.HOUR, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);

        return ca.getTime();
    }


    /**
     * 获取当前月最后一天
     *
     * @param dt
     * @return
     */
    public static Date getLastMonthDay(Date dt) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(dt);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        ca.set(Calendar.HOUR, 23);
        ca.set(Calendar.MINUTE, 59);
        ca.set(Calendar.SECOND, 59);
        return ca.getTime();
    }

    /**
     * 获取当前时间年份
     *
     * @param dt
     * @return
     */
    public static int getYear(Date dt) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(dt);
        return ca.get(Calendar.YEAR);
    }

    /**
     * 获取某年第一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getFirstDayOfYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 获取某年第一天日期
     *
     * @param dt 日期
     * @return Date
     */
    public static Date getFirstDayOfYear(Date dt) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt);
        int last = calendar.getActualMinimum(Calendar.DAY_OF_YEAR);
        calendar.set(Calendar.DAY_OF_YEAR, last);
        return calendar.getTime();
    }


    /**
     * 获取某年最后一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getLastDayOfYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }

    /**
     * 获取某年最后一天日期
     *
     * @param dt 日期
     * @return Date
     */
    public static Date getLastDayOfYear(Date dt) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt);
        int last = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
        calendar.set(Calendar.DAY_OF_YEAR, last);
        return calendar.getTime();
    }

    /**
     * LocalDate To Date
     *
     * @param localDate
     * @return
     */
    public static Date convertToDate(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * LocalDateTime To Date
     *
     * @param localDateTime
     * @return
     */
    public static Date convertToDate(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    /**
     * Date To LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate convertToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime.toLocalDate();
    }

    /**
     * Date To LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime convertToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * Date To LocalTime
     *
     * @param date
     * @return
     */
    public static LocalTime convertToLocalTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
    }

    /**
     * LocalTime To Date
     *
     * @param localTime
     * @return
     */
    public static Date convertToDate(LocalTime localTime) {
        return Date.from(localTime.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date getMysqlDefaultTime() {
        return new Date(63043200000L);
    }

    /**
     * 判断某日是否为当月第一天
     *
     * @param date
     * @return
     */
    public static boolean isFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
        return calendar.get(Calendar.DAY_OF_MONTH) == 2;
    }

    /**
     * 判断某日是否为周一
     *
     * @param date
     * @return
     */
    public static boolean isMondayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) - 1 == 1;
    }

    /**
     * 比较两段时间是否有重叠
     *
     * @param startOne
     * @param endOne
     * @param startTwo
     * @param endTwo
     * @return
     */
    public static boolean compareTwoTime(Date startOne, Date endOne, Date startTwo, Date endTwo) {
        if (ObjectUtils.anyNull(startOne, endOne, startTwo, endTwo)) {
            return false;
        }
        //after 当start1小于等于end2时返回flase  before end1大于等于start2返回flase
        if ((!startOne.after(endTwo)) && (!endOne.before(startTwo))) {
            //时间重叠
            return true;
        }
        //时间不重叠
        return false;
    }

    /**
     * 在日期上添加数小时（n 为负，则减少几个小时）
     */
    public static Date addHour(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, n);
        return cal.getTime();
    }

    //获取几年前的数据
    public static Date getDateBeforeYear(Date time, Integer second) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(time);
        calendar.add(Calendar.YEAR, second);
        return calendar.getTime();
    }

    public static Date getDateAfterYear(Date time, Integer year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }

    //两个日期相差时间
    public static String getBetweenDate(Date startDate, Date endDate) {
        long timeStamp1 = startDate.getTime();
        long timeStamp2 = endDate.getTime();

        long diff = Math.abs(timeStamp2 - timeStamp1);

        long days = diff / (24 * 60 * 60 * 1000);

        long hours = (diff / (60 * 60 * 1000)) % 24;

        long minutes = (diff / (60 * 1000)) % 60;

        long seconds = (diff / 1000) % 60;

        StringBuilder stringBuilder = new StringBuilder();
        if (days > 0) {
            stringBuilder.append(days).append("天");
        }
        if (hours > 0) {
            stringBuilder.append(hours).append("小时");
        }
        if (minutes > 0) {
            stringBuilder.append(minutes).append("分");
        }
        if (seconds > 0) {
            stringBuilder.append(seconds).append("秒");
        }
        return stringBuilder.toString();
    }

    /**
     * 获取两个时间相差秒
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Long getDateDifference(Date startDate, Date endDate) {
        long diff = endDate.getTime() - startDate.getTime();
        return diff / 1000;
    }

    /**
     * 得到几天前的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    /**
     * 得到几天后的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    // 获取上一个小时的起始时间
    public static Date getLastHourStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    // 获取上一个小时的结束时间
    public static Date getLastHourEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -1);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    //获取上个月一号
    public static Date getLastFirstMonthDay(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取日期当月的天数
     */
    public static int getDaysByDate(Date Date) {
        String dateStr = DateUtils.format(Date, "yyyy-MM-dd");
        int year = Integer.parseInt(dateStr.substring(0, 4));
        int month = Integer.parseInt(dateStr.substring(5, 7));
        Calendar c = Calendar.getInstance();
        c.set(year, month, 0);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public static Integer getRemainSecondsOneDay(Date currentDate) {
        //使用plusDays加传入的时间加1天，将时分秒设置成0
        LocalDateTime midnight = LocalDateTime.ofInstant(currentDate.toInstant(),
                        ZoneId.systemDefault()).plusDays(1).withHour(0).withMinute(0)
                .withSecond(0).withNano(0);
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault());
        //使用ChronoUnit.SECONDS.between方法，传入两个LocalDateTime对象即可得到相差的秒数
        long seconds = ChronoUnit.SECONDS.between(currentDateTime, midnight);
        return (int) seconds;
    }

    public static boolean TwoTimeIsOneDay(Date time1, Date time2) {
        return getDate000000(time1).equals(getDate000000(time2));
    }

    /**
     * 获取多少月后时间
     */
    public static Date getDate000000AfterMonth(Date time, Integer day) {

        // 创建 Calendar 实例并设置为创建时间
        Calendar createCal = Calendar.getInstance();
        createCal.setTime(time);
        // 在创建时间上增加六个月
        createCal.add(Calendar.MONTH, day);
        return createCal.getTime();
    }

    public static void main(String[] args) {
        Date date = new Date();
        Date time = getDateAfterDay(date, 3);
        System.out.println(DateUtils.format(time, "yyyy-MM-dd HH:mm:ss"));
    }
}

