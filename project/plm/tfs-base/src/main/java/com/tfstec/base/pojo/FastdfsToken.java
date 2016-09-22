/**   
 * @Project: tfscore 
 * @Title: FastdfsToken.java 
 * @Package com.tfscore.pojo 
 * @Description: 图片分布式服务器Token信息 
 * @author lx 
 * @date 2016年6月6日 上午8:52:51 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.pojo;

/** 
 * @ClassName FastdfsToken  
 * @Description 图片分布式服务器Token信息 
 * @author lx 
 * @date 2016年6月6日  
 *   
 */
public class FastdfsToken {
	private static final long serialVersionUID = -7949268109210426233L;
	private String timeOut;// 超时时间
	private String token;// 上传和下载的请求标识
	private String Message;// 成功与否的说明
	private String Code;// 成功与否的标识 0成功

	public String getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}
}
