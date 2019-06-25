package xin.spring.common.bean;

import java.util.Properties;

import xin.spring.base.database.DataBaseConnection;
import xin.spring.base.exception.BeanInitException;
import xin.spring.base.exception.DBException;
import xin.spring.base.orm.DataRow;
import xin.spring.base.params.OrmParams;

public class Conn {
	
	public static void main(String[] args) throws Exception {
		Properties properties = new Properties();
		properties.load(Conn.class.getClassLoader().getResourceAsStream("db.properties"));
		DataBaseConnection.seDataSource(properties);
		
		StudentDao dao = new StudentDao();
		long start = System.currentTimeMillis();
//		boolean dels = dao.dels(new Object[]{1,2,3,4});
//		System.out.println(dels);
		DataRow<Student> dataRow = new DataRow<Student>();
		dataRow.setStart(0);
		DataRow<Student> page = dao.page(dataRow, null);
		for(Student student : page.getDatas()){
			System.out.println(student);
		}
		System.out.println(dataRow);
		//insertOne(dao);
		//insert(dao);
		//queryById(dao);
		//querySet(dao);
		//queryAll(dao);
		//update(dao);
		System.out.println(System.currentTimeMillis() - start);
	}


	private static void insertOne(StudentDao dao) throws BeanInitException {
		System.out.println(dao.insert(new Student("爱丽丝女孩", 18, "女孩")));
	}

//	private static void update(StudentDao dao) {
//		System.out.println(dao.updateObject(new Student(1L, "天天", 14)));
//	}
//
//	private static void queryAll(StudentDao dao) throws DBException {
//		List<Student> list = dao.queryAll();
//		for(Student student : list){
//			System.out.println(student);
//		}
//	}
//
//	private static void querySet(StudentDao dao) throws DBException {
//		Set<Student> list = dao.querSet();
//		for(Student student : list){
//			System.out.println(student);
//		}
//	}

	private static void insert(StudentDao dao) throws BeanInitException {
		OrmParams params = new OrmParams("tb_student");
		for(int i=0;i<20;i++){			
			params.put("age", i+10);
			params.put("type", "女人");
			params.put("name", "爱丽丝"+i);
			System.out.println(dao.insert(params, Student.class));
		}
	}

	private static void queryById(StudentDao dao) throws DBException, Exception {
		Student student = dao.getById(1L);
		System.out.println(student);
	}

}
