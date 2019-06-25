package xin.spring.base.orm;

import xin.spring.base.exception.BeanInitException;
import xin.spring.base.params.OrmParams;

/**
 * @ClassName:  QuerySqlFactory   
 * @Description:TODO(这里用一句话描述这个类的作用) sql查询工厂   
 * @author: spring
 * @date:   2019年6月25日 下午7:54:03     
 * @Copyright: 2019 wwww.sprringbless.xin Inc. All rights reserved.
 */
public interface QuerySqlFactory {


	/**
	 * <p>查询数据</p>   
	 * @param sql
	 * @param params
	 * @param clazz
	 * @throws Exception   
	 * @see xin.spring.base.orm.QuerySqlFactory#query(java.lang.String, java.lang.Object[], java.lang.Class)
	 */
	<T> T query(String sql, Object[] params, Class<T> clazz) throws Exception;
	
	/**
	 * @Title: queryById   
	 * @Description: TODO(这里用一句话描述这个方法的作用)    查询单个实体byID
	 * @param: @param value 值
	 * @param: @param clazz 类型
	 * @param: @throws Exception      
	 * @return: T      
	 */
	<T> T queryById(Object value, Class<T> clazz) throws Exception;
	
	/**
	 * @param <T>
	 * @Title: insert   
	 * @Description: TODO(这里用一句话描述这个方法的作用)  新增记录 
	 * @param: @param params 实体
	 * @param: @param clazz 反射类型
	 * @return: boolean  true 成功： false：失败
	 */
	<T> boolean insert(OrmParams params, Class<T> clazz) throws BeanInitException;
	
	/**
	 * @param <T>
	 * @Title: insert   
	 * @Description: TODO(这里用一句话描述这个方法的作用)  新增记录 
	 * @param: @param t 实体
	 * @return: boolean  true 成功： false：失败
	 */
	<T> boolean insert(T t) throws BeanInitException;
	
}
