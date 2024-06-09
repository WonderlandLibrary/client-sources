package net.minecraft.client.main.neptune.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import net.minecraft.client.main.neptune.Neptune;
import net.minecraft.client.main.neptune.Mod.Mod;
import net.minecraft.client.main.neptune.Utils.FileManager.CustomFile;

import org.lwjgl.input.Keyboard;


public class Binds extends CustomFile {
    public Binds(String name, boolean Module, boolean loadOnStart) {
        super(name, Module, loadOnStart);
    }
    @Override
    public void loadFile() throws IOException {   	
    	BufferedReader variable9 = new BufferedReader(new FileReader(this.getFile()));
    	String line;
    	while ((line = variable9.readLine()) != null){
    		int i = line.indexOf(":");
    		if (i >= 0){
    			String module = line.substring(0, i).trim();
    			String key = line.substring(i + 1).trim();
    			Mod m = Neptune.getWinter().theMods.getMod(module);
                if(!key.isEmpty() && m != null)
    			    m.setBind(Keyboard.getKeyIndex(key.toUpperCase()));
    		}
    	}                       
    	variable9.close();
    	System.out.println("Loaded " + this.getName() + " File!");        
    }
    @Override
    public void saveFile() throws IOException {        
    	PrintWriter variable9 = new PrintWriter(new FileWriter(this.getFile()));
    	for (Mod m : Neptune.getWinter().theMods.getMods()) {
    		variable9.println(m.getName() + ":" + Keyboard.getKeyName(m.getBind()));
    	}
    	variable9.close();          
    }
}
