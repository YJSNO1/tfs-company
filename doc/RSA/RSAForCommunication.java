/**   
 * @Project: tfs-plm-web 
 * @Title: RSAForCommunication.java 
 * @Package com.tfstec.plm.util 
 * @Description: TODO 
 * @author A8552 
 * @date 2017年2月16日 上午11:30:01 
 * @Copyright: 2017 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.plm.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

import com.tfstec.base.BaseGlobal;
import com.tfstec.base.encry.Base64Coder;
import com.tfstec.base.gson.GsonUtils;
import com.tfstec.plm.ServiceConfig;

/** 
 * @ClassName RSAForCommunication  
 * @Description TODO 
 * @author A8552 
 * @date 2017年2月16日  
 *   
 */
public class RSAForCommunication {

	/**
	 * 
	 * @Title: getPrivateKey 
	 * @Description: 从文件读取的String  转换为私钥
	 * @param key   从文件读取的String
	 * @return
	 * @throws Exception 参数说明
	 * @return PrivateKey    返回类型
	 */
	public static PrivateKey getPrivateKey(String key) throws Exception {

		byte[] keyBytes = key.getBytes();
		keyBytes = Base64.decodeBase64(keyBytes);
		/* byte[] keyBytes = decode(key); */
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	// 读取私钥函数
	public static String readWantedText(String url) {
		try {
			FileReader fr = new FileReader(url);
			BufferedReader br = new BufferedReader(fr);
			StringBuffer sb = new StringBuffer();

			String temp = "";// 用于临时保存每次读取的内容
			temp = br.readLine();
			while ((temp = br.readLine()) != null) {
				if (temp.charAt(0) == '-') {
					continue;
				}
				sb.append(temp);
			}
			/*
			 * PrivateKey privateKey = getPrivateKey(sb.toString()); br.close();
			 */
			return sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @Title: readWantedText1 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param url
	 * @return 参数说明
	 * @return PublicKey    返回类型
	 */
	public static String readWantedText1(String url) {
		try {
			FileReader fr = new FileReader(url);
			BufferedReader br = new BufferedReader(fr);
			StringBuffer sb = new StringBuffer();

			String temp = "";// 用于临时保存每次读取的内容
			temp = br.readLine();
			while ((temp = br.readLine()) != null) {
				if (temp.charAt(0) == '-') {
					continue;
				}
				sb.append(temp);
			}
			// PublicKey publicKey = getPublicKey(sb.toString());
			return sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static PublicKey getPublicKey(String key) throws Exception {

		byte[] keyBytes = key.getBytes();
		keyBytes = Base64.decodeBase64(keyBytes);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	// ___________________________________________
	/**
	* 私钥解密
	* @param encryptedData 已加密数据
	* @param privateKey 私钥(BASE64编码)
	* @return
	* @throws Exception
	*/
	public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey)
			throws Exception {
		byte[] keyBytes = Base64Utils.decode(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateK);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}

	/**
	 * 公钥加密
	 * @param data 源数据
	 * @param publicKey 公钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */

	public static final String KEY_ALGORITHM = "RSA";
	private static final int MAX_ENCRYPT_BLOCK = 117;
	private static final int MAX_DECRYPT_BLOCK = 128;

	public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
		byte[] keyBytes = Base64Utils.decode(publicKey);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicK);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
	}

	public static String retJson(String code, Object msg, Object data) {

		String filePath = ServiceConfig.com_RecRSAPublicKey;
		String key = RSAForCommunication.readWantedText1(filePath);
		String asd = GsonUtils.retJson(code, msg, data, null);
		String encode = "";
		try {
			byte[] a = RSAForCommunication.encryptByPublicKey(asd.toString().getBytes(), key);
			encode = Base64Utils.encode(a);
		} catch (Exception e) {

		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(BaseGlobal.DATA, encode);
		return GsonUtils.toJson(map);
	}

	public static String retJson(String code, Object msg) {

		String filePath = ServiceConfig.com_RecRSAPublicKey;
		String key = RSAForCommunication.readWantedText1(filePath);
		String asd = GsonUtils.retJson(code, msg, "", null);
		String encode = "";
		try {
			byte[] a = RSAForCommunication.encryptByPublicKey(asd.toString().getBytes(), key);
			encode = Base64Utils.encode(a);
		} catch (Exception e) {

		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(BaseGlobal.DATA, encode);
		return GsonUtils.toJson(map);
	}
	
	public static String retJson(String asd) {

		String filePath = ServiceConfig.com_RecRSAPublicKey;
		String key = RSAForCommunication.readWantedText1(filePath);
		String encode = "";
		try {
			byte[] a = RSAForCommunication.encryptByPublicKey(asd.toString().getBytes(), key);
			encode = Base64Utils.encode(a);
		} catch (Exception e) {

		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(BaseGlobal.DATA, encode);
		return GsonUtils.toJson(map);
	}
	

}
