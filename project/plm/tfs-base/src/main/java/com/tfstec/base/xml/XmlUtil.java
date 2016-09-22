/**   
 * @Project: tfscore 
 * @Title: XmlUtil.java 
 * @Package com.tfscore.xml 
 * @Description: xml及bean转换解析功能类 
 * @author lx 
 * @date 2016年6月6日 上午12:27:05 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/** 
 * @ClassName XmlUtil  
 * @Description xml及bean转换解析功能类 
 * @author lx 
 * @date 2016年6月6日  
 *   
 */
public class XmlUtil {
	private static Logger logger = LoggerFactory.getLogger(XmlUtil.class);

	/**
	 * @Title: getTomcatPath 
	 * @Description: 根据相对路径得到Tomcat运行绝对路径
	 * @param path  相对路径
	 * @return String    返回类型
	 */
	public static String getTomcatPath(String path) {
		String nowPath;
		String tempPath;
		nowPath = System.getProperty("user.dir");
		tempPath = nowPath.replace("bin", "webapps");
		if (tempPath.contains("webapps")) {
			tempPath += "/" + path;
		} else {
			tempPath += "/webapps/" + path;
		}
		return tempPath;
	}

	/**
	* @Title: toBean 
	* @Description: 将传入xml文本转换成Java对象 
	* @param xmlStr xml文本
	* @param cls  xml对应的class类
	* @return T   xml对应的class类的实例对象
	*/
	@SuppressWarnings("unchecked")
	public static <T> T toBean(String xmlStr, Class<T> cls) {
		XStream xstream = new XStream(new DomDriver());
		xstream.processAnnotations(cls);
		T obj = (T) xstream.fromXML(xmlStr);
		return obj;
	}

	/**
	 * @Title: toBeanFromFile 
	 * @Description: 读取xml文件转换成Java对象  
	 * @param filePath 文件绝对路径
	 * @param cls xml对应的class类
	 * @return T  xml对应的class类的实例对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toBeanFromFile(String filePath, Class<T> cls) {
		InputStream ins = null;
		T obj = null;
		try {
			ins = new FileInputStream(new File(filePath));
			XStream xstream = new XStream(new DomDriver("UTF-8"));
			xstream.processAnnotations(cls);
			obj = (T) xstream.fromXML(ins);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.error("读" + filePath + "文件失败！");
		} finally {
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException e) {
					logger.error("读取xml文件转换成Java对象关闭文件流出现异常：" + e);
				}
			}
		}
		return obj;
	}

	/** 
	 * @Title: parserNCIICXp 
	 * @Description: 解析从公安部获取的图片信息 
	 * @param xmlString 获取的原xml文件
	 * @param labelName 标签名称
	 * @return
	 * @throws DocumentException 参数说明
	 * @return String    返回类型
	 */
	public static String parserNCIICXp(String xmlString, String labelName) throws DocumentException {
		Document document = DocumentHelper.parseText(xmlString);
		List<Element> listElement = document.selectNodes("//ROWS//ROW//OUTPUT//ITEM");

		for (int i = 0; i < listElement.size(); i++) {
			for (Iterator iterator = listElement.get(i).elementIterator(); iterator.hasNext();) {
				Element element = (Element) iterator.next();
				if (labelName.equalsIgnoreCase(element.getName())) {
					return element.getText();
				}
			}
		}
		return "";
	}
}
