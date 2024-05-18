package com.darkcart.xdolf.util;

public class Value {
	private String name;
	private float value;
	
	public Value(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public float getValue() {
		return value;
	}
	
	public void setValue(float f) {
		this.value = f;
	}
}
