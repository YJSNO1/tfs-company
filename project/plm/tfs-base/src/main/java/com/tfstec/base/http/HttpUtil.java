/**   
 * @Project: tfscore 
 * @Title: HttpUtil.java 
 * @Package com.tfscore.http 
 * @Description: Http请求对象 
 * @author lx 
 * @date 2016年6月6日 上午12:30:51 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.http;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.cookie.BestMatchSpec;
import org.apache.http.message.BasicNameValuePair;

/** 
 * @ClassName HttpUtil  
 * @Description Http请求对象 
 * @author lx 
 * @date 2016年6月6日  
 *   
 */
public class HttpUtil {
	private static final Log logger = LogFactory.getLog(HttpUtil.class);
	private static int bufferSize = 1024;
//	private static volatile HttpUtil instance;
	private ConnectionConfig connConfig;
	private SocketConfig socketConfig;
	private ConnectionSocketFactory plainSF;
	private KeyStore trustStore;
	private SSLContext sslContext;
	private LayeredConnectionSocketFactory sslSF;
	private Registry<ConnectionSocketFactory> registry;
	private PoolingHttpClientConnectionManager connManager;
	private volatile HttpClient client;
	private volatile BasicCookieStore cookieStore;
	public static String defaultEncoding = "utf-8";

	private HttpUtil(boolean isSSL) {
		// 设置连接参数
		connConfig = ConnectionConfig.custom()
				.setCharset(Charset.forName(defaultEncoding)).build();
		socketConfig = SocketConfig.custom().setSoTimeout(100000).build();
		RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder
				.<ConnectionSocketFactory> create();
		plainSF = new PlainConnectionSocketFactory();
		registryBuilder.register("http", plainSF);
		if (isSSL) {
			// 指定信任密钥存储对象和连接套接字工厂
			try {
				trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
				sslContext = SSLContexts.custom().useTLS()
						.loadTrustMaterial(trustStore, new AnyTrustStrategy())
						.build();
				sslSF = new SSLConnectionSocketFactory(sslContext,
						SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				registryBuilder.register("https", sslSF);
			} catch (Exception e) {
				logger.error("HttpUtil发起请求出现异常：" + e.getMessage(),e);
				throw new RuntimeException(e);
			}
		}
		registry = registryBuilder.build();
		// 设置连接管理
		connManager = new PoolingHttpClientConnectionManager(registry);
		connManager.setDefaultConnectionConfig(connConfig);
		connManager.setDefaultSocketConfig(socketConfig);
		// 指定cookie存储对象
		cookieStore = new BasicCookieStore();
		// 构建客户
		client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore)
				.setConnectionManager(connManager).build();
	}

	public static HttpUtil getInstance(boolean isSSL) {
		/*
		 * 
		 * synchronized (HttpUtil.class) { if (HttpUtil.instance == null) {
		 * instance = new HttpUtil(isSSL); } return instance; }
		 */
		return new HttpUtil(isSSL);
	}

	/**
	 * 基本的Get请求
	 * @param url 请求url
	 * @param queryParams 请求头的查询参数
	 * @return
	 * @throws URISyntaxException 
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public HttpResponse doGet(String url, Map<String, String> queryParams) {
		try {
			HttpGet gm = new HttpGet();
			URIBuilder builder = new URIBuilder(url);
			// 填入查询参数
			if (queryParams != null && !queryParams.isEmpty()) {
				builder.setParameters(HttpUtil.paramsConverter(queryParams));
			}
			gm.setURI(builder.build());
			return client.execute(gm);
		} catch (Exception e) {
			logger.error("HttpUtil发起Get请求出现异常：" + e.getMessage(),e);
		}
		return null;
	}
	
	/**
	 * 基本的Post请求
	 * @param url 请求url
	 * @param queryParams 请求头的查询参数
	 * @param headMap 添加的头部信息
	 * @return
	 * @throws URISyntaxException 
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public HttpResponse doGet(String url, Map<String, String> queryParams,Map<String, String> headMap) {
		try {
			HttpGet gm = new HttpGet();
			URIBuilder builder = new URIBuilder(url);
			// 填入查询参数
			if (queryParams != null && !queryParams.isEmpty()) {
				builder.setParameters(HttpUtil.paramsConverter(queryParams));
			}
			
			gm = setHead(gm, headMap);
			gm.setURI(builder.build());
			return client.execute(gm);
		} catch (Exception e) {
			logger.error("HttpUtil发起Get请求出现异常：" + e.getMessage(),e);
		}
		return null;
	}

	/**
	 * 设置请求头
	 * @Title: setHead 
	 * @Description: 设置请求头
	 * @param gm  GET方式
	 * @param headMap 请求头信息
	 * @return 参数说明
	 * @return HttpGet    返回类型
	 */
	public HttpGet setHead(HttpGet gm,Map<String, String> headMap){
		Set<Entry<String, String>> paramsSet = headMap.entrySet();
		for (Entry<String, String> paramEntry : paramsSet) {
			gm.setHeader(paramEntry.getKey(), paramEntry.getValue());
		}
		return gm;
	}
	
