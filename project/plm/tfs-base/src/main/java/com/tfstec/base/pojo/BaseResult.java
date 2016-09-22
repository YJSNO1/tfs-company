/**   
 * @Project: tfscore 
 * @Title: BaseResult.java 
 * @Package com.tfscore.pojo 
 * @Description: 请求返回基本数据信息 
 * @author lx 
 * @date 2016年6月6日 上午12:06:39 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.pojo;

import com.tfstec.base.BaseGlobal;


/** 
 * @ClassName BaseResult  
 * @Description 请求返回基本数据信息 
 * @author lx 
 * @date 2016年6月6日  
 *   
 */
public class BaseResult {
	protected static final long serialVersionUID = 1L;
	//返回代码
	protected String code = BaseGlobal.RC_FAIL;
	//描述错误返回信息
	protected String msg;
	//数据集
	protected Object data;
	//保留字段
	protected Object retain;
	
	/** 
	 * @Title: setResultOK 
	 * @Description: 设置成功信息
	 * @return void    返回类型
	 */
	public void setResultOK(){
		this.setCode(BaseGlobal.RC_SUCCESS);
		this.setMsg(BaseGlobal.RM_SUCCESS);
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	
	public Object getRetain() {
		return retain;
	}
	
	public void setRetain(Object retain) {
		this.retain = retain;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "BaseResult [code=" + code + ", msg=" + msg + "]";
	}
}
