package com.towd.vnfood;

import java.io.Serializable;

// mô tả món ăn
public class MonanObject implements Serializable {
	private int id;
	private String dishname;
	private String intro;
	private int categoryid;
	private String material;
	private String howtocook;
	private String dishpics;
	private String namesearch;
	private String basicmaterial;
	private int favorite;
	private String timetocook;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDishname() {
		return dishname;
	}
	public void setDishname(String dishname) {
		this.dishname = dishname;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public int getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getHowtocook() {
		return howtocook;
	}
	public void setHowtocook(String howtocook) {
		this.howtocook = howtocook;
	}
	public String getDishpics() {
		return dishpics;
	}
	public void setDishpics(String dishpics) {
		this.dishpics = dishpics;
	}
	public String getNamesearch() {
		return namesearch;
	}
	public void setNamesearch(String namesearch) {
		this.namesearch = namesearch;
	}
	public String getBasicmaterial() {
		return basicmaterial;
	}
	public void setBasicmaterial(String basicmaterial) {
		this.basicmaterial = basicmaterial;
	}
	public int getFavorite() {
		return favorite;
	}
	public void setFavorite(int favorite) {
		this.favorite = favorite;
	}
	public String getTimetocook() {
		return timetocook;
	}
	public void setTimetocook(String timetocook) {
		this.timetocook = timetocook;
	}
}
