package daiyun.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * time format.
 *
 * @author daiyun
 * @date 2016-07-28 11:17.
 */
public class TimeFormat {

  private static final int SECONDS = 1000;
  private static final int MINUTES = 60;
  private static final int HOUR = 60;
  private static final int DAY = 24;
  private static final int WEEK = 7;

  private TimeFormat() {
  }

  /**
   * 当前系统化格式化时间,默认格式 2016-07-28 11:17:23.
   *
   * @return 格式化后时间字符串
   */
  public static String currentTimeFormat() {
    Date currentTime = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return formatter.format(currentTime);
  }

  /**
   * 根据传入格式格式化当前时间.
   *
   * @param format 格式化规则
   * @return 对应格式化后时间字符串
   */
  public static String currentTimeFormat(String format) {
    Date currentTime = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat(format);
    return formatter.format(currentTime);
  }

  /**
   * 根据传入格式格式化第二天当前时间.
   *
   * @param format 格式化规则
   * @return 对应格式化后时间字符串
   */
  public static String tomorrowTimeFormat(String format) {
    Date currentTime = new Date();
    currentTime.setTime(currentTime.getTime() + DAY * HOUR * MINUTES * SECONDS);
    SimpleDateFormat formatter = new SimpleDateFormat(format);
    return formatter.format(currentTime);
  }

  /**
   * 格式化几天后当前时间格式.
   *
   * @param format 格式化规则
   * @param day    几天后
   * @return 对应格式化后时间字符串
   */
  public static String dayAfterFormat(String format, int day) {
    Date currentTime = new Date();
    currentTime.setTime(currentTime.getTime() + 1L * DAY * HOUR * MINUTES * SECONDS * day);
    SimpleDateFormat formatter = new SimpleDateFormat(format);
    return formatter.format(currentTime);
  }

  /**
   * 格式化传入的时间.
   *
   * @param format
   * @param date
   * @return
   */
  public static String dateFormat(String format, Date date) {
    SimpleDateFormat formatter = new SimpleDateFormat(format);
    return formatter.format(date);
  }


  public static String formatChange(String dateOld, String oldFormat, String newFormat) {
    String dateStr = "";
    try {
      Date date = formatData(dateOld, oldFormat);
      dateStr = dateFormat(newFormat, date);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return dateStr;
  }

  public static String getTime(String user_time) {
    String dateStr = "";
    try {
      Date date = formatData(user_time, "yyyy年MM月dd日");
      dateStr = dateFormat("yyyy-MM-dd", date);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return dateStr;
  }

  /**
   * 将默认格式时间字符串转为data.
   *
   * @param dataStr 默认格式时间字符串
   * @return 字符串对应data
   */
  public static Date formatData(String dataStr) {
    Date date = null;
    try {
      date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dataStr);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }

  /**
   * 根据指定格式将字符串转为data.
   *
   * @param dataStr   时间字符串
   * @param formatStr 时间字符串格式
   * @return 字符串对应data
   * @throws ParseException
   */
  public static Date formatData(String dataStr, String formatStr) throws ParseException {
    Date date = null;
    try {
      date = new SimpleDateFormat(formatStr).parse(dataStr);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }

  /**
   * 判断是否为当前时间节点前一周时间.
   *
   * @param time 判断的时间字符串 yyyy-MM-dd
   * @return 判断结果
   */
  public static boolean isLatestWeek(String time) {
    boolean judge = false;
    //评论时间
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try {
      Date date = sdf.parse(time);
      long million1 = date.getTime();
      //当前网络时间
      long million2 = System.currentTimeMillis();
      if ((million2 - million1) <= DAY * HOUR * MINUTES * SECONDS * WEEK) {
        judge = true;
      } else {

        judge = false;
      }
    } catch (ParseException e) {
      e.printStackTrace();
      SimpleDateFormat sdf01 = new SimpleDateFormat("yyyyMMdd");
      try {
        Date date = sdf01.parse(time);
        long million1 = date.getTime();
        //当前网络时间
        long million2 = System.currentTimeMillis();
        if ((million2 - million1) <= DAY * HOUR * MINUTES * SECONDS * WEEK) {
          judge = true;
        } else {
          judge = false;
        }
      } catch (ParseException e1) {
        e1.printStackTrace();
        judge = false;
      }
    }
    return judge;
  }

  public static boolean isBeforDate(String time) {
    boolean judge = false;
    //评论时间
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try {
      Date date = sdf.parse(time);
      long million1 = date.getTime();
      long million2 = System.currentTimeMillis();
      if (million1 <= million2) {
        judge = true;
      }
    } catch (ParseException e) {
      e.printStackTrace();

    }
    return judge;
  }

  /**
   * 毫秒级时间戳格式化.
   *
   * @param timestampString
   * @param formats
   * @return
   */
  public static String TimeStamp2Date(String timestampString, String formats) {
    long timestamp = Long.parseLong(timestampString);
    String date = new SimpleDateFormat(formats).format(new Date(timestamp));
    return date;
  }

  /**
   * 将时期字符串格式化为时间戳.
   *
   * @param time
   * @param formats
   * @return
   */
  public static long dateToTimeStamp(String time, String formats) {
    SimpleDateFormat format = new SimpleDateFormat(formats);
    Date date = null;
    try {
      date = format.parse(time);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date.getTime();
  }

  public static void main(String[] args) {
  }


}
