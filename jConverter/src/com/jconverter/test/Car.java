package com.jconverter.test;

import com.jconverter.annotation.ConvertSimple;

public class Car {

	private String name;
	private int year;
	private String modelOfTheCar;
	private Integer numberOfDoors;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@ConvertSimple
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	@ConvertSimple
	public String getModelOfTheCar() {
		return modelOfTheCar;
	}

	public void setModelOfTheCar(String modelOfTheCar) {
		this.modelOfTheCar = modelOfTheCar;
	}
	@ConvertSimple
	public Integer getNumberOfDoors() {
		return numberOfDoors;
	}

	public void setNumberOfDoors(Integer numberOfDoors) {
		this.numberOfDoors = numberOfDoors;
	}
	

}
