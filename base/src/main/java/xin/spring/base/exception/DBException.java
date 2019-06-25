package xin.spring.base.exception;

/**
 * @ClassName:  DBException   
 * @Description:TODO(这里用一句话描述这个类的作用)  自定义数据库连接异常类   
 * @author: spring
 * @date:   2019年6月25日 下午2:34:24     
 * @Copyright: 2019 wwww.sprringbless.xin Inc. All rights reserved.
 */
public class DBException extends Exception {

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 1L;

	public DBException(String message, Throwable cause) {
		super(message, cause);
	}

	public DBException(String message) {
		super(message);
	}

}
