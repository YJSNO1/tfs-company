/**   
 * @Project: tfs-base 
 * @Title: SqlSupport.java 
 * @Package com.tfstec.base.jdbc.mapper 
 * @Description: SQL的工具类，主要用于自动生成一些供JDBC使用的SQL或SQL参数 
 * @author lx 
 * @date 2016年6月28日 下午2:17:47 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.jdbc.mapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.tfstec.base.StringUtils;
import com.tfstec.base.jdbc.annotation.JdbcAnnotationSupport;
import com.tfstec.base.jdbc.bean.BaseTable;

/** 
 * @ClassName SqlSupport  
 * @Description SQL的工具类，主要用于自动生成一些供JDBC使用的SQL或SQL参数 
 * @author lx 
 * @date 2016年6月28日  
 *   
 */
public class SqlSupport {
	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * sql解析存储sql键
	 */
	public static final String DAO_PARAM_SQL = "sql";
	/**
	 * sql解析存储属性键
	 */
	public static final String DAO_PARAM_PROERTY = "propertys";

	/**
	 * 单引号
	 */
	public static final String SINGLE_QUOTE = "'";

	/**
	 * 左括号
	 */
	public static final String BLACKET_LEFT = "(";

	/**
	 * 右括号
	 */
	public static final String BLACKET_RIGHT = ")";

	/**
	 * 点
	 */
	public static final String BLACKET_POINT = ".";

	/***************************************************************************
	 * Create an array of MapSqlParameterSource objects populated with data from
	 * the values passed in. This will define what is included in a batch
	 * operation.
	 * @param valueMaps  array of Maps containing the values to be used
	 * @return an array of SqlParameterSource
	 */
	public static SqlParameterSource[] createBatchMap(List<Map> valueMaps) {
		MapSqlParameterSource[] batch = new MapSqlParameterSource[valueMaps.size()];
		for (int i = 0; i < valueMaps.size(); i++) {
			Map valueMap = valueMaps.get(i);
			batch[i] = new MapSqlParameterSource(valueMap);
		}
		return batch;
	}

	/***************************************************************************
	 * Create an array of BeanPropertySqlParameterSource objects populated with
	 * data from the values passed in. This will define what is included in a
	 * batch operation.
	 * @param beans object array of beans containing the values to be used
	 * @return an array of SqlParameterSource
	 */
	public static SqlParameterSource[] createBatchBean(List beans) {
		BeanPropertySqlParameterSource[] batch = new BeanPropertySqlParameterSource[beans.size()];
		for (int i = 0; i < beans.size(); i++) {
			Object bean = beans.get(i);
			batch[i] = new BeanPropertySqlParameterSource(bean);
		}
		return batch;
	}

	/***************************************************************************
	 * Create a wrapped value if parameter has type information, plain object if
	 * not.
	 * @param source  the source of paramer values and type information
	 * @param parameterName  the name of the parameter
	 * @return the value object
	 */
	public static Object getTypedValue(SqlParameterSource source, String parameterName) {
		int sqlType = source.getSqlType(parameterName);
		if (sqlType != SqlParameterSource.TYPE_UNKNOWN) {
			if (source.getTypeName(parameterName) != null) {
				return new SqlParameterValue(sqlType, source.getTypeName(parameterName),
						source.getValue(parameterName));
			} else {
				return new SqlParameterValue(sqlType, source.getValue(parameterName));
			}
		} else {
			return source.getValue(parameterName);
		}
	}

