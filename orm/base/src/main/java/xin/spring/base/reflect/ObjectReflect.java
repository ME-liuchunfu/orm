package xin.spring.base.reflect;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.List;

import xin.spring.base.annotation.tables.TbColumn;
import xin.spring.base.annotation.tables.TbId;
import xin.spring.base.annotation.tables.TbName;
import xin.spring.base.exception.BeanInitException;

/**
 * @ClassName:  ObjectReflect   
 * @Description:TODO(这里用一句话描述这个类的作用) 反射获取实体   
 * @author: spring
 * @date:   2019年6月25日 下午6:10:18     
 * @Copyright: 2019 wwww.sprringbless.xin Inc. All rights reserved.
 */
public class ObjectReflect {
	
	/**
	 * @Title: getTableName   
	 * @Description: TODO(这里用一句话描述这个方法的作用) 反射 + 注解得到数据库表明  
	 * @param: @param clazz 实体数据类型
	 * @return: String 返回注解的数据库表名     
	 */
	public static String getTableName(Class<?> clazz) throws BeanInitException{
		TbName annotation = clazz.getAnnotation(TbName.class);
		if(annotation != null){
			return annotation.value();
		}else{
			throw new BeanInitException(clazz.getName() + "还没有使用@TbName注解配置实体bean");
		}
	}
	
	/**
	 * @Title: getTableId   
	 * @Description: TODO(这里用一句话描述这个方法的作用) 反射 + 注解得到数据库表id
	 * @param: @param clazz 实体数据类型
	 * @return: String 返回注解的数据库 表id   
	 */
	public static String getTableId(Class<?> clazz) throws BeanInitException{
		String result = null;
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields){
			TbId tbId = field.getAnnotation(TbId.class);
			if(tbId != null){
				result = tbId.value();
				break;
			}
		}
		return result;
	}

	/**
	 * @Title: getValue   
	 * @Description: TODO(这里用一句话描述这个方法的作用) 获取指定注解的数据集合  ，xin.spring.base.annotation.tables.TbId;xin.spring.base.annotation.tables.TbColumn;
	 * @param: @param target 目标集合
	 * @param: @param clazz 反射数据类型
	 * @return: List<Field>      
	 */
	public static List<Field> getValue(List<Field> target, Class<?> clazz){
    	if(clazz.getName().equals(Object.class.getName())){
    		return target;
    	}
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields){
			TbId tbId = field.getAnnotation(TbId.class);
			TbColumn column = field.getAnnotation(TbColumn.class);
			if(tbId != null){
				target.add(field);
			}
			if(column != null){
				target.add(field);
			}
		}
		return getValue(target, clazz.getSuperclass());
    }
	
	/**
	 * @Title: setValue   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   设置反射实例数据
	 * @param: @param target 反射注解列表
	 * @param: @param column 数据表字段名数组
	 * @param: @param resultSet 数据库结果集
	 * @param: @param instance 反射实例
	 * @param: @throws Exception   异常类型
	 * @return: boolean 成功标记 ： true成功，false失败      
	 */
	public static boolean setValue(List<Field> target, String[] column, ResultSet resultSet, Object instance) throws Exception{
		boolean flag = true;
		if(target != null && column != null){
			try {
				for(int i=0; i<column.length; i++){
					for(Field field : target){
						field.setAccessible(true);
						if(field.getName().equals(column[i])){
							field.set(instance, resultSet.getObject(column[i]));
							break;
						}
					}
				}
			} catch (Exception e) {
				flag = false;
				e.printStackTrace();
				throw e;
			}
		}
		return flag;
	}

}
