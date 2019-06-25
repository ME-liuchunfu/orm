package xin.spring.base.orm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import xin.spring.base.exception.BeanInitException;
import xin.spring.base.exception.DBException;
import xin.spring.base.params.OrmParams;
import xin.spring.base.reflect.ObjectReflect;
import xin.spring.base.util.StringUtils;

/**
 * @ClassName:  Query   
 * @Description:TODO(这里用一句话描述这个类的作用) 数据查询对象   
 * @author: spring
 * @date:   2019年6月25日 下午5:03:50     
 * @Copyright: 2019 wwww.sprringbless.xin Inc. All rights reserved.
 */
public class Query extends BaseOrm implements QuerySqlFactory {
	
	protected static final Logger LOGGER = LogManager.getLogger(Query.class);
	
	public <T> T query(String sql, Object[] params, Class<T> clazz) throws Exception {
		if(StringUtils.isNull(sql)){
			throw new DBException("您的sql语句为空，请仔细查看");
		}
		if(clazz == null){
			throw new DBException("您的java反射类型为空，请仔细查看");
		}
		LOGGER.info("SQL: == > " + sql.toString());
		LOGGER.info("params : == > " + Arrays.toString(params));
		return queryObject(sql, params, clazz);
	}

	public <T> T queryById(Object value, Class<T> clazz) throws Exception {
		if(value == null){
			throw new DBException("您的value值句为空，请仔细查看");
		}
		if(clazz == null){
			throw new DBException("您的java反射类型为空，请仔细查看");
		}
		Object[] params = new Object[]{value};
		StringBuffer sql = new StringBuffer(SELECT);
		
		OrmParams column = new OrmParams(clazz);
		sql.append( column.get(OrmParams.TABLE_NAME_KEY) );
		sql.append(" WHERE " + ObjectReflect.getTableId(clazz) + " = ? ");
		
		LOGGER.info("SQL: == > " + sql.toString());
		LOGGER.info("params : == > " + Arrays.toString(params));
		return queryObject(sql.toString(), params, clazz);
	}

	public <T> boolean insert(OrmParams params, Class<T> clazz) throws BeanInitException {
		OrmParams column = new OrmParams(clazz);
		StringBuffer sql = new StringBuffer(INSERT + column.get(OrmParams.TABLE_NAME_KEY) );
		Object[] object = setColumn(sql, column, params);
		LOGGER.info("SQL: == > " + sql.toString());
		LOGGER.info("params : == > " + Arrays.toString(object));
		return getBoolean(toUpdate(sql.toString(), object));
	}

	/**
	 * @Title: setColumn   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   设置字段语句
	 * @param: @param sql
	 * @param: @param column      
	 * @return: void      
	 */
	private Object[] setColumn(StringBuffer sql, OrmParams column, OrmParams params) {
		column.remove(OrmParams.TABLE_NAME_KEY);
		List<Object> list = new ArrayList<Object>();
		StringBuffer tempVal = new StringBuffer(" ( ");
		sql.append(" ( ");
		Set<String> keySet = column.keySet();
		for(String s : keySet){
			Object obj = params.get(s);
			if(obj != null){
				sql.append(column.get(s) + ", ");
				list.add(obj);
				tempVal.append(" ?, ");
			}
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		sql.append(" ) ");
		sql.append(" VALUES ");
		
		tempVal.deleteCharAt(tempVal.lastIndexOf(","));
		tempVal.append(" ) ");
		sql.append(tempVal);
		return list.toArray();
	}

	public <T> boolean insert(T t) throws BeanInitException {
		OrmParams column = new OrmParams(t.getClass());
		StringBuffer sql = new StringBuffer(INSERT + column.get(OrmParams.TABLE_NAME_KEY) );
		Object[] object = null;
		try {
			object = setColumn(sql, column, new OrmParams(t));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		LOGGER.info("SQL: == > " + sql.toString());
		LOGGER.info("params : == > " + Arrays.toString(object));
		return getBoolean(toUpdate(sql.toString(), object));
	}

	public <T> List<T> queryList(Class<T> clazz) throws BeanInitException {
		StringBuffer sql = new StringBuffer(SELECT);
		OrmParams column = new OrmParams(clazz);
		sql.append( column.get(OrmParams.TABLE_NAME_KEY) );
		sql.append(" WHERE 1=1 ");
		
		LOGGER.info("SQL: == > " + sql.toString());
		try {
			return queryAll(sql.toString(), null, clazz);
		} catch (DBException e) {
			e.printStackTrace();
		}
		return null;
	}

	public <T> List<T> queryList(DataRow<T> dataRow, OrmParams params, Class<T> clazz) throws BeanInitException {
		StringBuffer sql = new StringBuffer(SELECT);
		OrmParams column = new OrmParams(clazz);
		sql.append( column.get(OrmParams.TABLE_NAME_KEY) );
		sql.append(" WHERE 1=1 ");
		
		LOGGER.info("SQL: == > " + sql.toString());
		try {
			return queryAll(sql.toString(), null, clazz);
		} catch (DBException e) {
			e.printStackTrace();
		}
		return null;
	}

	public <T> DataRow<T> queryList(String sql, DataRow<T> dataRow, Object[] params, Class<T> clazz)
			throws BeanInitException, DBException {
		int totle = getCountTotal("SELECT COUNT(*) AS total FROM ( " + sql + " ) template ", params);
		dataRow.setTotle(totle);
		Object[] object = null;
		
		if(params != null){
			object = new Object[params.length + 2];
			for(int i = 0; i<params.length; i++){
				object[i] = params[i];
			}
			object[params.length] = dataRow.getStart() - 1;
			object[params.length + 1] = dataRow.getSize();
		}else{
			object = new Object[2];
			object[0] = dataRow.getStart() - 1;
			object[1] = dataRow.getSize();
		}
		List<T> list = queryAll(sql + " LIMIT ?,? ", object, clazz);
		dataRow.setDatas(list);
		dataRow.setStart(dataRow.getStart() + 1);
		return dataRow;
	}

}
