package com.epam.hotelmanagement.bean;

public class Resident {
	private int id;
	private String name;
	private String email;
	private String country;
	private String room;
	
	
	public Resident(String name, String email, String country, String room) {
		super();
		this.name = name;
		this.email = email;
		this.country = country;
		this.room = room;
	}
	public Resident(int id, String name, String email, String country, String room) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.country = country;
		this.room = room;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
}
