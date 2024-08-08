package me.xatzdevelopments.manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.parser.JSONParser;

import com.google.common.io.Files;
import com.google.gson.JsonObject;

import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.clickgui.ClickGUI;
import me.xatzdevelopments.clickgui.Panel;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.settings.BooleanSetting;
import me.xatzdevelopments.settings.KeybindSetting;
import me.xatzdevelopments.settings.ModeSetting;
import me.xatzdevelopments.settings.NumberSetting;
import me.xatzdevelopments.settings.Setting;
import me.xatzdevelopments.util.ModulesUtils;
import net.minecraft.client.Minecraft;

public class FileManager {
	
	public ClickGUI clickgui;

	public JSONParser parser = new JSONParser();

	private Minecraft mc = Minecraft.getMinecraft();

	public File xatzDir = new File(mc.mcDataDir, "Xatz");

	//public File windowDataDir = new File(xatzDir, "windowData.json");

	public File keybindsDir = new File(xatzDir, "keybinds.txt");

	public File friendsDir = new File(xatzDir, "friends.txt");

	public File settingsDir = new File(xatzDir, "settings.txt");
	
	public File windowdataDir = new File(xatzDir, "windata.txt");
	
	public File altsDir = new File(xatzDir, "alts.json");

	//public File autoBuildDir = new File(xatzDir, "autobuild");
	
	public File configdir = new File(xatzDir, "configs");
	
	//public File configfile = new File(configdir, commandfile+".json");
	
	//public File firstStartDir = new File(xatzDir, ".start");

	//public boolean saveKeyBinds = true;

	public void copyFiles(File[] dir) throws IOException {
		for (File file : dir) {
			if (file.isDirectory()) {
				copyFiles(file.listFiles());
			} else {
				Files.copy(file, new File(file.getAbsolutePath().replaceAll("Xatz", "Xatz")));
			}
		}
	}

	public void load() throws IOException {
		if (!xatzDir.exists()) {
			xatzDir.mkdir();
		}
		//if (!autoBuildDir.exists()) {
		//	autoBuildDir.mkdir();
		//}
		if (!configdir.exists()) {
			configdir.mkdir();
		}
		if (!friendsDir.exists()) {
			friendsDir.createNewFile();
		}
		if (!keybindsDir.exists()) {
			keybindsDir.createNewFile();
		}
		
		if (!settingsDir.exists()) {
			settingsDir.createNewFile();
		}
		if(!altsDir.exists()) {
			altsDir.createNewFile();
		}
		if(!altsDir.exists()) {
			altsDir.createNewFile();
		}
		if(!windowdataDir.exists()) {
			windowdataDir.createNewFile();
		}
		//if(!firstStartDir.exists()) {
		//	Xatz.firstStart = true;
		//	firstStartDir.createNewFile();
		//	Files.write("This file indicates that Xatz has been launched before", firstStartDir, Charset.forName("UTF-8"));
		//}
	}
	
