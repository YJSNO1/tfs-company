/**   
 * @Project: tfscore 
 * @Title: HttpsUtil.java 
 * @Package com.tfscore.http 
 * @Description: HTTPS双向认证 工具 
 * @author lx 
 * @date 2016年6月6日 上午12:36:01 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.http;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tfstec.base.encry.Base64Coder;
import com.tfstec.base.encry.EncrypyUtil;
import com.tfstec.base.gson.GsonUtils;
import com.tfstec.base.pojo.CertificateBean;
import com.tfstec.base.pojo.Req;

/** 
 * @ClassName HttpsUtil  
 * @Description HTTPS双向认证 工具 
 * @author lx 
 * @date 2016年6月6日  
 *   
 */
public class HttpsUtil {
	private static Logger logger = LoggerFactory.getLogger(HttpsUtil.class);
	private static myHostnameVerifier1 hnv = new myHostnameVerifier1();

	/** 
	 * @Title: httpsRequest 
	 * @Description: HTTPS请求
	 * @param certificateBean 证书对象 
	 * @param url 请求url路径
	 * @param req 请求参数
	 * @param reqMethod 请求方法
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String httpsRequest(CertificateBean certificateBean,String url, Req req, String reqMethod) {
		return httpsRequest(certificateBean,url, req, reqMethod, null);
	}

	/** 
	 * @Title: httpsRequest 
	 * @Description: HTTPS请求
	 *  @param certificateBean 证书对象
	 * @param url 请求url路径
	 * @param req 请求参数
	 * @param reqMethod 请求方法
	 * @param localFilePath 文件路径
	 * @return 参数说明
	 * @return String    返回类型
	 */
	public static String httpsRequest(CertificateBean certificateBean,String url, Req req, String reqMethod, String localFilePath) {
		StringBuffer respStr = new StringBuffer();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			KeyStore keyStore = KeyStore.getInstance(certificateBean.getKeyStoreTypeP12());
			KeyStore trustStore = KeyStore.getInstance(certificateBean.getKeyStoreTypeJks());
			InputStream ksIn = new FileInputStream(certificateBean.getKeyStoreClientPath());
			InputStream tsIn = new FileInputStream(new File(certificateBean.getKeyStoreTrustPath()));
			try {
				keyStore.load(ksIn, certificateBean.getKeyStorePassword().toCharArray());
				trustStore.load(tsIn, certificateBean.getKeyStoreTrustPassword().toCharArray());
			} finally {
				try {
					ksIn.close();
				} catch (Exception ignore) {
				}
				try {
					tsIn.close();
				} catch (Exception ignore) {
				}
			}
			// 相信自己的CA和所有自签名的证书
			SSLContext sslcontext = SSLContexts.custom()
					.loadKeyMaterial(keyStore, certificateBean.getKeyStorePassword().toCharArray())
					.loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();

			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,
					new String[] { "TLSv1" }, null, hnv);
			httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

			// -----数据值处理-------
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();

			String method = req.getMethod();
			nvps.add(new BasicNameValuePair("pro", req.getPro()));
			nvps.add(new BasicNameValuePair("method", method));
			nvps.add(new BasicNameValuePair("caller", req.getCaller()));

			String json = "";
			if (req.getParam() != null) {
				json = GsonUtils.toJson(req.getParam());
			} else {
				json = GsonUtils.toJson(req.getParams());
			}
			String base64 = "";
			// 根据method选择不同的base64方法，当前安硕(财务系统)需要用原始的base64方法
			if ("amrsoft".equals(method)) {
				base64 = EncrypyUtil.encryptBASE64(json.getBytes("UTF-8"));
			} else {
				base64 = Base64Coder.encodeString(json);
			}
			nvps.add(new BasicNameValuePair("param", base64));
			nvps.add(new BasicNameValuePair("paramstyle", req.getParamstyle()));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			// ---------数据值处理end-----------

			CloseableHttpResponse response2 = httpclient.execute(httpPost);
			try {
				HttpEntity entity2 = response2.getEntity();
				InputStream is = entity2.getContent();
				if (StringUtils.isNotBlank(localFilePath)) {
					boolean bool = writeToFile(is, localFilePath);
					if (bool) {
						respStr.append("文件下载成功");
					} else {
						respStr.append("文件下载失败");
					}
				} else {
					List<String> lines = IOUtils.readLines(is, "UTF-8");
					for (int i = 0; i < lines.size(); i++) {
						respStr.append(lines.get(i));
					}
				}
			} finally {
				response2.close();
			}
		} catch (Exception e) {
			logger.error("httpsUtil发起请求出现异常：" + e.getMessage(), e);
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				logger.error("httpsUtil关闭请求出现异常：" + e.getMessage(), e);
			}
		}
		return respStr.toString();
	}

	/**
	 * 将流写入文件
	 * @param in 输入流
	 * @param localFilePath 文件路径
	 */
	public static boolean writeToFile(InputStream in, String localFilePath) {
		if (in == null) {
			return false;
		}
		File file = new File(localFilePath);
		try {
			String localpath = "";
			if (localFilePath.lastIndexOf("/") > 0) {
				localpath = localFilePath.substring(0, localFilePath.lastIndexOf("/"));
			} else if (localFilePath.lastIndexOf("\\") > 0) {
				localpath = localFilePath.substring(0, localFilePath.lastIndexOf("\\"));
			}
			File f = new File(localpath);
			// 文件夹不存在时创建文件夹
			if (!f.exists()) {
				f.mkdirs();
			}
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
			byte[] buf = new byte[2048];
			int length = in.read(buf);
			while (length != -1) {
				out.write(buf, 0, length);
				length = in.read(buf);
			}
			out.close();
		} catch (Exception e) {
			logger.error("将流写入文件出现异常：" + e.getMessage(), e);
		}
		return file.isFile();
	}

}

class myHostnameVerifier1 implements X509HostnameVerifier {
	private static Logger logger = LoggerFactory.getLogger(myHostnameVerifier1.class);
	public boolean verify(String hostname, SSLSession session) {
		logger.info("myHostnameVerifier1==> Warning: URL Host: " + hostname + " vs. " + session.getPeerHost());
		return true;
	}

	public void verify(String host, SSLSocket ssl) {
		return;
	}

	public void verify(String host, X509Certificate cert) {
		return;
	}

	public void verify(String host, String[] cns, String[] subjectAlts) {
		return;
	}
}
