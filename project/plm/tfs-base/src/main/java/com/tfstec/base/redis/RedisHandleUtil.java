/**   
 * @Project: tfscore 
 * @Title: RedisHandleUtil.java 
 * @Package com.tfscore.redis 
 * @Description: 分布式变量存储操作类 
 * @author lx 
 * @date 2016年6月6日 上午9:02:27 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.redis;

import java.util.HashMap;
import java.util.Map;

import com.yanxintec.nga.redis.core.ExpireTime;
import com.yanxintec.nga.redis.core.ExpireTime.NXXX;
import com.yanxintec.nga.redis.core.RedisParam;
import com.yanxintec.nga.redis.exception.RedisOperationException;

/** 
 * @ClassName RedisHandleUtil  
 * @Description 分布式变量存储操作类 
 * @author lx 
 * @date 2016年6月6日  
 *   
 */
public class RedisHandleUtil {
	private static RedisOperation redis = RedisOperation.getInstance();

	/**
	 * @Title: find 
	 * @param key 外部键
	 * @throws RedisOperationException 
	 */
	@SuppressWarnings("unchecked")
	public static Object find(String key) throws RedisOperationException {
		Map<String, Object> paramMap = (Map<String, Object>) redis.find(key);
		if (paramMap == null) {
			paramMap = new HashMap<String, Object>();
		}
		return paramMap;
	}

	/**
	 * @Title: find 
	 * @Description: 得到内部变量值
	 * @param key 外部键
	 * @param innerkey 内部键
	 * @throws RedisOperationException 
	 */
	@SuppressWarnings("unchecked")
	public static Object find(String key, String innerkey) throws RedisOperationException {
		Map<String, Object> paramMap = (Map<String, Object>) find(key);
		if (paramMap != null) {
			return paramMap.get(innerkey);
		}
		return "";
	}

	/**
	 * @Title: insertOrUpdate 
	 * @Description:  添加修改变量
	 * @param key 外部键
	 * @param paramMap 内部变量
	 * 6*60*60*24*30= 15552000(保存半年)
	 * @throws RedisOperationException 
	 */
	public static void insertOrUpdate(String key, Map<String, Object> paramMap)
			throws RedisOperationException {
		redis.insertOrUpdate(key, new RedisParam(paramMap, new ExpireTime(NXXX.NX, 15552000)));
	}

	/**
	 * 
	 * @Title: update 
	 * @Description: 避免调上文中的修改函数 更新key的存活时间 
	 * @param key 外部键
	 * @param paramMap 内部变量
	 * @throws RedisOperationException 参数说明
	 * @return void    返回类型
	 */
	public static void update(String key, Map<String, Object> paramMap,long expireTime)
			throws RedisOperationException {
		redis.insertOrUpdate(key,new RedisParam(paramMap, new ExpireTime(NXXX.NX, expireTime)));
	}
	
	/**
	 * 
	 * @Title: insert 
	 * @Description: 添加修改变量 
	 * @param key
	 * @param paramMap
	 * @param expireTime  过期时间
	 * @throws RedisOperationException 参数说明
	 * @return void    返回类型
	 */
	public static void insert(String key, Map<String, Object> paramMap,long expireTime)
			throws RedisOperationException {
		redis.insert(key, new RedisParam(paramMap, new ExpireTime(NXXX.NX, expireTime)));
	}
	
	
	public static void insert(String key, Object paramMap,long expireTime)
			throws RedisOperationException {
		redis.insert(key, new RedisParam(paramMap, new ExpireTime(NXXX.NX, expireTime)));
	}
	/**
	 * @Title: delete 
	 * @Description: 删除变量 
	 * @param key 外部键
	 * @throws RedisOperationException 
	 */
	public static void delete(String key) throws RedisOperationException {
		redis.delete(key);
	}

	/** 
	 * @Title: exists 
	 * @Description: 判断一个key是否存在 
	 * @param key
	 * @throws RedisOperationException 参数说明
	 * @return boolean    返回类型
	 */
	public static boolean exists(String key) {
		return redis.exists(key);
	}

	/** 
	 * @Title: set 
	 * @Description: 设置一个key的内容
	 * @param key
	 * @param value
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String set(String key, String value) {
		return redis.set(key, value);
	}
	
	/**
	 * 
	 * @Title: set 
	 * @Description: 创建一个key 值为value 存活时间为 expireTime
	 * @param key
	 * @param expireTime
	 * @param value
	 * @return 参数说明
	 * @return long    返回类型
	 */
	public static long set(String key ,int expireTime,String value){
		
		redis.set(key, value);
		return redis.expire(key, expireTime);
	}
	
	/**
	 * 
	 * @Title: expire 
	 * @Description: 将key的存活时间设为 expireTime
	 * @param key
	 * @param expireTime
	 * @return 参数说明
	 * @return long    返回类型
	 */
	public static long expire(String key ,int expireTime){
		return redis.expire(key, expireTime);
	}

	/** 
	 * @Title: get 
	 * @Description: 获取一个key的内容 
	 * @param key
	 * @return String    返回类型
	 */
	public static String get(String key) {
		return redis.get(key);
	}

	/** 
	 * @Title: del 
	 * @Description: 删除一个key
	 * @param key
	 * @return 参数说明
	 * @return long    返回类型
	 */
	public static long del(String key) {
		return redis.del(key);
	}

	/**
	 * @Title: delete 
	 * @Description: 删除变量 
	 * @param key 外部键
	 * @throws RedisOperationException 
	 */
	@SuppressWarnings("unchecked")
	public static void delete(String key, String innerkey) throws RedisOperationException {
		Object obj = find(key);
		if (obj != null) {
			Map<String, Object> paramMap = (Map<String, Object>) obj;
			if (paramMap != null) {
				paramMap.remove(innerkey);
				insertOrUpdate(key, paramMap);
			}
		}
	}
	/**
	 * @Title: incr 
	 * @Description: 给指定的 key 的值加一 并返回
	 * @param key
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String incr(String key){
		return String.valueOf(redis.incr(key));
	}
}
