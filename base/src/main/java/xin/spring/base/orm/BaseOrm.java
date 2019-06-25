package xin.spring.base.orm;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import xin.spring.base.database.DataBaseConnection;
import xin.spring.base.database.EntityMapper;
import xin.spring.base.database.RowMapper;
import xin.spring.base.exception.DBException;
import xin.spring.base.reflect.ObjectReflect;

/**
 * @ClassName:  BaseDao   
 * @Description:TODO(这里用一句话描述这个类的作用)   基础数据层封装
 * @author: spring
 * @date:   2019年6月25日 下午2:32:44     
 * @Copyright: 2019 wwww.sprringbless.xin Inc. All rights reserved.
 */
@SuppressWarnings("unchecked")
public abstract class BaseOrm {

	private static Connection connection;
	private static PreparedStatement preparedStatement;
	private static ResultSet resultSet;
	protected static final String SELECT = " SELECT * FROM ";
	protected static final String DELETE = " DELETE FROM ";
	protected static final String INSERT = " INSERT INTO ";
	protected static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * Gson解析
	 */
	protected static Gson GSON = new GsonBuilder().setDateFormat(DATE_FORMAT).create();
	
	/**
	 * 设置preparedStatement的sql语句值
	 * @param pStatement
	 * @param object
	 * @throws SQLException
	 */
	protected static void setProperty(PreparedStatement pStatement, Object[] object) throws SQLException{
		if(pStatement != null && object != null && object.length > 0){
			for(int i = 0; i < object.length; i++){
				pStatement.setObject(i + 1, object[i]);
			}
		}
	}
	
