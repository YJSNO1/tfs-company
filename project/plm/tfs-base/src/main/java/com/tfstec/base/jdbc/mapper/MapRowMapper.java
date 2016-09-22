/**   
 * @Project: tfs-base 
 * @Title: MapRowMapper.java 
 * @Package com.tfstec.base.jdbc.mapper 
 * @Description: Jdbc的bean的 映射的基础类 
 * @author lx 
 * @date 2016年6月28日 下午2:21:49 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.jdbc.mapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

/** 
 * @ClassName MapRowMapper  
 * @Description Jdbc的bean的 映射的基础类 
 * @author lx 
 * @date 2016年6月28日  
 *   
 */
public class MapRowMapper implements RowMapper {
	/** 
	 * @Title: mapRow 
	 * @Description: 映射指定数据集到bean对象
	 * @param rset  查询sql结果集
	 * @param rowIndex  当前行索引
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public Object mapRow(ResultSet rset, int rowIndex) throws SQLException {
		ResultSetMetaData rsmd = rset.getMetaData();
		int columnCount = rsmd.getColumnCount();
		Map mapc = new HashMap();
		for (int i = 0; i < columnCount; i++) {
			String cn = rsmd.getColumnName(i + 1);
			Object object = rset.getObject(i + 1);
			if (object instanceof java.sql.Clob) {
				java.sql.Clob clobObject = (java.sql.Clob) object;
				mapc.put(cn, SqlSupport.ClobToString(clobObject));
			} else if (object instanceof java.sql.Blob) {
				java.sql.Blob clobObject = (java.sql.Blob) object;
				mapc.put(cn, SqlSupport.BlobToString(clobObject));
			} else {
				if (object instanceof java.sql.Date) {
					Timestamp tsta = rset.getTimestamp(i + 1);
					Date dte = new Date(tsta.getTime());
					// Date d=new Date(((java.sql.Date)object).getTime());
					mapc.put(cn, dte);
				} else {
					mapc.put(cn, object);
				}
			}
		}
		return mapc;
	};
}
