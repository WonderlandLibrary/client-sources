package com.thunderware;

import org.lwjgl.opengl.Display;

import com.thunderware.module.ModuleManager;

public class Thunder {
	
	public static Thunder i = new Thunder();
	
	public String buildVersion = "B1.0";
	public static ModuleManager moduleManager = new ModuleManager();
	
	public void setupClient() {
		Display.setTitle("Thunderware B1.0");
		moduleManager.setupModules();
	}
	
	public void onClientClose() {
		
	}
	
}



/*

 	We all love FuzzySalt,
 	He was the best java developer,
 	We all miss FuzzySalt.
 
*/
