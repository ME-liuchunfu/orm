package xin.spring.common.bean;

import xin.spring.base.annotation.tables.TbColumn;

public class Man {
	
	@TbColumn("type")
	private String type;

	@Override
	public String toString() {
		return "Man [type=" + type + "]";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