	/***************************************************************************
	 * Create a Map of case insensitive parameter names together with the
	 * original name.
	 * @param parameterSource  the source of paramer names
	 * @return the Map that can be used for case insensitive matching of
	 *         parameter names
	 */
	public static Map extractCaseInsensitiveParameterNames(SqlParameterSource parameterSource) {
		Map caseInsensitiveParameterNames = new HashMap();
		if (parameterSource instanceof BeanPropertySqlParameterSource) {
			String[] propertyNames = ((BeanPropertySqlParameterSource) parameterSource)
					.getReadablePropertyNames();
			for (int i = 0; i < propertyNames.length; i++) {
				String name = propertyNames[i];
				caseInsensitiveParameterNames.put(name.toLowerCase(), name);
			}
		} else if (parameterSource instanceof MapSqlParameterSource) {
			for (String name : ((MapSqlParameterSource) parameterSource).getValues().keySet()) {
				caseInsensitiveParameterNames.put(name.toLowerCase(), name);
			}
		}
		return caseInsensitiveParameterNames;
	}

	/** 
	 * @Title: mapToQueryString 
	 * @Description: 把所有的字段转为可以直接用于SQL Query的字符串 
	 * @param colmsMap  colmsMap : <"USER_NO","userNo"> to "USER_NO as userNo , ..."
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String mapToQueryString(Map colmsMap) {
		StringBuffer sb = new StringBuffer();
		Iterator it = colmsMap.entrySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			if (i > 0) {
				sb.append(",");
			}
			sb.append(e.getValue());
			sb.append(" as \"");
			sb.append(e.getKey());
			sb.append("\"");
			i++;
		}
		return sb.toString();
	}

	/** 
	 * @Title: mapToUpdateString 
	 * @Description: 把所有的字段转为可以直接用于SQL Update的字符串 
	 * @param colmsMap colmsMap : <"userNo","USER_NO"> to " set USER_NO = :userNo ,  ..."
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String mapToUpdateString(Map colmsMap) {
		StringBuffer sb = new StringBuffer();
		Iterator it = colmsMap.entrySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			if (i > 0) {
				sb.append(",");
			}
			sb.append(e.getValue());
			sb.append(" = :");
			sb.append(e.getKey());
			i++;
		}
		return sb.toString();
	}

	/** 
	 * @Title: fieldsToInsertColumnString 
	 * @Description: 把所有的字段转为可以直接用于SQL insert的字符串 
	 * @param fs 需要的字段 "(USER_NO,USER_NAME)"
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String fieldsToInsertColumnString(String[] fs) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < fs.length; i++) {
			if (i > 0) {
				sb.append(",");
			}
			sb.append(fs[i]);
		}
		return sb.toString();
	}

	/** 
	 * @Title: fieldsToInsertValueString 
	 * @Description: 把所有的字段转为可以直接用于SQL insert的字符串 
	 * @param fs 需要的字段 "(:userNo,:userName)"
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String fieldsToInsertValueString(String[] fs) {
		StringBuffer b = new StringBuffer();
		for (int i = 0; i < fs.length; i++) {
			if (i > 0) {
				b.append(",");
			}
			b.append(":");
			b.append(fs[i]);
		}
		return b.toString();
	}

	/** 
	 * @Title: mapToWhereString 
	 * @Description: 把所有的字段转为可以直接用于SQL 查询、更新语句的where字符串 user_no = :userNo and user_name = :userName
	 * @param colmsMap  colmsMap : <"userNo","USER_NO"> to " where USER_NO = :userNo and ... "
	 * @param values  用于判断值是否为null，如果是，这需要把语句转为  USER_NO is null
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String mapToWhereString(Map colmsMap, Map values) {
		StringBuffer sb = new StringBuffer();
		Iterator it = colmsMap.entrySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			if (i > 0) {
				sb.append(" and ");
			}
			if (values != null
					&& (values.get(e.getKey()) == null || values.get(e.getKey()).equals(""))) {
				sb.append(e.getValue());
				sb.append(" is null ");
			} else {
				sb.append(e.getValue());
				sb.append(" = :");
				sb.append(e.getKey());
			}
			i++;
		}
		return sb.toString();
	}

	/** 
	 * @Title: ClobToString 
	 * @Description: 把Clob转化为String 
	 * @param clob 数据类型
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String ClobToString(java.sql.Clob clob) {
		String reString = "";
		Reader is;
		StringBuffer sb = new StringBuffer();
		try {
			is = clob.getCharacterStream();
			BufferedReader br = new BufferedReader(is);
			String s = br.readLine();
			while (s != null) {
				sb.append(s);
				s = br.readLine();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		reString = sb.toString();
		return reString;
	}

	/** 
	 * @Title: BlobToString 
	 * @Description: 把Blob转化为String
	 * @param blob  数据类型
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String BlobToString(java.sql.Blob blob) {
		String reString = "";
		InputStreamReader is;
		StringBuffer sb = new StringBuffer();
		try {
			is = new InputStreamReader(blob.getBinaryStream());
			BufferedReader br = new BufferedReader(is);
			String s = br.readLine();
			while (s != null) {
				sb.append(s);
				s = br.readLine();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		reString = sb.toString();
		return reString;
	}

	/** 
	 * @Title: fieldsToQueryString 
	 * @Description: 把所有的字段转为可以直接用于SQL Query的字符串 
	 * @param tbObj 表对象
	 * @param effectiveFields 是否只需要部分字段(对象的属性)，NULL则表示提取所有字段
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String fieldsToQueryString(BaseTable tbObj, String[] effectiveFields) {
		Map rtnMap = getFieldAndColumn(tbObj, effectiveFields);
		return SqlSupport.mapToQueryString(rtnMap);
	}

	/** 
	 * @Title: fieldsToUpdateString 
	 * @Description: 把所有的字段转为可以直接用于SQL Update的字符串 
	 * @param tbObj 表对象
	 * @param effectiveFields 是否只需要部分字段，NULL则表示提取所有字段
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String fieldsToUpdateString(BaseTable tbObj, String[] effectiveFields) {
		Map rtnMap = getFieldAndColumn(tbObj, effectiveFields);
		return SqlSupport.mapToUpdateString(rtnMap);
	}

	/** 
	 * @Title: fieldsToInsertColumnString 
	 * @Description: 把所有的字段转为可以直接用于SQL insert的字符串 
	 * @param tbObj 表对象
	 * @param effectiveFields 是否只需要部分字段，NULL则表示提取所有字段
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String fieldsToInsertColumnString(BaseTable tbObj, String[] effectiveFields) {
		Map<String, String> map = JdbcAnnotationSupport.getColumnMap(tbObj);
		String[] columns = new String[effectiveFields.length];
		for (int i = 0; i < effectiveFields.length; i++) {
			String s = effectiveFields[i];
			columns[i] = map.get(s);
		}
		return SqlSupport.fieldsToInsertColumnString(columns);
	}

	/** 
	 * @Title: fieldsToInsertValueString 
	 * @Description: 把所有的字段转为可以直接用于SQL insert的字符串 
	 * @param tbObj 表对象
	 * @param effectiveFields 是否只需要部分字段，NULL则表示提取所有字段
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String fieldsToInsertValueString(BaseTable tbObj, String[] effectiveFields) {
		Map<String, String> map = JdbcAnnotationSupport.getColumnMap(tbObj);
		String[] columns = new String[effectiveFields.length];
		for (int i = 0; i < effectiveFields.length; i++) {
			String s = effectiveFields[i];
			columns[i] = map.get(s);
		}
		return SqlSupport.fieldsToInsertValueString(columns);
	}

	/** 
	 * @Title: getFieldAndColumn 
	 * @Description: 把Java的字段和DB的字段管理起来 
	 * @param tbObj 表对象
	 * @param effectiveFields 是否只需要部分字段，NULL则表示提取所有字段
	 * @return 参数说明
	 * @return Map    返回类型
	 */
	public static Map getFieldAndColumn(BaseTable tbObj, String[] effectiveFields) {
		Map<String, String> map = JdbcAnnotationSupport.getColumnMap(tbObj);
		Map rtnMap = null;
		String[] fs = null;
		if (effectiveFields == null) {
			rtnMap = map;
		} else {
			rtnMap = new HashMap();
			for (int i = 0; i < effectiveFields.length; i++) {
				String s = effectiveFields[i];
				rtnMap.put(s, map.get(s));
			}
		}
		return rtnMap;
	}

