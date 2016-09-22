/**   
 * @Project: tfs-base 
 * @Title: BaseDtoImpl.java 
 * @Package com.tfstec.base.jdbc.bean 
 * @Description: 数据传输对象(DateTransferObject)，建议在参数传递过程中尽量使用Dto来传递
 * @author lx 
 * @date 2016年6月28日 上午10:02:28 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.jdbc.bean;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import com.tfstec.base.jdbc.json.JsonHelper;
import com.tfstec.base.jdbc.tools.TypeCaseHelper;

/** 
 * @ClassName BaseDtoImpl  
 * @Description 数据传输对象(DateTransferObject)，建议在参数传递过程中尽量使用Dto来传递 
 * @author lx 
 * @date 2016年6月28日  
 *   
 */
public class BaseDtoImpl<T> extends HashMap implements IBaseDto {
	/**
	 * nouserserialVersionUID
	 */
	private static final long serialVersionUID = 1618383471300369222L;

	public BaseDtoImpl() {
	}

	public BaseDtoImpl(String key, Object value) {
		put(key, value);
	}

	public BaseDtoImpl(Boolean success) {
		setSuccess(success);
	}

	public BaseDtoImpl(Boolean success, String msg) {
		setSuccess(success);
		setMsg(msg);
	}

	/** 
	 * @Title: getAsBigDecimal 
	 * @Description: 以BigDecimal类型返回键值 
	 * @param key 属性Key
	 * @return 键值value
	 * @return BigDecimal    返回类型
	 */
	public BigDecimal getAsBigDecimal(String key) {
		Object obj = TypeCaseHelper.convert(get(key), "BigDecimal", null);
		if (obj != null) {
			return (BigDecimal) obj;
		} else {
			return null;
		}
	}

	/** 
	 * @Title: getAsDate 
	 * @Description: 以Date类型返回键值 
	 * @param key 属性Key
	 * @return 键值value
	 * @return Date    返回类型
	 */
	public Date getAsDate(String key) {
		Object obj = TypeCaseHelper.convert(get(key), "Date", "yyyy-MM-dd");
		if (obj != null) {
			return (Date) obj;
		} else {
			return null;
		}
	}

	/** 
	 * @Title: getAsInteger
	 * @Description: 以Integer类型返回键值 
	 * @param key 属性Key
	 * @return 键值value
	 * @return Integer    返回类型
	 */
	public Integer getAsInteger(String key) {
		Object obj = TypeCaseHelper.convert(get(key), "Integer", null);
		if (obj != null) {
			return (Integer) obj;
		} else {
			return null;
		}
	}

	/** 
	 * @Title: getAsLong
	 * @Description: 以Long类型返回键值 
	 * @param key 属性Key
	 * @return 键值value
	 * @return Long    返回类型
	 */
	public Long getAsLong(String key) {
		Object obj = TypeCaseHelper.convert(get(key), "Long", null);
		if (obj != null) {
			return (Long) obj;
		} else {
			return null;
		}
	}

	/** 
	 * @Title: getAsString
	 * @Description: 以String类型返回键值 
	 * @param key 属性Key
	 * @return 键值value
	 * @return String    返回类型
	 */
	public String getAsString(String key) {
		Object obj = TypeCaseHelper.convert(get(key), "String", null);
		if (obj != null) {
			return (String) obj;
		} else {
			return "";
		}
	}

	/** 
	 * @Title: getAsString
	 * @Description: 以String类型返回键值 
	 * @param key 属性Key
	 * @param defaultValue 默认值
	 * @return 键值value
	 * @return String    返回类型
	 */
	public String getAsString(String key, String defaultValue) {
		Object obj = TypeCaseHelper.convert(get(key), "String", null);
		if (obj != null) {
			return (String) obj;
		} else {
			return defaultValue;
		}
	}

	/** 
	 * @Title: getAsList
	 * @Description: 以List类型返回键值
	 * @param key 属性Key
	 * @return 键值value
	 * @return List   返回类型
	 */
	public List getAsList(String key) {
		return (List) get(key);
	}

	/** 
	 * @Title: getAsTimestamp
	 * @Description: 以Timestamp类型返回键值
	 * @param key 属性Key
	 * @return 键值value
	 * @return Timestamp   返回类型
	 */
	public Timestamp getAsTimestamp(String key) {
		Object obj = TypeCaseHelper.convert(get(key), "Timestamp", "yyyy-MM-dd HH:mm:ss");
		if (obj != null) {
			return (Timestamp) obj;
		} else {
			return null;
		}
	}

