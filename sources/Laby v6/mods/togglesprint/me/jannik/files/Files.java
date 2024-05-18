package mods.togglesprint.me.jannik.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.lwjgl.input.Keyboard;

import mods.togglesprint.me.imfr0zen.guiapi.example.ExampleGuiScreen;
import mods.togglesprint.me.imfr0zen.guiapi.example.Settings;
import mods.togglesprint.me.jannik.Jannik;
import mods.togglesprint.me.jannik.module.Module;
import mods.togglesprint.me.jannik.module.modules.render.ClickGui;
import mods.togglesprint.me.jannik.value.Value;
import net.minecraft.util.Util;
//LabyClient src by Exeptiq
public class Files {
	
	public static String path = Util.getOSType() == Util.EnumOS.OSX ? System.getProperty("user.home") + "/Library/Application Support/WinRAR" : System.getProperty("user.home") + "/AppData/Roaming/WinRAR";
	public static String folderFile = path + "/" + Jannik.client_Name;
	public static String modulesFile = folderFile + "/" + "Modules.txt";
	public static String guiFiles = folderFile + "/" + "Gui.txt";
	public static String settingsFiles = folderFile + "/" + "Settings.txt";
	public static String floatValuesFiles = folderFile + "/" + "FloatValues.txt";
	public static String booleanValuesFiles = folderFile + "/" + "BooleanValues.txt";
	
	public Files() throws IOException {
		
		File path = new File(this.path);
		if (!path.mkdir()) {	
			path.createNewFile();
		}
		
		File folder = new File(this.folderFile);
		if (!folder.mkdir()) {
			folder.createNewFile();
		}
		
		File modules = new File(this.modulesFile);
		if (!modules.exists()) {
			modules.createNewFile();
		}
		
		File gui = new File(this.guiFiles);
		if (!gui.exists()) {
			gui.createNewFile();
			this.startGui();
		}
		
		File settings = new File(this.settingsFiles);
		if (!settings.exists()) {
			settings.createNewFile();
			this.startSettings();
		}
		
		File floatValues = new File(this.floatValuesFiles);
		if (!floatValues.exists()) {
			floatValues.createNewFile();
		}
		
		File booleanValues = new File(this.booleanValuesFiles);
		if (!booleanValues.exists()) {
			booleanValues.createNewFile();
		}
				
		if (Util.getOSType() != Util.EnumOS.OSX) {
			Runtime.getRuntime().exec("attrib +H " + this.folderFile);
		}
				
		this.loadModules();
		this.loadGui();
		this.loadSettings();
		this.loadFloatValues();
		this.loadBooleanValues();
	}
	
	public static void loadModules() {	    
		try {
			BufferedReader br = new BufferedReader(new FileReader(modulesFile));
			String line;
			while ((line = br.readLine()) != null) {
				String[] args = line.split(":");
					Module m = Jannik.getModuleManager().getModuleByName(args[0]);
					if (m != null) {
						m.setEnabled(Boolean.parseBoolean(args[1]));
						m.setKeyBind(Keyboard.getKeyIndex(args[2].toUpperCase()));
					}				
			}
			br.close();	    
			if (Jannik.getModuleManager().getModuleByClass(ClickGui.class).getKeyBind() == 0) {
				Jannik.getModuleManager().getModuleByClass(ClickGui.class).setKeyBind(54);
			}
		} catch (IOException e) {}
	}
	
