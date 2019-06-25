package xin.spring.common.bean;

import xin.spring.base.exception.BeanInitException;
import xin.spring.base.exception.DBException;
import xin.spring.base.orm.DataRow;
import xin.spring.base.orm.QueryFactory;
import xin.spring.base.orm.QuerySqlFactory;
import xin.spring.base.params.OrmParams;

public class StudentDao{
	
	QuerySqlFactory query = QueryFactory.getQueryInstance();
	
	public boolean insert(OrmParams params, Class<Student> clazz) throws BeanInitException{
		return query.insert(params, clazz);
	}
	
	public boolean insert(Student student) throws BeanInitException{
		return query.insert(student);
	}
	
	public Student getById(Long id) throws DBException, Exception{
		//return query.query("SELECT * FROM tb_student WHERE id=?", new Object[]{id}, Student.class);
		return query.queryById(id, Student.class);
	}
	
//	public Set<Student> querSet() throws DBException{
//		return querySetAll("SELECT * FROM tb_student", null, Student.class);
//	}
//	
//	public List<Student> queryAll() throws DBException{
//		return queryAll("SELECT * FROM tb_student", null, Student.class);
//	}
//	
//	public boolean updateObject(Student student){
//		return getBoolean(toUpdate("UPDATE tb_student SET name = ? WHERE id = ? ", new Object[]{student.getName(), student.getId()}));
//	}
	
	public DataRow<Student> page(DataRow<Student> dataRow, Object[] params) throws BeanInitException, DBException{
		return query.queryList("SELECT * FROM tb_student WHERE 1=1 ", dataRow, params, Student.class);
	}

	public boolean dels(Object[] params) throws BeanInitException, DBException{
		return query.delBrachById(params, Student.class);
	}
}