	/** 
	 * @Title: getAsBoolean
	 * @Description: 以Boolean类型返回键值
	 * @param key 属性Key
	 * @return 键值value
	 * @return Boolean   返回类型
	 */
	public Boolean getAsBoolean(String key) {
		Object obj = TypeCaseHelper.convert(get(key), "Boolean", null);
		if (obj != null) {
			return (Boolean) obj;
		} else {
			return null;
		}
	}

	/** 
	 * @Title: setDefaultAList
	 * @Description: 给Dto压入第一个默认List对象，为了方便存取(省去根据Key来存取和类型转换的过程)
	 * @param pList 压入Dto的List对象
	 * @return void   返回类型
	 */
	public void setDefaultAList(List pList) {
		put("defaultAList", pList);
	}

	/** 
	 * @Title: setDefaultBList
	 * @Description: 给Dto压入第二个默认List对象，为了方便存取(省去根据Key来存取和类型转换的过程)
	 * @param pList 压入Dto的List对象
	 * @return void   返回类型
	 */
	public void setDefaultBList(List pList) {
		put("defaultBList", pList);
	}

	/** 
	 * @Title: getDefaultAList
	 * @Description: 获取第一个默认List对象，为了方便存取(省去根据Key来存取和类型转换的过程)
	 * @return List   返回类型
	 */
	public List getDefaultAList() {
		return (List) get("defaultAList");
	}

	/** 
	 * @Title: getDefaultBList
	 * @Description: 获取第二个默认List对象，为了方便存取(省去根据Key来存取和类型转换的过程)
	 * @return List   返回类型
	 */
	public List getDefaultBList() {
		return (List) get("defaultBList");
	}

	/** 
	 * @Title: setDefaultJson
	 * @Description: 给Dto压入一个默认的Json格式字符串
	 * @param jsonString 压入对象
	 * @return void   返回类型
	 */
	public void setDefaultJson(String jsonString) {
		put("defaultJsonString", jsonString);
	}

	/** 
	 * @Title: getDefaultJson
	 * @Description: 获取默认的Json格式字符串
	 * @return String   返回类型
	 */
	public String getDefaultJson() {
		return getAsString("defaultJsonString");
	}

	/** 
	 * @Title: toJson
	 * @Description: 将此Dto对象转换为Json格式字符串
	 * @return String  返回类型
	 */
	public String toJson() {
		String strJson = null;
		strJson = JsonHelper.encodeObject2Json(this);
		return strJson;
	}

	/** 
	 * @Title: toBean
	 * @Description: 将此Dto对象转换Bean
	 * @param cls 转换Bean
	 * @return T   返回类型
	 */
	public T toBean(Class cls) {
		Object obj = null;
		try {
			obj = cls.newInstance();
			Field[] f = cls.getDeclaredFields();
			for (Field fd : f) {
				fd.setAccessible(true);
				String name = fd.getName();
				Object val = this.get(name);
				if (val != null) {
					fd.set(obj, val);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (T) obj;
	}

	/** 
	 * @Title: toJson
	 * @Description: 将此Dto对象转换为Json格式字符串(带日期时间型)
	 * @param pFormat 日期时间格式化类型
	 * @return string   返回Json格式字符串
	 */
	public String toJson(String pFormat) {
		String strJson = null;
		strJson = JsonHelper.encodeObject2Json(this, pFormat);
		return strJson;
	}

	/** 
	 * @Title: setSuccess
	 * @Description: 设置交易状态
	 * @param pSuccess 成功失败标识
	 * @return void   返回类型
	 */
	public void setSuccess(Boolean pSuccess) {
		put("success", pSuccess);
		if (pSuccess) {
			put("bflag", "1");
		} else {
			put("bflag", "0");
		}
	}

	/** 
	 * @Title: getSuccess
	 * @Description: 获取交易状态
	 * @return Boolean  返回类型
	 */
	public Boolean getSuccess() {
		return getAsBoolean("success");
	}

	/** 
	 * @Title: setMsg
	 * @Description: 设置交易提示信息
	 * @param pMsg 提示信息
	 * @return void   返回类型
	 */
	public void setMsg(String pMsg) {
		put("msg", pMsg);
	}

	/** 
	 * @Title: getMsg
	 * @Description: 获取交易提示信息
	 * @return String  返回类型
	 */
	public String getMsg() {
		return getAsString("msg");
	}
}
