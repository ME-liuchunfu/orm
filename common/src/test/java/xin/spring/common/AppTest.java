package xin.spring.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import xin.spring.base.annotation.tables.TbColumn;
import xin.spring.base.annotation.tables.TbId;
import xin.spring.common.bean.Student;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
    	List<Student> list = new ArrayList<Student>();
    	list.add(new Student(1L,"name1", 18));
    	list.add(new Student(2L,"name2", 19));
    	list.add(new Student(3L,"name3", 20));
    	list.add(new Student(4L,"name4", 21));
    	list.add(new Student(5L,"name5", 22));
    	list.add(new Student(6L,"name6", 23));
    	list.add(new Student(7L,"name7", 24));
    	
    	List<Field> target = new ArrayList<Field>();
    	System.out.println(Student.class.getSuperclass().getName().equals(Object.class.getName()));
    	target = setValue(target, Student.class);
    	
    	List<Student> re = new ArrayList<Student>();
    	
    	for(Student s : list){
    		Student instance = Student.class.newInstance();
	    	for(Field field : target){
	    		field.setAccessible(true);
	    		if(field.getName().equals("id")){
	    			field.set(instance, s.getId());	    			
	    		}
	    		if(field.getName().equals("name")){
	    			field.set(instance, s.getName());	    			
	    		}
	    		if(field.getName().equals("age")){
	    			field.set(instance, s.getAge());	    			
	    		}
	    		if(field.getName().equals("type")){
	    			field.set(instance, s.getType());	    			
	    		}
	    	}
	    	re.add(instance);
    	}
    	
		System.out.println(re);
	}
    
    protected static List<Field> setValue(List<Field> target, Class<?> clazz){
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
		return setValue(target, clazz.getSuperclass());
    }
    
}
