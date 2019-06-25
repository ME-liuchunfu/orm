package xin.spring.common.bean;

import xin.spring.base.annotation.tables.TbColumn;
import xin.spring.base.annotation.tables.TbId;
import xin.spring.base.annotation.tables.TbName;

@TbName("tb_student")
public class Student extends Man{

	@TbId("id")
	private Long id;
	
	@TbColumn("name")
	private String name;
	
	@TbColumn("age")
	private Integer age;
	
	@TbColumn("ce_temp")
	private String ceTemp;

	public String getCeTemp() {
		return ceTemp;
	}

	public void setCeTemp(String ceTemp) {
		this.ceTemp = ceTemp;
	}

	public Student() {
		super();
	}

	public Student(Long id, String name, Integer age) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
	}
	
	public Student(String name, Integer age, String type) {
		this.name = name;
		this.age = age;
		setType(type);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", age=" + age + ", ceTemp=" + ceTemp + "]" + super.toString();
	}

}
