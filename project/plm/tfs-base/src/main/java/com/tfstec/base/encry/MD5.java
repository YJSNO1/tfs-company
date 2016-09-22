/**   
 * @Project: tfscore 
 * @Title: MD5.java 
 * @Package com.tfscore.encry 
 * @Description: MD5加密数据加密工具
 * @author lx 
 * @date 2016年6月5日 下午1:28:37 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.encry;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @ClassName MD5  
 * @Description MD5加密数据加密工具 
 * @author lx 
 * @date 2016年6月5日  
 *   
 */
public class MD5 {
	private static Logger logger = LoggerFactory.getLogger(MD5.class);
	/**全局数组**/
	private final static String[] strDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
			"A", "B", "C", "D", "E", "F" };

	/**
	 * 返回形式为数字跟字符串
	 * @param bByte
	 * @return
	 */
	private static String byteToArrayString(byte bByte) {
		int iRet = bByte;
		if (iRet < 0) {
			iRet += 256;
		}
		int iD1 = iRet / 16;
		int iD2 = iRet % 16;
		return strDigits[iD1] + strDigits[iD2];
	}

	/**
	 * 转换字节数组为16进制字串
	 * @param bByte
	 * @return
	 */
	private static String byteToString(byte[] bByte) {
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < bByte.length; i++) {
			sBuffer.append(byteToArrayString(bByte[i]));
		}
		return sBuffer.toString();
	}

	/**
	 * MD5加密
	 * @param str 待加密的字符串
	 * @return
	 */
	public static String GetMD5Code(String str) {
		String result = null;
		try {
			result = new String(str);
			MessageDigest md = MessageDigest.getInstance("MD5");
			result = byteToString(md.digest(str.getBytes()));
		} catch (NoSuchAlgorithmException ex) {
			logger.error("MD5加密出现异常：" + ex.getMessage(), ex);
		}
		return result;
	}

	/**
	 * MD5加密
	 * @param str 待加密的字符串
	 * @param lowerCase 大小写
	 * @return
	 */
	public static String GetMD5Code(String str, boolean lowerCase) {
		String result = null;
		try {
			result = new String(str);
			MessageDigest md = MessageDigest.getInstance("MD5");
			result = byteToString(md.digest(str.getBytes()));
			if (lowerCase) {
				result = result.toLowerCase();
			}
		} catch (NoSuchAlgorithmException ex) {
			logger.error("MD5加密(大小写)出现异常：" + ex.getMessage(), ex);
		}
		return result;
	}
}
