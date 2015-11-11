package com.jconverter.core;

import java.awt.List;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jconverter.annotation.ConvertComplex;
import com.jconverter.annotation.ConvertSimple;

public class Converter {
	private final static Logger LOGGER = Logger.getLogger(Converter.class.getName());

	public void convert(Object o, OutputStream output) {
		if (o == null)
			return;
		LOGGER.log(Level.INFO, "Starting conversion...");
		Class<?> clazz = o.getClass();
		Formatter data = new Formatter();
		try {
			if (isCollection(clazz)) {
				data = doListConversion(o);
			} else {
				data = doConversion(o);
			}
			output.write(data.toString().getBytes());
			LOGGER.log(Level.INFO, "Conversion ended.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String getterToAttribute(Method m) {
		String result = m.getName().substring(3, 4).toLowerCase();
		result += m.getName().substring(4);
		return result;
	}

	private boolean isGetter(Method m) {
		return m.getName().startsWith("get") && m.getReturnType() != void.class && m.getParameterTypes().length == 0;
	}

	private boolean isCollection(Class<?> c) {
		return Collection.class.isAssignableFrom(c) || List.class.isAssignableFrom(c);
	}

	private Formatter doConversion(Object o) {
		Class<?> clazz = o.getClass();
		Formatter data = new Formatter();
		data.open();
		try {
			for (Method m : clazz.getMethods()) {
				if (isGetter(m)) {
					if (isCollection(m.getReturnType())) {
						if (m.isAnnotationPresent(ConvertSimple.class)) {
							LOGGER.log(Level.INFO, "Converting a simple list...");
							data.addList(getterToAttribute(m), simpleList(o, m));
						} else if (m.isAnnotationPresent(ConvertComplex.class)) {
							if (isCollection(m.getReturnType())) {
								LOGGER.log(Level.INFO, "Converting a complex list...");
								Formatter formattedList = new Formatter();
								formattedList = complexList(o, m);
								if (!formattedList.toString().isEmpty()) {
									data.addList(getterToAttribute(m), formattedList);
								}
							}
						}
					} else if (m.getReturnType().isArray()) {
						if (m.isAnnotationPresent(ConvertSimple.class)) {
							LOGGER.log(Level.INFO, "Theres is no implementation for array! :c .");
						} else if (m.isAnnotationPresent(ConvertComplex.class)) {
							LOGGER.log(Level.INFO, "Theres is no implementation for array! :c .");
							/*Object[] array = (Object[]) m.invoke(o);
							int length = Array.getLength(array);
							for (int i = 0; i < length; i++) {
								Object arrayElement = Array.get(array, i);
								System.out.println(arrayElement);
							}*/
						}
					} else {
						if (m.isAnnotationPresent(ConvertSimple.class)) {
							LOGGER.log(Level.INFO, "Converting attributes...");
							if (m.invoke(o) != null) {
								data.add(getterToAttribute(m), m.invoke(o));
							}
						} else if (m.isAnnotationPresent(ConvertComplex.class)) {
							LOGGER.log(Level.INFO, "Converting a complex object...");
							data.addComplex(getterToAttribute(m), doConversion(m.invoke(o)));
						}
					}
				}
			}
			data.close();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return data;
	}

	private Formatter simpleList(Object o, Method m)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ArrayList<?> list = (ArrayList<?>) m.invoke(o);
		Formatter data = new Formatter();
		data.openList();
		for (int i = 0; i < list.size(); i++) {
			data.addSimple(list.get(i));
		}
		data.closeList();
		return data;
	}

	private Formatter complexList(Object o, Method m)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ArrayList<?> list = (ArrayList<?>) m.invoke(o);
		Formatter data = new Formatter();
		if (list.isEmpty())
			return data;
		data.openList();
		for (Object obj : list) {
			data.addObjects(doConversion(obj));
		}
		data.closeList();
		return data;
	}

	private Formatter doListConversion(Object o) {
		ArrayList<?> list = (ArrayList<?>) o;
		Formatter data = new Formatter();
		if (list.isEmpty())
			return data;
		data.open();
		data.addKey("JSON");
		data.openList();
		for (Object obj : list) {
			data.addObjects(doConversion(obj));
		}
		data.closeList();
		data.close();
		return data;
	}
}
