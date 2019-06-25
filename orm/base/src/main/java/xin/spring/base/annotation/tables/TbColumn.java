package xin.spring.base.annotation.tables;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName:  TbColumn   
 * @Description:TODO(这里用一句话描述这个类的作用) 数据库表字段，作用于成员属性   
 * @author: spring
 * @date:   2019年6月25日 下午1:59:45     
 * @Copyright: 2019 wwww.sprringbless.xin Inc. All rights reserved.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TbColumn {

	/**
	 * @Title: value   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   数据库字段名称，作用于成员属性
	 * @return: String      
	 */
	String value();
	
}
