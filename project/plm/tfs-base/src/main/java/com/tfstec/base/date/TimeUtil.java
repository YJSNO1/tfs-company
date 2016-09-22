/**   
 * @Project: tfscore 
 * @Title: TimeUtil.java 
 * @Package com.tfscore.date 
 * @Description: 时间格式转换类 
 * @author lx 
 * @date 2016年6月5日 下午12:33:03 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.date;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tfstec.base.StringUtils;

/** 
 * @ClassName TimeUtil  
 * @Description 时间格式转换类 
 * @author lx 
 * @date 2016年6月5日  
 *   
 */
public class TimeUtil {
	private static Logger logger = LoggerFactory.getLogger(TimeUtil.class);
	//时间格式化格式
	/**
	 * 年月日(yyyy-MM-dd) 
	 */
	public static SimpleDateFormat ymdA = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 年月日(yyyy年MM月dd日) 
	 */
	public static SimpleDateFormat ymdB = new SimpleDateFormat("yyyy年MM月dd日");
	
	/**
	 * 年月日时分秒(yyyyMMddHHmmssms) 
	 */
	public static SimpleDateFormat ymdC = new SimpleDateFormat("yyyyMMddHHmmssms");
	
	/**
	 * 年月日时分秒(yyyy-MM-dd HH:mm:ss) 
	 */
	public static SimpleDateFormat ymdD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 时分秒(HH:mm:ss) 
	 */
	public static SimpleDateFormat dfYTime = new SimpleDateFormat("HH:mm:ss");
	
	/**
	 * 年月日(yyyyMMdd) 
	 */
	public static SimpleDateFormat ymdF = new SimpleDateFormat("yyyyMMdd");
	
	/**
	 * 年月(yyyy-MM) 
	 */
	public static SimpleDateFormat ymdG = new SimpleDateFormat("yyyy-MM");
	
	/**
	 * 年月日(yyyy/M/d) 
	 */
	public static SimpleDateFormat ymdH = new SimpleDateFormat("yyyy/M/d");
	private static final DateFormat dfY = new SimpleDateFormat("yyyy");
	private static final DateFormat dfYM = new SimpleDateFormat("yyyy/MM");
	private static final DateFormat dfYMD = new SimpleDateFormat("yyyy/MM/dd");
	private static final DateFormat dfYMDHMS = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	
	/** 
	 * @Title: getNowDateByFormat 
	 * @Description: 根据格式化风格获取当前时间 
	 * @param format 时间格式化风格
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String getNowDateByFormat(SimpleDateFormat fmt){
		Date date = new Date();
		return fmt.format(date);
	}
	
	/** 
	 * @Title: getDateByFormat 
	 * @Description: 根据指定时间和格式化风格获取字符串时间 
	 * @param date 指定时间
	 * @param format 格式化风格
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String getDateByFormat(Date date,SimpleDateFormat fmt){
		return fmt.format(date);
	}
	
	/** 
	 * @Title: getDateByFormat 
	 * @Description: 根据指定时间和格式化风格获取时间 
	 * @param date 指定时间
	 * @param format 格式化风格
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static Date getDateByFormat(String date,String fmt){
		SimpleDateFormat format = new SimpleDateFormat(fmt);
		try {
			return format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error("根据指定时间和格式化风格获取时间出现异常：" + date + ": " + fmt);
		}
		return null;
	}
	
	/**
	 * 获得当前时间的毫秒数
	 */
	public static long getcurrentTimeMillis() {
		return (long) System.currentTimeMillis();
	}