	/** 
	 * @Title: columnsToQueryString 
	 * @Description: 把所有的DB字段转为可以直接用于SQL Query的字符串 
	 * @param columns 字段组
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String columnsToQueryString(String[] columns) {
		StringBuffer b = new StringBuffer();
		for (int i = 0; i < columns.length; i++) {
			if (i > 0) {
				b.append(",");
			}
			b.append(columns[i]);
		}
		return b.toString();
	}

	/** 
	 * @Title: columnsToWhereString 
	 * @Description: 把所有的DB字段转为可以直接用于SQL Query的字符串 
	 * @param columns 字段组
	 * @param values  字段的值，主要用于判断值是否是‘’和null
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String columnsToWhereString(String[] columns, Map values) {
		StringBuffer b = new StringBuffer();
		for (int i = 0; i < columns.length; i++) {
			if (i > 0) {
				b.append(" and ");
			}
			if (values != null
					&& (values.get(columns[i]) == null || values.get(columns[i]).equals(""))) {
				b.append(columns[i]);
				b.append(" is null ");
			} else {
				b.append(columns[i]);
				b.append(" = :");
				b.append(columns[i]);
			}
		}
		return b.toString();
	}

	/** 
	 * @Title: columnsToUpdateString 
	 * @Description: 把所有的DB字段转为可以直接用于SQL Update的字符串 
	 * @param columns 需要的字段组
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String columnsToUpdateString(String[] columns) {
		StringBuffer b = new StringBuffer();
		for (int i = 0; i < columns.length; i++) {
			if (i > 0) {
				b.append(",");
			}
			b.append(columns[i]);
			b.append("=:");
			b.append(columns[i]);
		}
		return b.toString();
	}

	/** 
	 * @Title: columnsToInsertValueString 
	 * @Description: 把所有的DB字段转为可以直接用于SQL insert的字符串 
	 * @param columns 需要的字段
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String columnsToInsertValueString(String[] columns) {
		StringBuffer b = new StringBuffer();
		for (int i = 0; i < columns.length; i++) {
			if (i > 0) {
				b.append(",");
			}
			b.append(":");
			b.append(columns[i]);
		}
		return b.toString();
	}

	/** 
	 * @Title: paramBeanSql 
	 * @Description: 解析sql 将带：的所有属性解析 并返回替换的sql 
	 * @param sql 执行语句
	 * @return 参数说明
	 * @return Map    返回类型
	 */
	@SuppressWarnings("unchecked")
	public static Map paramBeanSql(String sql) {
		String[] p = sql.split(":");
		String replaceSql = sql;
		List<String> list = new ArrayList();
		Map map = new HashMap();
		for (int i = 1; i < p.length; i++) {
			int dhIndex = p[i].indexOf(",");
			int khIndex = p[i].indexOf(")");
			String[] g = null;
			if (dhIndex >= 0 || khIndex >= 0) {
				if ((dhIndex > khIndex && dhIndex > 0 && khIndex > 0)
						|| (dhIndex < 0 && khIndex > 0)) {
					g = p[i].split("\\)");
				} else {
					g = p[i].split(",");
				}
			} else {
				g = new String[] { p[i] };
			}
			// if (g.length == 1) {
			g = g[0].split(" ");
			// }
			list.add(g[0].trim());
		}
		List replaceList = new ArrayList();
		replaceList.addAll(list);
		Collections.sort(replaceList, new Comparator<String>() {
			public int compare(String s1, String s2) {
				if (s1.length() > s2.length()) {
					return -1;
				}
				if (s1.length() < s2.length()) {
					return 1;
				}
				return s1.compareTo(s2);
			}
		});
		for (int i = 0; i < replaceList.size(); i++) {
			replaceSql = replaceSql.replaceAll(":" + replaceList.get(i), "?");
		}

		map.put(DAO_PARAM_SQL, replaceSql);
		map.put(DAO_PARAM_PROERTY, list);
		return map;
	}

