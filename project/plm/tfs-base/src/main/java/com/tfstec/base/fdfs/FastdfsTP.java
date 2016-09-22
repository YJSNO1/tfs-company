/**   
 * @Project: tfscore 
 * @Title: FastdfsTP.java 
 * @Package com.tfscore.fdfs 
 * @Description: 使用http方式进行图片分布式存储 
 * @author lx 
 * @date 2016年6月6日 上午8:51:39 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.fdfs;

import java.io.File;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tfstec.base.BaseGlobal;
import com.tfstec.base.gson.GsonUtils;
import com.tfstec.base.http.HttpUtil;
import com.tfstec.base.pojo.FastdfsToken;
import com.tfstec.base.pojo.FastdfsUpload;

/** 
 * @ClassName FastdfsTP  
 * @Description 使用http方式进行图片分布式存储 
 * @author lx 
 * @date 2016年6月6日  
 *   
 */
public class FastdfsTP {
	private static Logger logger = LoggerFactory.getLogger(FastdfsTP.class);
	private static String fastDfsHost = "10.0.76.55";// ServiceConfig.getConfig().getHostConfig().getFastDfsHost();
	private static String accessKeyID = "admin";// ServiceConfig.getConfig().getHostConfig().getAccessKeyID();
	private static String accessKeySecret = "123";// ServiceConfig.getConfig().getHostConfig().getAccessKeySecret();
	private static String encoding = "utf-8";
	private static String token = null;

	public static String getDateNormal() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	/** 
	 * 获取token 
	 */
	public static void getToken() {
		HttpUtil util = HttpUtil.getInstance(false);
		String url = "http://" + fastDfsHost + "/FastDfsTP/user/getToken";
		Map<String, String> formParams = new HashMap<String, String>();
		formParams.put("accessKeyID", accessKeyID);
		formParams.put("accessKeySecret", accessKeySecret);
		formParams.put("uploadTime", getDateNormal());
		HttpResponse response = util.doPost(url, null, formParams);
		try {
			if (response != null) {
				String res = EntityUtils.toString(response.getEntity());
				FastdfsToken info = GsonUtils.getJson(res, FastdfsToken.class);
				if ("0".equals(info.getCode())) {
					token = info.getToken();
				}
			}
		} catch (Exception e) {
			logger.error("文件上传下载(分布式系统)获取Token值出现异常：", e);
		}
	}

	/** 
	 * 文件上传 
	 */
	@SuppressWarnings("deprecation")
	public static FastdfsUpload uploadFile(String localFilePath) {
		if (token == null) {
			getToken();
		}
		HttpUtil util = HttpUtil.getInstance(false);
		String url = "http://" + fastDfsHost + "/FastDfsTP/file/uploadFile";
		try {
			List<FormBodyPart> formParts = new ArrayList<FormBodyPart>();
			formParts.add(new FormBodyPart("token",
					new StringBody(token, Charset.forName(encoding))));
			formParts.add(new FormBodyPart("fileData", new FileBody(new File(localFilePath))));
			formParts.add(new FormBodyPart("uploadTime", new StringBody(getDateNormal(), Charset
					.forName(encoding))));
			HttpResponse response = util.multipartPost(url, null, formParts);
			if (response != null) {
				String res = EntityUtils.toString(response.getEntity());
				FastdfsUpload info = GsonUtils.getJson(res, FastdfsUpload.class);
				if ("501".equals(info.getResult().getCode())
						|| "502".equals(info.getResult().getCode())) {
					getToken();
					// 移除token并重新添加
					formParts.remove(0);
					formParts.add(new FormBodyPart("token", new StringBody(token, Charset
							.forName(encoding))));
					response = util.multipartPost(url, null, formParts);
					res = EntityUtils.toString(response.getEntity());
					info = GsonUtils.getJson(res, FastdfsUpload.class);
				}
				return info;
			}
		} catch (Exception e) {
			logger.error("文件上传上传到分布式系统出现异常：", e);
		}
		return null;
	}

	/** 
	 * 文件下载 
	 */
	public static String downFile(String filePath, String localFilePath, String Origin) {
		// 失败标识
		String str = BaseGlobal.RC_FAIL;
		// 本地文件存在就不再下载
		File file = new File(localFilePath);
		if (file.exists()) {
			str = BaseGlobal.RC_SUCCESS;
		} else {
			if (token == null) {
				getToken();
			}
			HttpUtil util = HttpUtil.getInstance(false);
			String url = "http://" + fastDfsHost + "/FastDfsTP/file/downloadFile?token=" + token
					+ "&filePath=" + filePath + "&fileOrigin=" + Origin;
			HttpResponse response = util.doGet(url, null);
			try {
				if (response != null) {
					int code = response.getStatusLine().getStatusCode();
					if (code == 200) {
						HttpUtil.writeToFile(response.getEntity().getContent(), localFilePath);
						str = BaseGlobal.RC_SUCCESS;
					} else if (code == 403) {
						getToken();
						response = util.doGet(url, null);
						code = response.getStatusLine().getStatusCode();
						if (code == 200) {
							HttpUtil.writeToFile(response.getEntity().getContent(), localFilePath);
							str = BaseGlobal.RC_SUCCESS;
						}
					}
				}
			} catch (Exception e) {
				logger.error("从分布式系统下载文件出现异常：", e);
			}
		}
		return str;
	}
}