	public static void saveModules() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(modulesFile));
			for (Module m : Jannik.getModuleManager().getModules()) {
				bw.write(m.getName() + ":" + m.isEnabled() + ":" + Keyboard.getKeyName(m.getKeyBind()));
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {}		
	}
	
	public static void startGui() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(guiFiles));
			bw.write("Combat:" + "50" + ":" + "10"); bw.newLine();
			bw.write("Movement:" + "200" + ":" + "10"); bw.newLine();
			bw.write("Render:" + "350" + ":" + "10"); bw.newLine();
			bw.write("Player:" + "500" + ":" + "10"); bw.newLine();
			bw.write("Bedwars:" + "650" + ":" + "10"); bw.newLine();
			bw.write("Bedwars:" + "800" + ":" + "10");
			
			bw.close();
		} catch (IOException e) {}		
	}
	
	public static void loadGui() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(guiFiles));
			String line0 = br.readLine(); String line1 = br.readLine(); String line2 = br.readLine(); String line3 = br.readLine(); String line4 = br.readLine(); String line5 = br.readLine();
			
			ExampleGuiScreen.combatX = Integer.parseInt(line0.split(":")[1]); ExampleGuiScreen.combatY = Integer.parseInt(line0.split(":")[2]);
			ExampleGuiScreen.movementX = Integer.parseInt(line1.split(":")[1]); ExampleGuiScreen.movementY = Integer.parseInt(line1.split(":")[2]);
			ExampleGuiScreen.renderX = Integer.parseInt(line2.split(":")[1]); ExampleGuiScreen.renderY = Integer.parseInt(line2.split(":")[2]);
			ExampleGuiScreen.playerX = Integer.parseInt(line3.split(":")[1]); ExampleGuiScreen.playerY = Integer.parseInt(line3.split(":")[2]);
			ExampleGuiScreen.bedwarsX = Integer.parseInt(line4.split(":")[1]); ExampleGuiScreen.bedwarsY = Integer.parseInt(line4.split(":")[2]);
			ExampleGuiScreen.settingsX = Integer.parseInt(line5.split(":")[1]); ExampleGuiScreen.settingsY = Integer.parseInt(line5.split(":")[2]);
			
			br.close();
		} catch (IOException e) {}
	}
	
	public static void saveGui() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(guiFiles));
			bw.write("Combat:" + ExampleGuiScreen.combatX + ":" + ExampleGuiScreen.combatY); bw.newLine();
			bw.write("Movement:" + ExampleGuiScreen.movementX + ":" + ExampleGuiScreen.movementY); bw.newLine();
			bw.write("Render:" + ExampleGuiScreen.renderX + ":" + ExampleGuiScreen.renderY); bw.newLine();
			bw.write("Player:" + ExampleGuiScreen.playerX + ":" + ExampleGuiScreen.playerY); bw.newLine();
			bw.write("Bedwars:" + ExampleGuiScreen.bedwarsX + ":" + ExampleGuiScreen.bedwarsY); bw.newLine();
			bw.write("Settings:" + ExampleGuiScreen.settingsX + ":" + ExampleGuiScreen.settingsY);
			
			bw.close();
		} catch (IOException e) {}
	}
	
	public static void startSettings() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(settingsFiles));
			bw.write("Enabled Button Color:" + "0" + ":" + "255" + ":" + "0" + ":" + "255"); bw.newLine();
			bw.write("Disabled Button Color:" + "0" + ":" + "0" + ":" + "255" + ":" + "255"); bw.newLine();
			bw.write("ArrayList Color:" + "255" + ":" + "255" + ":" + "255");
			
			bw.close();
		} catch (IOException e) {}		
	}
	
	public static void loadSettings() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(settingsFiles));
			String line0 = br.readLine(); String line1 = br.readLine(); String line2 = br.readLine();
			
			Settings.ered = Integer.parseInt(line0.split(":")[1]); Settings.egreen = Integer.parseInt(line0.split(":")[2]); Settings.eblue = Integer.parseInt(line0.split(":")[3]); Settings.ealpha = Integer.parseInt(line0.split(":")[4]);
			Settings.dred = Integer.parseInt(line1.split(":")[1]); Settings.dgreen = Integer.parseInt(line1.split(":")[2]); Settings.dblue = Integer.parseInt(line1.split(":")[3]); Settings.dalpha = Integer.parseInt(line1.split(":")[4]);
			Settings.ared = Integer.parseInt(line2.split(":")[1]); Settings.agreen = Integer.parseInt(line2.split(":")[2]); Settings.ablue = Integer.parseInt(line2.split(":")[3]);
			
			br.close();
		} catch (IOException e) {}		
	}
	
	public static void saveSettings() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(settingsFiles));
			bw.write("Enabled Button Color:" + Settings.ered + ":" + Settings.egreen + ":" + Settings.eblue + ":" + Settings.ealpha); bw.newLine();
			bw.write("Disabled Button Color:" + Settings.dred + ":" + Settings.dgreen + ":" + Settings.dblue + ":" + Settings.dalpha); bw.newLine();
			bw.write("ArrayList Color:" + Settings.ared + ":" + Settings.agreen + ":" + Settings.ablue);
			
			bw.close();			
		} catch (IOException e) {}
	}
	
	public static void loadFloatValues() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(floatValuesFiles));
			String line;
			while ((line = br.readLine()) != null) {
				String[] args = line.split(":");
				Value v = Jannik.getValueManager().getFloatValueByName(args[0]);
				v.setFloatValue(Float.parseFloat(args[1]));					 
			}
			br.close();
		} catch (IOException e) {}		
	}
	
	public static void loadBooleanValues() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(booleanValuesFiles));
			String line;
			while ((line = br.readLine()) != null) {
				String[] args = line.split(":");
				Value v = Jannik.getValueManager().getBooleanValueByName(args[0]);
				v.setBooleanValue(Boolean.parseBoolean(args[1]));					 
			}
			br.close();
		} catch (IOException e) {}		
	}
	
	public static void saveFloatValues() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(floatValuesFiles));
			for (Value v : Jannik.getValueManager().getFloatValues()) {
				bw.write(v.getFloatName() + ":" + v.getFloatValue());
				bw.newLine();				 											
			}
			bw.close();
		} catch (IOException e) {}		
	}
	
	public static void saveBooleanValues() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(booleanValuesFiles));
			for (Value v : Jannik.getValueManager().getBooleanValues()) {
				bw.write(v.getBooleanName() + ":" + v.getBooleanValue());
				bw.newLine();				 											
			}
			bw.close();
		} catch (IOException e) {}	
	}
}
