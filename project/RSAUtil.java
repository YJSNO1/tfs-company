/**   
 * @Project: tfs-plm-web 
 * @Title: RSAUtil.java 
 * @Package com.tfstec.plm.util 
 * @Description: TODO 
 * @author A8552 
 * @date 2016年10月28日 下午4:54:45 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.plm.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.tfstec.base.StringUtils;
import com.tfstec.base.encry.MD5;
import com.tfstec.base.xml.XmlUtil;

/** 
 * @ClassName RSAUtil  
 * @Description TODO 
 * @author A8552 
 * @date 2016年10月28日  
 *   
 */
public class RSAUtil {

	  private static Cipher cipher;

	    static {
	        try {
	            cipher = Cipher.getInstance("RSA");
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        } catch (NoSuchPaddingException e) {
	            e.printStackTrace();
	        }
	    }

    /**
     * 
     * @Title: rsaDeCode 
     * @Description: rsa 解密函数 
     * @param filePath 私钥的文件地址
     * @param enPassword   App传递过来的密文
     * @return 参数说明
     * @return String    返回类型
     */
	public static String rsaDeCode(String filePath , String enPassword) {

		try {
			PrivateKey key = readWantedText(filePath);
			byte[] enBytes = Base64.decode(enPassword);
			String md5 = decrypt(key,enBytes);
		    String result = md5.substring(4, md5.length() -4);
		    return result;
		    
		} catch (Exception e) {

			return "";
		}
	}

		
	//解密主要过程函数
	 public static String decrypt(PrivateKey privateKey,  byte[]  enBytes) {
	        try {
	            cipher.init(Cipher.DECRYPT_MODE, privateKey);
	            byte[] deBytes = cipher.doFinal(enBytes);
	            return new String(deBytes);
	        } catch (InvalidKeyException e) {
	            e.printStackTrace();
	        } catch (IllegalBlockSizeException e) {
	            e.printStackTrace();
	        } catch (BadPaddingException e) {
	            e.printStackTrace();
	        } 
	        return "";
	    }

	//读取私钥函数
	public static PrivateKey readWantedText(String url) {
		try {
			FileReader fr = new FileReader(url);
			BufferedReader br = new BufferedReader(fr);
			StringBuffer sb = new StringBuffer();

			String temp = "";// 用于临时保存每次读取的内容
			temp = br.readLine();
			while ((temp = br.readLine()) != null) {
				if(temp.charAt(0) ==  '-'){
					 continue;
				}
				sb.append(temp);
			}
			PrivateKey privateKey =  getPrivateKey(sb.toString());
			return privateKey;
		
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
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
	
		byte[]  keyBytes = key.getBytes();
		keyBytes = Base64.decode(key);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
  }

}
