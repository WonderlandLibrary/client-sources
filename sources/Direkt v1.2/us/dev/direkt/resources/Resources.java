package us.dev.direkt.resources;

import java.io.InputStream;

public class Resources {

	public static InputStream getResource(String fileName){
		return Resources.class.getResourceAsStream(fileName);
	}
	
}
