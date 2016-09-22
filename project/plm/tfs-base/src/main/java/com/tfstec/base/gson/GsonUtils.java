/**   
 * @Project: tfscore 
 * @Title: GsonUtils.java 
 * @Package com.tfscore.gson 
 * @Description: JSON转换器 
 * @author lx 
 * @date 2016年6月6日 上午12:04:39 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.gson;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tfstec.base.BaseGlobal;
import com.tfstec.base.pojo.BaseResult;
import com.tfstec.base.pojo.Param;

/** 
 * @ClassName GsonUtils  
 * @Description JSON转换器 
 * @author lx 
 * @date 2016年6月6日  
 *   
 */
public class GsonUtils {
	private static Logger logger = LoggerFactory.getLogger(GsonUtils.class);
	private static Gson gson = new GsonBuilder().disableHtmlEscaping().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	/**
	 * 
	 * @Title: retJson 
	 * @Description: 将对象转换为JSON字符串 
	 * @param code 返回代码
	 * @param msg  描述返回信息
	 * @return 参数说明
	 * @return String    返回类型
	 * @throws
	 */
	public static String retJson(String code, String msg) {
		return GsonUtils.retJson(code,msg,null);
	}

	/**
	 * 
	 * @Title: retJson 
	 * @Description: 将对象转换为JSON字符串
	 * @param code 返回代码
	 * @param msg 描述返回信息
	 * @param data 数据集
	 * @return 参数说明
	 * @return String    返回类型
	 * @throws
	 */
	public static String retJson(String code,Object msg,Object data) {
		return GsonUtils.retJson(code,msg,data,null);
	}

	/**
	 * 
	 * @Title: retJson 
	 * @Description: 将对象转换为JSON字符串
	 * @param code 返回代码
	 * @param desc 描述返回信息
	 * @param data 数据集
	 * @param retain 保留字段
	 * @return 参数说明
	 * @return String    返回类型
	 * @throws
	 */
	public static String retJson(String code,Object desc, Object data, 
			Object retain) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(BaseGlobal.CODE, code);
		map.put(BaseGlobal.MSG, desc);
		map.put(BaseGlobal.DATA, data);
		map.put(BaseGlobal.RETAIN, retain);
		logger.info(GsonUtils.toJson(map));
		return GsonUtils.toJson(map);

	}
	
	/**
	 * 
	 * @Title: toJson 
	 * @Description: 将对象转换为JSON字符串
	 * @param obj
	 * @return 参数说明
	 * @return String    返回类型
	 * @throws
	 */
	public static String toJson(Object obj) {
		return gson.toJson(obj);
	}
	
	/**
	 * @Title: getJson 
	 * @Description: 将JSON字符串转换为对象 
	 * @param json JSON字符串
	 * @param classOfT 转换对象
	 * @return 参数说明
	 * @return T    返回类型
	 * @throws
	 */
	public static <T> T getJson(String json, Class<T> classOfT) {
		return gson.fromJson(json, classOfT);
	}
	
	/** 
	 * @Title: getJson 
	 * @Description: 将JSON字符串转换为集合对象 
	 * @param json JSON字符串
	 * @param type 集合类别
	 * @return 参数说明
	 * @return List<T>    返回类型
	 */
	public static <T> List<T> getJson(String json, Type type) {
		// 用法
//		 List<Bean> beans = GsonUtils.getJson(json,new TypeToken<List<Bean>>() {}.getType());
		return gson.fromJson(json, type);
	}

	/** 
	 * @Title: retJson 
	 * @Description: 将基本结果信息数据转换为json串 
	 * @param baseResult 基本结果信息数据
	 * @return 参数说明
	 * @return String    返回类型
	 * @throws 
	 */
	public static String retJson(BaseResult baseResult) {
		return retJson(baseResult.getCode(),baseResult.getMsg(),baseResult.getData(),baseResult.getRetain());
	}
	
	/**
	 * @Title: getJson 
	 * @Description: 将JSON字符串转换为集合
	 * @param json JSON字符串
	 */
	public static <T> List<Param> getJsonToParam(String json) {
		// 用法
		// List<Param> beans = GsonUtils.getJson(json,new TypeToken<List<Param>>() {}.getType());
		return GsonUtils.getJson(json, new TypeToken<List<Param>>() {
		}.getType());
	}
}