	/**
	 * String转Date类型
	 * @Title: str2Date 
	 * @Description: String转Date类型
	 * @param dateStr 日期字符串
	 * @return 参数说明
	 * @return Date    返回类型
	 */
	public static Date strToDate(String dateStr,SimpleDateFormat fmt) {
		Date date = null;
		try {
			date = fmt.parse(dateStr);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return date;
	}
	
	/** 
	 * @Title: longToGregorian 
	 * @Description: 将Date转换为XMLGregorianCalendar
	 * @param date 转换日期
	 * @return 参数说明
	 * @return XMLGregorianCalendar    返回类型
	 */
	public static XMLGregorianCalendar longToGregorian(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		DatatypeFactory dtf = null;
		try {
			dtf = DatatypeFactory.newInstance();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		XMLGregorianCalendar dateType = dtf.newXMLGregorianCalendar();
		dateType.setYear(cal.get(Calendar.YEAR));
		// 由于Calendar.MONTH取值范围为0~11,需要加1
		dateType.setMonth(cal.get(Calendar.MONTH) + 1);
		dateType.setDay(cal.get(Calendar.DAY_OF_MONTH));
		// dateType.setHour(cal.get(Calendar.HOUR_OF_DAY));
		// dateType.setMinute(cal.get(Calendar.MINUTE));
		// dateType.setSecond(cal.get(Calendar.SECOND));
		return dateType;
	}

	/** 
	 * 将XMLGregorianCalendar转换为Date 
	 * @param cal xml格式的日期
	 * @return  
	 */
	public static Date xmlDateToDate(XMLGregorianCalendar cal) {
		return cal.toGregorianCalendar().getTime();
	}
	
	/**
	 * 获得某一时间的左右范围时间
	 * @param chooseTime 某一时间
	 * @param TimeDis 左右分钟数
	 */
	public static String getRangeTime(String chooseTime, String TimeDis) {
		String chooseTimes = "", bchooseTimes = "";
		if (chooseTime != null) {
			// 使用Time类进行时间的加减
			String date = chooseTime.substring(0, chooseTime.indexOf(" "));
			String times = chooseTime.substring(chooseTime.indexOf(" ") + 1);
			Time time = new Time(times);
			Integer timedis = Integer.parseInt(TimeDis);
			Time t = new Time(0, 0, timedis, 0);
			chooseTimes = (time.addTime(t)).toString();

			// 已经将时间进行了增加，现在又要将其进行还原
			chooseTimes = date + " " + chooseTimes;
			chooseTimes = chooseTimes.replace("0, ", "");

			// 经过了时间增加后的时间times为"+times+"增加后的时间为:"+chooseTimes
			bchooseTimes = (new Time(times).subtractTime(t)).toString();
			bchooseTimes = date + " " + bchooseTimes;
			bchooseTimes = bchooseTimes.replace("0, ", "");

			Date day = null;
			try {
				day = ymdA.parse(chooseTime);
			} catch (ParseException e) {
				logger.error("获得某一时间的左右范围时间出现异常：", e);
			}
			if (bchooseTimes.contains(",")) {
				bchooseTimes = addDateOneDay(day, -1,ymdA)
						+ bchooseTimes.substring(bchooseTimes.indexOf(",") + 1);
			}
			if (chooseTimes.contains(",")) {
				chooseTimes = addDateOneDay(day, 1,ymdA)
						+ chooseTimes.substring(chooseTimes.indexOf(",") + 1);
			}
		}
		return bchooseTimes + "#" + chooseTimes;
	}

	/** 
	 * @Title: addDateOneDay 
	 * @Description: 加减日期操作，返回指定格式化的日期
	 * @param date 基础日期
	 * @param num  日期加减天数
	 * @param sdf 格式化类型
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String addDateOneDay(Date date, int num, SimpleDateFormat sdf) {
		Calendar c = Calendar.getInstance();
		c.setTime(date); // 设置当前日期
		c.add(Calendar.DATE, num); // 日期加减
		return sdf.format(c.getTime());
	}

	/** 
	 * @Title: getSubDateNormal 
	 * @Description: 现在时间加减几分钟
	 * @param num 加减几分钟数
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String getSubDateNormal(int num) {
		Calendar nowTime = Calendar.getInstance();
		nowTime.add(Calendar.MINUTE, num);
		return ymdC.format(nowTime.getTime());
	}

	/** 
	 * @Title: getDateTime 
	 * @Description: 获取现在时间 
	 * @return 参数说明
	 * @return Date    返回类型
	 */
	public static Date getDateTime() {
		return new Date();
	}

	/** 
	 * @Title: getDate2 
	 * @Description: 返回格式划的日期 
	 * @param dateStr 字符串日期格式 （yyyy-MM-dd HH:mm:ss）
	 * @return
	 * @throws ParseException 参数说明
	 * @return String    返回类型
	 */
	public static String getDateCH(String dateStr) throws ParseException {
		Date date = ymdD.parse(dateStr);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);// 获取年份
		int month = cal.get(Calendar.MONTH) + 1;// 获取月份
		int day = cal.get(Calendar.DATE);// 获取日
		int hour = cal.get(Calendar.HOUR_OF_DAY);// 小时
		int minute = cal.get(Calendar.MINUTE);// 分
		return year + "年" + month + "月" + day + "日 " + hour + "时" + minute + "分";
	}

	/** 
	 * @Title: surplusTime 
	 * @Description: 计算时间差 
	 * @param date1 时间一（格式：yyyy-MM-dd HH:mm:ss）
	 * @param date2 时间二  （格式：yyyy-MM-dd HH:mm:ss）
	 * @return 参数说明
	 * @return long    返回类型
	 */
	public static long surplusTime(String date1, String date2) {
		long surplusTime = 0;
		try {
			surplusTime = ymdD.parse(date1).getTime() - ymdD.parse(date2).getTime();
			surplusTime = surplusTime / 1000;
		} catch (ParseException e) {
			logger.error("计算时间差出现异常：", e);
		}
		return surplusTime;
	}

	/** 
	 * @Title: getMonthSpace 
	 * @Description: 获取两个日期之间的相差月份数量 
	 * @param date1 开始日期（格式：yyyy-MM）
	 * @param date2 结束日期 （格式：yyyy-MM）
	 * @return
	 * @throws ParseException 参数说明
	 * @return int    返回类型
	 */
	public static int getMonthSpace(String date1, String date2) throws ParseException {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(ymdG.parse(date1));
		c2.setTime(ymdG.parse(date2));
		int year = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		// 开始日期若小月结束日期
		if (year < 0) {
			year = -year;
			return year * 12 + c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
		}
		return year * 12 + c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
	}

	/** 
	 * @Title: daysBetween 
	 * @Description: 计算两个时间的日期差 
	 * @param earlydate 日期一（格式：yyyy-MM-dd）
	 * @param latedate 日期二 （格式：yyyy-MM-dd）
	 * @return 参数说明
	 * @return int    返回类型
	 */
	public static final int daysBetween(String earlydate, String latedate) {
		int days = 0;
		try {
			Date early = ymdA.parse(earlydate.replace("/", "-"));
			Date late = ymdA.parse(latedate.replace("/", "-"));
			java.util.Calendar calst = java.util.Calendar.getInstance();
			java.util.Calendar caled = java.util.Calendar.getInstance();
			calst.setTime(early);
			caled.setTime(late);
			// 设置时间为0时
			calst.set(java.util.Calendar.HOUR_OF_DAY, 0);
			calst.set(java.util.Calendar.MINUTE, 0);
			calst.set(java.util.Calendar.SECOND, 0);
			caled.set(java.util.Calendar.HOUR_OF_DAY, 0);
			caled.set(java.util.Calendar.MINUTE, 0);
			caled.set(java.util.Calendar.SECOND, 0);
			// 得到两个日期相差的天数
			days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst.getTime().getTime() / 1000)) / 3600 / 24;
		} catch (ParseException e) {
			logger.error("计算日期差出现异常：", e);
		}
		return days;
	}

	/** 
	 * @Title: isBetweenDate 
	 * @Description: 计算日期区间差 
	 * @param earlydate 日期一 （格式：yyyy/M/d）
	 * @param latedate 日期二 （格式：yyyy/M/d）
	 * @param daydate 需要计算的日期
	 * @return 参数说明
	 * @return boolean    返回类型
	 */
	public static final boolean isBetweenDate(String earlydate, String latedate, String daydate) {
		boolean bool = false;
		try {
			Calendar startCalendar = Calendar.getInstance();
			Calendar endCalendar = Calendar.getInstance();
			Calendar dayCalendar = Calendar.getInstance();
			Date startDate = ymdH.parse(earlydate.replace("-", "/"));
			startCalendar.setTime(startDate);
			Date endDate = ymdH.parse(latedate.replace("-", "/"));
			endCalendar.setTime(endDate);
			Date dayDate = ymdH.parse(daydate.replace("-", "/"));
			dayCalendar.setTime(dayDate);
			int day = dayCalendar.get(Calendar.DATE);
			while (true) {
				startCalendar.add(Calendar.DAY_OF_MONTH, 1);
				if (startCalendar.getTimeInMillis() < endCalendar.getTimeInMillis()) {
					int perDay = Integer.parseInt(ymdH.format(startCalendar.getTime()).split("/")[2]);
					if (perDay == day) {
						bool = true;
						break;
					}
				} else {
					break;
				}
			}
		} catch (ParseException e) {
			logger.error("计算日期区间差出现异常：", e);
		}
		return bool;
	}
	
	/** 
	 * @Title: formatDate 
	 * @Description: 返回格式化后的日期字符串 
	 * @param datestr 要格式化的字符串（20080209）
	 * @return 格式化后的字符串 （2008/02/09）
	 * @return String    返回类型
	 */
	public static String formatDate(String datestr) {
		if (datestr.trim().length() == 8) {
			datestr = datestr.trim();
			return (datestr.substring(0, 4) + "/" + datestr.substring(4, 6) + "/" + datestr
					.substring(6, 8));
		} else if (datestr.trim().length() == 14) {
			datestr = datestr.trim();
			return (datestr.substring(0, 4) + "/" + datestr.substring(4, 6) + "/"
					+ datestr.substring(6, 8) + datestr.substring(8, 10) + ":"
					+ datestr.substring(10, 12) + ":" + datestr.substring(12, 14));
		} else {
			return datestr;
		}
	}

	/** 
	 * @Title: parseToDate 
	 * @Description: 字符串型日期转java.util.date 
	 * @param dateStr 要格式化的字符串（2009/12/23、20091223）
	 * @param fmt 日期格式化模型(yyyy/MM/dd、yyyyMMdd)
	 * @return 参数说明
	 * @return Date    返回类型
	 */
	public static Date parseToDate(String dateStr, String fmt) {
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}

	/** 
	 * @Title: parseToTimestamp 
	 * @Description: 字符串型日期转java.sql.Timestamp
	 *               例如：（2009/09/09 , yyyy/MM/dd）（2009-09-09 12:32:32, yyyy-MM-dd
	 *               HH:mm:ss）
	 * @param dateStr 要格式化的字符串
	 * @param fmt 日期格式化模型
	 * @return 参数说明
	 * @return Timestamp    返回类型
	 */
	public static Timestamp parseToTimestamp(String dateStr, String fmt) {
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		try {
			Date date = sdf.parse(dateStr);
			return new Timestamp(date.getTime());
		} catch (ParseException e) {
			return null;
		}
	}

	/** 
	 * @Title: timestampToDate 
	 * @Description: Date类型转换(java.sql.Timestamp to java.util.Date) 
	 * @param sdate 带时间戳的日期类型
	 * @return 参数说明
	 * @return Date    返回类型
	 */
	public static Date timestampToDate(Timestamp sdate) {
		return new Date(sdate.getTime());
	}

	/** 
	 * @Title: dateToTimestamp 
	 * @Description: Date类型转换(java.util.Date to java.sql.Timestamp) 
	 * @param sdate 普通的时间类型
	 * @return 参数说明
	 * @return Timestamp    返回类型
	 */
	public static Timestamp dateToTimestamp(Date sdate) {
		return new Timestamp(sdate.getTime());
	}

	/** 
	 * @Title: getDate 
	 * @Description: 返回当前日期 
	 * @return 例如：2008/6/24, 下午 04:27:57
	 * @return Date    返回类型
	 */
	public static Date getDate() {
		return (new Date());
	}

	/** 
	 * @Title: getDate 
	 * @Description: 返回当前时间经过格式化的日期 
	 * @param fmt 格式化模型
	 * @return 例如：2008/6/24, 下午 04:27:57
	 * @return String    返回类型
	 */
	public static String getDate(String fmt) {
		SimpleDateFormat sf = new SimpleDateFormat(fmt);
		return sf.format(new Date());
	}

	/** 
	 * @Title: getDate 
	 * @Description: 返回格式划的日期
	 * @param date 需要格式化的时间
	 * @param fmt 格式化模型
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String getDate(Date date, String fmt) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sf = new SimpleDateFormat(fmt);
		return sf.format(date);
	}

	/** 
	 * @Title: getDate 
	 * @Description: 返回格式划的日期(在当前日期上推移) 
	 * @param date 需要格式化的时间
	 * @param dateNum  推移天数
	 * @param fmt 格式化模型
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String getDate(Date date, int dateNum, String fmt) {

		SimpleDateFormat sf = new SimpleDateFormat(fmt);
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		c.add(Calendar.DATE, dateNum);
		Date dt = new Date(c.getTimeInMillis());
		return sf.format(dt);
	}

	/** 
	 * @Title: getMDate 
	 * @Description: 返回格式划的日期(在当前日期上推移月数) 
	 * @param date 需要格式化的时间
	 * @param monthNum 推移月数
	 * @param fmt 格式化模型
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String getMDate(Date date, int monthNum, String fmt) {

		SimpleDateFormat sf = new SimpleDateFormat(fmt);
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		c.add(Calendar.MONTH, monthNum);
		Date dt = new Date(c.getTimeInMillis());
		return sf.format(dt);
	}

	/** 
	 * @Title: getSQLDate 
	 * @Description: 将带有时间戳的时间格式化 
	 * @param date 带有时间戳的时间
	 * @param fmt 格式化模型
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String getSQLDate(Timestamp date, String fmt) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sf = new SimpleDateFormat(fmt);
		return sf.format(date);
	}

	/** 
	 * @Title: getSQLDate 
	 * @Description: 取得当前时间的SQL类型DATE
	 * @return 参数说明
	 * @return Timestamp    返回类型
	 */
	public static Timestamp getSQLDate() {
		return new Timestamp(System.currentTimeMillis());
	}

	/** 
	 * @Title: getSQLDate 
	 * @Description: 按照给定的格式化模型取得当前时间的SQL类型DATE
	 * @param fmtStr 格式化模型
	 * @return 参数说明
	 * @return Timestamp    返回类型
	 */
	public static Timestamp getSQLDate(String fmtStr) {
		String dateStr = getDate(fmtStr);
		return getSQLDate(dateStr, fmtStr);
	}

	/** 
	 * @Title: getSQLDate 
	 * @Description: 取格式化后的timestemp 
	 * @param dateStr 需要格式化的时间，例如：'2008/12/01'
	 * @param fmtStr 格式化模型，例如： 'yyyy/MM/dd'
	 * @return 参数说明
	 * @return Timestamp    返回类型
	 */
	public static Timestamp getSQLDate(String dateStr, String fmtStr) {
		SimpleDateFormat sf = new SimpleDateFormat(fmtStr);
		java.util.Date date = new java.util.Date();
		try {
			date = sf.parse(dateStr);
		} catch (ParseException e) {
			logger.error("getSQLDate desc=" + e);
			return null;
		}
		return new Timestamp(date.getTime());
	}

	/** 
	 * @Title: getDate 
	 * @Description: 取當天日期加減后的日期 
	 * @param dateNum 加减天数
	 * @param fmt 格式化模型
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String getDate(int dateNum, String fmt) {
		SimpleDateFormat sf = new SimpleDateFormat(fmt);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, dateNum);
		return sf.format(c.getTime());
	}

	/** 
	 * @Title: getStartDtAndEndDtOfDay 
	 * @Description: 返回日期的開始和結束時間 
	 * @param date 需要计算的时间
	 * @return map.get("start") 開始日期 map.get("end") 結束日期
	 * @throws Exception 参数说明
	 * @return Map<String,Date>    返回类型
	 */
	public static Map<String, Date> getStartDtAndEndDtOfDay(Date date) throws Exception {
		try {
			Date startDt, endDt;
			Map<String, Date> rtnValueMap = new HashMap<String, Date>();
			GregorianCalendar gc = new GregorianCalendar();
			// log.info("當前日期為>>>["+dfYMDHMS.format(date));
			startDt = dfYMD.parse(dfYMD.format(date));
			// log.info("計算開始日期>>>["+dfYMDHMS.format(startDt)+"]");
			gc.setTime(startDt);
			gc.add(gc.DAY_OF_MONTH, 1);
			gc.add(gc.MILLISECOND, -1);
			endDt = gc.getTime();
			// log.info("計算結束日期>>>["+dfYMDHMS.format(endDt)+"]");
			rtnValueMap.put("start", startDt);
			rtnValueMap.put("end", endDt);
			return rtnValueMap;
		} catch (Exception ex) {
			throw new Exception("計算日期[" + date + "]的開始和結束時間失敗", ex.getCause());
		}
	};

	/** 
	 * @Title: getFirstDayAndLastDayOfMonth 
	 * @Description: 提取一个月中的最后一天和前一天 
	 * @param date  需要计算的时间
	 * @return map.get("start") 開始日期 map.get("end") 結束日期
	 * @throws Exception 参数说明
	 * @return Map<String,Date>    返回类型
	 */
	public static Map<String, Date> getFirstDayAndLastDayOfMonth(Date date) throws Exception {
		try {
			Date startDt, endDt;
			Map<String, Date> rtnValueMap = new HashMap<String, Date>();
			GregorianCalendar gc = new GregorianCalendar();
			// log.info("當前日期為>>>["+dfYM.format(date));
			startDt = dfYM.parse(dfYM.format(date));
			// log.info("計算開始日期>>>["+dfYMDHMS.format(startDt)+"]");
			gc.setTime(startDt);
			gc.add(gc.MONTH, 1);
			gc.add(gc.MILLISECOND, -1);
			endDt = gc.getTime();
			// log.info("計算結束日期>>>["+dfYMDHMS.format(endDt)+"]");
			rtnValueMap.put("start", startDt);
			rtnValueMap.put("end", endDt);
			return rtnValueMap;
		} catch (Exception ex) {
			throw new Exception("計算日期[" + date + "]當月中的最后一天和前一天失敗", ex.getCause());
		}
	}

	/** 
	 * @Title: getFmtDate 
	 * @Description: 字符型日期转日期型 
	 * @param dateStr  字符型日期，例如：2008/02
	 * @param fmt 格式化模板，例如：yyyy/mm
	 * @return 参数说明
	 * @return Date    返回类型
	 */
	public static Date getFmtDate(String dateStr, String fmt) {
		DateFormat sf = new SimpleDateFormat(fmt);
		Date date = null;
		try {
			date = sf.parse(dateStr);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return date;
	}

	/** 
	 * @Title: getStepSQLDate 
	 * @Description: 返回當前日期上下移動的日期 
	 * @param dateNum 移动天数
	 * @param fmt 格式化模板
	 * @return 如果是(yyyy/MM/dd)則會省略 HH:mm:ss 默認設置為 00:00:00
	 * @return Timestamp    返回类型
	 */
	public static Timestamp getStepSQLDate(int dateNum, String fmt) {
		SimpleDateFormat sf = new SimpleDateFormat(fmt);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, dateNum);
		Date dt = new Date(c.getTimeInMillis());
		String str = sf.format(dt);
		dt = getFmtDate(str, fmt);
		Timestamp rtnDate = new Timestamp(dt.getTime());
		return rtnDate;
	}

	/** 
	 * @Title: getStepSQLDate 
	 * @Description: 返回當前日期上下移動的日期 
	 * @param dateNum 移动天数
	 * @param indate 基准时间
	 * @return 参数说明
	 * @return Timestamp    返回类型
	 */
	public static Timestamp getStepSQLDate(int dateNum, Timestamp indate) {
		Calendar c = new GregorianCalendar();
		c.setTime(indate);
		c.add(Calendar.DATE, dateNum);
		Date dt = new Date(c.getTimeInMillis());
		Timestamp rtnDate = new Timestamp(dt.getTime());
		return rtnDate;
	}

	/** 
	 * @Title: getFirstDayAndLastDayOfYear 
	 * @Description: 提取一年中的最后一天和前一天 
	 * @param date 需要计算的时间
	 * @return map.get("start") 開始日期 map.get("end") 結束日期
	 * @throws Exception 参数说明
	 * @return Map<String,Date>    返回类型
	 */
	public static Map<String, Date> getFirstDayAndLastDayOfYear(Date date) throws Exception {
		try {
			Date startDt, endDt;
			Map<String, Date> rtnValueMap = new HashMap<String, Date>();
			GregorianCalendar gc = new GregorianCalendar();
			// log.info("當前日期為>>>["+dfYM.format(date));
			startDt = dfY.parse(dfY.format(date));
			// log.info("計算開始日期>>>["+dfYMDHMS.format(startDt)+"]");
			gc.setTime(startDt);
			gc.add(gc.YEAR, 1);
			gc.add(gc.MILLISECOND, -1);
			endDt = gc.getTime();
			// log.info("計算結束日期>>>["+dfYMDHMS.format(endDt)+"]");
			rtnValueMap.put("start", startDt);
			rtnValueMap.put("end", endDt);
			return rtnValueMap;
		} catch (Exception ex) {
			throw new Exception("計算日期[" + date + "]當月中的最后一天和前一天失敗", ex.getCause());
		}
	}

	/** 
	 * @Title: getFirstDayAndLastDayOfWeek 
	 * @Description: 提取一周中的最后一天和前一天 
	 * @param date 需要计算的时间
	 * @return map.get("start") 開始日期 map.get("end") 結束日期
	 * @throws Exception 参数说明
	 * @return Map<String,Date>    返回类型
	 */
	public static Map<String, Date> getFirstDayAndLastDayOfWeek(Date date) throws Exception {
		try {
			Date startDt, endDt;
			Map<String, Date> rtnValueMap = new HashMap<String, Date>();
			GregorianCalendar gc = new GregorianCalendar();
			date = dfYMD.parse(dfYMD.format(date));
			gc.setTime(date);
			int week = gc.get(gc.DAY_OF_WEEK);
			// log.info("日期是一周中的第["+week+"]天");
			if (week == 1) {
				startDt = date;
			} else {
				gc.add(gc.DATE, 1 - week);
				startDt = gc.getTime();
			}
			// log.info("開始日期>>>["+dfYMDHMS.format(startDt)+"]");
			// if(week == 7){
			// endDt = date;
			// }else{ //guhaizhou 2009/3/21 update
			gc.setTime(date);
			gc.add(gc.DATE, 8 - week);
			gc.add(gc.MILLISECOND, -1);
			endDt = gc.getTime();
			// }
			// log.info("結束開始日期>>>["+dfYMDHMS.format(endDt)+"]");
			rtnValueMap.put("start", startDt);
			rtnValueMap.put("end", endDt);
			return rtnValueMap;
		} catch (Exception ex) {
			throw new Exception("計算日期[" + date + "]當月中的最后一天和前一天失敗", ex.getCause());
		}
	}

	/** 
	 * @Title: sqlDateToDate 
	 * @Description: 时间格式转换（sql date to util date）
	 * @param dt 数据库类型的时间
	 * @return 参数说明
	 * @return Date    返回类型
	 */
	public static Date sqlDateToDate(java.sql.Date dt) {
		return new java.util.Date(dt.getTime());
	}

	/** 
	 * @Title: getDaysBetween 
	 * @Description: 计算两日期之间的间隔(普通时间类型)
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return 参数说明
	 * @return int    返回类型
	 */
	public static int getDaysBetween(Date startDate, Date endDate) {
		Calendar d1 = new GregorianCalendar();
		d1.setTime(startDate);
		Calendar d2 = new GregorianCalendar();
		d2.setTime(endDate);
		if (d1.after(d2)) {
			d1.setTime(endDate);
			d2.setTime(startDate);
		}
		int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
		int y2 = d2.get(Calendar.YEAR);
		if (d1.get(Calendar.YEAR) != y2) {
			d1 = (Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);// 得到当年的实际天数
				d1.add(Calendar.YEAR, 1);
			} while (d1.get(Calendar.YEAR) != y2);
		}
		return days;
	}

	/** 
	 * @Title: getDaysBetween 
	 * @Description: 计算两日期之间的间隔(数据库时间类型) 
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return 参数说明
	 * @return int    返回类型
	 */
	public static int getDaysBetween(java.sql.Date startDate, java.sql.Date endDate) {
		Calendar d1 = new GregorianCalendar();
		d1.setTime(startDate);
		Calendar d2 = new GregorianCalendar();
		d2.setTime(endDate);
		if (d1.after(d2)) {
			d1.setTime(endDate);
			d2.setTime(startDate);
		}
		int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
		int y2 = d2.get(Calendar.YEAR);
		if (d1.get(Calendar.YEAR) != y2) {
			d1 = (Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);// 得到当年的实际天数

				d1.add(Calendar.YEAR, 1);
			} while (d1.get(Calendar.YEAR) != y2);
		}
		return days;
	}

	/** 
	 * @Title: getTimeBetween 
	 * @Description: 对比两日期之间的差值(只支持時分秒)
	 * @param startDt 开始时间
	 * @param endDt 结束时间
	 * @param kind 对比类别，'h' 时,'m' 分,'s' 秒
	 * @return 参数说明
	 * @return long    返回类型
	 */
	public static long getTimeBetween(Date startDt, Date endDt, String kind) {
		long seconds = (endDt.getTime() - startDt.getTime()) / 1000;
		long date = seconds / (24 * 60 * 60);
		// log.info(">>>>seconds="+seconds+">>>>>date="+date);
		// log.info(">>>>seconds="+(seconds-date*60*60)/(60*60));
		if (kind.equals("h")) {
			return seconds / (60 * 60);
		} else if (kind.equals("m")) {
			return (seconds) / (60);
		} else {
			return seconds;
		}
	}

	/** 
	 * @Title: getTimeBetween 
	 * @Description: 对比两带时间戳日期之间的差值(只支持時分秒) 
	 * @param startDt 开始时间
	 * @param endDt 结束时间
	 * @param kind 对比类别，'h' 时,'m' 分,'s' 秒
	 * @return 参数说明
	 * @return long    返回类型
	 */
	public static long getTimeBetween(Timestamp startDt, Timestamp endDt, String kind) {
		Date sdt = new Date(startDt.getTime());
		Date edt = new Date(endDt.getTime());
		return getTimeBetween(sdt, edt, kind);
	}

	/** 
	 * @Title: getTimeBetween 
	 * @Description: 对比两数据库日期之间的差值(只支持時分秒) 
	 * @param startDt 开始时间
	 * @param endDt 结束时间
	 * @param kind 对比类别，'h' 时,'m' 分,'s' 秒
	 * @return 参数说明
	 * @return long    返回类型
	 */
	public static long getTimeBetween(java.sql.Date startDt, java.sql.Date endDt, String kind) {
		Date sdt = new Date(startDt.getTime());
		Date edt = new Date(endDt.getTime());
		return getTimeBetween(sdt, edt, kind);
	}

	/** 
	 * @Title: getDateLastTime 
	 * @Description: 取得某天得最后日期 
	 * @param date 操作时间
	 * @return 参数说明
	 * @return Date    返回类型
	 */
	public static Date getDateLastTime(Date date) {
		String dataStr = getDate(date, "yyyy/MM/dd");
		return parseToDate(dataStr + " 23:59:59", "yyyy/MM/dd HH:mm:ss");
	}

	/** 
	 * @Title: getDateLastTime 
	 * @Description: 取得某天得最后日期(带时间戳) 
	 * @param date 操作时间
	 * @return 参数说明
	 * @return Timestamp    返回类型
	 */
	public static Timestamp getDateLastTime(Timestamp date) {
		String dataStr = getDate(date, "yyyy/MM/dd");
		return parseToTimestamp(dataStr + " 23:59:59", "yyyy/MM/dd HH:mm:ss");
	}

	/** 
	 * @Title: parseTimeToSecond 
	 * @Description: 将00:00:00的格式转换成秒 
	 * @param time 操作时间
	 * @return 参数说明
	 * @return long    返回类型
	 */
	public static long parseTimeToSecond(String time) {
		try {
			Date times = dfYTime.parse(time);
			long r = times.getHours() * 60 * 60 + times.getMinutes() * 60 + times.getSeconds();
			return r;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;

	}

	/** 
	 * @Title: parseSecondToTime 
	 * @Description: 将秒转换成00:00:00的格式 
	 * @param second 操作的秒数
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String parseSecondToTime(Long second) {
		if (second != null) {
			String hour = second / (60 * 60) + "";
			String minute = (second % (60 * 60)) / 60 + "";
			String sec = ((second % (60 * 60)) % 60) + "";
			return (hour.length() == 1 ? "0" + hour : hour) + ":"
					+ (minute.length() == 1 ? "0" + minute : minute) + ":"
					+ (sec.length() == 1 ? "0" + sec : sec);
		} else {
			return "00:00:00";
		}
	}

	/** 
	 * @Title: getWeekOfYear 
	 * @Description: 取得指定日期是多少周，最后一周跨年，则将这周算作下一年第一周 
	 * @param date 计算时间
	 * @param weekFirstDay 指定每一周的第一天是星期一或者是星期天，取值为Calendar.MONDAY或者Calendar.SUNDAY
	 * @return 参数说明
	 * @return int    返回类型
	 */
	public static int getWeekOfYear(Date date, int weekFirstDay) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(weekFirstDay);
		c.setMinimalDaysInFirstWeek(1);
		c.setTime(date);

		return c.get(Calendar.WEEK_OF_YEAR);
	}

	/** 
	 * @Title: getWeekStrOfYear 
	 * @Description: 取得指定日期是多少周，最后一周跨年，则将这周算作下一年第一周 
	 * @param date 计算时间
	 * @param weekFirstDay 指定每一周的第一天是星期一或者是星期天，取值为Calendar.MONDAY或者Calendar.SUNDAY
	 * @return YYYYWW 201452
	 * @return String    返回类型
	 */
	public static String getWeekStrOfYear(Date date, int weekFirstDay) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(weekFirstDay);
		c.setMinimalDaysInFirstWeek(1);
		c.setTime(date);
		Calendar c2 = new GregorianCalendar();
		c2.setTime(getLastDayOfWeek(c.getTime(), weekFirstDay));

		return c2.get(Calendar.YEAR)
				+ StringUtils.leftPad(c.get(Calendar.WEEK_OF_YEAR) + "", 2, "0");
	}

	/** 
	 * @Title: getMaxWeekNumOfYear 
	 * @Description: 得到某一年周的总数， 最后一周跨年，则将这周算作下一年第一周
	 * @param year 年份，例如：2016
	 * @param weekFirstDay 指定每一周的第一天是星期一或者是星期天，取值为Calendar.MONDAY或者Calendar.SUNDAY
	 * @return 参数说明
	 * @return int    返回类型
	 */
	public static int getMaxWeekNumOfYear(int year, int weekFirstDay) {
		Calendar c = new GregorianCalendar();
		c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);

		c.setTime(getLastDayOfWeek(c.getTime(), weekFirstDay));
		if (c.get(Calendar.YEAR) > year) {
			c.add(Calendar.DATE, -7);
		}
		return getWeekOfYear(c.getTime(), weekFirstDay);
	}

	/** 
	 * @Title: getFirstDayOfWeek 
	 * @Description: 得到某年某周的第一天 
	 * @param year 年份，例如：2016
	 * @param week 周数，例如：12
	 * @param weekFirstDay 指定每一周的第一天是星期一或者是星期天，取值为Calendar.MONDAY或者Calendar.SUNDAY
	 * @return 参数说明
	 * @return Date    返回类型
	 */
	public static Date getFirstDayOfWeek(int year, int week, int weekFirstDay) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, (week - 1) * 7);

		return getFirstDayOfWeek(cal.getTime(), weekFirstDay);
	}

	/** 
	 * @Title: getLastDayOfWeek 
	 * @Description: 得到某年某周的最后一天 
	 * @param year  年份，例如：2016
	 * @param week  周数，例如：12
	 * @param weekFirstDay 指定每一周的第一天是星期一或者是星期天，取值为Calendar.MONDAY或者Calendar.SUNDAY
	 * @return 参数说明
	 * @return Date    返回类型
	 */
	public static Date getLastDayOfWeek(int year, int week, int weekFirstDay) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, (week - 1) * 7);

		return getLastDayOfWeek(cal.getTime(), weekFirstDay);
	}

	/** 
	 * @Title: getFirstDayOfWeek 
	 * @Description: 取得指定日期所在周的第一天 
	 * @param date 指定日期
	 * @param weekFirstDay 指定每一周的第一天是星期一或者是星期天，取值为Calendar.MONDAY或者Calendar.SUNDAY
	 * @return 参数说明
	 * @return Date    返回类型
	 */
	public static Date getFirstDayOfWeek(Date date, int weekFirstDay) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(weekFirstDay);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return c.getTime();
	}

	/**
	 * 取得指定日期所在周的最后一天
	 * 
	 * @param date
	 * @param weekFirstDay Calendar.MONDAY,Calendar.SUNDAY
	 * @return
	 */
	/** 
	 * @Title: getLastDayOfWeek 
	 * @Description: 取得指定日期所在周的最后一天
	 * @param date 指定日期 
	 * @param weekFirstDay 指定每一周的第一天是星期一或者是星期天，取值为Calendar.MONDAY或者Calendar.SUNDAY
	 * @return 参数说明
	 * @return Date    返回类型
	 */
	public static Date getLastDayOfWeek(Date date, int weekFirstDay) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(weekFirstDay);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
		return c.getTime();
	}

	/** 
	 * @Title: getFirstDayOfWeek 
	 * @Description: 取得当前日期所在周的第一天
	 * @param weekFirstDay 指定每一周的第一天是星期一或者是星期天，取值为Calendar.MONDAY或者Calendar.SUNDAY
	 * @return 参数说明
	 * @return Date    返回类型
	 */
	public static Date getFirstDayOfWeek(int weekFirstDay) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(weekFirstDay);
		c.setTime(new Date());
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return c.getTime();
	}

	/** 
	 * @Title: getLastDayOfWeek 
	 * @Description: 取得当前日期所在周的最后一天 
	 * @param weekFirstDay 指定每一周的第一天是星期一或者是星期天，取值为Calendar.MONDAY或者Calendar.SUNDAY
	 * @return 参数说明
	 * @return Date    返回类型
	 */
	public static Date getLastDayOfWeek(int weekFirstDay) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(weekFirstDay);
		c.setTime(new Date());
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
		return c.getTime();
	}

	/** 
	 * @Title: getMaxYear 
	 * @Description: 获取List中年份最大的那个 
	 * @param dateList 年份集合，例如：2016,2014
	 * @param format 格式化模板
	 * @return 参数说明
	 * @return List    返回类型
	 */
	public static List getMaxYear(List dateList, String format) {
		int maxyear = 0;
		int minyear = 0;
		List list = new ArrayList();
		for (int i = 0; i < dateList.size(); i++) {
			if (!StringUtils.isEmpty(dateList.get(i))) {
				String date = dateList.get(i).toString();
				Date yearDate = parseToDate(date, format);
				Calendar ca = Calendar.getInstance();
				ca.setTime(yearDate);
				int year = ca.get(Calendar.YEAR);
				if (maxyear == 0) {
					maxyear = year;
					minyear = year;
				} else {
					if (year > maxyear) {
						maxyear = year;
					}
					if (year < minyear) {
						minyear = year;
					}
				}
			}
		}
		if (minyear > 0) {
			list.add(minyear);
			list.add(maxyear);
		}
		return list;
	}
	/**
	 * @Description: 获取当前给定日期的日
	 * @param date
	 * @return 参数说明
	 * @return int    返回类型
	 */
	public static int getDay(Date date) {
		if(null==date) date = new Date();
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    return calendar.get(Calendar.DAY_OF_MONTH);
	}
}