	public void loadSettings() {
		 //final File file = new File("Xatz" , "settings.txt");
         //FileReader file = new FileReader(Xatz.getFileMananger().settingsDir);
         final boolean exists = settingsDir.exists();
         if (!exists) {
             return;
         }
         Scanner scan = null;
         try {
             scan = new Scanner(settingsDir);
         }
         catch (IOException e2) {
             e2.printStackTrace();
             //this.toggled = false;
         }
         if (scan == null) {
             //this.toggled = false;
         }
         for(final Module m : Xatz.modules) {
        	 ModulesUtils.DisableAll(m.name);
         }
         
         while (scan.hasNextLine()) {
             String Line = scan.nextLine();
             if (Line == null) {
                 //this.toggled = false;
                 return;
             }
             if (Line.contains("Version=")) {
                 Line = Line.replace("Version=", "");
                 if (Integer.getInteger(Line) != Integer.getInteger(Xatz.version)) {
                     //System.out.println("Starting to load settings...");
                     //System.out.println(String.valueOf(Line) + " " + Xatz.version);
                     //this.toggled = false;
                     return;
                 }
             }
             if (Line.contains("SetModeSetting(")) {
                 Line = Line.replace("SetModeSetting(", "");
                 Line = Line.replace(")", "");
                 Line = Line.replace("\"", "");
                 //Line = Line.replace(" ", "");
                 final String[] LineArr = Line.split(",");
                 //System.out.println("\"" + String.valueOf(LineArr[1]).toString() + "\"");
                 Xatz.getModuleByName(String.valueOf(LineArr[0]).toString().intern()).getModeSetting(String.valueOf(LineArr[1]).toString().intern()).set(Integer.valueOf(LineArr[2]));
                 //ModulesUtils.SetSettingsModeWithNumberInt(Integer.valueOf(LineArr[0]), Integer.valueOf(LineArr[1]), Integer.valueOf(LineArr[2]));
             }
             if (Line.contains("SetBooleanSetting(")) {
                 Line = Line.replace("SetBooleanSetting(", "");
                 Line = Line.replace(")", "");
                 Line = Line.replace("\"", "");
                 //Line = Line.replace(" ", "");
                 final String[] LineArr = Line.split(",");
                 Xatz.getModuleByName(String.valueOf(LineArr[0]).toString().intern()).getBooleanSetting(String.valueOf(LineArr[1]).toString().intern()).setEnabled(Boolean.valueOf(LineArr[2]));
                 //ModulesUtils.SetSettingsBooleanWithNumber(Integer.valueOf(LineArr[0]), Integer.valueOf(LineArr[1]), Boolean.valueOf(LineArr[2]));
             }
             if (Line.contains("SetNumberSetting(")) {
                 try{
                 Line = Line.replace("SetNumberSetting(", "");
                 Line = Line.replace(")", "");
                 Line = Line.replace("\"", "");
                 //Line = Line.replace(" ", "");
                 final String[] LineArr = Line.split(",");
                 Xatz.getModuleByName(String.valueOf(LineArr[0]).toString().intern()).getNumberSetting(String.valueOf(LineArr[1]).toString().intern()).setValue(Double.valueOf(LineArr[2]));
                 //ModulesUtils.SetSettingsNumberWithNumber(Integer.valueOf(LineArr[0]), Integer.valueOf(LineArr[1]), Float.valueOf(LineArr[2]));
                 }
                 catch(Exception e){
                 }
             }
             if (Line.contains("EnableModule(")) {
                 Line = Line.replace("EnableModule(", "");
                 Line = Line.replace(")", "");
                 Line = Line.replace(" ", "");
                 //System.out.println("\"" + String.valueOf(Line).toString() + "\"");
                 Xatz.getModuleByName(String.valueOf(Line).toString().intern()).toggled = true;
             }
             }
	}
	
