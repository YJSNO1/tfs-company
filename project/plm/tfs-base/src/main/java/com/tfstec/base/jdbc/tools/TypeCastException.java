/**   
 * @Project: tfs-base 
 * @Title: TypeCastException.java 
 * @Package com.tfstec.base.jdbc.tools 
 * @Description: 类型转换工具异常类 
 * @author lx 
 * @date 2016年6月28日 下午2:00:43 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.jdbc.tools;

import java.io.PrintStream;
import java.io.PrintWriter;

/** 
 * @ClassName TypeCastException  
 * @Description 类型转换工具异常类 
 * @author lx 
 * @date 2016年6月28日  
 *   
 */
public class TypeCastException extends RuntimeException {
	Throwable nested;

	public TypeCastException() {
		nested = null;
	}

	public TypeCastException(String msg) {
		super(msg);
		nested = null;
	}

	public TypeCastException(String msg, Throwable nested) {
		super(msg);
		this.nested = null;
		this.nested = nested;
	}

	public TypeCastException(Throwable nested) {
		this.nested = null;
		this.nested = nested;
	}

	public String getMessage() {
		if (nested != null) {
			return super.getMessage() + " (" + nested.getMessage() + ")";
		} else {
			return super.getMessage();
		}
	}

	public String getNonNestedMessage() {
		return super.getMessage();
	}

	public Throwable getNested() {
		if (nested == null) {
			return this;
		} else {
			return nested;
		}
	}

	public void printStackTrace() {
		super.printStackTrace();
		if (nested != null) {
			nested.printStackTrace();
		}
	}

	public void printStackTrace(PrintStream ps) {
		super.printStackTrace(ps);
		if (nested != null) {
			nested.printStackTrace(ps);
		}
	}

	public void printStackTrace(PrintWriter pw) {
		super.printStackTrace(pw);
		if (nested != null) {
			nested.printStackTrace(pw);
		}
	}
}