	/**
	 * @Title: initSqlParams   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   sql 链接
	 * @return: void      
	 */
	protected static int initSqlParams(String sql, Object[] object, boolean autoCommit) {
		int totle = 0;
		try {
			connection = DataBaseConnection.getConnection();
			if(autoCommit){
				connection.setAutoCommit(false);
			}
			preparedStatement = connection.prepareStatement(sql);
			setProperty(preparedStatement, object);
			if(!autoCommit){
				resultSet = preparedStatement.executeQuery();
			}else{
				totle = preparedStatement.executeUpdate();
				connection.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return totle;
	}
	
	/**
	 * @Title: getCountTotal   
	 * @Description: TODO(这里用一句话描述这个方法的作用)  查询总记录数 
	 */
	protected int getCountTotal(String countSql, Object[] object){
		Integer total = (Integer) queryObject(new EntityMapper<Integer>() {
			public Integer getEntity(ResultSet resultSet) throws SQLException {
				Integer total = resultSet.getInt("total");
				return total;
			}
		}, countSql, object);
		if(total == null){
			total = 0;
		}
		return total;
	}
	
	/**
	 * toUpdate();增删改操作
	 * @param sql
	 * @param object
	 * @return int 影响记录条数
	 */
	protected static int toUpdate(String sql, Object[] object){
		int i = 0;
		try{
			i  = initSqlParams(sql, object, true);
			return i;
		}finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DataBaseConnection.closeAll(connection, preparedStatement, resultSet);
		}
	}
	
	/**
	 * 判断真假
	 * @param msg
	 * @return true or false
	 */
	protected static boolean getBoolean(int msg){
		return (msg > 0)? true:false;
	}
	
	/**
	 * 通过匿名内部类实现数据库连接和执行业务, 如想使用类加载器解析请使用同名重载函数
	 * @param mapper
	 * @param sql
	 * @param object
	 * @return <T> List<T>
	 */
	protected static <T> List<T> queryAll(RowMapper<T> mapper, String sql, Object[] object){
		List<T> arrayList = null;
		try {
			initSqlParams(sql, object, false);
			if(resultSet != null){
				arrayList = new ArrayList<T>();
				while(resultSet.next()){
					arrayList.add(mapper.mapping(resultSet));
				}
			}
			return arrayList;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DataBaseConnection.closeAll(connection, preparedStatement, resultSet);
		}
		return arrayList;
	}
	
	/**
	 * 通过匿名内部类获取实体，如想使用类加载器获取数据请使用同名重载函数
	 * @param @param entityMapper
	 * @param @param object
	 * @param @param resultSet
	 * @param @throws SQLException   
	 * @return T  
	 * @date 2018年11月8日
	 */
	protected static <T> Object queryObject(EntityMapper<T> entityMapper, String sql, Object[] object){
		Object t = null;
		try {
			initSqlParams(sql, object, false);
			if(resultSet != null){
				while(resultSet.next()){
					t = entityMapper.getEntity(resultSet);
				}
			}
			return t;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DataBaseConnection.closeAll(connection, preparedStatement, resultSet);
		}
		return t;		
	}
	
	/**
	 * if success return true
	 * else return false 
	 * 执行多条sql，增删改
	 * @param sqls
	 * @param parameter
	 * @return true or false 
	 */
	protected static boolean branch(String[] sqls, Object[]...parameter){
		boolean flag = false;
		Object[][] parameters = parameter;
		if(sqls == null || sqls.length < 0){
			return flag;
		}
		if(parameters == null || parameters.length < 0){
			return flag;
		}
		if(sqls.length != parameters.length){
			return flag;
		}
		
		try{
			connection = DataBaseConnection.getConnection();
			connection.setAutoCommit(false);
			for(int i=0; i<sqls.length; i++){
				preparedStatement = connection.prepareStatement(sqls[i]);
				setProperty(preparedStatement, parameters[i]);
				preparedStatement.executeUpdate();
			}
			connection.commit();
			flag = true;
			return flag;
		}catch(SQLException e){
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DataBaseConnection.closeAll(connection, preparedStatement, resultSet);
		}
		return flag;
	}
	
	
	/**
	 * 使用java反射设计模式返回数据集合
	 * @param sql sql
	 * @param object 参数数组
	 * @param t 泛型类.class
	 * @throws DBException 
	 */
	protected static <T> List<T> queryAll(String sql, Object[] object, Class<?> clazz) throws DBException{
		try {
			List<T> list = new ArrayList<T>();
			queryListResultInvoke(sql, object, clazz, list);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DBException("获取数据列表异常");
		}finally{
			DataBaseConnection.closeAll(connection, preparedStatement, resultSet);
		}
	}
	
	/**
	 * @Title: querySetAll   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   使用java反射设计模式返回数据集合
	 * @param: @param sql sql语句
	 * @param: @param object 参数数组
	 * @param: @param t 泛型.class
	 * @return: Set<T>     结果集
	 */
	protected static <T> Set<T> querySetAll(String sql, Object[] object, Class<?> clazz) throws DBException{
		try {
			Set<T> list = new HashSet<T>();
			queryListResultInvoke(sql, object, clazz, list);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DBException("持久层数据与实体映射异常！");
		}finally{
			DataBaseConnection.closeAll(connection, preparedStatement, resultSet);
		}		
	}
	
	/**
	 * @Title: queryListResultInvoke   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   结果集提取
	 * @param: @param sql
	 * @param: @param object
	 * @param: @param clazz
	 * @param: @param list
	 * @param: @throws Exception      
	 * @return: void      
	 */
	private static <T> void queryListResultInvoke(String sql, Object[] object, Class<?> clazz, Collection<T> list) throws Exception{
		initSqlParams(sql, object, false);
		// 取得ResultSet的列名
		String[] column = getColumnNames(resultSet);
		
		List<Field> target = new ArrayList<Field>();
		target = ObjectReflect.getValue(target, clazz);
		
		while(resultSet.next()){
			// 得到实例
			T instance = (T) clazz.newInstance();

			ObjectReflect.setValue(target, column, resultSet, instance);
			list.add((T) instance);
		}
	}
	
	/**
	 * 使用java反射机制，通过自动类识别获取数据
	 * @param sql sql
	 * @param object 参数数组
	 * @param t 泛型类.class
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	protected static <T> T queryObject(String sql, Object[] object, Class<?> clazz) throws DBException, Exception{
		try {
			initSqlParams(sql, object, false);
			if(resultSet != null && resultSet.next()){//下移了一个下标
				// 取得ResultSet的列名
				String[] column = getColumnNames(resultSet);
				
				List<Field> target = new ArrayList<Field>();
				target = ObjectReflect.getValue(target, clazz);
				
				// 得到实例
				T instance = (T) clazz.newInstance();
				
				ObjectReflect.setValue(target, column, resultSet, instance);
				return instance;
			}else{
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("数据持久层与实体映射异常 ！");
		}finally{
			DataBaseConnection.closeAll(connection, preparedStatement, resultSet);
		}
	}
	
	/**
	 * @Title: count   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   返回统计sql执行的记录数
	 * @param: @param sql
	 * @param: @throws DBException      
	 * @return: long      
	 */
	protected long count(String sql) throws DBException{
		long totle = 0L;
		try{
			sql = "SELECT COUNT(*) AS totle FROM (" + sql + ")";
			initSqlParams(sql, null, false);
			if(resultSet != null && resultSet.next()){
				totle = resultSet.getLong("totle");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new DBException(sql + "查询异常！");
		}finally {
			DataBaseConnection.closeAll(connection, preparedStatement, resultSet);
		}
		return totle;
	}
	
	/**
	 * 获取数据表字段，java属性名（成员）
	 * @param resultSet
	 * @throws SQLException
	 * columnNames：成员
	 * columnValues：字段 
	 */
	protected static String[][] getColumn(ResultSet resultSet) throws SQLException{
		ResultSetMetaData rsmd = resultSet.getMetaData();
		int columnsCount = rsmd.getColumnCount();
		String[] columnNames = new String[columnsCount];
		String[] columnValues = new String[columnsCount];
		for (int i = 0; i < columnsCount; i++) {
			String label = rsmd.getColumnLabel(i + 1);
			columnValues[i] = label;
			if(label.indexOf("_") != -1){
				String[] arr = label.split("_");
				StringBuffer sbf = new StringBuffer();
				sbf.append(arr[0]);
				for(int j=1; j<arr.length; j++){
	    			sbf.append(String.valueOf(arr[j].charAt(0)).toUpperCase().concat(arr[j].substring(1)));
				}
				columnNames[i] = sbf.toString();
			}else{
				columnNames[i] = label;
			}
		}
		return new String[][]{columnNames, columnValues};
	}
	
	/**
	 * @Title: getColumnNames   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   获取结果集字段
	 * @param: @param resultSet
	 * @param: @throws SQLException      
	 * @return: String[]      
	 */
	protected static String[] getColumnNames(ResultSet resultSet) throws SQLException{
		ResultSetMetaData rsmd = resultSet.getMetaData();
		int columnsCount = rsmd.getColumnCount();
		String[] columnValues = new String[columnsCount];
		for (int i = 0; i < columnsCount; i++) {
			String label = rsmd.getColumnLabel(i + 1);
			columnValues[i] = label;
		}
		return columnValues;
	}
	
}