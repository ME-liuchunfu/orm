package xin.spring.base.exception;

/**
 * @ClassName:  BeanInitException   
 * @Description:TODO(这里用一句话描述这个类的作用)   实体初始化异常
 * @author: spring
 * @date:   2019年6月25日 下午8:02:32     
 * @Copyright: 2019 wwww.sprringbless.xin Inc. All rights reserved.
 */
public class BeanInitException extends Exception {

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 1L;

	public BeanInitException() {
		this("java bean 实体对象初始化失败，可能缺少@TbName注解", null);
	}

	public BeanInitException(String message, Throwable cause) {
		super(message, cause);
	}

	public BeanInitException(String message) {
		super(message);
	}

}
