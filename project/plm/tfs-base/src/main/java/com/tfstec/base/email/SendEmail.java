/**   
 * @Project: tfscore 
 * @Title: SendEmail.java 
 * @Package com.tfscore.email 
 * @Description: 发送邮件工具 
 * @author lx 
 * @date 2016年6月5日 下午1:03:17 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.email;


/** 
 * @ClassName SendEmail  
 * @Description 发送邮件工具 
 * @author lx 
 * @date 2016年6月5日  
 *   
 */
public class SendEmail {
	/** 
	 * @Title: sendEmail 
	 * @Description: 发送邮件 
	 * @param smtp  邮件协议
	 * @param fromAddress 发送人地址
	 * @param fromPass 发送人密码
	 * @param toAddress 收件人地址
	 * @param subject 发送主题
	 * @param content 发送内容
	 * @return 参数说明
	 * @return boolean    返回类型
	 */
	public static boolean sendEmail(String smtp,String fromAddress,String fromPass, String toAddress,String subject,String content) {
		return EmailUtils.send(smtp, fromAddress, fromPass, toAddress, subject, content);
	}
}
