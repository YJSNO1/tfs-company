/**   
 * @Project: tfscore 
 * @Title: DataUtils.java 
 * @Package com.tfscore 
 * @Description: 数据处理类 
 * @author lx 
 * @date 2016年6月5日 下午12:50:54 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.CodeSource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.ProtectionDomain;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/** 
 * @ClassName DataUtils  
 * @Description 数据处理类 
 * @author lx 
 * @date 2016年6月5日  
 *   
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {
	private static Logger logger = LoggerFactory.getLogger(StringUtils.class);

	/**
	 * 获得唯一编号
	 * @Title: getUuidNm 
	 * @Description: 获得唯一编号
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String getUuidNm() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 获得唯一随机值
	 * @Title: getRandomUnique 
	 * @Description: 获得唯一随机值
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String getRandomUnique() {
		StringBuffer str = new StringBuffer();
		long time = System.currentTimeMillis();
		str.append(time);
		Random ran = new Random();
		for (int i = 0; i < 5; i++) {
			str.append(ran.nextInt(9));
		}
		return str.toString();
	}

	/**
	 * 判断对象是否Empty(null或元素为0)<br>
	 * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
	 * @param pObj 待检查对象
	 * @return boolean 返回的布尔值
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object pObj) {
		if (pObj == null) {
			return true;
		}
		if ("".equals(pObj)) {
			return true;
		}
		if ("null".equals(pObj)) {
			return true;
		}
		if (pObj instanceof String) {
			if (((String) pObj).length() == 0) {
				return true;
			}
		} else if (pObj instanceof Collection) {
			if (((Collection) pObj).size() == 0) {
				return true;
			}
		} else if (pObj instanceof Map) {
			if (((Map) pObj).size() == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断对象是否为NotEmpty(!null或元素>0)<br>
	 * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
	 * @param pObj 待检查对象
	 * @return boolean 返回的布尔值
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNotEmpty(Object pObj) {
		if (pObj == null) {
			return false;
		}
		if ("".equals(pObj)) {
			return false;
		}
		if ("null".equals(pObj)) {
			return false;
		}
		if (" ".equals(pObj)) {
			return false;
		}
		if (pObj instanceof String) {
			if (((String) pObj).length() == 0) {
				return false;
			}
		} else if (pObj instanceof Collection) {
			if (((Collection) pObj).size() == 0) {
				return false;
			}
		} else if (pObj instanceof JSONObject) {
			if (((JSONObject) pObj).isEmpty()) {
				return false;
			}
			if (((JSONObject) pObj).size() == 0) {
				return false;
			}
		} else if (pObj instanceof Map) {
			if (((Map) pObj).size() == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 根据正则表达式验证数据
	 * @param regx 正则表达式
	 * @param pObj 待检查对象
	 * @return boolean 返回的布尔值
	 */
	public static boolean isMatch(String regx, String pObj) {
		Pattern pattern = Pattern.compile(regx);
		Matcher matcher = pattern.matcher(pObj);
		return matcher.matches();
	}

	/**
	 * Double类型验证
	 * @author lx
	 * @craeteDae	2016-06-10
	 * @param str	
	 * @return 	true 是 
	 */
	public static boolean valiDouble(String str) {
		if (str != null && str.trim().length() != 0) {
			String reg = "^[0-9]*\\.?[0-9]*$";
			Pattern pattern = Pattern.compile(reg);
			Matcher matcher = pattern.matcher(str);
			if (matcher.find()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 自然数类型验证
	 * @author lx
	 * @craeteDae	2016-06-10
	 * @param str	
	 * @return 	true 是 
	 */
	public static boolean valiInt(String str) {
		if (str != null && str.trim().length() != 0) {
			String reg = "^[0-9]+$";
			Pattern pattern = Pattern.compile(reg);
			Matcher matcher = pattern.matcher(str);
			if (matcher.find()) {
				return true;
			}
		}
		return false;
	}

	/** 
	 * 手机号验证 
	 * @param  str 
	 * @return 验证通过返回true 
	 */
	public static boolean isMobile(String str) {
		boolean b = false;
		Pattern p = Pattern.compile("^1[3,4,5,7,8][0-9]\\d{8}$"); // 验证手机号
		Matcher m = p.matcher(str);
		b = m.matches();
		return b;
	}

	/** 
	 * @Title: nvl 
	 * @Description: 取代Null值
	 * @param in 判空数据对象
	 * @param nullString 替换数据
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String nvl(Object in, String nullString) {
		return in == null ? nullString : in.toString();
	}

	/** 
	 * @Title: transHumpString 
	 * @Description: 转为驼峰字符串 
	 * @param in 需要转换的字符串，例如：user_info to UserInfo
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String transHumpString(String in) {
		in = in.toLowerCase();
		String a[] = in.split("_");
		String out = a[0];
		for (int i = 1; i < a.length; i++) {
			out = out + a[i].substring(0, 1).toUpperCase() + a[i].substring(1);
		}
		return out;
	}

	/** 
	 * @Title: rtrim 
	 * @Description: 删除右边的空格 
	 * @param str 操作数据
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String rtrim(String str) {
		if (str == null) {
			return null;
		}
		int num = str.length();
		for (int i = num - 1; i > -1; i--) {
			if (!(str.substring(i, i + 1).equals(" "))) {
				return str.substring(0, i + 1);
			} else if (i == 0) {
				return "";
			}
		}
		return str;
	}

	/** 
	 * @Title: haveNumber 
	 * @Description: 判断是否包含数字
	 * @param str 操作数据
	 * @return 参数说明
	 * @return boolean    返回类型
	 */
	public static boolean haveNumber(String str) {
		boolean isExistNum = false;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {
				isExistNum = true;
				break;
			}
		}
		return isExistNum;
	}

	/** 
	 * @Title: tomd5 
	 * @Description: 将字符串转为md5加密
	 * @param str 操作数据
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String tomd5(String str) {
		StringBuffer hexString = new StringBuffer();
		if (str != null && str.trim().length() != 0) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(str.getBytes());
				byte[] hash = md.digest();
				for (int i = 0; i < hash.length; i++) {
					if ((0xff & hash[i]) < 0x10) {
						hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
					} else {
						hexString.append(Integer.toHexString(0xFF & hash[i]));
					}
				}
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}

		}
		return hexString.toString();
	}

	// 国标码和区位码转换常量
	static final int GB_SP_DIFF = 160;

	// 存放国标一级汉字不同读音的起始区位码
	static final int[] secPosValueList = { 1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594, 2787,
			3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027, 4086, 4390, 4558, 4684, 4925, 5249,
			5600 };

	// 存放国标一级汉字不同读音的起始区位码对应读音
	static final char[] firstLetter = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l', 'm',
			'n', 'o', 'p', 'q', 'r', 's', 't', 'w', 'x', 'y', 'z' };

	/** 
	 * @Title: getFirstLetter 
	 * @Description: 获取一个字符串的拼音码 
	 * @param oriStr 操作数据
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String getFirstLetter(String oriStr) {
		String str = oriStr.toLowerCase();
		StringBuffer buffer = new StringBuffer();
		char ch;
		char[] temp;
		for (int i = 0; i < str.length(); i++) { // 依次处理str中每个字符
			ch = str.charAt(i);
			temp = new char[] { ch };
			byte[] uniCode = new String(temp).getBytes();
			if (uniCode[0] < 128 && uniCode[0] > 0) { // 非汉字
				buffer.append(temp);
			} else {
				buffer.append(convert(uniCode));
			}
		}
		return buffer.toString();
	}

	/**
	 * 获取一个汉字的拼音首字母。 * GB码两个字节分别减去160，转换成10进制码组合就可以得到区位码 *
	 * 例如汉字“你”的GB码是0xC4/0xE3，分别减去0xA0（160）就是0x24/0x43 *
	 * 0x24转成10进制就是36，0x43是67，那么它的区位码就是3667，在对照表中读音为‘n’
	 */

	/** 
	 * @Title: convert 
	 * @Description: 字节码转换
	 * @param bytes 字节码
	 * @return 参数说明
	 * @return char    返回类型
	 */
	static char convert(byte[] bytes) {
		char result = '-';
		int secPosValue = 0;
		int i;
		for (i = 0; i < bytes.length; i++) {
			bytes[i] -= GB_SP_DIFF;
		}

		secPosValue = bytes[0] * 100 + bytes[1];
		for (i = 0; i < 23; i++) {
			if (secPosValue >= secPosValueList[i] && secPosValue < secPosValueList[i + 1]) {
				result = firstLetter[i];
				break;
			}
		}
		return result;
	}

	/** 
	 * @Title: objectToString 
	 * @Description: 字符串为空 返回空串 否则返回原始字符 
	 * @param str 数据对象
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String objectToString(Object str) {
		return null == str ? "" : str.toString();
	}

	/** 
	 * @Title: ltrim 
	 * @Description: 头部去空格 
	 * @param str 操作数据
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String ltrim(String str) {
		return str.replaceAll("^\\s{1,}(\\S{0,})", "$1");
	}

	/** 
	 * @Title: ltrim 
	 * @Description: 去除头部的字符， 例如：去除前导0;ltrim("0001230","0")==>1230
	 * @param str 原字符串
	 * @param trimChar 要去除的字符
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String ltrim(String str, String trimChar) {
		if (StringUtils.isNotEmpty(str)) {
			return str.replaceAll("^(" + trimChar + ")+", "");
		}
		return null;
	}

	/** 
	 * @Title: objectArrayToStringArray 
	 * @Description: 将对象数组 转换成字符串数组 
	 * @param obj 对象数组
	 * @return 参数说明
	 * @return String[]    返回类型
	 */
	public static String[] objectArrayToStringArray(Object[] obj) {
		String[] str = new String[obj.length];
		for (int i = 0; i < obj.length; i++) {
			str[i] = objectToString(obj[i]);
		}
		return str;
	}

	/** 
	 * @Title: htmlTagFilter 
	 * @Description: 将字符串中的标签去除 
	 * @param inputStr html字符串
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String htmlTagFilter(String inputStr) {
		if (inputStr == null || "".equals(inputStr.trim())) {
			return "";
		}
		String outStr = inputStr.replaceAll("//&[a-zA-Z]{1,10};", "")
				.replaceAll("<[a-zA-Z]+[1-9]?[^><]*>", "").replaceAll("</[a-zA-Z]+[1-9]?>", "");
		if (outStr == null || "null".equals(outStr)) {
			outStr = "";
		}
		return outStr;
	}

	/** 
	 * @Title: splitToFixLength 
	 * @Description: 把一个字符串截成固定长度的字符串数组 
	 * @param inStr 需要截的字符串
	 * @param length 需要截成的长度 
	 * @return 需要截的字符串 "1234567"，需要截成的长度 "3"，1234567==>"123","456","7"
	 * @return String[]    返回类型
	 */
	public static String[] splitToFixLength(String inStr, int length) {
		int slen = inStr.length();
		Double m = Math.ceil(Double.valueOf(slen) / length);
		String[] ss = new String[m.intValue()];
		for (int i = 0; i < m.intValue(); i++) {
			int sidx = i * length;
			int eidx = (i * length + length) > slen ? slen : (i * length + length);
			String s = inStr.substring(sidx, eidx);
			ss[i] = s;
		}
		return ss;
	}

	/** 
	 * @Title: splitToExtendLength 
	 * @Description: 将带小数位的数字 截成 小数位为length的数据 如果length小数位之后有大于0的数据 直接length之前补0.##1
	 * @param str 需要截的字符串
	 * @param length 需要截成的长度
	 * @return 参数说明 0.7001或者 0.7000 都截取为0.70
	 * @return String    返回类型
	 */
	public static String splitToExtendLength(String str, int length) {
		String[] strArray = str.split("\\.");
		String rtnStr = str;
		if (strArray.length <= 1) {
			if (length > 0) {
				rtnStr += ".";
				for (int i = 0; i < length; i++) {
					rtnStr += "0";
				}

			}
		} else {
			int pointLength = strArray[1].length();
			if (length == 0) {
				String hzValue = strArray[1].substring(length);
				if (Double.parseDouble(hzValue) > 0) {
					rtnStr = (Integer.parseInt(strArray[0]) + 1) + "";
				} else {
					rtnStr = (Integer.parseInt(strArray[0])) + "";
				}
			} else {
				if (pointLength > length) {
					String preValue = strArray[1].substring(0, length);
					String hzValue = strArray[1].substring(length);
					// 0.1203
					if (Double.parseDouble(hzValue) > 0) {
						String addValue = "0.";
						for (int i = 0; i < length - 1; i++) {
							addValue += "0";
						}
						addValue += "1";
						rtnStr = (Double.parseDouble(strArray[0] + "." + preValue)
								+ Double.parseDouble(addValue)) + "";
					} else {
						// 0.2200
						rtnStr = strArray[0] + "." + preValue;
					}
				}
				// 0.1
				if (pointLength < length) {
					String addValue = "";
					for (int i = 0; i < length - pointLength; i++) {
						addValue += "0";
					}
					rtnStr = strArray[0] + "." + strArray[1] + addValue;
				}
			}
		}
		return rtnStr;
	}

	/** 
	 * @Title: formatNumber 
	 * @Description: 如果数字小數位不能满足format的位数 自动补0 
	 * @param str 原始小数位数据
	 * @param format 格式化长度
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String formatNumber(String str, int format) {
		String[] strArray = str.split("\\.");
		String rtnStr = str;
		if (strArray.length <= 1) {
			rtnStr = str;
		} else {
			if (strArray[1].length() < format) {
				int addZero = format - strArray[1].length();
				for (int i = 0; i < addZero; i++) {
					rtnStr += "0";
				}
			}
		}
		return rtnStr;
	}

	/** 
	 * @Title: dropNull 
	 * @Description: 删除数组中的null 
	 * @param ins 数组数据
	 * @return 参数说明
	 * @return String[]    返回类型
	 */
	public static String[] dropNull(String[] ins) {
		if (ins == null) {
			return null;
		}
		int i = ins.length;
		for (String s : ins) {
			if (s == null) {
				i--;
			}
		}
		String[] fs = ins;
		if (i < ins.length) {
			fs = new String[i];
			i = 0;
			for (String s : ins) {
				if (s != null) {
					fs[i] = s;
					i++;
				}
			}
		}
		return fs;
	}

	/** 
	 * @Title: compare 
	 * @Description: 返回两个字符串对比大小 
	 * @param a 字符串一
	 * @param b 字符串二
	 * @return 参数说明 a>b ==> 1, a=b ==> 0,a<b ==> -1
	 * @return int    返回类型
	 */
	public static int compare(String a, String b) {
		try {
			if (StringUtils.isChinesecharacters(a)) {
				a = StringUtils.getFirstLetter(a);
				b = StringUtils.getFirstLetter(b);
			}
			if (a instanceof Comparable) {
				Comparable d1 = (Comparable) a;
				Comparable d2 = (Comparable) b;
				int r = d1.compareTo(d2);
				if (r == 0) {
					return 0;
				}
				return r > 0 ? 1 : -1;
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/** 
	 * @Title: isChinesecharacters 
	 * @Description: 判断是否是汉字 
	 * @param str 原始字符串
	 * @return 参数说明
	 * @return boolean    返回类型
	 */
	public static boolean isChinesecharacters(String str) {
		if (str == null) {
			return false;
		}
		char[] chars = str.toCharArray();
		boolean isGB2312 = false;
		for (int i = 0; i < chars.length; i++) {
			byte[] bytes = ("" + chars[i]).getBytes();
			if (bytes.length == 2) {
				int[] ints = new int[2];
				ints[0] = bytes[0] & 0xff;
				ints[1] = bytes[1] & 0xff;
				if (ints[0] >= 0x81 && ints[0] <= 0xFE && ints[1] >= 0x40 && ints[1] <= 0xFE) {
					isGB2312 = true;
					break;
				}
			}
		}
		return isGB2312;
	}

	/** 
	 * @Title: isNum 
	 * @Description: 判断是否为字符串数字 
	 * @param str  原始字符串
	 * @return 参数说明
	 * @return boolean    返回类型
	 */
	public static boolean isNum(String str) {
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	/** 
	 * @Title: isTypeBoolean 
	 * @Description: 判断对象类型是否为Boolean 
	 * @param obj 待检查对象
	 * @return 参数说明
	 * @return boolean    返回类型
	 */
	public static boolean isTypeBoolean(Object obj) {
		if (obj instanceof Boolean) {
			return true;
		}
		return false;
	}

	/** 
	 * @Title: isTheStyle 
	 * @Description: 判断一个字符串是否由数字、字母、数字字母组成
	 * @param pStr 需要判断的字符串
	 * @param pStyle 判断规则,取值为MvcConstants.S_STYLE_N | MvcConstants.S_STYLE_L | MvcConstants.S_STYLE_NL
	 * @return 参数说明
	 * @return boolean    返回类型
	 */
	public static boolean isTheStyle(String pStr, String pStyle) {
		for (int i = 0; i < pStr.length(); i++) {
			char c = pStr.charAt(i);
			if (pStyle.equals(BaseGlobal.S_STYLE_N)) {
				if (!Character.isDigit(c))
					return false;
			} else if (pStyle.equals(BaseGlobal.S_STYLE_L)) {
				if (!Character.isLetter(c))
					return false;
			} else if (pStyle.equals(BaseGlobal.S_STYLE_NL)) {
				if (Character.isLetterOrDigit(c))
					return false;
			}
		}
		return true;
	}

	/**
	 * 将传入的身份证号码进行校验，并返回一个对应的18位身份证
	 * 
	 * @param personIDCode
	 *            身份证号码
	 * @return String 十八位身份证号码
	 * @throws 无效的身份证号
	 */
	/** 
	 * @Title: getFixedPersonIDCode 
	 * @Description: 将传入的身份证号码进行校验，并返回一个对应的18位身份证 
	 * @param personIDCode 身份证号码
	 * @return 十八位身份证号码
	 * @throws Exception 参数说明
	 * @return String    返回类型
	 */
	public static String getFixedPersonIDCode(String personIDCode) throws Exception {
		if (personIDCode == null) {
			throw new Exception("输入的身份证号无效，请检查");
		}
		if (personIDCode.length() == 18) {
			if (isIdentity(personIDCode)) {
				return personIDCode;
			} else {
				throw new Exception("输入的身份证号无效，请检查");
			}
		} else if (personIDCode.length() == 15) {
			return fixPersonIDCodeWithCheck(personIDCode);
		} else {
			throw new Exception("输入的身份证号无效，请检查");
		}
	}

	/** 
	 * @Title: fixPersonIDCodeWithCheck 
	 * @Description: 修补15位居民身份证号码为18位，并校验15位身份证有效性 
	 * @param personIDCode 十五位身份证号码
	 * @return 十八位身份证号码
	 * @throws Exception 参数说明
	 * @return String    返回类型
	 */
	public static String fixPersonIDCodeWithCheck(String personIDCode) throws Exception {
		if (personIDCode == null || personIDCode.trim().length() != 15) {
			throw new Exception("输入的身份证号不足15位，请检查");
		}
		if (!isIdentity(personIDCode)) {
			throw new Exception("输入的身份证号无效，请检查");
		}
		return fixPersonIDCodeWithoutCheck(personIDCode);
	}

	/** 
	 * @Title: fixPersonIDCodeWithoutCheck 
	 * @Description: 修补15位居民身份证号码为18位，不校验身份证有效性 
	 * @param personIDCode 十五位身份证号码
	 * @return 十八位身份证号码
	 * @throws Exception 参数说明
	 * @return String    返回类型
	 */
	public static String fixPersonIDCodeWithoutCheck(String personIDCode) throws Exception {
		if (personIDCode == null || personIDCode.trim().length() != 15) {
			throw new Exception("输入的身份证号不足15位，请检查");
		}
		String id17 = personIDCode.substring(0, 6) + "19" + personIDCode.substring(6, 15); // 15位身份证补'19'
		char[] code = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' }; // 11个校验码字符
		int[] factor = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 }; // 18个加权因子
		int[] idcd = new int[18];
		int sum; // 根据公式 ∑(ai×Wi) 计算
		int remainder; // 第18位校验码
		for (int i = 0; i < 17; i++) {
			idcd[i] = Integer.parseInt(id17.substring(i, i + 1));
		}
		sum = 0;
		for (int i = 0; i < 17; i++) {
			sum = sum + idcd[i] * factor[i];
		}
		remainder = sum % 11;
		String lastCheckBit = String.valueOf(code[remainder]);
		return id17 + lastCheckBit;
	}

	/** 
	 * @Title: isIdentity 
	 * @Description: 判断是否是有效的18位或15位居民身份证号码 
	 * @param identity 18位或15位居民身份证号码
	 * @return 参数说明
	 * @return boolean    返回类型
	 */
	public static boolean isIdentity(String identity) {
		if (identity == null) {
			return false;
		}
		if (identity.length() == 18 || identity.length() == 15) {
			String id15 = null;
			if (identity.length() == 18) {
				id15 = identity.substring(0, 6) + identity.substring(8, 17);
			} else {
				id15 = identity;
			}
			try {
				Long.parseLong(id15); // 校验是否为数字字符串

				String birthday = "19" + id15.substring(6, 12);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				sdf.parse(birthday); // 校验出生日期
				if (identity.length() == 18
						&& !fixPersonIDCodeWithoutCheck(id15).equals(identity)) {
					return false; // 校验18位身份证
				}
			} catch (Exception e) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	/** 
	 * @Title: getBirthdayFromPersonIDCode 
	 * @Description: 从身份证号中获取出生日期，身份证号可以为15位或18位
	 * @param identity  身份证号
	 * @return 出生日期
	 * @throws Exception 身份证号出生日期段有误
	 * @return Timestamp    返回类型
	 */
	public static Timestamp getBirthdayFromPersonIDCode(String identity) throws Exception {
		String id = getFixedPersonIDCode(identity);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			Timestamp birthday = new Timestamp(sdf.parse(id.substring(6, 14)).getTime());
			return birthday;
		} catch (ParseException e) {
			throw new Exception("不是有效的身份证号，请检查");
		}
	}

	/** 
	 * @Title: getGenderFromPersonIDCode 
	 * @Description: 从身份证号获取性别 
	 * @param identity 身份证号
	 * @return 性别代码
	 * @throws Exception 无效的身份证号码
	 * @return String    返回类型
	 */
	public static String getGenderFromPersonIDCode(String identity) throws Exception {
		String id = getFixedPersonIDCode(identity);
		char sex = id.charAt(16);
		return sex % 2 == 0 ? "2" : "1";
	}

	/** 
	 * @Title: mergeStringArray 
	 * @Description: 合并字符串数组 
	 * @param a 字符串数组0
	 * @param b 字符串数组1
	 * @return 返回合并后的字符串数组
	 * @return String[]    返回类型
	 */
	public static String[] mergeStringArray(String[] a, String[] b) {
		if (a.length == 0 || isEmpty(a)) {
			return b;
		}
		if (b.length == 0 || isEmpty(b)) {
			return a;
		}
		String[] c = new String[a.length + b.length];
		for (int m = 0; m < a.length; m++) {
			c[m] = a[m];
		}
		for (int i = 0; i < b.length; i++) {
			c[a.length + i] = b[i];
		}
		return c;
	}

	/** 
	 * @Title: encodeChineseDownloadFileName 
	 * @Description: 对文件流输出下载的中文文件名进行编码 屏蔽各种浏览器版本的差异性 
	 * @param request 请求对象
	 * @param pFileName 文件名称
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String encodeChineseDownloadFileName(HttpServletRequest request,
			String pFileName) {
		String agent = request.getHeader("USER-AGENT");
		try {
			if (null != agent && -1 != agent.indexOf("MSIE")) {
				pFileName = URLEncoder.encode(pFileName, "utf-8");
			} else {
				pFileName = new String(pFileName.getBytes("utf-8"), "iso8859-1");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return pFileName;
	}

	/** 
	 * @Title: isIE 
	 * @Description: 判断是否是IE浏览器 
	 * @param request 请求对象
	 * @return 参数说明
	 * @return boolean    返回类型
	 */
	public static boolean isIE(HttpServletRequest request) {
		String userAgent = request.getHeader("USER-AGENT").toLowerCase();
		boolean isIe = true;
		int index = userAgent.indexOf("msie");
		if (index == -1) {
			isIe = false;
		}
		return isIe;
	}

	/** 
	 * @Title: isChrome 
	 * @Description: 判断是否是Chrome浏览器
	 * @param request 请求对象
	 * @return 参数说明
	 * @return boolean    返回类型
	 */
	public static boolean isChrome(HttpServletRequest request) {
		String userAgent = request.getHeader("USER-AGENT").toLowerCase();
		boolean isChrome = true;
		int index = userAgent.indexOf("chrome");
		if (index == -1) {
			isChrome = false;
		}
		return isChrome;
	}

	/** 
	 * @Title: isFirefox 
	 * @Description: 判断是否是Firefox浏览器 
	 * @param request 请求对象
	 * @return 参数说明
	 * @return boolean    返回类型
	 */
	public static boolean isFirefox(HttpServletRequest request) {
		String userAgent = request.getHeader("USER-AGENT").toLowerCase();
		boolean isFirefox = true;
		int index = userAgent.indexOf("firefox");
		if (index == -1) {
			isFirefox = false;
		}
		return isFirefox;
	}

	/** 
	 * @Title: getClientExplorerType 
	 * @Description: 获取客户端类型
	 * @param request 请求对象
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String getClientExplorerType(HttpServletRequest request) {
		String userAgent = request.getHeader("USER-AGENT").toLowerCase();
		String explorer = "非主流浏览器";
		if (isIE(request)) {
			int index = userAgent.indexOf("msie");
			explorer = userAgent.substring(index, index + 8);
		} else if (isChrome(request)) {
			int index = userAgent.indexOf("chrome");
			explorer = userAgent.substring(index, index + 12);
		} else if (isFirefox(request)) {
			int index = userAgent.indexOf("firefox");
			explorer = userAgent.substring(index, index + 11);
		}
		return explorer.toUpperCase();
	}

	/** 
	 * @Title: getPathFromClass 
	 * @Description: 获取class文件所在绝对路径 
	 * @param cls 类文件
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String getPathFromClass(Class cls) {
		String path = null;
		if (cls == null) {
			throw new NullPointerException();
		}
		URL url = getClassLocationURL(cls);
		if (url != null) {
			path = url.getPath();
			if ("jar".equalsIgnoreCase(url.getProtocol())) {
				try {
					path = new URL(path).getPath();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				int location = path.indexOf("!/");
				if (location != -1) {
					path = path.substring(0, location);
				}
			}
			File file = new File(path);
			try {
				path = file.getCanonicalPath();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return path;
	}

	/** 
	 * @Title: getFullPathRelateClass 
	 * @Description: 这个方法可以通过与某个类的class文件的相对路径来获取文件或目录的绝对路径。 通常在程序中很难定位某个相对路径，特别是在B/S应用中。
	 *               通过这个方法，我们可以根据我们程序自身的类文件的位置来定位某个相对路径。
	 *               比如：某个txt文件相对于程序的Test类文件的路径是../../resource/test.txt，
	 *               那么使用本方法Path.getFullPathRelateClass("../../resource/test.txt",Test.class)
	 *               得到的结果是txt文件的在系统中的绝对路径。 
	 * @param relatedPath 相对路径
	 * @param cls 用来定位的类
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String getFullPathRelateClass(String relatedPath, Class cls) {
		String path = null;
		if (relatedPath == null) {
			throw new NullPointerException();
		}
		String clsPath = getPathFromClass(cls);
		File clsFile = new File(clsPath);
		String tempPath = clsFile.getParent() + File.separator + relatedPath;
		File file = new File(tempPath);
		try {
			path = file.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}

	/** 
	 * @Title: getClassLocationURL 
	 * @Description: 获取类的class文件位置的URL 
	 * @param cls 用来定位的类
	 * @return 参数说明
	 * @return URL    返回类型
	 */
	private static URL getClassLocationURL(final Class cls) {
		if (cls == null) {
			throw new IllegalArgumentException("null input: cls");
		}
		URL result = null;
		final String clsAsResource = cls.getName().replace('.', '/').concat(".class");
		final ProtectionDomain pd = cls.getProtectionDomain();
		if (pd != null) {
			final CodeSource cs = pd.getCodeSource();
			if (cs != null) {
				result = cs.getLocation();
			}
			if (result != null) {
				if ("file".equals(result.getProtocol())) {
					try {
						if (result.toExternalForm().endsWith(".jar")
								|| result.toExternalForm().endsWith(".zip")) {
							result = new URL("jar:".concat(result.toExternalForm()).concat("!/")
									.concat(clsAsResource));
						} else if (new File(result.getFile()).isDirectory())
							result = new URL(result, clsAsResource);
					} catch (MalformedURLException ignore) {
						ignore.printStackTrace();
					}
				}
			}
		}
		if (result == null) {
			final ClassLoader clsLoader = cls.getClassLoader();
			result = clsLoader != null ? clsLoader.getResource(clsAsResource)
					: ClassLoader.getSystemResource(clsAsResource);
		}
		return result;
	}

	/** 
	 * @Title: getRandom 
	 * @Description: 获取start到end区间的随机数,不包含start+end
	 * @param start 开始号码
	 * @param end 结束号码
	 * @return 参数说明
	 * @return BigDecimal    返回类型
	 */
	public static BigDecimal getRandom(int start, int end) {
		return new BigDecimal(start + Math.random() * end);
	}

	/** 
	 * @Title: getMapKeys 
	 * @Description: 提取Map的所有Key 
	 * @param map 集合数据
	 * @return 参数说明
	 * @return String[]    返回类型
	 */
	public static String[] getMapKeys(Map map) {
		Iterator it = map.entrySet().iterator();
		String[] out = new String[map.size()];
		int i = 0;
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			out[i] = e.getKey().toString();
			i++;
		}
		return out;
	}

	/** 
	 * @Title: getMapByKey 
	 * @Description: Map的Key中是否包含某个字符 
	 * @param map 集合数据
	 * @param key 键值
	 * @return 参数说明
	 * @return Map    返回类型
	 */
	public static Map getMapByKey(Map map, String key) {
		Map map1 = new HashMap();
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			if (e.getKey().toString().startsWith(key)) {
				map1.put(e.getKey(), e.getValue());
			}
		}
		return map1;
	}

	/** 
	 * @Title: getValByKey 
	 * @Description: 根据Map的Key获取数据
	 * @param map 集合
	 * @param key 键值
	 * @return 参数说明
	 * @return Object    返回类型
	 */
	public static Object getValByKey(Map map, String key) {
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			if (e.getKey().toString() == key) {
				return map.get(e.getKey());
			}
		}
		return null;
	}

	/** 
	 * @Title: arrayToStr 
	 * @Description: 将数组转换成 ，号隔开的字符串 
	 * @param str 数组
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String arrayToStr(String[] str) {
		String split = "";
		for (int i = 0; i < str.length; i++) {
			split += str[i] + ",";
		}
		if (split.endsWith(",")) {
			split = split.substring(0, split.length() - 1);
		}
		return split;
	}

	/** 
	 * @Title: addNonRepeatedList 
	 * @Description: 将dest的值存入src 并且更新拓展的src 
	 * @param src 原集合
	 * @param dest 目标集合
	 * @param extSrc 拓展的原集合
	 * @param extDest 拓展的目标集合 
	 * @return void    返回类型
	 */
	public static void addNonRepeatedList(List src, List dest, List extSrc, List extDest) {
		for (int i = 0; dest != null && i < dest.size(); i++) {
			Object obj = dest.get(i);
			if (!src.contains(obj)) {
				src.add(obj);
				if (extSrc != null) {
					extSrc.add(extDest.get(i));
				}
			} else {
				int index = src.indexOf(obj);
				if (extSrc != null) {
					extSrc.remove(index);
					extSrc.add(index, extDest.get(i));
				}
			}
		}
	}

	/** 
	 * @Title: getRandomNumber 
	 * @Description: 产生digCount位的随机数
	 * @param digCount 产生随机数的指定数值
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String getRandomNumber(int digCount) {
		SecureRandom sr = new SecureRandom();
		StringBuilder sb = new StringBuilder(digCount);
		for (int i = 0; i < digCount; i++)
			sb.append((char) ('0' + sr.nextInt(10)));
		return sb.toString();
	}

	/** 
	 * @Title: transList 
	 * @Description: 将List中值与Map中的key替换 
	 * @param lm 集合数据
	 * @param key Map集合
	 * @return 参数说明
	 * @return List<Map>    返回类型
	 */
	public static List<Map> transList(List lm, Map key) {
		List newList = null;
		try {
			newList = new ArrayList();
			for (int i = 0; i < lm.size(); i++) {
				Object object = lm.get(i);
				Map newMap = new HashMap();
				if (object instanceof Map) {
					Map tmp = (Map) object;

					Object[] keyName = tmp.keySet().toArray();
					for (int j = 0; j < keyName.length; j++) {
						if (key.keySet().contains(keyName[j])) {
							newMap.put(key.get(keyName[j]), tmp.get(keyName[i]));
						} else {
							newMap.put(keyName[j], tmp.get(keyName[i]));
						}
					}
				} else {
					Field[] keyName = object.getClass().getDeclaredFields();
					for (int j = 0; j < keyName.length; j++) {
						keyName[j].setAccessible(true);
						if (key.keySet().contains(keyName[j].getName())) {
							newMap.put(key.get(keyName[j].getName()), keyName[j].get(object));
						} else {
							newMap.put(keyName[j].getName(), keyName[j].get(object));
						}
					}
				}
				newList.add(newMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newList;
	}

	/** 
	 * @Title: existAtStringArray 
	 * @Description: 判断字符串数组中是否存在该值
	 * @param array 字符串数组
	 * @param val 判定值
	 * @return 参数说明
	 * @return boolean    返回类型
	 */
	public static boolean existAtStringArray(String[] array, String val) {
		for (String tmp : array) {
			if (tmp.equals(val)) {
				return true;
			}
		}
		return false;
	}

	/** 
	 * @Title: getExceptionMsg 
	 * @Description: 提取完整的错误信息
	 * @param ex 异常信息
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String getExceptionMsg(Exception ex) {
		StringBuffer sbf = new StringBuffer();
		StackTraceElement[] err = ex.getStackTrace();

		String msg = ("错误详细信息：" + err[0].getClassName() + "." + err[0].getMethodName()
				+ err[0].getLineNumber() + "行出现" + ex.getMessage() + "异常！");

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ex.printStackTrace(new PrintStream(baos));
		String exception = baos.toString();
		return msg + exception;
	}

	/** 
	 * @Title: getKey 
	 * @Description: 获取唯一的编号 
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String getKey() {
		return UUID.randomUUID().toString().toUpperCase();
	}

	/** 
	 * @Title: trimObject 
	 * @Description: 字符串对象去掉空格
	 * @param str 字符串对象
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String trimObject(Object str) {
		if (str == null) {
			str = "";
		}
		return str.toString().trim();
	}

	/** 
	 * @Title: groupBy 
	 * @Description: 通过字段对rtnList进行分组 
	 * @param rtnList 分组集合
	 * @param groupField 分组字段
	 * @return 参数说明
	 * @return List<Map>    返回类型
	 */
	public static List<Map> groupBy(List<Map> rtnList, List<String> groupField) {
		List<Map> rtnArray = new ArrayList<Map>();
		for (int i = 0; rtnList != null && i < rtnList.size(); i++) {
			boolean ifExist = false;
			Map record = null;
			for (int k = 0; k < rtnArray.size(); k++) {
				boolean ifEqual = true;
				for (int s = 0; s < groupField.size(); s++) {
					String field = groupField.get(s);
					if (rtnArray.get(k).get(field) != rtnList.get(i).get(field)
							&& !rtnArray.get(k).get(field).equals(rtnList.get(i).get(field))) {
						ifEqual = false;
					}
				}
				if (ifEqual) {
					record = rtnArray.get(k);
					ifExist = true;
					break;
				}
			}
			if (ifExist) {
				List grouparray = (List) record.get("grouparray");
				grouparray.add(rtnList.get(i));
			} else {
				Map cmap = new HashMap();
				List cList = new ArrayList();
				cmap.put("grouparray", cList);
				rtnArray.add(cmap);
				cList.add(rtnList.get(i));
				for (int s = 0; s < groupField.size(); s++) {
					String field = groupField.get(s);
					cmap.put(field, rtnList.get(i).get(field));
				}
			}

		}
		return rtnArray;
	}

	/** 
	 * @Title: getRealSessionId 
	 * @Description: 提取真正的SessionID
	 * @param sid 真正的SessionID
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String getRealSessionId(String sid) {
		if (StringUtils.isNotBlank(sid)) {
			if (sid.length() > 52) {
				return sid.substring(0, 52);
			}
		}
		return sid;
	}

	/**
	 * 姓名至少2个汉字
	 * @author lx
	 * @craeteDae	2013-12-11
	 * @param str	
	 * @return 	true 是 
	 */
	public static boolean valiName(String str) {
		if (str != null && str.trim().length() != 0) {
			String reg = "[\u4e00-\u9fa5]{2}";
			Pattern pattern = Pattern.compile(reg);
			Matcher matcher = pattern.matcher(str);
			if (matcher.find()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 验证6位数字的短信验证码或者交易密码
	 * @author lx
	 * @craeteDae   2013-12-11
	 * @param str   
	 * @return  true 是 
	 */
	public static boolean valiSixNum(String str) {
		if (str != null && str.trim().length() != 0) {
			String reg = "^\\d{6}$";
			Pattern pattern = Pattern.compile(reg);
			Matcher matcher = pattern.matcher(str);
			if (matcher.find()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 去掉前后符号
	 * @param dealStr
	 * @return
	 */
	public static String getDealWithFBMark(String dealStr) {
		if (dealStr.indexOf(";") == 0) {
			dealStr = dealStr.substring(1);
		}
		if (dealStr.lastIndexOf(";") == dealStr.length() - 1) {
			dealStr = dealStr.substring(0, dealStr.length() - 1);
		}
		return dealStr;
	}

	/**
	 * 可以为空但长度有限制
	 * @author lx
	 * @craeteDae	2013-12-11
	 * @param str	
	 * @return 	true 是 
	 */
	public static boolean AllowNullLength(String str, int num) {
		if (str == null || str.trim().length() <= num) {
			return true;
		}
		return false;
	}

	/**
	 * 非空且长度有限制
	 * @author lx
	 * @craeteDae	2013-12-11
	 * @param str	
	 * @return 	true 是 
	 */
	public static boolean valiLength(String str, int num) {
		if (str != null && str.trim().length() != 0 && str.trim().length() <= num) {
			return true;
		}
		return false;
	}

	/**
	 * 用于验证类型 AB
	 * */
	public static boolean CheckABType(String str) {
		if (str != null && str.trim().length() != 0) {
			String reg = "^[A-B]$";
			Pattern pattern = Pattern.compile(reg);
			Matcher matcher = pattern.matcher(str);
			if (matcher.find()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 用于验证类型 A-Z等
	 * */
	public static boolean CheckStatusType(String str) {
		if (str != null && str.trim().length() != 0) {
			String reg = "^[A-Z]$";
			Pattern pattern = Pattern.compile(reg);
			Matcher matcher = pattern.matcher(str);
			if (matcher.find()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 用于验证DataNo
	 * */
	public static boolean CheckDataNo(String str) {
		if (str != null && str.trim().length() != 0) {
			String reg = "^[A-Z]+$";
			Pattern pattern = Pattern.compile(reg);
			Matcher matcher = pattern.matcher(str);
			if (matcher.find()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 用于验证按揭还款日1-31等
	 * */
	public static boolean CheckInstallRepayDay(String str) {
		if (str != null && str.trim().length() != 0 && valiInt(str)) {
			if (Integer.parseInt(str) > 0 && Integer.parseInt(str) < 32) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 用于验证提现还款日1-28等
	 * */
	public static boolean CheckRepayDay(String str, String repayMethod) {
		if (str != null && str.trim().length() != 0 && valiInt(str)) {
			if ("0".equals(repayMethod)) {
				if (Integer.parseInt(str) > 0 && Integer.parseInt(str) < 29) {
					return true;
				}
			} else {
				if (Integer.parseInt(str) > 0 && Integer.parseInt(str) <= 31) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 用于验证字母
	 * */
	public static boolean CheckLetter(String str) {
		if (str != null && str.trim().length() != 0) {
			String reg = "[a-z|A-Z]";
			Pattern pattern = Pattern.compile(reg);
			Matcher matcher = pattern.matcher(str);
			if (matcher.find()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断密码长度必须大于8位小于16位，且必须包括数字及英文字符
	 * @author lx
	 * @craeteDae	2015-05-11
	 * @param str	
	 * @return 	true 是 
	 */
	public static boolean valiPassWord(String str) {
		if (str != null && str.trim().length() != 0) {
			String reg = "^(?![a-z][A-Z]+$)(?!\\d+$)[a-z0-9]{8,16}$";
			Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(str);
			if (matcher.find()) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 
	 * @Title: valiPassword 
	 * @Description: 在原有的函数的基础上  判断密码长度必须大于6位小于16位，且必须包括数字及英文字符
	 * @param str
	 * @return 参数说明
	 * @return boolean    返回类型
	 */
	public static boolean valiPassword(String str) {
		if (str != null && str.trim().length() != 0) {
			String reg = "^(?![a-z][A-Z]+$)(?!\\d+$)[a-z0-9]{6,16}$";
			Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(str);
			if (matcher.find()) {
				return true;
			}
		}
		return false;
	}
	
	

	/**
	 * 开户账号只可输入16或19位数字
	 * @author lx
	 * @craeteDae	2013-12-11
	 * @param str	
	 * @return 	true 是 
	 */
	public static boolean valiBankNumber(String str) {
		if (str != null && str.trim().length() != 0) {
			str.trim();
//			String reg = "^\\d{0,30}$";
			String reg = "^(\\d{16}|\\d{19})$";
			Pattern pattern = Pattern.compile(reg);
			Matcher matcher = pattern.matcher(str);
			if (matcher.find()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 验证邮箱
	 * @author lx
	 * @craeteDae	2013-12-11
	 * @param str	
	 * @return 	true 是 
	 */
	public static boolean valiEmail(String str, int num) {
		if (str != null && str.trim().length() != 0 && str.trim().length() <= num) {
			if (!str.substring(str.lastIndexOf("@") + 1).toLowerCase().contains("zxfinance")) {
				String reg = "^([a-z0-9A-Z]+[_|\\.]?)+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
				Pattern pattern = Pattern.compile(reg);
				Matcher matcher = pattern.matcher(str);
				if (matcher.find()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 姓别为男和女
	 * @author lx
	 * @craeteDae	2013-12-11
	 * @param str	
	 * @return 	true 是 
	 */
	public static boolean valiSex(String str) {
		if (str != null && str.trim().length() != 0) {
			if ("男".equals(str) || "女".equals(str)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 手机只可输入1开头的十一位数
	 * @author lx
	 * @craeteDae	2013-12-11
	 * @param str	
	 * @return 	true 是 
	 */
	public static boolean valiMobile(String str) {
		if (str != null && str.trim().length() != 0) {
			String reg = "^[1]\\d{10}$";
			Pattern pattern = Pattern.compile(reg);
			Matcher matcher = pattern.matcher(str);
			if (matcher.find()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * token值位数为32位
	 * @author lx
	 * @craeteDae	2013-12-11
	 * @param str	
	 * @return 	true 是 
	 */
	public static boolean valiToken(String str) {
		if (StringUtils.isBlank(str) || str.trim().length() == 32 || str.trim().length() == 64) {
			return true;
		}
		return false;
	}

	/**
	 * 日期时间类型验证(含日期 时间)
	 * @author lx
	 * @craeteDae	2013-12-11
	 * @param str	
	 * @return 	true 是 
	 */
	public static boolean valiDateTime(String str) {
		if (str != null && str.trim().length() != 0) {
			if (str.indexOf(".") > 0) {
				str = str.substring(0, str.indexOf("."));
			}
			String reg = "^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))(?:\\s)?(([0-1]?[0-9])|([2][0-3])):([0-5]?[0-9])(:([0-5]?[0-9]))?$";
			Pattern pattern = Pattern.compile(reg);
			Matcher matcher = pattern.matcher(str);
			if (matcher.find()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 年月类型验证
	 * @author lx
	 * @craeteDae	2013-12-11
	 * @param str	
	 * @return 	true 是 
	 */
	public static boolean valiYearMonth(String str) {
		if (str != null && str.trim().length() != 0) {
			if (str.indexOf(".") > 0) {
				str = str.substring(0, str.indexOf("."));
			}
			String reg = "^[0-9]{4}-((0[13578]|(10|12))|(02)|(0[469]|11))$";
			Pattern pattern = Pattern.compile(reg);
			Matcher matcher = pattern.matcher(str);
			if (matcher.find()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 日期类型验证(不含时间)
	 * @author lx
	 * @craeteDae	2013-12-11
	 * @param str	
	 * @return 	true 是 
	 */
	public static boolean valiDate(String str) {
		if (str != null && str.trim().length() != 0) {
			if (str.indexOf(".") > 0) {
				str = str.substring(0, str.indexOf("."));
			}
			String reg = "^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$";
			Pattern pattern = Pattern.compile(reg);
			Matcher matcher = pattern.matcher(str);
			if (matcher.find()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 时间类型验证(不含日期 )
	 * @author lx
	 * @craeteDae	2013-12-11
	 * @param str	
	 * @return 	true 是 
	 */
	public static boolean valiTime(String str) {
		if (str != null && str.trim().length() != 0 && "0:00:00" != str) {
			if (str.indexOf(".") > 0) {
				str = str.substring(0, str.indexOf("."));
			}
			String re = "^(([0-1]?[0-9])|([2][0-3])):([0-5]?[0-9])(:([0-5]?[0-9]))?$";
			Pattern pattern = Pattern.compile(re);
			Matcher matcher = pattern.matcher(str);
			if (matcher.find()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 1和0类型验证
	 * @author lx
	 * @craeteDae	2014-04-17
	 * @param str	
	 * @return 	true 是 
	 */
	public static boolean valiZeroAndOne(String str) {
		if ("0".equals(str) || "1".equals(str)) {
			return true;
		}
		return false;
	}

	/**
	 * 1和2类型验证
	 * @author lx
	 * @craeteDae	2014-04-17
	 * @param str	
	 * @return 	true 是 
	 */
	public static boolean valiOneAndTwo(String str) {
		if ("1".equals(str) || "2".equals(str)) {
			return true;
		}
		return false;
	}

	/**
	 * 0,1和2类型验证
	 * @author lx
	 * @craeteDae	2014-04-17
	 * @param str	
	 * @return 	true 是 
	 */
	public static boolean valiZeroOneTwo(String str) {
		if ("0".equals(str) || "1".equals(str) || "2".equals(str)) {
			return true;
		}
		return false;
	}

	/**
	 * 1,2和3类型验证
	 * @author lx
	 * @craeteDae	2014-04-17
	 * @param str	
	 * @return 	true 是 
	 */
	public static boolean valiOneTwoThree(String str) {
		if ("1".equals(str) || "2".equals(str) || "3".equals(str)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @Title: getPartString 
	 * @Description: 用于身份证和手机号码的打码  对于姓名  打码backNum 传0
	 * @param str 源目标字符串
	 * @param frontNum 前面保留多少位
	 * @param backNum   后面保留多少位
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String getPartString(String str, int frontNum, int backNum) {

		String result = "";
		String front_str = "";
		String back_str = "";
		if (isNotEmpty(str)) {

			int length = str.length();
			if (backNum != 0) {
				if (frontNum < length) {
					front_str = str.substring(0, frontNum);
				}
				if (length - backNum > 0) {
					back_str = str.substring(length - backNum, length);
				}
				result = front_str + "***" + back_str;
			} else {
				front_str = str.substring(0, frontNum);
				result = front_str + "***";
			}
		}
		return result;
	}

	/**
	 * 
	 * @Title: formatNumber 
	 * @Description: 将字符串转换为标准会计格式的数字显示
	 * @param strNum
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String formatNumber(String strNum) {
		String result = "fail";
		if (isNum(strNum)) {
			double quota = Double.parseDouble(strNum);
			if (quota > 1) {
				DecimalFormat format = new DecimalFormat("#,###.00");
				result = format.format(quota);
			} else if (quota < 1 && quota > 0) {
				DecimalFormat format = new DecimalFormat("0.00");
				result = format.format(quota);
			}

		}
		return result;
	}
	public static String formatNumber(Double num){
		String result = "fail";
		if (num > 1) {
			DecimalFormat format = new DecimalFormat("#,###.00");
			result = format.format(num);
		} else if (num < 1 && num > 0) {
			DecimalFormat format = new DecimalFormat("0.00");
			result = format.format(num);
		}
		return result;
		
		
		
	}
	public static String formatPlain(Double num){
		String result = "fail";
		if(StringUtils.isNotEmpty(num)){
			String str = String.valueOf(num);
			BigDecimal db = new BigDecimal(str);
			result = db.toPlainString();
		}
		return result;
	}

	/**
	 * 
	 * @Title: getFormatBankNum 
	 * @Description: 格式化银行卡输出
	 * @param str
	 * @param frontNum
	 * @param backNum
	 * @return 6228 **** **** **** 123
	 * @return String    返回类型
	 */
	public static String getFormatBankNum(String str, int frontNum, int backNum) {

		String result = "";
		String front_str = "";
		String back_str = "";
		String middle_str=" **** **** **** ";
		if (isNotEmpty(str)) {

			int length = str.length();
			if (backNum != 0) {
				if (frontNum < length) {
					front_str = str.substring(0, frontNum);
				}
				if (length - backNum > 0) {
					back_str = str.substring(length - backNum, length);
				}
				result = front_str + middle_str + back_str;
			} else {
				front_str = str.substring(0, frontNum);
				result = front_str + "***";
			}
		}
		return result;
	}
}
