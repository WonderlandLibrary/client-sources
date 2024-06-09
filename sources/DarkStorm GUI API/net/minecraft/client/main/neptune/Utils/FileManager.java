package net.minecraft.client.main.neptune.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Util;


public class FileManager {
    public static ArrayList<CustomFile> Files = new ArrayList<CustomFile>();
    private static File directory = null;
    private static File moduleDirectory = null;
    public FileManager(){
        if (Util.getOSType() == Util.EnumOS.LINUX) {
            directory = new File(Minecraft.getMinecraft().mcDataDir, "/libraries/pk");
            moduleDirectory = new File(Minecraft.getMinecraft().mcDataDir, "/libraries/pk");
        } else {
            directory = new File(Minecraft.getMinecraft().mcDataDir + "\\libraries\\pk");
            moduleDirectory = new File(Minecraft.getMinecraft().mcDataDir + "\\libraries\\pk");
        }
    	makeDirectories();
    	Files.add(new Binds("Binds", true ,true));
    }
    public void loadFiles(){
    	for(CustomFile f : Files){    		
    		try{ 
    			if(f.loadOnStart())
    			f.loadFile();
    		}catch(Exception e){e.printStackTrace();}
    	}
    }
    public void saveFiles(){
    	for(CustomFile f : Files){    		
    		try{ 
    			f.saveFile();
    		}catch(Exception e){e.printStackTrace();}
    	}
    }
    public CustomFile getFile(Class<? extends CustomFile> clazz){
		for(CustomFile file : Files){
			if(file.getClass() == clazz){
				return file;
			}
		}
		return null;	
    }
    public void makeDirectories(){
    	if (!directory.exists()){
    		if (directory.mkdir()) 
    			System.out.println("Directory is created!");
    		else 
    			System.out.println("Failed to create directory!");			
		}
    	if (!moduleDirectory.exists()){
    		if (moduleDirectory.mkdir()) 
    			System.out.println("Directory is created!");
    		else 
    			System.out.println("Failed to create directory!");			
		}
    }
    public static abstract class CustomFile {
        private final java.io.File file;
        private final String name;
        private boolean load;
        
        public CustomFile(String name, boolean Module, boolean loadOnStart) {
            this.name = name;
            this.load = loadOnStart;
            if(Module) file = new java.io.File(moduleDirectory, name + ".txt");
            else file = new java.io.File(directory, name + ".txt");
            if (!file.exists()){
            	 try { saveFile(); } catch(Exception e){ e.printStackTrace(); }
            }
        }
        public <T> T getValue(T value) {    
			return null;
        }
      
        public final java.io.File getFile() {
            return file;
        }
        private boolean loadOnStart(){
        	return load;
        }
        public final String getName() {
            return name;
        }
        public abstract void loadFile() throws IOException;

        public abstract void saveFile() throws IOException;
         
    }   
}
