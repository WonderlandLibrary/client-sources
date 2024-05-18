package me.protocol_client.update;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import me.protocol_client.Protocol;

public class CheckUpdates {
	public static String newestversion = "https://www.dropbox.com/s/5uc7xhtwnrth08f/ProtocolVersion.txt?raw=1";
	public static int newestversionnumber;
	
	public static boolean shouldUpdate = false;
	
	public static void StartUp(){
		getNewestVersion();
		newestversionnumber = Integer.parseInt(downloadString(newestversion));
	}
	
	public static void getNewestVersion() {
		try {
			if(Integer.parseInt(downloadString(newestversion)) > Protocol.version){
				shouldUpdate = true;
			}
		} catch (Exception e) {
			shouldUpdate = false;
		}
	}
	public static String downloadString(String link){
		try {
			URL url = new URL(link);
			BufferedReader result = new BufferedReader(new InputStreamReader(url.openStream()));
			return result.readLine();
		} catch (Exception e) {
		}
		return "Unbale to connet to webpage!";
	}
}
