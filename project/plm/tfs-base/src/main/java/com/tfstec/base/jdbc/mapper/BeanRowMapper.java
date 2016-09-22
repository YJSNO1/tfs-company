/**   
 * @Project: tfs-base 
 * @Title: BeanRowMapper.java 
 * @Package com.tfstec.base.jdbc.mapper 
 * @Description: Jdbc的bean的 映射的基础类 
 * @author lx 
 * @date 2016年6月28日 下午2:16:02 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.jdbc.mapper;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.tfstec.base.date.TimeUtil;

/** 
 * @ClassName BeanRowMapper  
 * @Description Jdbc的bean的 映射的基础类 
 * @author lx 
 * @date 2016年6月28日  
 *   
 */
public class BeanRowMapper<T> implements RowMapper<T> {
	// 对象的类型
	private Class<T> cls;

	// 设置需要返回的对象的类型
	public BeanRowMapper(Class<T> cls) {
		this.cls = cls;
	}

	/** 
	 * @Title: mapRow 
	 * @Description: 映射指定数据集到bean对象 
	 * @param rset  查询sql结果集
	 * @param paramInt 当前行索引
	 * @return 参数说明
	 * @return T   返回类型
	 */
	public T mapRow(ResultSet rset, int paramInt) throws SQLException {
		try {
			ResultSetMetaData rsmd = rset.getMetaData();
			int columnCount = rsmd.getColumnCount();
			Object mapobj = cls.newInstance();

			for (int i = 0; i < columnCount; i++) {
				String cn = rsmd.getColumnName(i + 1);
				Object object = rset.getObject(i + 1);
				Field fd = null;
				try {
					fd = mapobj.getClass().getDeclaredField(cn);
					fd.setAccessible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (fd == null) {
					continue;
				}
				// clob转换string
				if (object instanceof java.sql.Clob) {
					java.sql.Clob clobObject = (java.sql.Clob) object;
					fd.set(mapobj, SqlSupport.ClobToString(clobObject));
				} else if (object instanceof java.sql.Blob) {
					java.sql.Blob clobObject = (java.sql.Blob) object;
					fd.set(mapobj, SqlSupport.BlobToString(clobObject));
				} else if (object instanceof BigDecimal) {
					// BigDecimal转换基本数据类型
					if (fd.getType().getName().equals("int")
							|| fd.getType().getName().equals("java.lang.Integer"))
						fd.set(mapobj, ((BigDecimal) object).intValue());
					else if (fd.getType().getName().equals("short")
							|| fd.getType().getName().equals("java.lang.Short"))
						fd.set(mapobj, ((BigDecimal) object).shortValue());
					else if (fd.getType().getName().equals("long")
							|| fd.getType().getName().equals("java.lang.Long"))
						fd.set(mapobj, ((BigDecimal) object).longValue());
					else if (fd.getType().getName().equals("float")
							|| fd.getType().getName().equals("java.lang.Float"))
						fd.set(mapobj, ((BigDecimal) object).floatValue());
					else if (fd.getType().getName().equals("double")
							|| fd.getType().getName().equals("java.lang.Double"))
						fd.set(mapobj, ((BigDecimal) object).doubleValue());
					else if (fd.getType().getName().equals("java.lang.String"))
						fd.set(mapobj, ((BigDecimal) object).toString());
					else {
						fd.set(mapobj, object);
					}
				} else if (object instanceof java.sql.Date) {
					Timestamp tsta = rset.getTimestamp(i + 1);
					Date dte = new Date(tsta.getTime());
					// java.sql.Date转换基本日期格式和字符串
					if (fd.getType().getName().equals("java.util.Date")) {
						fd.set(mapobj, dte);
					}
					if (fd.getType().getName().equals("java.lang.String")) {
						fd.set(mapobj, TimeUtil.getDate(dte, "yyyy-MM-dd HH:mm:ss"));
					}
				} else {
					// 字符串格式自动处理
					if (object != null) {
						fd.set(mapobj, object);
					}
				}
			}
			return (T) mapobj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
