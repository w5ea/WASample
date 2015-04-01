package cn.way.wandroid.json;

import java.util.Date;

public class User {
	private String name;
	public Date birthday;
	public String getName() {
		return name;
	}
	
	public int getNumber(){
		return 0;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return name;
	}
}
