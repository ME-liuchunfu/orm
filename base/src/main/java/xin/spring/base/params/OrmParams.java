package xin.spring.base.params;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import xin.spring.base.annotation.tables.TbColumn;
import xin.spring.base.annotation.tables.TbId;
import xin.spring.base.exception.BeanInitException;
import xin.spring.base.reflect.ObjectReflect;

/**
 * @ClassName:  OrmParams   
 * @Description:TODO(这里用一句话描述这个类的作用) sql结构化参数集   
 * @author: spring
 * @date:   2019年6月25日 下午7:59:26     
 * @Copyright: 2019 wwww.sprringbless.xin Inc. All rights reserved.
 */
public class OrmParams extends HashMap<String, Object> {

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 1L;
	
	public static final String TABLE_NAME_KEY = "_tableName";
	
	public OrmParams(){}
	
	public OrmParams(String tableName){
		put(TABLE_NAME_KEY, tableName);
	}
	
	public <T> OrmParams(Class<T> clazz) throws BeanInitException{
		if(clazz == null){
			throw new BeanInitException("实体类型未指定，请仔细检查");
		}
		String tableName = ObjectReflect.getTableName(clazz);
		put(TABLE_NAME_KEY, tableName);
		List<Field> target = new ArrayList<Field>();
		target = ObjectReflect.getValue(target, clazz);
		for(Field field : target){
			TbId tbId = field.getAnnotation(TbId.class);
			TbColumn column = field.getAnnotation(TbColumn.class);
			if(tbId != null){
				put(field.getName(), tbId.value());				
			}
			if(column != null){
				put(field.getName(), column.value());
			}
		}
	}
	
	public <T> OrmParams(T t) throws BeanInitException, IllegalArgumentException, IllegalAccessException{
		if(t == null){
			throw new BeanInitException("实体类型未指定，请仔细检查");
		}
		String tableName = ObjectReflect.getTableName(t.getClass());
		put(TABLE_NAME_KEY, tableName);
		List<Field> target = new ArrayList<Field>();
		target = ObjectReflect.getValue(target, t.getClass());
		for(Field field : target){
			field.setAccessible(true);
			TbId tbId = field.getAnnotation(TbId.class);
			TbColumn column = field.getAnnotation(TbColumn.class);
			if(tbId != null){
				put(field.getName(), field.get(t));		
			}
			if(column != null){
				put(field.getName(), field.get(t));
			}
		}
	}

}
