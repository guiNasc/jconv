package com.jconverter.core;

public class Formatter {

	private StringBuilder sb;

	public Formatter() {
		this.sb = new StringBuilder();
	}

	public StringBuilder open() {
		return this.sb.append("{");
	}

	public StringBuilder openList() {
		return this.sb.append("[");
	}

	public void add(Object key, Object val) {
		this.sb.append("\"" + key + "\"");
		this.sb.append(":");
		this.sb.append("\"" + val + "\",");
	}

	public void addComplex(Object key, Object val) {
		this.sb.append("\"" + key + "\"");
		this.sb.append(":");
		this.sb.append(val + ",");
	}

	public void addSimple(Object val) {
		this.sb.append("\"" + val + "\"");
		this.sb.append(",");
	}

	public void addList(Object key, Object val) {
		this.sb.append("\"" + key + "\"");
		this.sb.append(":");
		this.sb.append(val + ",");
	}

	public void addObjects(Formatter f) {
		this.sb.append(f + ",");
	}

	public void addKey(Object key) {
		this.sb.append("\"" + key + "\"");
		this.sb.append(":");
	}

	private void removeLastComma() {
		this.sb.setLength(this.sb.length() - 1);
	}

	public String close() {
		if (this.sb.length() > 1) {
			if (this.sb.toString().endsWith(",")) {
				removeLastComma();
			}
			return this.sb.append("}").toString();
		}
			return "";
	}

	public String closeList() {
		if (this.sb.length() > 1) {
			if (this.sb.toString().endsWith(",")) {
				removeLastComma();
			}
			return this.sb.append("]").toString();
		}
		return "";
	}

	@Override
	public String toString() {
		return sb.toString();
	}
}
