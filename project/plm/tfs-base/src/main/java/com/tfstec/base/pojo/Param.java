/**   
 * @Project: tfscore 
 * @Title: Param.java 
 * @Package com.tfscore.pojo 
 * @Description: HTTPS请求参数对象 
 * @author lx 
 * @date 2016年6月6日 上午8:40:21 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.pojo;

/** 
 * @ClassName Param  
 * @Description HTTPS请求参数对象 
 * @author lx 
 * @date 2016年6月6日  
 *   
 */
public class Param {
	// 参数名称
	private String name;

	// 参数值
	private Object value;

	/*
	 * 取得默认值,有许多的数据需要进行默认值的填写(如请求商家ID)，那么这个时候为了避免在客户端做这个配置工作， 那么客户端可以使用一系列值来指定默认操作 可选值: DEF
	 * 使用默认值,一般用于指定使用默认与第三方对接的商家ID和key等,如果平台有此值的默认值， 则用默认平台值，若无，则指定为"" MD5 将此数据执行MD5操作 TIMES
	 * 指定默认值为时间戳 格式如：1409256249894
	 */
	private String operate;

	/** 
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param name
	 * @param value
	 * @param operate 
	 */

	public Param(String name, Object value, String operate) {
		super();
		this.name = name;
		this.value = value;
		this.operate = operate;
	}

	@Override
	public String toString() {
		return "Param [name=" + name + ", value=" + value + ", operate=" + operate + "]";
	}

	/** 
	 * @return name 
	 */

	public String getName() {
		return name;
	}

	/** 
	 * @param name 要设置的 name 
	 */

	public void setName(String name) {
		this.name = name;
	}

	/** 
	 * @return value 
	 */

	public Object getValue() {
		return value;
	}

	/** 
	 * @param value 要设置的 value 
	 */

	public void setValue(Object value) {
		this.value = value;
	}

	/** 
	 * @return operate 
	 */

	public String getOperate() {
		return operate;
	}

	/** 
	 * @param operate 要设置的 operate 
	 */

	public void setOperate(String operate) {
		this.operate = operate;
	}
}