	/**
	 * 设置请求头
	 * @Title: setHead 
	 * @Description: 设置请求头
	 * @param gm  POST方式
	 * @param headMap 请求头信息
	 * @return 参数说明
	 * @return HttpGet    返回类型
	 */
	public HttpPost setHead(HttpPost pm,Map<String, String> headMap){
		Set<Entry<String, String>> paramsSet = headMap.entrySet();
		for (Entry<String, String> paramEntry : paramsSet) {
			pm.setHeader(paramEntry.getKey(), paramEntry.getValue());
		}
		return pm;
	}

	/**
	 * 基本的Post请求
	 * @param url 请求url
	 * @param queryParams 请求头的查询参数
	 * @param formParams post表单的参敄
	 * @return
	 * @throws URISyntaxException 
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public HttpResponse doPost(String url, Map<String, String> queryParams,
			Map<String, String> formParams) {
		try {
			HttpPost pm = new HttpPost();
			URIBuilder builder = new URIBuilder(url);
			// 填入查询参数
			if (queryParams != null && !queryParams.isEmpty()) {
				builder.setParameters(HttpUtil.paramsConverter(queryParams));
			}
			pm.setURI(builder.build());
			// 填入表单参数
			if (formParams != null && !formParams.isEmpty()) {
				pm.setEntity(new UrlEncodedFormEntity(HttpUtil
						.paramsConverter(formParams)));
			}
			return client.execute(pm);
		} catch (Exception e) {
			logger.error("HttpUtil发起Post请求出现异常：" + e.getMessage(),e);
		}
		return null;
	}
	
	/**
	 * 基本的Post请求
	 * @param url 请求url
	 * @param queryParams 请求头的查询参数
	 * @param formParams post表单的参敄
	 * @param headMap 头部信息
	 * @return
	 * @throws URISyntaxException 
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public HttpResponse doPost(String url, Map<String, String> queryParams,
			Map<String, String> formParams,Map<String, String> headMap) {
		try {
			HttpPost pm = new HttpPost();
			URIBuilder builder = new URIBuilder(url);
			// 填入查询参数
			if (queryParams != null && !queryParams.isEmpty()) {
				builder.setParameters(HttpUtil.paramsConverter(queryParams));
			}
			pm.setURI(builder.build());
			pm = setHead(pm, headMap);
			// 填入表单参数
			if (formParams != null && !formParams.isEmpty()) {
				pm.setEntity(new UrlEncodedFormEntity(HttpUtil
						.paramsConverter(formParams)));
			}
			return client.execute(pm);
		} catch (Exception e) {
			logger.error("HttpUtil发起Post请求出现异常：" + e.getMessage(),e);
		}
		return null;
	}

	/**
	 * 多块Post请求
	 * @param url 请求url
	 * @param queryParams 请求头的查询参数
	 * @param formParts post表单的参敄,支持字符丄-文件(FilePart)和字符串-字符丄(StringPart)形式的参敄
	 * @param maxCount 多尝试请求的次数
	 * @return
	 * @throws URISyntaxException 
	 * @throws ClientProtocolException 
	 * @throws HttpException
	 * @throws IOException
	 */
	public HttpResponse multipartPost(String url,
			Map<String, String> queryParams, List<FormBodyPart> formParts) {
		try {
			HttpPost pm = new HttpPost();
			URIBuilder builder = new URIBuilder(url);
			// 填入查询参数
			if (queryParams != null && !queryParams.isEmpty()) {
				builder.setParameters(HttpUtil.paramsConverter(queryParams));
			}
			pm.setURI(builder.build());
			// 填入表单参数
			if (formParts != null && !formParts.isEmpty()) {
				MultipartEntityBuilder entityBuilder = MultipartEntityBuilder
						.create();
				entityBuilder = entityBuilder
						.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
				for (FormBodyPart formPart : formParts) {
					entityBuilder = entityBuilder.addPart(formPart.getName(),
							formPart.getBody());
				}
				pm.setEntity(entityBuilder.build());
			}
			return client.execute(pm);
		} catch (Exception e) {
			logger.error("HttpUtil发起多块Post请求出现异常：" + e.getMessage(),e);
		}
		return null;
	}

	/**
	 * 获取当前Http客户端状态中的Cookie
	 * @param domain 作用埄
	 * @param port 端口 传null 默认80
	 * @param path Cookie路径 传null 默认"/"
	 * @param useSecure Cookie是否采用安全机制 传null 默认false
	 * @return
	 */
	public Map<String, Cookie> getCookie(String domain, Integer port,
			String path, Boolean useSecure) {
		if (domain == null) {
			return null;
		}
		if (port == null) {
			port = 80;
		}
		if (path == null) {
			path = "/";
		}
		if (useSecure == null) {
			useSecure = false;
		}
		List<Cookie> cookies = cookieStore.getCookies();
		if (cookies == null || cookies.isEmpty()) {
			return null;
		}

		CookieOrigin origin = new CookieOrigin(domain, port, path, useSecure);
		BestMatchSpec cookieSpec = new BestMatchSpec();
		Map<String, Cookie> retVal = new HashMap<String, Cookie>();
		for (Cookie cookie : cookies) {
			if (cookieSpec.match(cookie, origin)) {
				retVal.put(cookie.getName(), cookie);
			}
		}
		return retVal;
	}

