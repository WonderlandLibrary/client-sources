package me.xatzdevelopments.util;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import me.xatzdevelopments.clickgui.Panel;
import net.minecraft.client.Minecraft;

public class LoginUtil {
	
	public boolean loggedin = false;
	
	public void login(String username, String password) {
		
		   final File file = new File(Minecraft.getMinecraft().mcDataDir, "login.txt");
	       //FileReader file = new FileReader(Xatz.getFileMananger().settingsDir);
	       final boolean exists = file.exists();
	       if (!exists) {
	           return;
	       }
	       Scanner scan = null;
	       try {
	           scan = new Scanner(file);
	       }
	       catch (IOException e2) {
	           e2.printStackTrace();
	           //this.toggled = false;
	       }
	       if (scan == null) {
	           //this.toggled = false;
	       }
	       
	       
	       while (scan.hasNextLine()) {
	           String Line = scan.nextLine();
	           if (Line == null) {
	               //this.toggled = false;
	               return;
	           }
	           
	           
	           
	           final String[] LineArr = Line.split(":");
	           if(String.valueOf(LineArr[0]) == username && String.valueOf(LineArr[1]) == password) {
	        	   loggedin = true;
	        	   System.out.println("Logged in as: " + username);
	           }else if(LineArr[0] == username && LineArr[1] != password) {
	        	   loggedin = false;
	        	   System.out.println("Invalid password " + password);
	           }else if(LineArr[0] != username && LineArr[1] == password) {
	        	   loggedin = false;
	        	   System.out.println("Invalid username " + username);
	           }else if(LineArr[0] != username && LineArr[1] != password) {
	        	   loggedin = false;
	        	   System.out.println("Invalid credentials");
	           }
	          
	        }
		}
		
	

}
