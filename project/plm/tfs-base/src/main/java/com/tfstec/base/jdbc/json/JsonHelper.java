/**   
 * @Project: tfs-base 
 * @Title: JsonHelper.java 
 * @Package com.tfstec.base.jdbc.json 
 * @Description: Json处理器 
 * @author lx 
 * @date 2016年6月28日 下午1:41:16 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.jdbc.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tfstec.base.StringUtils;
import com.tfstec.base.jdbc.bean.BaseDtoImpl;
import com.tfstec.base.jdbc.bean.IBaseDto;

/** 
 * @ClassName JsonHelper  
 * @Description Json处理器 
 * @author lx 
 * @date 2016年6月28日  
 *   
 */
public class JsonHelper {
	private static Log log = LogFactory.getLog(JsonHelper.class);
	private static final int MAX_SIZE = 10000;// 如果超过1000笔数据，需要自己封装JSON字符串
	private static final String SIZE_ERROR = " List Size大于10000笔需要自行转换为JSON ";

	/** 
	 * @Title: encodeObject2Json 
	 * @Description: 将不含日期时间格式的Java对象系列化为Json资料格式 
	 * @param pObject 传入的Java对象
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static final String encodeObject2Json(Object pObject) {
		String jsonString = "[]";
		if (StringUtils.isEmpty(pObject)) {
			log.warn("传入的Java对象为空,不能将其序列化为Json资料格式.请检查!");
		} else {
			if (pObject instanceof ArrayList) {
				JSONArray jsonArray = JSONArray.fromObject(pObject);
				jsonString = jsonArray.toString();
			} else {
				JSONObject jsonObject = JSONObject.fromObject(pObject);
				jsonString = jsonObject.toString();
			}
		}
		return jsonString;
	}

	/** 
	 * @Title: encodeObject2Json 
	 * @Description: 将含有日期时间格式的Java对象系列化为Json资料格式，
	 *               Json-Lib在处理日期时间格式是需要实现其JsonValueProcessor接口,
	 *               所以在这里提供一个重载的方法对含有日期时间格式的Java对象进行序列化
	 * @param pObject  传入的Java对象
	 * @param pFormatString 需要格式化的日期格式
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static final String encodeObject2Json(Object pObject, String pFormatString) {
		String jsonString = "[]";
		if (StringUtils.isEmpty(pObject)) {
			log.warn("传入的Java对象为空,不能将其序列化为Json资料格式.请检查!");
		} else {
			JsonConfig cfg = new JsonConfig();
			cfg.registerJsonValueProcessor(java.sql.Timestamp.class, new JsonValueProcessorImpl(
					pFormatString));
			cfg.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessorImpl(
					pFormatString));
			cfg.registerJsonValueProcessor(java.sql.Date.class, new JsonValueProcessorImpl(
					pFormatString));
			if (pObject instanceof ArrayList) {
				JSONArray jsonArray = JSONArray.fromObject(pObject, cfg);
				jsonString = jsonArray.toString();
			} else {
				JSONObject jsonObject = JSONObject.fromObject(pObject, cfg);
				jsonString = jsonObject.toString();
			}
		}
		return jsonString;
	}

	/** 
	 * @Title: encodeJson2PageJson 
	 * @Description: 将分页信息压入JSON字符串，此类内部使用,不对外暴露 
	 * @param jsonString JSON字符串
	 * @param totalCount 记录总数
	 * @return 返回合并后的字符串
	 * @return String    返回类型
	 */
	private static String encodeJson2PageJson(String jsonString, Integer totalCount) {
		jsonString = "{TOTALCOUNT:" + totalCount + ", ROOT:" + jsonString + "}";
		return jsonString;
	}

	/** 
	 * @Title: encodeList2PageJson 
	 * @Description: 直接将List转为分页所需要的Json资料格式 
	 * @param list  需要编码的List对象
	 * @param totalCount 记录总数
	 * @param dataFormat 时间日期格式化,传null则表明List不包含日期时间属性
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static final String encodeList2PageJson(List list, Integer totalCount, String dataFormat) {
		String subJsonString = "";
		if (StringUtils.isEmpty(dataFormat)) {
			subJsonString = encodeObject2Json(list);
		} else {
			subJsonString = encodeObject2Json(list, dataFormat);
		}
		String jsonString = "{TOTALCOUNT:" + totalCount + ", ROOT:" + subJsonString + "}";
		return jsonString;
	}

	/** 
	 * @Title: encodeDto2FormLoadJson 
	 * @Description: 将数据系列化为表单数据填充所需的Json格式 
	 * @param pDto 待系列化的对象
	 * @param pFormatString  日期时间格式化,如果为null则认为没有日期时间型字段
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String encodeDto2FormLoadJson(IBaseDto pDto, String pFormatString) {
		String jsonString = "";
		String sunJsonString = "";
		if (StringUtils.isEmpty(pFormatString)) {
			sunJsonString = encodeObject2Json(pDto);
		} else {
			sunJsonString = encodeObject2Json(pDto, pFormatString);
		}
		jsonString = "{success:"
				+ (StringUtils.isEmpty(pDto.getAsString("success")) ? "true" : pDto
						.getAsString("success")) + ",data:" + sunJsonString + "}";

		return jsonString;
	}

	/** 
	 * @Title: parseSingleJson2Dto 
	 * @Description: 将单一Json对象解析为DTOJava对象 
	 * @param jsonString 简单的Json对象
	 * @return 参数说明
	 * @return IBaseDto    返回类型
	 */
	public static IBaseDto parseSingleJson2Dto(String jsonString) {
		IBaseDto dto = new BaseDtoImpl();
		if (StringUtils.isEmpty(jsonString)) {
			return dto;
		}
		JSONObject jb = JSONObject.fromObject(jsonString);
		dto = (BaseDtoImpl) JSONObject.toBean(jb, BaseDtoImpl.class);
		return dto;
	}

