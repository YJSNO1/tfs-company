/**   
 * @Project: tfs-base 
 * @Title: JdbcAnnotationSupport.java 
 * @Package com.tfstec.base.jdbc.annotation 
 * @Description: 用于缓存所有的配置信息 
 * @author lx 
 * @date 2016年6月28日 上午9:52:44 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.jdbc.annotation;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.tfstec.base.StringUtils;

/** 
 * @ClassName JdbcAnnotationSupport  
 * @Description 用于缓存所有的配置信息 
 * @author lx 
 * @date 2016年6月28日  
 *   
 */
public class JdbcAnnotationSupport {
	// 用于存放Table Name的描述 key为java类的ClassName ,value为 数据库的表名
	public static Map<String, String> tableInfoMap = new HashMap<String, String>();
	// 用于存放Table Name的描述 key为java类的ClassName ,value为 pk 对应的Sequence
	public static Map<String, String> tablePkSeqMap = new HashMap<String, String>();
	// 用于存放Table 字段的描述<key1,<key2:value>> key1为java类的ClassName ，key2为Java属性 Value为DB字段
	public static Map<String, Map<String, String>> tableColumnMap = new HashMap<String, Map<String, String>>();

	/** 
	 * @Title: getTablePkSn 
	 * @Description: 用于提取表名（包含catalog schema） 
	 * @param obj 数据对象
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String getTablePkSn(Object obj) {
		String key = obj.getClass().getCanonicalName();
		String fullName = tablePkSeqMap.get(key);
		if (fullName == null) {
			LocalTable jt = obj.getClass().getAnnotation(LocalTable.class);
			if (jt != null) {
				tablePkSeqMap.put(key, jt.sequence());
				fullName = jt.sequence();
			}
		}
		return fullName;
	}

	/** 
	 * @Title: getFullTableName 
	 * @Description: 用于提取表名（包含catalog schema） 
	 * @param obj 数据对象
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String getFullTableName(Object obj) {
		String key = obj.getClass().getCanonicalName();
		String fullName = tableInfoMap.get(key);
		if (fullName == null) {
			LocalTable jt = obj.getClass().getAnnotation(LocalTable.class);
			if (jt != null) {
				fullName = "";
				if (StringUtils.isNotBlank(jt.schema())) {
					fullName = jt.schema() + ".";
				}
				if (StringUtils.isNotBlank(jt.name())) {
					fullName += jt.name();
				} else {
					fullName += key;
				}
				tableInfoMap.put(key, fullName);
			}
		}
		return fullName;
	}

	/** 
	 * @Title: getColumnName 
	 * @Description: 用于提取字段名 （Table的列名）
	 * @param obj 数据对象 
	 * @param fieldName 属性名称
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String getColumnName(Object obj, String fieldName) {
		Map<String, String> colmsMap = getColumnMap(obj);
		String columnName = colmsMap.get(fieldName);
		if (StringUtils.isBlank(columnName)) {
			columnName = fieldName;
		}
		return columnName;
	}

	/** 
	 * @Title: getAllColumnName 
	 * @Description: 用于提取所有的字段(所有有和数据库映射的DB中表字段的名称) 
	 * @param obj 数据对象，obj<key:value> key为Java属性  Value为DB字段
	 * @return 参数说明
	 * @return String[]    返回类型
	 */
	public static String[] getAllColumnName(Object obj) {
		Map<String, String> colmsMap = getColumnMap(obj);
		String[] columnNames = new String[colmsMap.size()];

		Iterator it = colmsMap.entrySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			columnNames[i] = (String) e.getValue();
			i++;
		}
		return columnNames;
	}

	/** 
	 * @Title: getAllColunmFieldName 
	 * @Description: 用于提取所有的字段(所有有和数据库映射的Java属性名称) 
	 * @param obj 数据对象，obj<key:value> key为Java属性  Value为DB字段
	 * @return 参数说明
	 * @return String[]    返回类型
	 */
	public static String[] getAllColunmFieldName(Object obj) {
		Map<String, String> colmsMap = getColumnMap(obj);
		String[] columnNames = new String[colmsMap.size()];

		Iterator it = colmsMap.entrySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			columnNames[i] = (String) e.getKey();
			i++;
		}
		return columnNames;
	}

	/** 
	 * @Title: getColumnMap 
	 * @Description: 设定所有Key值
	 * @param obj 数据对象，obj<key:value> key为Java属性  Value为DB字段
	 * @return 参数说明
	 * @return Map<String,String>    返回类型
	 */
	public static Map<String, String> getColumnMap(Object obj) {
		String key = obj.getClass().getCanonicalName();
		Field[] fs = obj.getClass().getDeclaredFields();
		Map<String, String> colmsMap = tableColumnMap.get(key);
		if (colmsMap == null) {
			colmsMap = new HashMap();
			try {
				for (Field f : fs) {
					LocalColumn jc = obj.getClass().getDeclaredField(f.getName())
							.getAnnotation(LocalColumn.class);
					if (jc != null) {
						if (StringUtils.isNotBlank(jc.name())) {
							colmsMap.put(f.getName(), jc.name());
						} else {
							colmsMap.put(f.getName(), f.getName());
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			tableColumnMap.put(key, colmsMap);
		}
		Map map = new HashMap();
		map.putAll(colmsMap);
		return map;
	}
}
