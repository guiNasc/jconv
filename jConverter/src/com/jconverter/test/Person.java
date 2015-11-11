package com.jconverter.test;

import java.util.ArrayList;
import java.util.List;

import com.jconverter.annotation.ConvertComplex;
import com.jconverter.annotation.ConvertSimple;

public class Person {
	private String name;
	private Integer age;
	private String[] jobs;
	private List<Phone> phones;
	private ArrayList<String> dependents;
	private Car car;
	private Integer[] channels;
	
	@ConvertSimple
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@ConvertSimple
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	//@ConvertSimple
	public String[] getJobs() {
		return jobs;
	}
	public void setJobs(String[] jobs) {
		this.jobs = jobs;
	}
	@ConvertComplex
	public List<Phone> getPhones() {
		return phones;
	}
	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}
	@ConvertComplex
	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
	}
	@ConvertSimple
	public ArrayList<String> getDependents() {
		return dependents;
	}
	public void setDependents(ArrayList<String> dependents) {
		this.dependents = dependents;
	}
	@ConvertSimple
	public Integer[] getChannels() {
		return channels;
	}
	public void setChannels(Integer[] channels) {
		this.channels = channels;
	}
	
	
}
