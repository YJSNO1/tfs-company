/**   
 * @Project: tfscore 
 * @Title: RedisOperation.java 
 * @Package com.tfscore.redis 
 * @Description: Redis 操作类 
 * @author lx 
 * @date 2016年6月6日 上午9:01:05 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.redis;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yanxintec.nga.redis.core.ExpireTime;
import com.yanxintec.nga.redis.core.ExpireTime.NXXX;
import com.yanxintec.nga.redis.core.RedisParam;
import com.yanxintec.nga.redis.core.support.JedisSentinelOperation;
import com.yanxintec.nga.redis.exception.RedisOperationException;
import com.yanxintec.nga.redis.serialize.support.KryoSerialization;

/** 
 * @ClassName RedisOperation  
 * @Description Redis 操作类 
 * @author lx 
 * @date 2016年6月6日  
 *   
 */
public class RedisOperation {
	private static Logger logger = LoggerFactory.getLogger(RedisOperation.class);
	private static JedisSentinelOperation jedisSentinelOperation;
	private static RedisOperation redisOperation;
	private RedisOperation() {}

	/** 
	 * @Title: init 
	 * @Description: 初始化redis数据库连接
	 * @return void    返回类型
	 */
	private static void init() {
		jedisSentinelOperation = new JedisSentinelOperation();
		try {
			jedisSentinelOperation.setSerialization(new KryoSerialization());
		} catch (Exception e) {
			logger.error("#E 初始化redis发生异常，可能是连接redis失败." + e.getMessage());
		}
	}

	/**
	 * 初始化
	 * @Title: getInstance 
	 * @Description: 初始化
	 * @return 参数说明
	 * @return JedisSentinelOperation    返回类型
	 */
	public static RedisOperation getInstance() {
		if (jedisSentinelOperation == null) {
			redisOperation = new RedisOperation();
			init();
		}
		return redisOperation;
	}

	/**
	 * 新增
	 * @Title: insert 
	 * @param key
	 * @param param
	 * @return
	 * @throws RedisOperationException 参数说明
	 * @return boolean    返回类型
	 */
	public boolean insert(String key, RedisParam param) throws RedisOperationException {
		return jedisSentinelOperation.insert(key, param);
	}
	
	/**
	 * 根据key查找
	 * @Title: find 
	 * @Description: 根据key查找
	 * @param key
	 * @return
	 * @throws RedisOperationException 参数说明
	 * @return Object    返回类型
	 */
	public Object find(String key) throws RedisOperationException{
		return jedisSentinelOperation.find(key);
	}
	
	/**
	 * 删除key
	 * @Title: delete 
	 * @Description: 删除key 
	 * @param key
	 * @return
	 * @throws RedisOperationException 参数说明
	 * @return boolean    返回类型
	 */
	public boolean delete(String key) throws RedisOperationException{
		return jedisSentinelOperation.delete(key);
	}
	
	/**
	 * 修改
	 * @Title: insert 
	 * @param key
	 * @param param
	 * @return
	 * @throws RedisOperationException 参数说明
	 * @return boolean    返回类型
	 */
	public boolean update(String key, RedisParam param) throws RedisOperationException {
		return jedisSentinelOperation.update(key, param);
	}
	

	/**
	 * 不存在新增，存在修改
	 * @Title: insertOrUpdate 
	 * @param key
	 * @param param
	 * @return
	 * @throws RedisOperationException 参数说明
	 * @return boolean    返回类型
	 */
	public boolean insertOrUpdate(String key, RedisParam param) throws RedisOperationException {
		Object obj = jedisSentinelOperation.find(key);
		if(obj == null){
			return jedisSentinelOperation.insert(key, param);
		}else{
			return jedisSentinelOperation.update(key, param);
		}
	}
	/** 
	 * @Title: exists 
	 * @Description: 判断一个key是否存在 
	 * @param key
	 * @throws RedisOperationException 参数说明
	 * @return boolean    返回类型
	 */
	public boolean exists(String key){
		return jedisSentinelOperation.getResource().exists(key);
	}
	/** 
	 * @Title: set 
	 * @Description: 设置一个key的内容
	 * @param key
	 * @param value
	 * @return String    返回类型
	 */
	public String set(String key,String value){
		return jedisSentinelOperation.getResource().set(key, value);
	}
	/** 
	 * @Title: get 
	 * @Description: 获取一个key的内容 
	 * @param key
	 * @return String    返回类型
	 */
	public String get(String key){
		return jedisSentinelOperation.getResource().get(key);
	}
	
	/** 
	 * @Title: del 
	 * @Description: 删除一个key
	 * @param key
	 * @return 参数说明
	 * @return long    返回类型
	 */
	public long del(String key){
		return jedisSentinelOperation.getResource().del(key);
	}
	/**
	 * 
	 * @Title: incr 
	 * @Description: 使key对应的value增加1
	 * @param key
	 * @return 参数说明
	 * @return long    返回类型
	 */
	public long incr(String key){
		return jedisSentinelOperation.getResource().incr(key);
	}

	/**
	 * 
	 * @Title: expire 
	 * @Description: 设置Key的存活时间 
	 * @param key
	 * @param expireTime
	 * @return 参数说明
	 * @return long    返回类型
	 */
	
	public long expire(String key,int expireTime){
		return jedisSentinelOperation.getResource().expire(key, expireTime);
		
	}

	public static void main(String[] args) throws RedisOperationException, Exception {
		RedisOperation redis = RedisOperation.getInstance();
		redis.insert("aaaa", new RedisParam("111111111", new ExpireTime(NXXX.NX, 15)));
		//这个用于测试，如果是三秒，那么就还能看到信息，如果是设置为6秒，那么下面find就是null
		Thread.sleep(3000);
		System.out.println( redis.find("aaaa"));
		redis.update("aaaa", new RedisParam("1111111131"));
		Thread.sleep(3000);
		System.out.println( redis.find("aaaa"));
		
		//---------------------------Map 测试
		/*Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("bb", "bb");
		paramMap.put("cc", "ccc");
		paramMap.put("dd", "ddd");
		redis.insert("1111", new RedisParam(paramMap, new ExpireTime(NXXX.NX, 10)));
		
		System.out.println(redis.find("1111"));
		System.out.println("-------------------------------");
		redis.incr("testincr");
		System.out.println(redis.find("testincr"));*/
		
	}
}
