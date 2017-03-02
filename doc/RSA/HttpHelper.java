/**   
 * @Project: tfs-plm-web 
 * @Title: HttpHelper.java 
 * @Package com.tfstec.plm.util 
 * @Description: TODO 
 * @author A8509 
 * @date 2017年2月9日 下午2:10:24 
 * @Copyright: 2017 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.plm.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.tfstec.base.BaseGlobal;
import com.tfstec.base.StringUtils;
import com.tfstec.base.gson.GsonUtils;
import com.tfstec.plm.ServiceConfig;
import com.tfstec.plm.global.GlobalVar;

/** 
 * @ClassName HttpHelper  
 * @Description TODO 
 * @author A8509 
 * @date 2017年2月9日  
 *   
 */
public class HttpHelper {
	private static Logger logger = LoggerFactory.getLogger(HttpHelper.class);

	/**
	 * @Title: getHttpRequestParamsByStream 
	 * @Description: 解析http请求POST方式数据 
	 * @param request
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String getParamsByStream(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		InputStream inputStream = null;
		BufferedReader reader = null;
		String str = "";
		String deCodeStr = "";
		try {
			inputStream = request.getInputStream();
			reader = new BufferedReader(
					new InputStreamReader(inputStream, Charset.forName("UTF-8")));
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			str = sb.toString();
		} catch (IOException e) {
			logger.error("获取请求参数发生错误", e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		try{
			// H5请求格式处理
			if (str.contains(GlobalVar.HTML5_CONTENT_REQUEST_FLAG)) {
				str = URLDecoder.decode(str, "UTF-8");
				return str;
			}
			// APP 加载H5请求格式处理
			if (str.contains(GlobalVar.APP_LOAD_HTML5_FLAG)) {
				str = URLDecoder.decode(str, "UTF-8");
				deCodeStr = str.replace("APP_LOAD_HTML5_FLAG", "");
				return deCodeStr;
			}
			
			Map<String, Object> paramsMap = GsonUtils.getJsonToObjectMap(str);
			String enCode = "";
	
			enCode = MapUtils.getString(paramsMap, "data");
			// 此处解密
			byte[] a;
			a = Base64Utils.decode(enCode);
			String privatekeyP = ServiceConfig.com_RecRSAPrivateKey;
			String privatekey = RSAForCommunication.readWantedText(privatekeyP);
			byte[] b = RSAForCommunication.decryptByPrivateKey(a, privatekey);
			deCodeStr = new String(b);
		
			logger.debug("接收到的请求数据：{}", str);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return deCodeStr;
	}
	
	public static String retJson(String code, Object msg, Object data) {

		String filePath = ServiceConfig.com_RecRSAPublicKey;
		String key = RSAForCommunication.readWantedText1(filePath);
		String asd = GsonUtils.retJson(code, msg, data, null);
		logger.info("返回数据："+asd);
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
		logger.info("返回数据："+asd);
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

		logger.info("返回数据："+asd);
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
