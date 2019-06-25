package xin.spring.base.annotation.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName:  JsonInt   
 * @Description:TODO(这里用一句话描述这个类的作用)   Integer数据类型注解
 * @author: spring
 * @date:   2019年6月26日 上午12:27:02     
 * @Copyright: 2019 wwww.sprringbless.xin Inc. All rights reserved.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface JsonInt {

	String value();
	
}
