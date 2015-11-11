package com.jconverter.test;

import com.jconverter.annotation.ConvertSimple;

public class Phone {
	private String number;
	private String provider;
	
	public Phone(String num, String prov){
		number = num;
		provider = prov;
	}
	@ConvertSimple
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	@ConvertSimple
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
}
