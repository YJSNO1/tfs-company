/**   
 * @Project: tfs-base 
 * @Title: LocalTable.java 
 * @Package com.tfstec.base.jdbc.annotation 
 * @Description: 用于定义Table对象 
 * @author lx 
 * @date 2016年6月28日 上午9:55:24 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.jdbc.annotation;

/** 
 * @ClassName LocalTable  
 * @Description 用于定义Table对象 
 * @author lx 
 * @date 2016年6月28日  
 *   
 */
@java.lang.annotation.Target(value = { java.lang.annotation.ElementType.TYPE })
@java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface LocalTable {
	public abstract java.lang.String name() default "";

	public abstract java.lang.String catalog() default "";

	public abstract java.lang.String schema() default "";

	public abstract java.lang.String sequence() default "";
}
