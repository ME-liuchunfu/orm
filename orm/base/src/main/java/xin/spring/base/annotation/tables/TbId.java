package xin.spring.base.annotation.tables;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName:  TbId   
 * @Description:TODO(这里用一句话描述这个类的作用)  数据库表id,只有一个，作用在成员属性上   
 * @author: spring
 * @date:   2019年6月25日 下午1:54:11     
 * @Copyright: 2019 wwww.sprringbless.xin Inc. All rights reserved.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TbId {
	
	/**
	 * @Title: value   
	 * @Description: TODO(这里用一句话描述这个方法的作用) 字段id名   
	 * @return: String      
	 */
	String value() default "id";

}
