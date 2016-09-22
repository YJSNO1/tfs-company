/**   
 * @Project: tfscore 
 * @Title: ConfigLoader.java 
 * @Package com.tfscore.config 
 * @Description: 读取系统默认配置信息 
 * @author lx 
 * @date 2016年6月5日 下午12:13:24 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.config;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/** 
 * @ClassName ConfigLoader  
 * @Description 读取系统默认配置信息 
 * @author lx 
 * @date 2016年6月5日  
 *   
 */
public class ConfigLoader {
	private static final Logger logger = Logger.getLogger(ConfigLoader.class);

	private static final ConfigLoader INSTANCE = new ConfigLoader();

	// 系统默认配置文件名称
	private String configPath = "config.properties";

	private Properties configuration = null;

	public static final ConfigLoader getInstance() {
		return INSTANCE;
	}

	public static final ConfigLoader getInstance(String configPath) {
		return new ConfigLoader(configPath);
	}

	private ConfigLoader(String configPath) {
		this.configuration = load(configPath);
	}

	private ConfigLoader() {
		this.configuration = load(this.configPath);
	}

	private Properties load(String configPath) {
		Properties configuration = null;

		if (StringUtils.isBlank(configPath)) {
			try {
				Properties prop = System.getProperties();
				Object fileName = prop.get("sys.config.name");
				if ((fileName != null) && (!"".equals((String) fileName))) {
					logger.info("通过环境变量获取配置信息>>>>" + (String) fileName);
					configPath = (String) fileName;
				} else {
					configPath = this.configPath;
				}
			} catch (Exception e) {
				logger.error("配置文件不存在，请检查系统参数[sys.config.name]的配置。", e);
			}
		}

		InputStream is = ConfigLoader.class.getClassLoader().getResourceAsStream(configPath);
		try {
			configuration = new Properties();
			configuration.load(is);
			logger.info("Loading " + configPath + " success");
		} catch (Exception e) {
			logger.error("Loading " + configPath + " error ", e);
			System.exit(-1);
		}

		return configuration;
	}

	public String getValue(String key) {
		return this.configuration.getProperty(key);
	}

	public int getIntValue(String key) {
		String valueStr = this.configuration.getProperty(key);
		try {
			return Integer.parseInt(valueStr);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return -1;
	}

	public int getIntValue(String key, int defaultValue) {
		String valueStr = this.configuration.getProperty(key);
		if (StringUtils.isEmpty(valueStr))
			return defaultValue;
		try {
			return Integer.parseInt(valueStr);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return defaultValue;
	}

	public double getDoubleValue(String key) {
		String valueStr = this.configuration.getProperty(key);
		try {
			return Double.parseDouble(valueStr);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return -1.0D;
	}

	public long getLongValue(String key) {
		String valueStr = this.configuration.getProperty(key);
		try {
			return Long.parseLong(valueStr);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return -1L;
	}
}