	public void saveSettings() {
		 //final File file = new File("RiseClientSettings.txt");
        PrintWriter pw = null;
        try {
            final FileWriter fw = new FileWriter(Xatz.getFileMananger().settingsDir);
            if (fw != null) {
                pw = new PrintWriter(fw);
            }
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        if (pw == null) {
            //this.toggled = false;
        }
        int ModuleNumber = 0;
        pw.println("Version=" + Xatz.version);
        for (final Module m : Xatz.modules) {
            if (m.isEnabled()) {
                pw.println("EnableModule(" + m.name + ")");
            }
            int SettingsNumber = 0;
            for (final Setting settings : m.settings) {
                if (settings instanceof BooleanSetting) {
                    pw.println("SetBooleanSetting(" + m.name + "," + settings.name + "," + ((BooleanSetting)settings).isEnabled() + ")");
                }
                if (settings instanceof NumberSetting) {
                    pw.println("SetNumberSetting(" + m.name + "," + settings.name + "," + ((NumberSetting)settings).getValue() + ")");
                }
                if (settings instanceof ModeSetting) {
                    
                    pw.println("SetModeSetting(" + m.name + "," + settings.name + "," + ((ModeSetting)settings).getIndex() + ")");
                }
               
                ++SettingsNumber;
            }
            ++ModuleNumber;
        }
        pw.close();
	}
	
	public void loadKeybinds() {
		 //final File file = new File("Xatz" , "settings.txt");
        //FileReader file = new FileReader(Xatz.getFileMananger().settingsDir);
        final boolean exists = keybindsDir.exists();
        if (!exists) {
            return;
        }
        Scanner scan = null;
        try {
            scan = new Scanner(keybindsDir);
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
            
            if (!Line.contains("Keybind(")) {
                continue;
            }
            Line = Line.replace("Keybind(", "");
            Line = Line.replace(")", "");
            Line = Line.replace("\"", "");
            Line = Line.replace(" ", "");
            final String[] LineArr = Line.split(",");
            Xatz.getModuleByName(String.valueOf(LineArr[0]).toString().intern()).keycode.setKeyCode(Integer.valueOf(LineArr[2]));
            //ModulesUtils.SetSettingsKeyCodeWithNumber(Integer.valueOf(LineArr[0]), Integer.valueOf(LineArr[1]), Integer.valueOf(LineArr[2]));
        }
	}

	
	public void saveKeybinds() {
		 //final File file = new File("RiseClientSettings.txt");
       PrintWriter pw = null;
       try {
           final FileWriter fw = new FileWriter(Xatz.getFileMananger().keybindsDir);
           if (fw != null) {
               pw = new PrintWriter(fw);
           }
       }
       catch (IOException e2) {
           e2.printStackTrace();
       }
       if (pw == null) {
           //this.toggled = false;
       }
       int ModuleNumber = 0;
       pw.println("Version=" + Xatz.version);
       for (final Module m : Xatz.modules) {
           
           int SettingsNumber = 0;
           for (final Setting settings : m.settings) {
              
               if (settings instanceof KeybindSetting) {
                   
                   pw.println("Keybind(" + m.name + "," + settings.name + "," + ((KeybindSetting)settings).code + ")");
               }
               ++SettingsNumber;
           }
           ++ModuleNumber;
       }
       pw.close();
	}
	
	public void loadSettingsfromFile(String filename) {
		 final File configfile = new File(configdir , filename + ".txt");
        //FileReader file = new FileReader(Xatz.getFileMananger().settingsDir);
        final boolean exists = configfile.exists();
        if (!exists) {
            return;
        }
        Scanner scan = null;
        try {
            scan = new Scanner(configfile);
        }
        catch (IOException e2) {
            e2.printStackTrace();
            //this.toggled = false;
        }
        if (scan == null) {
            //this.toggled = false;
        }
        for(final Module m : Xatz.modules) {
       	 ModulesUtils.DisableAll(m.name);
        }
        
        while (scan.hasNextLine()) {
            String Line = scan.nextLine();
            if (Line == null) {
                //this.toggled = false;
                return;
            }
            if (Line.contains("Version=")) {
                Line = Line.replace("Version=", "");
                if (Integer.getInteger(Line) != Integer.getInteger(Xatz.version)) {
                    //System.out.println("Starting to load settings...");
                    //System.out.println(String.valueOf(Line) + " " + Xatz.version);
                    //this.toggled = false;
                    return;
                }
            }
            if (Line.contains("SetModeSetting(")) {
                Line = Line.replace("SetModeSetting(", "");
                Line = Line.replace(")", "");
                Line = Line.replace("\"", "");
                //Line = Line.replace(" ", "");
                final String[] LineArr = Line.split(",");
                //System.out.println("\"" + String.valueOf(LineArr[1]).toString() + "\"");
                Xatz.getModuleByName(String.valueOf(LineArr[0]).toString().intern()).getModeSetting(String.valueOf(LineArr[1]).toString().intern()).set(Integer.valueOf(LineArr[2]));
                //ModulesUtils.SetSettingsModeWithNumberInt(Integer.valueOf(LineArr[0]), Integer.valueOf(LineArr[1]), Integer.valueOf(LineArr[2]));
            }
            if (Line.contains("SetBooleanSetting(")) {
                Line = Line.replace("SetBooleanSetting(", "");
                Line = Line.replace(")", "");
                Line = Line.replace("\"", "");
                //Line = Line.replace(" ", "");
                final String[] LineArr = Line.split(",");
                Xatz.getModuleByName(String.valueOf(LineArr[0]).toString().intern()).getBooleanSetting(String.valueOf(LineArr[1]).toString().intern()).setEnabled(Boolean.valueOf(LineArr[2]));
                //ModulesUtils.SetSettingsBooleanWithNumber(Integer.valueOf(LineArr[0]), Integer.valueOf(LineArr[1]), Boolean.valueOf(LineArr[2]));
            }
            if (Line.contains("SetNumberSetting(")) {
                Line = Line.replace("SetNumberSetting(", "");
                Line = Line.replace(")", "");
                Line = Line.replace("\"", "");
                //Line = Line.replace(" ", "");
                final String[] LineArr = Line.split(",");
                Xatz.getModuleByName(String.valueOf(LineArr[0]).toString().intern()).getNumberSetting(String.valueOf(LineArr[1]).toString().intern()).setValue(Double.valueOf(LineArr[2]));
                //ModulesUtils.SetSettingsNumberWithNumber(Integer.valueOf(LineArr[0]), Integer.valueOf(LineArr[1]), Float.valueOf(LineArr[2]));
            }
            if (Line.contains("EnableModule(")) {
                Line = Line.replace("EnableModule(", "");
                Line = Line.replace(")", "");
                Line = Line.replace(" ", "");
                //System.out.println("\"" + String.valueOf(Line).toString() + "\"");
                Xatz.getModuleByName(String.valueOf(Line).toString().intern()).toggled = true;
            }
            }
	}
	
	public void saveSettingstoFile(String filename) {
		 final File file = new File(configdir, filename + ".txt");
       PrintWriter pw = null;
       try {
           //final FileWriter fw = new FileWriter(Xatz.getFileMananger().settingsDir);
           if (file != null) {
               pw = new PrintWriter(file);
           }
       }
       catch (IOException e2) {
           e2.printStackTrace();
       }
       if (pw == null) {
           //this.toggled = false;
       }
       int ModuleNumber = 0;
       pw.println("Version=" + Xatz.version);
       for (final Module m : Xatz.modules) {
           if (m.isEnabled()) {
               pw.println("EnableModule(" + m.name + ")");
           }
           int SettingsNumber = 0;
           for (final Setting settings : m.settings) {
               if (settings instanceof BooleanSetting) {
                   pw.println("SetBooleanSetting(" + m.name + "," + settings.name + "," + ((BooleanSetting)settings).isEnabled() + ")");
               }
               if (settings instanceof NumberSetting) {
                   pw.println("SetNumberSetting(" + m.name + "," + settings.name + "," + ((NumberSetting)settings).getValue() + ")");
               }
               if (settings instanceof ModeSetting) {
                   
                   pw.println("SetModeSetting(" + m.name + "," + settings.name + "," + ((ModeSetting)settings).getIndex() + ")");
               }
              
               ++SettingsNumber;
           }
           ++ModuleNumber;
       }
       pw.close();
	}
	
	
/*	public void saveAltsa() {

      PrintWriter pw = null;
      try {
          final FileWriter fw = new FileWriter(Xatz.getFileMananger().altsDir);
          if (fw != null) {
              pw = new PrintWriter(fw);
          }
      }
      catch (IOException e2) {
          e2.printStackTrace();
      }
      if (pw == null) {
          //this.toggled = false;
      }
      int ModuleNumber = 0;
      //pw.println("Version=" + Xatz.version);
      for (Alts alts : clickgui.panels) {
          
   	   pw.println(panel.title + "(" + panel.x + "," + panel.y + "," + panel.extended + ")");
          
      }
      pw.close();
	} */
	
	public void saveWinData() {
		 //final File file = new File("RiseClientSettings.txt");
       PrintWriter pw = null;
       try {
           final FileWriter fw = new FileWriter(Xatz.getFileMananger().windowdataDir);
           if (fw != null) {
               pw = new PrintWriter(fw);
           }
       }
       catch (IOException e2) {
           e2.printStackTrace();
       }
       if (pw == null) {
           //this.toggled = false;
       }
       int ModuleNumber = 0;
       //pw.println("Version=" + Xatz.version);
       for (Panel panel : clickgui.panels) {
           
    	   pw.println(panel.title + "(" + panel.x + "," + panel.y + "," + panel.extended + ")");
           
       }
       pw.close();
	}
	
	public void loadWinData() {
		 //final File file = new File("Xatz" , "settings.txt");
       //FileReader file = new FileReader(Xatz.getFileMananger().settingsDir);
       final boolean exists = windowdataDir.exists();
       if (!exists) {
           return;
       }
       Scanner scan = null;
       try {
           scan = new Scanner(windowdataDir);
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
           
           
           Line = Line.replace("(", " ");
           Line = Line.replace(")", "");
           Line = Line.replace(",", " ");
           Line = Line.replace("\"", "");
           //Line = Line.replace(" ", "");
           final String[] LineArr = Line.split(" ");
           for (Panel panel : clickgui.panels) {
        	   if(Line.contains(panel.title)) {
        		   panel.x = Double.valueOf(LineArr[1]);
        		   panel.y = Double.valueOf(LineArr[2]);
        		   panel.extended = Boolean.valueOf(LineArr[3]);
        	   }
        		   
        	   
           }
           
           
           }
	}
	public void saveAlts() {
		Xatz.getAltManager().saveAlts();
	}
	
	public void loadAlts() {
		Xatz.getAltManager().loadAlts();
	}
	
	
	
}