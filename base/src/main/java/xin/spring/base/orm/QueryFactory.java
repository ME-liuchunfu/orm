package xin.spring.base.orm;

/**
 * @ClassName:  QueryFactory   
 * @Description:TODO(这里用一句话描述这个类的作用)   数据持久层工厂
 * @author: spring
 * @date:   2019年6月26日 上午12:09:45     
 * @Copyright: 2019 wwww.sprringbless.xin Inc. All rights reserved.
 */
public abstract class QueryFactory {

	/**
	 * @Title: getQueryInstance   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   持久层sql实例
	 * @param: @throws InstantiationException
	 * @param: @throws IllegalAccessException      
	 * @return: QuerySqlFactory      
	 */
	public static final QuerySqlFactory getQueryInstance(){
		try {
			return Query.class.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
