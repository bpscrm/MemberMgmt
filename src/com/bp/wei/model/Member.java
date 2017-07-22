package com.bp.wei.model;

public class Member {
	private String name;
	
	private String mobile;
	
	private String gender;
	
	private String birthday;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	@Override
	public String toString() {
		return "Member [name=" + name + ", mobile=" + mobile + ", gender=" + gender + ", birthday=" + birthday + "]";
	}	
	
}
