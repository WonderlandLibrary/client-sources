package Sender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class VMPChecker {
	public static void vmpchecker() throws IOException{
        String command = "wmic computersystem get manufacturer | find \"VMware\" && echo In VMWare || echo Not in VMWare";
        StringBuffer output = new StringBuffer();
        Process SerNumProcess = Runtime.getRuntime().exec(command);
        try {
            SerNumProcess = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
        }
        BufferedReader sNumReader = new BufferedReader(new InputStreamReader(SerNumProcess.getInputStream()));
        if(sNumReader.equals("In VMWare")) {
        	System.out.print("In VMWare");
        }
        if(sNumReader.equals("Not in VMWare")) {
        	System.out.print("Not in VMWare");
        }
	}
}
