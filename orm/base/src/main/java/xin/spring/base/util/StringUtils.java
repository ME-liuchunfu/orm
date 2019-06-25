package xin.spring.base.util;

/**
 * @ClassName:  StringUtils   
 * @Description:TODO(这里用一句话描述这个类的作用)  常用字符串工具类   
 * @author: spring
 * @date:   2019年6月25日 下午7:42:27     
 * @Copyright: 2019 wwww.sprringbless.xin Inc. All rights reserved.
 */
public class StringUtils {

	/**
	 * @Title: isNotNull   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   字符串不为空
	 * @param: @param str
	 * @return: boolean 空true，非空：false      
	 */
	public static boolean isNotNull(String str){
		return !isNull(str);
	}
	
	/**
	 * @Title: isNull   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   字符串为空
	 * @param: @param str
	 * @return: boolean      空true，非空：false
	 */
	public static boolean isNull(String str){
		if(str == null || "".equals(str)){
			return true;
		}else if(str != null && "".equals(str.trim())){
			return true;
		}
		return false;
	}
	
}
