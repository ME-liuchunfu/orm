package xin.spring.base.database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @ClassName:  EntityMapper   
 * @Description:TODO(这里用一句话描述这个类的作用)    jdbc 封装实体映射接口类，是一个泛型接口
 * @author: spring
 * @date:   2019年6月25日 下午2:30:55   
 * @param <T>  
 * @Copyright: 2019 wwww.sprringbless.xin Inc. All rights reserved.
 */
public interface EntityMapper<T> {

	/**
	 * 实体映射方法
	 * @param object
	 * @param resultSet
	 * @throws SQLException
	 */
	T getEntity(ResultSet resultSet) throws SQLException;
	
}
