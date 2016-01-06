package com.horizon.mqriver.test;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Message implements Serializable {

	/** 序列化 */
	private static final long	serialVersionUID	= 8397462852151458099L;

	private int					age;
	private long				cnt;
	private String				name;
	private String				lastName;
	private Date				date;
	private BigDecimal			bb;

	public Message() {
	}

	public Message(int age, long cnt, String name, String lastName, Date date) {
		this.age = age;
		this.cnt = cnt;
		this.name = name;
		this.lastName = lastName;
		this.date = date;
		this.bb = new BigDecimal(45121.454);
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public long getCnt() {
		return cnt;
	}

	public void setCnt(long cnt) {
		this.cnt = cnt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getBb() {
		return bb;
	}

	public void setBb(BigDecimal bb) {
		this.bb = bb;
	}


	@Override
	public String toString() {
		return "age==" + age + ",cnt==" + cnt + ",name==" + name + ",lastName==" + lastName + ",date==" + date + ",bb="
				+ bb;
	}

}
