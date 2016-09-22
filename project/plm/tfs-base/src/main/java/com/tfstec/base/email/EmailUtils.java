/**   
 * @Project: tfscore 
 * @Title: EmailUtils.java 
 * @Package com.tfscore.email 
 * @Description: 邮件发送工具 
 * @author lx 
 * @date 2016年6月5日 下午12:49:32 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.email;

import java.util.List;
import java.util.StringTokenizer;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tfstec.base.StringUtils;

/** 
 * @ClassName EmailUtils  
 * @Description 邮件发送工具 
 * @author lx 
 * @date 2016年6月5日  
 *   
 */
public class EmailUtils {
	private static Logger logger = LoggerFactory.getLogger(EmailUtils.class);

	/**
	 * 发送邮件
	 * @Title: send 
	 * @Description: 发送邮件
	 * @param smtp        邮件协议
	 * @param fromAddress 发送人地址
	 * @param fromPass    发送人密码
	 * @param toaddress   收件人地址
	 * @param subject     发送主题
	 * @param content     发送内容
	 * @return void    返回类型
	 */
	public static boolean send(String smtp, String fromAddress, String fromPass, String toAddress,
			String subject, String content) {
		try {
			logger.info("开始向" + toAddress + "发送邮件");
			EmailHandle emailHandle = new EmailHandle(smtp);
			emailHandle.setFrom(fromAddress);
			emailHandle.setNeedAuth(true);
			emailHandle.setSubject(subject);
			emailHandle.setBody(content);
			emailHandle.setTo(toAddress);
			emailHandle.setFrom(fromAddress);
			emailHandle.setNamePass(fromAddress, fromPass);
			emailHandle.sendEmail();
			logger.info("邮件发送结束!");
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 发送邮件 可抄送
	 * @Title: send 
	 * @Description: 发送邮件 可抄送 
	 * @param smtp        邮件协议
	 * @param fromAddress 发送人地址
	 * @param fromPass    发送人密码
	 * @param toAddress   收件人地址
	 * @param ccAdress    抄送人地址
	 * @param subject     发送主题
	 * @param content     发送内容
	 * @return void    返回类型
	 */
	public static void send(String smtp, String fromAddress, String fromPass, String toAddress,
			String ccAdress, String subject, String content) {
		try {
			logger.info("开始向" + toAddress + "发送邮件");
			EmailHandle emailHandle = new EmailHandle(smtp);
			emailHandle.setFrom(fromAddress);
			emailHandle.setNeedAuth(true);
			emailHandle.setSubject(subject);
			emailHandle.setBody(content);
			emailHandle.setTo(toAddress);
			/**添加抄送**/
			if (StringUtils.isNotEmpty(ccAdress)) {
				emailHandle.setCopyTo(ccAdress);
			}
			emailHandle.setFrom(fromAddress);
			emailHandle.setNamePass(fromAddress, fromPass);
			emailHandle.sendEmail();
			logger.info("邮件发送结束!");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 给两个个人发送
	 * @Title: send 
	 * @Description: 给两个人发送，如果要给多个人，那么可以考虑用;拼接，重写获取地址方法 
	 * @param smtp
	 * @param fromAddress
	 * @param fromPass
	 * @param toAddress
	 * @param toAddress2
	 * @param ccAdress
	 * @param subject
	 * @param content 参数说明
	 * @return void    返回类型
	 */
	public static void send(String smtp, String fromAddress, String fromPass, String toAddress,
			String toAddress2, String ccAdress, String subject, String content) {
		try {
			logger.info("开始向" + toAddress + "发送邮件");
			EmailHandle emailHandle = new EmailHandle(smtp);
			emailHandle.setFrom(fromAddress);
			emailHandle.setNeedAuth(true);
			emailHandle.setSubject(subject);
			emailHandle.setBody(content);
			InternetAddress[] adds = parseAddress(toAddress + ";" + toAddress2);
			emailHandle.setTos(adds);
			/**添加抄送**/
			if (StringUtils.isNotEmpty(ccAdress)) {
				emailHandle.setCopyTo(ccAdress);
			}
			emailHandle.setFrom(fromAddress);
			emailHandle.setNamePass(fromAddress, fromPass);
			emailHandle.sendEmail();
			logger.info("邮件发送结束!");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 生成多个收件人的地址数组
	 * @Title: parseAddress 
	 * @Description: 生成多个收件人的地址数组
	 * @param addr
	 * @return 参数说明
	 * @return InternetAddress[]    返回类型
	 */
	public static InternetAddress[] parseAddress(String addr) {
		StringTokenizer token = new StringTokenizer(addr, ";");
		InternetAddress[] addrArr = new InternetAddress[token.countTokens()];
		int i = 0;
		while (token.hasMoreTokens()) {
			try {
				addrArr[i] = new InternetAddress(token.nextToken().toString());
			} catch (AddressException e1) {
				return null;
			}
			i++;
		}
		return addrArr;
	}

	/**
	 * 发送邮件 不抄送可附件
	 * @Title: send 
	 * @Description: 发送邮件 可附件
	 * @param smtp        邮件协议
	 * @param fromAddress 发送人地址
	 * @param fromPass    发送人密码
	 * @param toaddress   收件人地址
	 * @param subject     发送主题
	 * @param content     发送内容
	 * @param fileList 	    附件
	 * @return void    返回类型
	 */
	public static void send(String smtp, String fromAddress, String fromPass, String toAddress,
			String subject, String content, List<String> fileList) {
		try {
			logger.info("开始向" + toAddress + "发送邮件");
			EmailHandle emailHandle = new EmailHandle(smtp);
			emailHandle.setFrom(fromAddress);
			emailHandle.setNeedAuth(true);
			emailHandle.setSubject(subject);
			emailHandle.setBody(content);
			emailHandle.setTo(toAddress);
			emailHandle.setFrom(fromAddress);
			emailHandle.setNamePass(fromAddress, fromPass);
			/** 附件文件路径 **/
			for (String file : fileList) {
				emailHandle.addFileAffix(file);
			}
			emailHandle.sendEmail();
			logger.info("邮件发送结束!");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 发送邮件 可抄送 可附件
	 * @Title: send 
	 * @Description: 发送邮件 可抄送 可附件
	 * @param smtp        邮件协议
	 * @param fromAddress 发送人地址
	 * @param fromPass    发送人密码
	 * @param toAddress   收件人地址
	 * @param ccAdress    抄送人地址
	 * @param subject     发送主题
	 * @param content     发送内容
	 * @param fileList 	      附件
	 * @return void    返回类型
	 */
	public static void send(String smtp, String fromAddress, String fromPass, String toAddress,
			String ccAdress, String subject, String content, List<String> fileList) {
		try {
			logger.info("开始向" + toAddress + "发送邮件");
			EmailHandle emailHandle = new EmailHandle(smtp);
			emailHandle.setFrom(fromAddress);
			emailHandle.setNeedAuth(true);
			emailHandle.setSubject(subject);
			emailHandle.setBody(content);
			emailHandle.setTo(toAddress);
			/**添加抄送**/
			if (StringUtils.isNotEmpty(ccAdress)) {
				emailHandle.setCopyTo(ccAdress);
			}
			emailHandle.setFrom(fromAddress);
			emailHandle.setNamePass(fromAddress, fromPass);
			/** 附件文件路径 **/
			for (String file : fileList) {
				emailHandle.addFileAffix(file);
			}
			emailHandle.sendEmail();
			logger.info("邮件发送结束!");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void main(String[] args) {
		String ip = "172.16.102.90";
		String pwd = "liaoxi3#";
		String reer = "liaogd@yanxintec.com";
		send(ip, reer, pwd, "mozf@yanxintec.com", "测试", "测试");
	}
}
