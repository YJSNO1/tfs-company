/**   
 * @Project: tfs-base 
 * @Title: BeanUtils.java 
 * @Package com.tfstec.base.jdbc.tools 
 * @Description: 实体对象的一些反射處理方法 
 * @author lx 
 * @date 2016年6月28日 下午1:57:46 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.jdbc.tools;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tfstec.base.jdbc.bean.IBaseDto;

/** 
 * @ClassName BeanUtils  
 * @Description 实体对象的一些反射處理方法 
 * @author lx 
 * @date 2016年6月28日  
 *   
 */
public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {
	private static Log log = LogFactory.getLog(BeanUtils.class);

	/** 
	 * @Title: paramBeanMapper 
	 * @Description: 实体属性的映射 
	 * @param object 数据对象
	 * @param property 实体属性
	 * @return 参数说明
	 * @return Object[]    返回类型
	 */
	public static Object[] paramBeanMapper(Object object, List property) {
		List list = new ArrayList();
		Map rmap = null;
		if (object instanceof Map)
			rmap = (Map) object;
		for (int i = 0; i < property.size(); i++) {
			try {
				if (rmap != null) {
					list.add(rmap.get(property.get(i).toString()));
				} else {
					Field fd = object.getClass().getDeclaredField(property.get(i).toString());
					fd.setAccessible(true);
					list.add(fd.get(object));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Object[] obj = new Object[list.size()];
		list.toArray(obj);
		return obj;
	}

	/** 
	 * @Title: getFieldValueByName 
	 * @Description: 根据属性名获取属性值 
	 * @param fieldName 属性名称
	 * @param obj 数据对象
	 * @return 参数说明
	 * @return Object    返回类型
	 */
	public static Object getFieldValueByName(String fieldName, Object obj) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = obj.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(obj, new Object[] {});
			return value;
		} catch (Exception e) {
			return null;
		}
	}

	/** 
	 * @Title: copyPropBetweenBeans 
	 * @Description: JavaBean之间对象属性值拷贝 
	 * @param pFromObj Bean源对象
	 * @param pToObj Bean目标对象
	 * @return void    返回类型
	 */
	public static void copyPropBetweenBeans(Object pFromObj, Object pToObj) {
		if (pToObj != null) {
			try {
				BeanUtils.copyProperties(pToObj, pFromObj);
			} catch (Exception e) {
				log.error("==请注意:==\n JavaBean之间的属性值拷贝发生错误啦!" + "\n详细错误信息如下:");
				e.printStackTrace();
			}
		}
	}

	/** 
	 * @Title: copyPropFromBean2Dto 
	 * @Description: 将JavaBean对象中的属性值拷贝到Dto对象 
	 * @param pFromObj  JavaBean对象源
	 * @param pToDto  Dto目标对象
	 * @return void    返回类型
	 */
	public static void copyPropFromBean2Dto(Object pFromObj, IBaseDto pToDto) {
		if (pToDto != null) {
			try {
				pToDto.putAll(BeanUtils.describe(pFromObj));
				// BeanUtils自动加入了一个Key为class的键值,故将其移除
				pToDto.remove("class");
			} catch (Exception e) {
				log.error("==请注意:==\n 将JavaBean属性值拷贝到Dto对象发生错误啦!" + "\n详细错误信息如下:");
				e.printStackTrace();
			}
		}
	}
}
