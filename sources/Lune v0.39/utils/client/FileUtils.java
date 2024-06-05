package me.superskidder.lune.utils.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import me.superskidder.lune.manager.ModuleManager;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.values.Value;

public class FileUtils {

    public static String readFile(String path){
        StringBuilder result = new StringBuilder();
        try {
            File file = new File(path);
            if(!file.exists()){
                file.createNewFile();
            }
            FileInputStream fIn = new FileInputStream(file);
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fIn))) {
                String str;
                while((str = bufferedReader.readLine()) != null){
                    result.append(str);
                    result.append(System.lineSeparator());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static String readFile(File file){
        StringBuilder result = new StringBuilder();

        try {
            FileInputStream fIn = new FileInputStream(file);
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fIn))) {
                String str;
                while((str = bufferedReader.readLine()) != null){
                    result.append(str);
                    result.append(System.lineSeparator());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static List<File> getFilesFromDir(String dir) {
    	List<File> file = new ArrayList<>();
    	File startFolder = new File(dir);
    	
		File[] files = startFolder.listFiles();
		for(File tempFile : files) {
	    	if(startFolder.isDirectory()) {
	    		file.addAll(getFilesFromDir(tempFile.getAbsolutePath()));
	    	} else {
	    		file.add(tempFile);
	    	}
		}
    	return file;
    }

}
