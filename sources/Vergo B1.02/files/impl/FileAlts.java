package xyz.vergoclient.files.impl;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class FileAlts {
	
	@SerializedName(value = "alts")
	public ArrayList<Alt> alts = new ArrayList<>();
	
	public static class Alt {
		
		public String username = "", email = "", password = "";
		public long banTime = Long.MIN_VALUE;
		
	}
	
}
