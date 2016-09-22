/**   
 * @Project: tfs-base 
 * @Title: IBaseDto.java 
 * @Package com.tfstec.base.jdbc.bean 
 * @Description: 数据传输对象接口 
 * @author lx 
 * @date 2016年6月28日 上午9:58:57 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.jdbc.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/** 
 * @ClassName IBaseDto  
 * @Description 数据传输对象接口 
 * @author lx 
 * @date 2016年6月28日  
 *   
 */
public interface IBaseDto extends Map, Serializable {
	/** 
	 * @Title: getAsInteger 
	 * @Description: 以Integer类型返回键值 
	 * @param pStr 键名
	 * @return 键值value
	 * @return Integer    返回类型
	 */
	public Integer getAsInteger(String pStr);

	/** 
	 * @Title: getAsLong 
	 * @Description: 以Long类型返回键值
	 * @param pStr 键名
	 * @return 键值value
	 * @return Long    返回类型
	 */
	public Long getAsLong(String pStr);

	/** 
	 * @Title: getAsString 
	 * @Description: 以String类型返回键值 
	 * @param pStr 键名
	 * @return 键值value
	 * @return String    返回类型
	 */
	public String getAsString(String pStr);

	/** 
	 * @Title: getAsString 
	 * @Description: 以String类型返回键值
	 * @param pStr 键名
	 * @param defaultValue 当取值为空时，默认值
	 * @return 键值value
	 * @return String    返回类型
	 */
	public String getAsString(String pStr, String defaultValue);

	/** 
	 * @Title: getAsBigDecimal 
	 * @Description: 以BigDecimal类型返回键值 
	 * @param pStr 属性Key
	 * @return 键值value
	 * @return BigDecimal    返回类型
	 */
	public BigDecimal getAsBigDecimal(String pStr);

	/** 
	 * @Title: getAsDate 
	 * @Description: 以Date类型返回键值 
	 * @param pStr 属性Key
	 * @return 键值value
	 * @return Date    返回类型
	 */
	public Date getAsDate(String pStr);

	/** 
	 * @Title: getAsList 
	 * @Description: 以List类型返回键值 
	 * @param key 属性Key
	 * @return 键值value
	 * @return List    返回类型
	 */
	public List getAsList(String key);

	/** 
	 * @Title: getAsTimestamp 
	 * @Description: 以Timestamp类型返回键值 
	 * @param pStr 属性Key
	 * @return 键值value
	 * @return Timestamp    返回类型
	 */
	public Timestamp getAsTimestamp(String pStr);

	/** 
	 * @Title: getAsBoolean 
	 * @Description: 以Boolean类型返回键值 
	 * @param key 属性Key
	 * @return 键值value
	 * @return Boolean    返回类型
	 */
	public Boolean getAsBoolean(String key);

	/** 
	 * @Title: setDefaultAList 
	 * @Description: 给Dto压入第一个默认List对象， 为了方便存取(省去根据Key来存取和类型转换的过程)
	 * @param pList 压入Dto的List对象
	 * @return void    返回类型
	 */
	public void setDefaultAList(List pList);

	/** 
	 * @Title: setDefaultBList 
	 * @Description: 给Dto压入第二个默认List对象，为了方便存取(省去根据Key来存取和类型转换的过程)
	 * @param pList 压入Dto的List对象
	 * @return void    返回类型
	 */
	public void setDefaultBList(List pList);

	/** 
	 * @Title: getDefaultAList 
	 * @Description: 获取第一个默认List对象， 为了方便存取(省去根据Key来存取和类型转换的过程)
	 * @return 参数说明
	 * @return List    返回类型
	 */
	public List getDefaultAList();

	/** 
	 * @Title: getDefaultBList 
	 * @Description: 获取第二个默认List对象， 为了方便存取(省去根据Key来存取和类型转换的过程)
	 * @return 参数说明
	 * @return List    返回类型
	 */
	public List getDefaultBList();

	/** 
	 * @Title: setDefaultJson 
	 * @Description: 给Dto压入一个默认的Json格式字符串 
	 * @param jsonString JSON格式的字符串
	 * @return void    返回类型
	 */
	public void setDefaultJson(String jsonString);

	/** 
	 * @Title: getDefaultJson 
	 * @Description: 获取默认的Json格式字符串 
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public String getDefaultJson();

	/** 
	 * @Title: toJson 
	 * @Description: 将此Dto对象转换为Json格式字符串 
	 * @return 返回Json格式字符串
	 * @return String    返回类型
	 */
	public String toJson();

	/** 
	 * @Title: toJson 
	 * @Description: 将此Dto对象转换为Json格式字符串(带日期时间型) 
	 * @param pFormat 日期时间格式类型
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public String toJson(String pFormat);

	/** 
	 * @Title: setSuccess 
	 * @Description: 设置交易状态 
	 * @param pSuccess 交易状态
	 * @return void    返回类型
	 */
	public void setSuccess(Boolean pSuccess);

	/** 
	 * @Title: getSuccess 
	 * @Description: 获取交易状态 
	 * @return 参数说明
	 * @return Boolean    返回类型
	 */
	public Boolean getSuccess();

	/** 
	 * @Title: setMsg 
	 * @Description: 设置交易提示信息 
	 * @param pMsg 交易提示信息
	 * @return void    返回类型
	 */
	public void setMsg(String pMsg);

	/** 
	 * @Title: getMsg 
	 * @Description: 获取交易提示信息 
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public String getMsg();
}
