package xin.spring.base.orm;

import java.util.List;

/**
 * @ClassName:  DataRow   
 * @Description:TODO(这里用一句话描述这个类的作用)   分页数据集
 * @author: spring
 * @param <T>
 * @date:   2019年6月25日 下午10:41:21     
 * @Copyright: 2019 wwww.sprringbless.xin Inc. All rights reserved.
 */
public class DataRow<T> {
	
	public static final int START_ROW = 1;
	
	public static final int SIZE = 20;
	
	private int totle;

	private int start;
	
	private int size;
	
	private List<T> datas;
	
	public DataRow(){
		start = START_ROW;
		size = SIZE;
	}
	
	public DataRow(int start, int size){
		this.start = start;
		this.size = size;
	}

	public List<T> getDatas() {
		return datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start <= 0 ? 1 : start;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size <= 0 ? SIZE : size;
	}

	@Override
	public String toString() {
		return "DataRow [totle=" + totle + ", start=" + start + ", size=" + size + ", datas=" + datas + "]";
	}

	public int getTotle() {
		return totle;
	}

	public void setTotle(int totle) {
		this.totle = totle;
	}
	
}
