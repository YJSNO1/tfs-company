/**   
 * @Project: tfscore 
 * @Title: Result.java 
 * @Package com.tfscore.pojo 
 * @Description: 使用FDFS文件处理返回结果信息 
 * @author lx 
 * @date 2016年6月6日 上午8:54:08 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.pojo;

import java.io.Serializable;

/** 
 * @ClassName Result  
 * @Description 使用FDFS文件处理返回结果信息 
 * @author lx 
 * @date 2016年6月6日  
 *   
 */
public class Result<T> implements Serializable {
	private static final long serialVersionUID = -8727590546167284227L;
	private T Result;

	public T getResult() {
		return Result;
	}

	@Override
	public String toString() {
		return "Result [Result=" + Result + "]";
	}
}
