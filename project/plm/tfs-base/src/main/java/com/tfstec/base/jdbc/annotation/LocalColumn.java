/**   
 * @Project: tfs-base 
 * @Title: LocalColumn.java 
 * @Package com.tfstec.base.jdbc.annotation 
 * @Description: 用于定义字段
 * @author lx 
 * @date 2016年6月28日 上午9:53:47 
 * @Copyright: 2016 年 前海阿拉海钜科技. All rights reserved  
 * @version V1.0   
 */
package com.tfstec.base.jdbc.annotation;

/** 
 * @ClassName LocalColumn  
 * @Description 用于定义字段 
 * @author lx 
 * @date 2016年6月28日  
 *   
 */
@java.lang.annotation.Target(value = { java.lang.annotation.ElementType.FIELD })
@java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface LocalColumn {
	public abstract java.lang.String name() default "";

	public abstract java.lang.String type() default "";
}
