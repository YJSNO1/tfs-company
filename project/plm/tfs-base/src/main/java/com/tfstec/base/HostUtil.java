/**   
 * @Project: tfscore 
 * @Title: HostUtil.java 
 * @Package com.tfscore 
 * @Description: 获取IP地址 
 * @author lx 
 * @date 2016年6月5日 下午12:56:18 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @ClassName HostUtil  
 * @Description 获取IP地址 
 * @author lx 
 * @date 2016年6月5日  
 *   
 */
public class HostUtil {
	private static Logger logger = LoggerFactory.getLogger(HostUtil.class);
	public static String localHost = getLocalHost();

	/**
	 * @Title: getLocalHost 
	 * @Description: 获取本机ip地址，并自动区分Windows还是linux操作系统
	 * @return 参数说明
	 * @return String    返回类型
	 */
	private static String getLocalHost() {
		String sIP = null;
		InetAddress ip = null;
		try {
			// 如果是Windows操作系统
			if (isWindowsOS()) {
				ip = InetAddress.getLocalHost();
			} else {
				// 如果是Linux操作系统
				boolean bFindIP = false;
				Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface
						.getNetworkInterfaces();
				while (netInterfaces.hasMoreElements()) {
					if (bFindIP) {
						break;
					}
					NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
					// ----------特定情况，可以考虑用ni.getName判断
					// 遍历所有ip
					Enumeration<InetAddress> ips = ni.getInetAddresses();
					while (ips.hasMoreElements()) {
						ip = (InetAddress) ips.nextElement();
						if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() // 127.开头的都是lookback地址
								&& ip.getHostAddress().indexOf(":") == -1) {
							bFindIP = true;
							break;
						}
					}

				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		if (null != ip) {
			sIP = ip.getHostAddress();
		}
		return sIP;
	}

	/**
	 * 获得真实的ip
	 * @Title: getClientHost 
	 * @param request
	 */
	public static String getClientHost(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	}

	/** 
	 * @Title: isWindowsOS 
	 * @Description: 判断是否是windows操作系统 
	 * @return 参数说明
	 * @return boolean    返回类型
	 */
	public static boolean isWindowsOS() {
		boolean isWindowsOS = false;
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().indexOf("windows") > -1) {
			isWindowsOS = true;
		}
		return isWindowsOS;
	}
}
