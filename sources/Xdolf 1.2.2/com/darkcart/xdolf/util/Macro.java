package com.darkcart.xdolf.util;

import java.util.ArrayList;

public class Macro {
	public static ArrayList<Macro> macroList = new ArrayList<Macro>();
	
	private int key;
	private String cmd;
	
	public Macro(int key, String cmd) {
		this.key = key;
		this.cmd = cmd;
		macroList.add(this);
	}
	
	public int getKey() {
		return key;
	}
	
	public String getCommand() {
		return cmd;
	}
}