	/** 
	 * @Title: getOracleSQLIn 
	 * @Description: 处理oracle sql 语句in子句中（where id in (1, 2, ..., 1000, 1001)）,
	 *               如果子句中超过1000项就会报错。这主要是oracle考虑性能问题做的限制。
	 *               如果要解决次问题，可以用 where id (1, 2, ..., 1000) or id (1001, ...)
	 * @param ids  in语句中的集合对象
	 * @param count  in语句中出现的条件个数
	 * @param field  in语句对应的数据库查询字段
	 * @return 参数说明
	 * @return String    返回 field in (...) or field in (...) 字符串
	 */
	public static String getOracleSQLIn(List<?> ids, int count, String field) {
		count = Math.min(count, 1000);
		int len = ids.size();
		int size = len % count;
		if (size == 0) {
			size = len / count;
		} else {
			size = (len / count) + 1;
		}

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < size; i++) {
			int fromIndex = i * count;
			int toIndex = Math.min(fromIndex + count, len);
			String productId = StringUtils.defaultIfEmpty(
					StringUtils.join(ids.subList(fromIndex, toIndex), "','"), "");
			if (i != 0) {
				builder.append(" or ");
			}
			builder.append(field).append(" in ('").append(productId).append("')");
		}
		return StringUtils.defaultIfEmpty(builder.toString(), field + " in ('')");
	}

	/** 
	 * @Title: transToLikeValue 
	 * @Description: 转化Sql查询语句中的like参数值,把值的最后一个*或第一个字符的*号转为查询的% 
	 * @param inStr 需要转换的语句
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String transToLikeValue(String inStr) {
		if (inStr != null) {
			if (inStr.startsWith("*")) {
				inStr = inStr.replaceFirst("\\*", "%");
			}
			if (inStr.endsWith("*")) {
				inStr = inStr.substring(0, inStr.length() - 1) + "%";
			}
		}
		return inStr;
	}

	/** 
	 * @Title: transSqlToAlias 
	 * @Description: 别名转换 
	 * @param inSql  需要转换的语句
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String transSqlToAlias(String inSql) {
		String tmpSql = inSql;
		int pos = inSql.lastIndexOf(")");
		if (pos >= 0) {
			if (pos < inSql.length() - 1) {
				tmpSql = inSql.substring(inSql.lastIndexOf(")") + 1);
				String tempSql1 = tmpSql.replaceAll(" {1,}", " ");
				tmpSql = tmpSql.replaceAll(" {1,}", " ").toUpperCase();
				if (tmpSql.lastIndexOf(" AS") >= 0) {
					if (tmpSql.substring(tmpSql.lastIndexOf(" AS") + 4).indexOf("\"") >= 0) {
						return tempSql1.substring(tmpSql.lastIndexOf(" AS") + 4).split("\"")[1];
					} else {
						return tmpSql.substring(tmpSql.lastIndexOf(" AS") + 4);
					}
				}
			}
		} else {
			String tmpSql1 = tmpSql.replaceAll(" {1,}", " ");
			tmpSql = tmpSql.replaceAll(" {1,}", " ").toUpperCase();
			if (tmpSql.lastIndexOf(" AS") >= 0) {
				if (tmpSql.substring(tmpSql.lastIndexOf(" AS") + 4).indexOf("\"") >= 0) {
					return tmpSql1.substring(tmpSql.lastIndexOf(" AS") + 4).split("\"")[1];
				} else {
					return tmpSql.substring(tmpSql.lastIndexOf(" AS") + 4);
				}
			} else {
				if (tmpSql.lastIndexOf(".") >= 0) {
					tmpSql = tmpSql.substring(tmpSql.lastIndexOf(".") + 1);
				}
			}
		}
		return tmpSql;
	}
}