	/** 
	 * @Title: parseJson2List 
	 * @Description: 将复杂Json资料格式转换为List对象 
	 * @param jsonString 复杂Json对象,格式必须符合如下契约，
	 *                   {"1":{"name":"托尼.贾","age":"27"},
	 *                   "2":{"name":"甄子丹","age":"72"}}
	 * @return 参数说明
	 * @return List    返回类型
	 */
	public static List parseJson2List(String jsonString) {
		List list = new ArrayList();
		JSONObject jbJsonObject = JSONObject.fromObject(jsonString);
		Iterator iterator = jbJsonObject.keySet().iterator();
		while (iterator.hasNext()) {
			IBaseDto dto = parseSingleJson2Dto(jbJsonObject.getString(iterator.next().toString()));
			list.add(dto);
		}
		return list;
	}

	/** 
	 * @Title: getJsonType 
	 * @Description: 判断json的类型  0表示值  1 表示json对象  2表示json数组 
	 * @param json  Json对象
	 * @return 参数说明
	 * @return int    返回类型
	 */
	public static int getJsonType(String json) {
		int objectType = 0;
		try {
			if (!StringUtils.isEmpty(json)) {
				com.alibaba.fastjson.JSONObject joc1 = com.alibaba.fastjson.JSONObject
						.parseObject(json);
				objectType = 1;
			}
		} catch (Exception e) {
			objectType = 0;
		}
		if (objectType != 1) {
			try {
				if (!StringUtils.isEmpty(json)) {
					com.alibaba.fastjson.JSONArray joc1 = com.alibaba.fastjson.JSONArray
							.parseArray(json);
					objectType = 2;
				}
			} catch (Exception e) {
				objectType = 0;
			}
		}
		return objectType;
	}

	/** 
	 * @Title: checkIsChanged 
	 * @Description: 判断某个JSON中某个字段的值是否发生了变更
	 * 在数据走BPM时进行需要用到，当A字段和B字段发生了变更时就需要审批，而其它字段发生变更时则无需要走审批
	 * 所就编写此方法，此方法只能用于前台使用Json对比产生的JSON字符串，因为有old,new 属性；
	 * @param jsonData Json的数据对象
	 * @param fields 数据具体字段，是多层次的，如'a'.'b'.'c' 表示监听c字段是否变更；['a'.'b'.'c','a'.'v'.'table.field']
	 * @return 参数说明
	 * @return boolean    返回类型
	 */
	public static boolean checkIsChanged(net.sf.json.JSONObject jsonData, String[] fields) {
		for (int i = 0; i < fields.length; i++) {
			String rule = fields[i];
			String[] rlist = rule.split("'.");
			for (int j = 0; j < rlist.length; j++) {
				rlist[j] = rlist[j].replaceAll("'", "");
			}
			boolean needBpm = checkEachRule(jsonData, rlist, 0);
			if (needBpm) {
				return needBpm;
			}
		}
		return false;
	}

	/** 
	 * @Title: checkEachRule 
	 * @Description: 判断某个字段值是否改变 
	 * @param data Json数据对象
	 * @param rlist 数组
	 * @param pidx 数据索引
	 * @return 参数说明
	 * @return boolean    返回类型
	 */
	private static boolean checkEachRule(net.sf.json.JSONObject data, String[] rlist, int pidx) {
		boolean needBpm = false;
		if (data != null && rlist.length > 0) {
			String p = rlist[pidx];
			Object datap = data.get(p);
			if (datap == null) {// 没有这个参数

			} else if (datap instanceof net.sf.json.JSONArray) {// 是个数组
				net.sf.json.JSONArray datapa = (net.sf.json.JSONArray) datap;
				for (int i = 0; i < datapa.size(); i++) {
					JSONObject item = datapa.getJSONObject(i);
					String otype = (String) item.get("otype");
					if ("A".equals(otype) || "D".equals(otype)) {
						needBpm = true;
						break;
					} else if ("M".equals(otype)) {
						needBpm = checkEachRule(item, rlist, pidx + 1);
					}
					if (needBpm) {
						break;
					}
				}
			} else if (datap instanceof net.sf.json.JSONObject) {// 是个对象
				JSONObject datapo = (JSONObject) datap;
				if (datapo.containsKey("otype")) {// 有OType属性，表示不是叶子节点
					String otype = (String) datapo.get("otype");
					if ("A".equals(otype) || "D".equals(otype)) {
						needBpm = true;
					} else if ("O".equals(otype)) {
						needBpm = checkEachRule(datapo, rlist, pidx + 1);
					}
				} else {// 是最终的叶子节点，就有old,new 属性
					if (datapo.containsKey("old") && datapo.containsKey("new")) {
						if (!datapo.get("old").equals(datapo.get("old"))) {
							needBpm = true;
						}
					} else {
						needBpm = checkEachRule(datapo, rlist, pidx + 1);
					}
				}
			}
		}
		return needBpm;
	}
}
