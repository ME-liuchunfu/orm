package xin.spring.base.annotation.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName:  JsonChar   
 * @Description:TODO(这里用一句话描述这个类的作用)  Char类型   
 * @author: spring
 * @date:   2019年6月26日 上午12:29:29     
 * @Copyright: 2019 wwww.sprringbless.xin Inc. All rights reserved.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface JsonChar {

	String value();
}
