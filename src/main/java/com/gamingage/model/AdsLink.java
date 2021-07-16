package com.gamingage.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AdsLink {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String flot;
	private String top;
	private String middle;
	private String bottom;
	private String sidebar;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFlot() {
		return flot;
	}
	public void setFlot(String flot) {
		this.flot = flot;
	}
	public String getTop() {
		return top;
	}
	public void setTop(String top) {
		this.top = top;
	}
	public String getMiddle() {
		return middle;
	}
	public void setMiddle(String middle) {
		this.middle = middle;
	}
	public String getBottom() {
		return bottom;
	}
	public void setBottom(String bottom) {
		this.bottom = bottom;
	}
	public String getSidebar() {
		return sidebar;
	}
	public void setSidebar(String sidebar) {
		this.sidebar = sidebar;
	}
	
	

}