	/**
	 * 批量设置Cookie
	 * @param cookies cookie
	 * @param domain 作用埄  不可为空
	 * @param path 路径 传null默认丄 "/"
	 * @param useSecure 是否使用安全机制 传null 默认为false
	 * @return 是否成功设置cookie
	 */
	public boolean setCookie(Map<String, String> cookies, String domain,
			String path, Boolean useSecure) {
		synchronized (cookieStore) {
			if (domain == null) {
				return false;
			}
			if (path == null) {
				path = "/";
			}
			if (useSecure == null) {
				useSecure = false;
			}
			if (cookies == null || cookies.isEmpty()) {
				return true;
			}
			Set<Entry<String, String>> set = cookies.entrySet();
			String key = null;
			String value = null;
			for (Entry<String, String> entry : set) {
				key = entry.getKey();
				if (key == null || key.isEmpty() || value == null
						|| value.isEmpty()) {
					throw new IllegalArgumentException(
							"cookies key and value both can not be empty");
				}
				BasicClientCookie cookie = new BasicClientCookie(key, value);
				cookie.setDomain(domain);
				cookie.setPath(path);
				cookie.setSecure(useSecure);
				cookieStore.addCookie(cookie);
			}
			return true;
		}
	}

	/**
	 * 设置单个Cookie
	 * @param key Cookie
	 * @param value Cookie
	 * @param domain 作用埄  不可为空
	 * @param path 路径 传null默认丄 "/"
	 * @param useSecure 是否使用安全机制 传null 默认为false
	 * @return 是否成功设置cookie
	 */
	public boolean setCookie(String key, String value, String domain,
			String path, Boolean useSecure) {
		Map<String, String> cookies = new HashMap<String, String>();
		cookies.put(key, value);
		return setCookie(cookies, domain, path, useSecure);
	}

	/**
	 * 设置post请求表单参数
	 * @param params 参数map集合
	 */
	private static List<NameValuePair> paramsConverter(
			Map<String, String> params) {
		List<NameValuePair> nvps = new LinkedList<NameValuePair>();
		Set<Entry<String, String>> paramsSet = params.entrySet();
		for (Entry<String, String> paramEntry : paramsSet) {
			nvps.add(new BasicNameValuePair(paramEntry.getKey(), paramEntry
					.getValue()));
		}
		return nvps;
	}

	/**
	 * 将流读取成字节数组
	 * @param in 输入流
	 */
	public static byte[] readToByte(InputStream in) {
		if (in == null) {
			return null;
		}
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream(bufferSize);
			byte[] temp = new byte[bufferSize];
			int size = 0;
			while ((size = in.read(temp)) != -1) {
				out.write(temp, 0, size);
			}
			byte[] bytes = out.toByteArray();
			return bytes;
		} catch (Exception e) {
			logger.error("读取返回内容出错", e);
		}
		return null;
	}

	/**
	 * 将流写入文件
	 * @param in 输入流
	 * @param localFilePath 文件地址
	 */
	public static void writeToFile(InputStream in, String localFilePath) {
		if (in == null) {
			return;
		}
		try {
			File file = new File(localFilePath);
			String localpath = "";
			if (localFilePath.lastIndexOf("/") > 0) {
				localpath = localFilePath.substring(0,
						localFilePath.lastIndexOf("/"));
			} else if (localFilePath.lastIndexOf("\\") > 0) {
				localpath = localFilePath.substring(0,
						localFilePath.lastIndexOf("\\"));
			}
			File f = new File(localpath);
			// 文件夹不存在时创建文件夹
			if (!f.exists()) {
				f.mkdirs();
			}
			BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream(file));
			byte[] buf = new byte[2048];
			int length = in.read(buf);
			while (length != -1) {
				out.write(buf, 0, length);
				length = in.read(buf);
			}
			out.close();
		} catch (Exception e) {
			logger.error("读取返回内容出错", e);
		}
	}

	/**
	 * 将流转成指定编码的字符串
	 * @param in 输入浄
	 * @param encoding 编码格式
	 */
	public static String readStream(InputStream in, String encoding) {
		if (in == null) {
			return null;
		}
		try {
			InputStreamReader inReader = null;
			if (encoding == null) {
				inReader = new InputStreamReader(in, defaultEncoding);
			} else {
				inReader = new InputStreamReader(in, encoding);
			}
			char[] buffer = new char[bufferSize];
			int readLen = 0;
			StringBuffer sb = new StringBuffer();
			while ((readLen = inReader.read(buffer)) != -1) {
				sb.append(buffer, 0, readLen);
			}
			inReader.close();
			return sb.toString();
		} catch (Exception e) {
			logger.error("读取返回内容出错", e);
		}
		return null;
	}

	public class AnyTrustStrategy implements TrustStrategy {
		public boolean isTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			return true;
		}

	}
}
