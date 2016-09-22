/**   
 * @Project: tfscore 
 * @Title: AES.java 
 * @Package com.tfscore.encry 
 * @Description: 加密解密算法 
 * @author lx 
 * @date 2016年6月5日 下午1:15:28 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.encry;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @ClassName AES  
 * @Description 加密解密算法 
 * @author lx 
 * @date 2016年6月5日  
 *   
 */
public class AES {
	private static Logger logger = LoggerFactory.getLogger(AES.class);
	/**
	 * 获取解密后的字符串
	 * @param content 解密内容
	 * @param passcode 解密密码
	 * @return
	 */
	public static String RevertAESCode(String content, String passcode) {
		byte[] decryptFrom = parseHexStr2Byte(content);
		byte[] decryptResult = decrypt(decryptFrom, passcode);
		String decryptString = new String(decryptResult);
		return decryptString;
	}

	/**
	 * 获取加密后的字符串
	 * @param content 加密内容
	 * @param passcode 加密密码
	 * @return
	 */
	public static String GetAESCode(String content, String passcode) {
		byte[] encryptResult = encrypt(content, passcode);
		String encryptResultStr = parseByte2HexStr(encryptResult);
		return encryptResultStr;
	}

	/**
	 * 加密
	 * @param content
	 * @param password
	 * @return
	 */
	private static byte[] encrypt(String content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			// kgen.init(128,new SecureRandom(password.getBytes()));
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(password.getBytes());
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			/**创建密码器**/
			Cipher cipher = Cipher.getInstance("AES");
			byte[] byteContent = content.getBytes("utf-8");
			/**初始化密码器**/
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] result = cipher.doFinal(byteContent);
			return result;
		} catch (Exception e) {
			logger.info("AES加密出错了:" + e.getMessage());
		}
		return null;
	}

	/**
	 * 解密
	 * @param content
	 * @param password
	 * @return
	 */
	private static byte[] decrypt(byte[] content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
//			kgen.init(128,new SecureRandom(password.getBytes()));
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(password.getBytes());
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			/**创建密码器**/
			Cipher cipher = Cipher.getInstance("AES");
			/**初始化密码器**/
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] result = cipher.doFinal(content);
			return result;
		} catch (Exception e) {
			logger.info("AES解密出错了:" + e.getMessage());
		}
		return null;
	}

	/**
	 * 将二进制转换成十六进制
	 * @param buf
	 * @return
	 */
	private static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将十六进制转换为二进制
	 * @param hexStr
	 * @return
	 */
	private static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1) {
			return null;
		} else {
			byte[] result = new byte[hexStr.length() / 2];
			for (int i = 0; i < hexStr.length() / 2; i++) {
				int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
				int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
				result[i] = (byte) (high * 16 + low);
			}
			return result;
		}
	}
}
