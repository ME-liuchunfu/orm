package xin.spring.base.database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @ClassName:  RowMapper   
 * @Description:TODO(这里用一句话描述这个类的作用)   jdbc 封装接口类，是一个泛型接口
 * @author: spring
 * @date:   2019年6月25日 下午2:31:09   
 * @param <T>  
 * @Copyright: 2019 wwww.sprringbless.xin Inc. All rights reserved.
 */
public interface RowMapper<T> {

	/**
	 * @decoration JDBC数据映射方法
	 * @param resultSet
	 * @return 返回泛型
	 * @throws SQLException
	 */
	T mapping(ResultSet resultSet) throws SQLException;

}
