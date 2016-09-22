/**   
 * @Project: tfscore 
 * @Title: Time.java 
 * @Package com.tfscore.date 
 * @Description: 时间计算工具类 
 * @author lx 
 * @date 2016年6月5日 下午12:29:56 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.date;

import java.util.Arrays;

/** 
 * @ClassName Time  
 * @Description 时间计算工具类 
 * @author lx 
 * @date 2016年6月5日  
 *   
 */
public class Time {
	/**
	 * 时间字段常量，表示“秒”
	 */
	public final static int SECOND = 0;

	/**
	 * 时间字段常量，表示“分”
	 */
	public final static int MINUTE = 1;

	/**
	 * 时间字段常量，表示“时”
	 */
	public final static int HOUR = 2;

	/**
	 * 时间字段常量，表示“天”
	 */
	public final static int DAY = 3;

	/**
	 * 各常量允许的最大值
	 */
	private final int[] maxFields = { 59, 59, 23, Integer.MAX_VALUE - 1 };

	/**
	 * 各常量允许的最小值
	 */
	private final int[] minFields = { 0, 0, 0, Integer.MIN_VALUE };

	/**
	 * 默认的字符串格式时间分隔符
	 */
	private String timeSeparator = ":";

	/**
	 * 时间数据容器
	 */
	private int[] fields = new int[4];

	/**
	 * 无参构造，将各字段置为 0
	 */
	public Time() {
		this(0, 0, 0, 0);
	}

	/**
	 * 使用时、分构造一个时间
	 * @param hour      小时
	 * @param minute    分钟
	 */
	public Time(int hour, int minute) {
		this(0, hour, minute, 0);
	}

	/**
	 * 使用时、分、秒构造一个时间
	 * @param hour      小时
	 * @param minute    分钟
	 * @param second    秒
	 */
	public Time(int hour, int minute, int second) {
		this(0, hour, minute, second);
	}

	/**
	 * 使用一个字符串构造时间<br/>
	 * Time time = new Time("14:22:23");
	 * @param time      字符串格式的时间，默认采用“:”作为分隔符
	 */
	public Time(String time) {
		this(time, null);
	}

	/**
	 * 使用天、时、分、秒构造时间，进行全字符的构造
	 * @param day       天
	 * @param hour      时
	 * @param minute    分
	 * @param second    秒
	 */
	public Time(int day, int hour, int minute, int second) {
		initialize(day, hour, minute, second);
	}

	/**
	 * 使用一个字符串构造时间，指定分隔符<br/>
	 * Time time = new Time("14-22-23", "-");
	 * @param time      字符串格式的时间
	 */
	public Time(String time, String timeSeparator) {
		if (timeSeparator != null) {
			setTimeSeparator(timeSeparator);
		}
		parseTime(time);
	}

	/**
	 * 设置时间字段的值
	 * @param field     时间字段常量
	 * @param value     时间字段的值
	 */
	public void set(int field, int value) {
		if (value < minFields[field]) {
			throw new IllegalArgumentException(value + ", time value must be positive.");
		}
		fields[field] = value % (maxFields[field] + 1);
		// 进行进位计算
		int carry = value / (maxFields[field] + 1);
		if (carry > 0) {
			int upFieldValue = get(field + 1);
			set(field + 1, upFieldValue + carry);
		}
	}

	/**
	 * 获得时间字段的值
	 * @param field     时间字段常量
	 * @return          该时间字段的值
	 */
	public int get(int field) {
		if (field < 0 || field > fields.length - 1) {
			throw new IllegalArgumentException(field + ", field value is error.");
		}
		return fields[field];
	}

	/**
	 * 将时间进行“加”运算，即加上一个时间
	 * @param time      需要加的时间
	 * @return          运算后的时间
	 */
	public Time addTime(Time time) {
		Time result = new Time();
		int up = 0; // 进位标志
		for (int i = 0; i < fields.length; i++) {
			int sum = fields[i] + time.fields[i] + up;
			up = sum / (maxFields[i] + 1);
			result.fields[i] = sum % (maxFields[i] + 1);
		}
		return result;
	}

	/**
	 * 将时间进行“减”运算，即减去一个时间
	 * @param time      需要减的时间
	 * @return          运算后的时间
	 */
	public Time subtractTime(Time time) {
		Time result = new Time();
		int down = 0; // 退位标志
		for (int i = 0, k = fields.length - 1; i < k; i++) {
			int difference = fields[i] + down;
			if (difference >= time.fields[i]) {
				difference -= time.fields[i];
				down = 0;
			} else {
				difference += maxFields[i] + 1 - time.fields[i];
				down = -1;
			}
			result.fields[i] = difference;
		}
		result.fields[DAY] = fields[DAY] - time.fields[DAY] + down;
		return result;
	}

	/**
	 * 获得时间字段的分隔符
	 * @return
	 */
	public String getTimeSeparator() {
		return timeSeparator;
	}

