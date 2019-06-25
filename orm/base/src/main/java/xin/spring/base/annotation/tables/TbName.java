package xin.spring.base.annotation.tables;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName:  TbName   
 * @Description:TODO(这里用一句话描述这个类的作用) 数据库表名称，作用于类   
 * @author: spring
 * @date:   2019年6月25日 下午2:01:42     
 * @Copyright: 2019 wwww.sprringbless.xin Inc. All rights reserved.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TbName {
	
	/**
	 * @Title: value   
	 * @Description: TODO(这里用一句话描述这个方法的作用)  数据库表名称，作用于类   
	 * @return: String      
	 */
	String value();

}
