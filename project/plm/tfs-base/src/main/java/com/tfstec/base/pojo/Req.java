/**   
 * @Project: tfscore 
 * @Title: Req.java 
 * @Package com.tfscore.pojo 
 * @Description: HTTPS请求基类 
 * @author lx 
 * @date 2016年6月6日 上午8:38:57 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.pojo;

import java.util.List;

/** 
 * @ClassName Req  
 * @Description HTTPS请求基类 
 * @author lx 
 * @date 2016年6月6日  
 *   
 */
public class Req {
	/*
	 * 预计格式
	 * {"pro":"LKB",method:"LKB1",caller:"","url":"","param":"[{"paramName":"aaa","value":"bb","operate":"DEF"},{"paramName":"aaa","value":"bb","operate":"DEF"}]"}
	 */
	//第三方名称  如果没有第三方项目名称，那么这个时候客户端自行指定
	private String pro;
	
	//所有的参数 BASE64
	private List<Param> params;
	
	//调用传输参数
	private Param param;
	
	//如果是json格式的，那么就不需要发送指定的req格式
	private String jsonParams;
	
	//调用者
	private String caller;
	
	//调用方法名称
	private String method;
	
	//用户可以自行定义，当url不为空的时候，采用此url进行调用 BASE64
	private String url;
	
	//返回值是否要加密 BASE64 | ""
	private String encry;
	
	//head 头信息  Map<String,String> 并且BASE64
	private String head;//
	
	/*
	 * 参数的类型   XML | JSON | OTHER  
	 * XML  将直接将此xml数据发送过去，不进行参数化
	 * JSON 执行内部的json预处理值方式，进行设置name和value方式提交
	 * OTHER 排除XML和JSON 直接将值进行发送，如果对方直接需要JSON，或者是特殊的格式，那么采用此方式
	 * 
	 * 默认采用Map的方式存储，将去除map的每一个数据进行组装post表单
	 */
	private String paramstyle;
	
	//每次请求的唯一序列号
	private String uniqueId;
	
	/** 最简单的单一param 请求对象
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param pro
	 * @param param
	 * @param caller
	 * @param method
	 * @param paramstyle 
	 */ 
	public Req(String pro, Param param, String caller,
			String method, String paramstyle) {
		super();
		this.pro = pro;
		this.param = param;
		this.caller = caller;
		this.method = method;
		this.paramstyle = paramstyle;
	}

	/** 最简单话请求对象
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param pro
	 * @param params
	 * @param caller
	 * @param method
	 */ 
	public Req(String pro, List<Param> params, String caller, String method) {
		super();
		this.pro = pro;
		this.params = params;
		this.caller = caller;
		this.method = method;
	}

	/** 通用请求对象类
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param pro
	 * @param params
	 * @param caller
	 * @param method
	 * @param url
	 * @param encry
	 * @param head
	 * @param paramstyle 
	 */ 
	public Req(String pro, List<Param> params, String caller, String method,
			String url, String encry, String head, String paramstyle) {
		super();
		this.pro = pro;
		this.params = params;
		this.caller = caller;
		this.method = method;
		this.url = url;
		this.encry = encry;
		this.head = head;
		this.paramstyle = paramstyle;
	}

	/** 
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param pro
	 * @param params
	 * @param caller
	 * @param method
	 * @param url
	 * @param encry
	 * @param head
	 * @param paramstyle 
	 */ 
	public Req(String pro, List<Param> params,String jsonParams, String caller, String method,
			String url, String encry, String head, String paramstyle,String uniqueId) {
		super();
		this.pro = pro;
		this.params = params;
		this.jsonParams = jsonParams;
		this.caller = caller;
		this.method = method;
		this.url = url;
		this.encry = encry;
		this.head = head;
		this.paramstyle = paramstyle;
		this.uniqueId = uniqueId;
	}

 
	@Override
	public String toString() {
		return "Req [pro=" + pro + ", params=" + params + ", param=" + param
				+ ", jsonParams=" + jsonParams + ", caller=" + caller
				+ ", method=" + method + ", url=" + url + ", encry=" + encry
				+ ", head=" + head + ", paramstyle=" + paramstyle
				+ ", uniqueId=" + uniqueId + "]";
	}

	public Param getParam() {
		return param;
	}

	public void setParam(Param param) {
		this.param = param;
	}

	/** 
	 * @return jsonParams 
	 */
	public String getJsonParams() {
		return jsonParams;
	}

	/** 
	 * @param jsonParams 要设置的 jsonParams 
	 */
	public void setJsonParams(String jsonParams) {
		this.jsonParams = jsonParams;
	}

	/** 
	 * @return uniqueId 
	 */
	public String getUniqueId() {
		return uniqueId;
	}


	/** 
	 * @param uniqueId 要设置的 uniqueId 
	 */
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}


	/** 
	 * @return encry 
	 */
	public String getEncry() {
		return encry;
	}

	/** 
	 * @param encry 要设置的 encry 
	 */
	public void setEncry(String encry) {
		this.encry = encry;
	}

	/** 
	 * @return head 
	 */
	public String getHead() {
		return head;
	}

	/** 
	 * @param head 要设置的 head 
	 */
	public void setHead(String head) {
		this.head = head;
	}

	/** 
	 * @return paramstyle 
	 */
	public String getParamstyle() {
		return paramstyle;
	}

	/** 
	 * @param paramstyle 要设置的 paramstyle 
	 */
	public void setParamstyle(String paramstyle) {
		this.paramstyle = paramstyle;
	}

	/** 
	 * @return pro 
	 */
	public String getPro() {
		return pro;
	}

	/** 
	 * @param pro 要设置的 pro 
	 */
	public void setPro(String pro) {
		this.pro = pro;
	}

	/** 
	 * @return params 
	 */
	public List<Param> getParams() {
		return params;
	}

	/** 
	 * @param params 要设置的 params 
	 */
	public void setParams(List<Param> params) {
		this.params = params;
	}

	/** 
	 * @return caller 
	 */
	public String getCaller() {
		return caller;
	}

	/** 
	 * @param caller 要设置的 caller 
	 */
	public void setCaller(String caller) {
		this.caller = caller;
	}

	/** 
	 * @return method 
	 */
	public String getMethod() {
		return method;
	}

	/** 
	 * @param method 要设置的 method 
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/** 
	 * @return url 
	 */
	public String getUrl() {
		return url;
	}

	/** 
	 * @param url 要设置的 url 
	 */
	public void setUrl(String url) {
		this.url = url;
	}
}
