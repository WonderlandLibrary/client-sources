package Hydro.auth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Hydro.util.HWID;

public class Authentication {

	
	
	public static void authenticate()
	{
		try {
			
			String hwid = HWID.getHWID();
			
			
		} catch (Exception e) {
			
			
		}
	}
	
	public static boolean detectWireshark() throws IOException
	{
		boolean detected = false;
		
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("tasklist.exe");
        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        
        while ((line = reader.readLine()) != null) 
        {
            if (line.toLowerCase().contains("wireshark")) 
            {
                return true;
            } 
        }
        
		return false;
        
        
	}
}
