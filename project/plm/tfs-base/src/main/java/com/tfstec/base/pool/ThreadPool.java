/**   
 * @Project: tfscore 
 * @Title: ThreadPool.java 
 * @Package com.tfscore.pool 
 * @Description: 线程池 
 * @author lx 
 * @date 2016年6月6日 上午12:23:58 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.tfstec.base.config.ConfigLoader;

/** 
 * @ClassName ThreadPool  
 * @Description 线程池 
 * @author lx 
 * @date 2016年6月6日  
 *   
 */
public class ThreadPool {
	// 创建线程池
	private final static ExecutorService executor = Executors.newFixedThreadPool(ConfigLoader
			.getInstance().getIntValue("log.threadpool.count", 30));

	/**
	 * 执行线程任务
	 * @param task 任务
	 */
	public void processTask(ThreadTask task) {
		executor.execute(task);
	}
}
