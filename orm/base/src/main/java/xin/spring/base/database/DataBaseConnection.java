package xin.spring.base.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

/**
 * @ClassName:  DataBaseConnection   
 * @Description:TODO(这里用一句话描述这个类的作用)   负责得到数据库的连接,做成一个工具类（如果是工具类一般不会实例化new）,工具类方法一般会做成静态方法
 * @author: spring
 * @date:   2019年6月25日 下午2:14:01     
 * @Copyright: 2019 wwww.sprringbless.xin Inc. All rights reserved.
 */
public class DataBaseConnection {
	
	
	private static BasicDataSource bds;
	
	/**
	 * @Title: seDataSource   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   配置初始化数据源
	 * @param: @param properties
	 * @param: @throws Exception      
	 * @return: void      
	 */
	public static void seDataSource(Properties properties) throws Exception{
		bds = (BasicDataSource) BasicDataSourceFactory.createDataSource(properties);
	}
	
	/**
	 * 封装得到一个 Connection的方法
	 * @throws SQLException 
	 */
	public static Connection getConnection() throws SQLException{
		return bds.getConnection();
	}
	
	
	/**
	 * 关闭Connection
	 * @param conn
	 */
	public static void close(Connection conn){
		closeAll(conn, null, null);
	}
	
	/**
	 * 关闭结果集ResultSet
	 * @param resultSet
	 */
	public static void close(ResultSet resultSet){
		closeAll(null, null, resultSet);
	}
	
	/**
	 * 关闭结果集PreparedStatement
	 * @param resultSet
	 */
	public static void close(PreparedStatement pStatement){
		closeAll(null, pStatement, null);
	}
	
	/**
	 * 关闭Connection连接和ResultSet结果集
	 * @param conn
	 * @param resultSet
	 */
	public static void closeAll(Connection conn, PreparedStatement pStatement, ResultSet resultSet){
		try{
			if(resultSet != null){
				resultSet.close();
			}
			if(pStatement != null){
				pStatement.close();
			}
			if(conn != null){
				conn.close();
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

}
