/**   
 * @Project: tfscore 
 * @Title: Getpool.java 
 * @Package com.tfscore.pool 
 * @Description: 创建单例拿到线程池
 * @author lx 
 * @date 2016年6月6日 上午12:25:10 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.pool;


/** 
 * @ClassName Getpool  
 * @Description 创建单例拿到线程池 
 * @author lx 
 * @date 2016年6月6日  
 *   
 */
public class Getpool {
	// 初始化线程池
	private static ThreadPool pool = null;

	/**
	 * 创建单例线程
	 * @return
	 */
	public static synchronized ThreadPool getInstance() {
		if (pool == null) {
			pool = new ThreadPool();
		}
		return pool;
	}
}
