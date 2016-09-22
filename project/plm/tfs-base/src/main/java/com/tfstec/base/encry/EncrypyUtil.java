/**   
 * @Project: tfscore 
 * @Title: EncrypyUtil.java 
 * @Package com.tfscore.encry 
 * @Description: 财务系统利用基础包的BASE64加密和解密 
 * @author lx 
 * @date 2016年6月6日 上午8:44:29 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.encry;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/** 
 * @ClassName EncrypyUtil  
 * @Description 财务系统利用基础包的BASE64加密和解密 
 * @author lx 
 * @date 2016年6月6日  
 *   
 */
public class EncrypyUtil {
	private static Logger logger = LoggerFactory.getLogger(EncrypyUtil.class);

	/**    
	 * BASE64解密   
	 * @param key          
	 * @return          
	 * @throws Exception          
	 */
	@SuppressWarnings("restriction")
	public static byte[] decryptBASE64(String key) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	/**         
	 * BASE64加密   
	 * @param key          
	 * @return          
	 * @throws Exception          
	 */
	@SuppressWarnings("restriction")
	public static String encryptBASE64(byte[] key) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(key);
	}

	// 加密
	@SuppressWarnings("restriction")
	public static String getBase64(String str) {
		byte[] b = null;
		String s = null;
		try {
			b = str.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		if (b != null) {
			s = new BASE64Encoder().encode(b);
		}
		return s;
	}

	// 解密
	@SuppressWarnings("restriction")
	public static String getFromBase64(String s) {
		byte[] b = null;
		String result = null;
		if (s != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				b = decoder.decodeBuffer(s);
				result = new String(b, "UTF-8");
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return result;
	}
}
