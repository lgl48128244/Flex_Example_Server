package com.ctvit.flex.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
	private Integer id;
	private String uname;
	private String pwd;
	private String nickname;
	private String email;
	private String power;
	private String teacherid;
	private Date createTime;
	private Date updateTime;

	public User() {

	}

	public User(Integer id, String uname, String nickname, String email, String power, String teacherid,Date createTime,Date updateTime) {
		this.id = id;
		this.uname = uname;
		this.nickname = nickname;
		this.email = email;
		this.power = power;
		this.teacherid = teacherid;
		this.createTime=createTime;
		this.updateTime=updateTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getTeacherid() {
		return teacherid;
	}

	public void setTeacherid(String teacherid) {
		this.teacherid = teacherid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