	/**
	 * 设置时间字段的分隔符（用于字符串格式的时间）
	 * @param timeSeparator     分隔符字符串
	 */
	public void setTimeSeparator(String timeSeparator) {
		this.timeSeparator = timeSeparator;
	}

	private void initialize(int day, int hour, int minute, int second) {
		set(DAY, day);
		set(HOUR, hour);
		set(MINUTE, minute);
		set(SECOND, second);
	}

	private void parseTime(String time) {
		if (time == null) {
			initialize(0, 0, 0, 0);
			return;
		}
		String t = time;
		int field = DAY;
		set(field--, 0);
		int p = -1;
		while ((p = t.indexOf(timeSeparator)) > -1) {
			parseTimeField(time, t.substring(0, p), field--);
			t = t.substring(p + timeSeparator.length());
		}
		parseTimeField(time, t, field--);
	}

	private void parseTimeField(String time, String t, int field) {
		if (field < SECOND || t.length() < 1) {
			parseTimeException(time);
		}
		char[] chs = t.toCharArray();
		int n = 0;
		for (int i = 0; i < chs.length; i++) {
			if (chs[i] <= ' ') {
				continue;
			}
			if (chs[i] >= '0' && chs[i] <= '9') {
				n = n * 10 + chs[i] - '0';
				continue;
			}
			parseTimeException(time);
		}
		set(field, n);
	}

	private void parseTimeException(String time) {
		throw new IllegalArgumentException(time + ", time format error, HH" + this.timeSeparator
				+ "mm" + this.timeSeparator + "ss");
	}

	public String toString() {
		StringBuilder sb = new StringBuilder(16);
		sb.append(fields[DAY]).append(',').append(' ');
		buildString(sb, HOUR).append(timeSeparator);
		buildString(sb, MINUTE).append(timeSeparator);
		buildString(sb, SECOND);
		return sb.toString();
	}

	private StringBuilder buildString(StringBuilder sb, int field) {
		if (fields[field] < 10) {
			sb.append('0');
		}
		return sb.append(fields[field]);
	}

	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + Arrays.hashCode(fields);
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Time other = (Time) obj;
		if (!Arrays.equals(fields, other.fields)) {
			return false;
		}
		return true;
	}

	/**
	 *在原来的时间基础上增加一个时间 例如:48:20:30 
	 *@author liaogd 2013-7-10 10:20:00
	 *
	 * */
	public String addTimeSupe(String time, int addNum) {
		String h = time.substring(time.indexOf(", ") + 2, time.indexOf(":"));
		Integer hours = Integer.parseInt(h);
		time = time.replace(time.substring(0, time.indexOf(":")), (hours + addNum) + "");
		System.out.println("time--" + time);
		return time;
	}

	/**
	 * 处理时间格式，将天数去掉，只显示时间
	 * @author liaogd 2013-7-10 14:20:00
	 * */
	public String updateTimeFormat(String time) {
		if (time.indexOf(",") > 0) {
			time = time.substring(time.indexOf(", ") + 2);
		}
		return time;

	}

	/**
	 * 比较两个时间的大小
	 * @author liaogd 2013-7-12 15:32:00
	 * @param  time  比较时间 eg:15:32:00
	 * @param  tBase 基础时间
	 * */
	public boolean comparaTime(String tRe, String tBase) {
		/* 分割时间 */
		Integer tR_H = Integer.parseInt(tRe.substring(0, tRe.indexOf(":")));
		Integer tR_m = Integer.parseInt(tRe.substring(tRe.indexOf(":") + 1, tRe.lastIndexOf(":")));
		Integer tR_s = Integer.parseInt(tRe.substring(tRe.lastIndexOf(":") + 1));

		Integer tB_H = Integer.parseInt(tBase.substring(0, tBase.indexOf(":")));
		Integer tB_m = Integer.parseInt(tBase.substring(tBase.indexOf(":") + 1,
				tBase.lastIndexOf(":")));
		Integer tB_s = Integer.parseInt(tBase.substring(tBase.lastIndexOf(":") + 1));
		if (tR_H > tB_H) {
			return true;
		}

		if (tR_H < tB_H) {
			if (tR_m > tB_m) {
				return true;
			}
		}
		if (tR_H < tB_H) {
			if (tR_m < tB_m) {
				if (tR_s > tB_s) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 补全时间
	 * */
	public String renewTime(int tagH, int tagm, int tags) {
		String tagSH = null, tagSm = null, tagSs = null;
		if (tagH < 10) {
			tagSH = "0" + tagH;
		} else {
			tagSH = tagH + "";
		}
		if (tagm == 0) {
			tagSm = "0" + tagm;
		} else {
			tagSm = "" + tagm;
		}
		if (tags == 0) {
			tagSs = "0" + tags;
		} else {
			tagSs = "" + tags;
		}
		String tagTime = tagSH + ":" + tagSm + ":" + tagSs;
		return tagTime;
	}
}
