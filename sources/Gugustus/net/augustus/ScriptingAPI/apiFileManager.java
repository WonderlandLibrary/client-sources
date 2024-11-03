package net.augustus.ScriptingAPI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class apiFileManager {
	
	public static File ROOT_DIR = new File("gugustus/scripts");
	public static ArrayList<Script> scriptsFound = new ArrayList<Script>();
	
	public void init() {
		if(!ROOT_DIR.exists()) {
			ROOT_DIR.mkdirs();
		}else {
			if(ROOT_DIR.listFiles().length > 0) {
				for(File fle : ROOT_DIR.listFiles()) {
					if(fle.getName().endsWith(".js")) {
						scriptsFound.add(new Script(fle.getName(), 1.0, this.getContentFromFile(fle)));
					}
				}
			}
			if(!scriptsFound.isEmpty()) {
				for(Script s : scriptsFound)
					System.out.println(s.getName());
			}
		}
	}
	
	public static String getContentFromFile(File file) {
        StringBuilder content = new StringBuilder();
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
        	e.printStackTrace();
        }
        
        return content.toString();
    }

}
