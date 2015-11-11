package com.jconverter.test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.jconverter.core.Converter;

public class TestCases {
	private OutputStream output;
	private Converter converter;
	private ByteArrayOutputStream baos;

	private void prepareTest(Object o) {
		output = new ByteArrayOutputStream();
		converter = new Converter();
		baos = new ByteArrayOutputStream();
		converter.convert(o, output);
		baos = (ByteArrayOutputStream) output;
	}
	
	private String sortString(String s) {
		char[] chars = s.toCharArray();
		Arrays.sort(chars);
		String sorted = new String(chars);
		return sorted;
	}

	@Test
	public void testNullValue() {
		prepareTest(null);
		assertEquals("", baos.toString());
	}

	@Test
	public void testSimplePojo() {
		Car myCar = new Car();
		myCar.setName("M4 COUPÉ");
		myCar.setYear(2016);
		myCar.setModelOfTheCar("BMW");
		myCar.setNumberOfDoors(4);

		prepareTest(myCar);
		String expected = sortString("{\"year\":\"2016\",\"modelOfTheCar\":\"BMW\",\"numberOfDoors\":\"4\"}");
		String result = sortString(baos.toString());
		assertEquals(expected, result);
	}

	@Test
	public void testComplexList() {
		Phone p1 = new Phone("123", "NET");
		Phone p2 = new Phone("466", "Claro");
		Phone p3 = new Phone("112233", "Tim");

		ArrayList<Phone> phones = new ArrayList<Phone>();
		phones.add(p1);
		phones.add(p2);
		phones.add(p3);

		prepareTest(phones);
		String expected = sortString("{\"JSON\":[{\"provider\":\"NET\",\"number\":\"123\"},{\"provider\":\"Claro\",\"number\":\"466\"},{\"provider\":\"Tim\",\"number\":\"112233\"}]}");
		String result = sortString(baos.toString());
		assertEquals(expected, result);
	}

	@Test
	public void testComplexPojo() {
		Car myCar = new Car();
		myCar.setName("M4 COUPÉ");
		myCar.setYear(2016);
		myCar.setModelOfTheCar("BMW");
		myCar.setNumberOfDoors(4);

		Person person = new Person();
		person.setName("Guilherme dos Reis Nascimento");
		person.setAge(25);
		person.setCar(myCar);
		String[] jobs = new String[2];
		jobs[0] = "Developer";
		jobs[1] = "Musician";
		person.setJobs(jobs);
		
		Integer[] channels = new Integer[3];
		channels[0] = 25;
		channels[1] = 82;
		person.setChannels(channels);
		
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(new Phone("93322526", "Telefonic"));
		phones.add(new Phone("32226225", "NET"));
		person.setPhones(phones);
		ArrayList<String> dependents = new ArrayList<>();
		dependents.add("Miss A");
		dependents.add("Mister X");
		person.setDependents(dependents);
		
		prepareTest(person);
		String expected = sortString("{\"age\":\"25\",\"phones\":[{\"provider\":\"Telefonic\",\"number\":\"93322526\"},{\"provider\":\"NET\",\"number\":\"32226225\"}],\"car\":{\"year\":\"2016\",\"modelOfTheCar\":\"BMW\",\"numberOfDoors\":\"4\"},\"dependents\":[\"Miss A\",\"Mister X\"],\"name\":\"Guilherme dos Reis Nascimento\"}");
		String result = sortString(baos.toString());
		assertEquals(expected, result);
		
	}
	@Test
	public void emptyList(){
		ArrayList<String> stringList = new ArrayList<String>();
		prepareTest(stringList);
		assertEquals("",baos.toString());
	}
	
	@Test
	public void complexPojoEmptyList() {
		Car myCar = new Car();
		myCar.setName("M4 COUPÉ");
		myCar.setYear(2016);
		myCar.setModelOfTheCar("BMW");
		myCar.setNumberOfDoors(4);

		Person person = new Person();
		person.setName("Guilherme dos Reis Nascimento");
		person.setAge(25);
		person.setCar(myCar);
		String[] jobs = new String[2];
		jobs[0] = "Developer";
		jobs[1] = "Musician";
		person.setJobs(jobs);
		
		//An empty list of phones.
		List<Phone> phones = new ArrayList<Phone>();
		person.setPhones(phones);
		
		ArrayList<String> dependents = new ArrayList<>();
		dependents.add("Miss A");
		dependents.add("Mister X");
		person.setDependents(dependents);
		
		prepareTest(person);
		String expected = sortString("{\"age\":\"25\",\"car\":{\"year\":\"2016\",\"modelOfTheCar\":\"BMW\",\"numberOfDoors\":\"4\"},\"dependents\":[\"Miss A\",\"Mister X\"],\"name\":\"Guilherme dos Reis Nascimento\"}");
		String result = sortString(baos.toString());
		assertEquals(expected, result);
		
	} 
}
