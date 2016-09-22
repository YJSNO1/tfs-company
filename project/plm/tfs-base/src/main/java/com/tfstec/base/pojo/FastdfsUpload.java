/**   
 * @Project: tfscore 
 * @Title: FastdfsUpload.java 
 * @Package com.tfscore.pojo 
 * @Description: 文件上传处理 
 * @author lx 
 * @date 2016年6月6日 上午8:55:33 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.pojo;

/** 
 * @ClassName FastdfsUpload  
 * @Description 文件上传处理 
 * @author lx 
 * @date 2016年6月6日  
 *   
 */
public class FastdfsUpload extends Result<FastdfsUpload.Upload> {
	private static final long serialVersionUID = -4175706764542350802L;

	public static class Upload {
		private String code;// 成功与否的标识
		private String message;// 详细说明
		private String filePath;// 文件保存路径
		private String requestTime;// 请求时间
		private String accessKeyID;// 请求ID

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getFilePath() {
			return filePath;
		}

		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}

		public String getRequestTime() {
			return requestTime;
		}

		public void setRequestTime(String requestTime) {
			this.requestTime = requestTime;
		}

		@Override
		public String toString() {
			return "Upload [accessKeyID=" + accessKeyID + ", code=" + code + ", filePath="
					+ filePath + ", message=" + message + ", requestTime=" + requestTime + "]";
		}
	}
}
