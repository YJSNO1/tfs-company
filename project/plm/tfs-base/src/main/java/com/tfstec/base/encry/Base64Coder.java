/**   
 * @Project: tfscore 
 * @Title: Base64Coder.java 
 * @Package com.tfscore.encry 
 * @Description: Base64加减密工具 
 * @author lx 
 * @date 2016年6月5日 下午1:24:23 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.encry;

import it.sauronsoftware.base64.Base64;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @ClassName Base64Coder  
 * @Description Base64加减密工具 
 * @author lx 
 * @date 2016年6月5日  
 *   
 */
public class Base64Coder {
	private static Logger logger = LoggerFactory.getLogger(Base64Coder.class);

	/**
	 * 
	 * @Title: encodeString 
	 * @Description: 将字符串进行Base64编码
	 * @param str 要转码的字符串
	 * @return String    返回类型
	 * @throws
	 */
	public static String encodeString(String str) {
		return Base64.encode(str, "UTF-8");
	}

	/**
	 * 
	 * @Title: decodeString 
	 * @Description: 将Base64编码后的字符串进行解码 
	 * @param str 要解码的字符串
	 * @return String    返回类型
	 * @throws
	 */
	public static String decodeString(String str) {
		return Base64.decode(str, "UTF-8");
	}

	/**
	 * 
	 * @Title: decodeString 
	 * @Description:将Base64编码后的字符串进行解码 
	 * @param str
	 * @param encode GBK或者UTF-8
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String decodeString(String str,String encode) {
		return Base64.decode(str, encode);
	}
	/*
	 * base64字符串转化成图片 对字节数组字符串进行Base64解码并生成图片
	 */
	/** 
	 * @Title: generateImage 
	 * @Description: base64字符串转化成图片 对字节数组字符串进行Base64解码并生成图片 
	 * @param imgStr 原始图片路径
	 * @param imgName 将要生成的图片名称
	 * @param imgPath 将要生成的图片路径
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String generateImage(String imgStr, String imgName, String imgPath) {
		// 图像数据为空
		if (imgStr == null) {
			return null;
		}
		try {
			// Base64解码
			byte[] b = Base64.decode(imgStr.getBytes());
			for (int i = 0; i < b.length; ++i) {
				// 调整异常数据
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			// 生成jpeg图片
			String imgFilePath = imgPath + "/" + imgName;
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			return imgFilePath;
		} catch (Exception e) {
			logger.error("base64字符串转化成图片 对字节数组字符串进行Base64解码并生成图片出现异常：" + e);
			return null;
		}
	}
}
