/**   
 * @Project: tfs-base 
 * @Title: BaseTable.java 
 * @Package com.tfstec.base.jdbc.bean 
 * @Description: 所有基于JDBC的操作数据库的Table对象基类 
 * @author lx 
 * @date 2016年6月28日 上午10:01:06 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.jdbc.bean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.tfstec.base.jdbc.annotation.JdbcAnnotationSupport;
import com.tfstec.base.jdbc.tools.BeanUtils;

/** 
 * @ClassName BaseTable  
 * @Description 所有基于JDBC的操作数据库的Table对象基类 
 * @author lx 
 * @date 2016年6月28日  
 *   
 */
public abstract class BaseTable {
	/** 
	 * @Title: loadTableName 
	 * @Description: 提取表名 
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public String loadTableName() {
		return JdbcAnnotationSupport.getFullTableName(this);
	}

	/** 
	 * @Title: loadTablePk 
	 * @Description: 提取表的PK 
	 * @return 参数说明
	 * @return String[]    返回类型
	 */
	public abstract String[] loadTablePk();

	/** 
	 * @Title: loadTablePkMap 
	 * @Description: T提取PK，包含java字段对应的DB字段 
	 * @return <key:value> key为Java属性  Value为DB字段
	 * @return Map   返回类型
	 */
	public Map loadTablePkMap() {
		String[] pks = this.loadTablePk();
		Map<String, String> map = JdbcAnnotationSupport.getColumnMap(this);
		Map pkm = new HashMap();
		if (pks != null) {
			for (String pk : pks) {
				pkm.put(pk, map.get(pk));
			}
		}
		return pkm;
	}

	/** 
	 * @Title: toMap 
	 * @Description: 转为Map 
	 * @return <key:value>
	 * @return Map    返回类型
	 */
	public Map toMap() {
		String[] pks = this.loadTablePk();
		Map<String, String> map = JdbcAnnotationSupport.getColumnMap(this);
		Map values = new HashMap();
		Iterator it = map.entrySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			String filed = (String) e.getKey();
			values.put(filed, BeanUtils.getFieldValueByName(filed, this));
			i++;
		}
		return values;
	}

	/** 
	 * @Title: loadTableColumn 
	 * @Description: 提取表的所有字段,数据库对应的字段 
	 * @return 参数说明
	 * @return String[]    返回类型
	 */
	public String[] loadTableColumn() {
		return JdbcAnnotationSupport.getAllColumnName(this);
	}

	/** 
	 * @Title: loadJavaField 
	 * @Description: 提取表的所有Java属性,有和DB映射的；
	 * @return 参数说明
	 * @return String[]    返回类型
	 */
	public String[] loadJavaField() {
		return JdbcAnnotationSupport.getAllColunmFieldName(this);
	}

	/** 
	 * @Title: loadFieldAndColumn 
	 * @Description: 提取表的所有Java属性,有和DB映射的； 
	 * @return Java属性
	 * @return Map    返回类型
	 */
	public Map loadFieldAndColumn() {
		return JdbcAnnotationSupport.getColumnMap(this);
	}

	/** 
	 * @Title: loadTableColumn 
	 * @Description: 提取表的所有Java属性,有和DB映射的； 
	 * @param fields  参数集合
	 * @return DB表字段
	 * @return String[]    返回类型
	 */
	public String[] loadTableColumn(String[] fields) {
		Map<String, String> all = loadFieldAndColumn();
		String[] columns = new String[fields.length];

		for (int i = 0; i < fields.length; i++) {
			String s = fields[i];
			columns[i] = all.get(s);
		}
		return columns;
	}
}
