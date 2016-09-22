/**   
 * @Project: InterfaceCore 
 * @Title: CertificateBean.java 
 * @Package com.tfstec.interfacecore.pojo 
 * @Description: 证书对象 
 * @author lx 
 * @date 2016年6月16日 下午3:04:34 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.pojo;

/** 
 * @ClassName CertificateBean  
 * @Description 证书对象 
 * @author lx 
 * @date 2016年6月16日  
 *   
 */
public class CertificateBean {

	private String keyStoreTypeJks = "jks";
	private String keyStoreTypeP12 = "PKCS12";

	private String keyStoreClientPath;
	private String keyStoreTrustPath;
	private String keyStorePassword;
	private String keyStoreTrustPassword;
	
	 
	/** 
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param keyStoreClientPath
	 * @param keyStoreTrustPath
	 * @param keyStorePassword
	 * @param keyStoreTrustPassword 
	 */ 
	
	public CertificateBean(String keyStoreClientPath, String keyStoreTrustPath,
			String keyStorePassword, String keyStoreTrustPassword) {
		super();
		this.keyStoreClientPath = keyStoreClientPath;
		this.keyStoreTrustPath = keyStoreTrustPath;
		this.keyStorePassword = keyStorePassword;
		this.keyStoreTrustPassword = keyStoreTrustPassword;
	}
	
	/** 
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param keyStoreTypeJks
	 * @param keyStoreTypeP12
	 * @param keyStoreClientPath
	 * @param keyStoreTrustPath
	 * @param keyStorePassword
	 * @param keyStoreTrustPassword 
	 */ 
	
	public CertificateBean(String keyStoreTypeJks, String keyStoreTypeP12,
			String keyStoreClientPath, String keyStoreTrustPath,
			String keyStorePassword, String keyStoreTrustPassword) {
		super();
		this.keyStoreTypeJks = keyStoreTypeJks;
		this.keyStoreTypeP12 = keyStoreTypeP12;
		this.keyStoreClientPath = keyStoreClientPath;
		this.keyStoreTrustPath = keyStoreTrustPath;
		this.keyStorePassword = keyStorePassword;
		this.keyStoreTrustPassword = keyStoreTrustPassword;
	}

	@Override
	public String toString() {
		return "CertificateBean [keyStoreTypeJks=" + keyStoreTypeJks
				+ ", keyStoreTypeP12=" + keyStoreTypeP12
				+ ", keyStoreClientPath=" + keyStoreClientPath
				+ ", keyStoreTrustPath=" + keyStoreTrustPath
				+ ", keyStorePassword=" + keyStorePassword
				+ ", keyStoreTrustPassword=" + keyStoreTrustPassword + "]";
	}

	public String getKeyStoreTypeJks() {
		return keyStoreTypeJks;
	}
	public void setKeyStoreTypeJks(String keyStoreTypeJks) {
		this.keyStoreTypeJks = keyStoreTypeJks;
	}
	public String getKeyStoreTypeP12() {
		return keyStoreTypeP12;
	}
	public void setKeyStoreTypeP12(String keyStoreTypeP12) {
		this.keyStoreTypeP12 = keyStoreTypeP12;
	}
	public String getKeyStoreClientPath() {
		return keyStoreClientPath;
	}
	public void setKeyStoreClientPath(String keyStoreClientPath) {
		this.keyStoreClientPath = keyStoreClientPath;
	}
	public String getKeyStoreTrustPath() {
		return keyStoreTrustPath;
	}
	public void setKeyStoreTrustPath(String keyStoreTrustPath) {
		this.keyStoreTrustPath = keyStoreTrustPath;
	}
	public String getKeyStorePassword() {
		return keyStorePassword;
	}
	public void setKeyStorePassword(String keyStorePassword) {
		this.keyStorePassword = keyStorePassword;
	}
	public String getKeyStoreTrustPassword() {
		return keyStoreTrustPassword;
	}
	public void setKeyStoreTrustPassword(String keyStoreTrustPassword) {
		this.keyStoreTrustPassword = keyStoreTrustPassword;
	}
}
