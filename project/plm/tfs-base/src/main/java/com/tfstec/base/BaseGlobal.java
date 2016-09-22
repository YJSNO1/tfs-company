/**   
 * @Project: tfscore 
 * @Title: BaseGlobal.java 
 * @Package com.tfscore 
 * @Description: 基础的各种返回编码值及其对应含义 
 * @author lx 
 * @date 2016年6月6日 上午12:08:09 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base;

import java.io.Serializable;

/** 
 * @ClassName BaseGlobal  
 * @Description 基础的各种返回编码值及其对应含义 
 * @author lx 
 * @date 2016年6月6日  
 *   
 */
public class BaseGlobal implements Serializable{
	private static final long serialVersionUID = 7339541141888044936L;
	/**返回代码**/
	public final static String CODE = "code";
	
	/**描述错误返回信息**/
	public final static String MSG = "msg";
	
	/**数据集**/
	public final static String DATA = "data";
	
	/**保留字段*/
	public final static String RETAIN = "retain";
	
	//操作成功
	public static final String RC_SUCCESS = "0000";
		
	//操作失败
	public static final String RC_FAIL = "-3003";
	
	//返回信息
	public static final String RM_SUCCESS = "操作成功。";
	public static final String RM_FAIL = "操作失败。";
	public static final String RM_EXCEPMES = "网络繁忙，请稍后再试。";
	
	/**
	 * 字符串组成类型<br>
	 * number:数字字符串
	 */
	public static final String S_STYLE_N = "number";

	/**
	 * 字符串组成类型<br>
	 * letter:字母字符串
	 */
	public static final String S_STYLE_L = "letter";

	/**
	 * 字符串组成类型<br>
	 * numberletter:数字字母混合字符串
	 */
	public static final String S_STYLE_NL = "numberletter";
}
