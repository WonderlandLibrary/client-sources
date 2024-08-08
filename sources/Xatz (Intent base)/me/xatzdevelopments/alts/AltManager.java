package me.xatzdevelopments.alts;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.xatzdevelopments.Xatz;


public class AltManager {
	
	static ArrayList<Alt> alts = new ArrayList<Alt>();
	
	public static me.xatzdevelopments.alts.Alt lastAlt;
	
	public static ArrayList<Alt> getAlts() {
		return alts;
	}
	
	public void addAlt(Alt alt) {
		alts.add(alt);
	}
	


    public void setLastAlt(Alt alt2) {
        lastAlt = alt2;
    }

	public void saveAlts() {
		System.out.println("Saving alts...");
		JsonObject json = new JsonObject();
		int i = 0;
		for (Alt alt : alts) {
			JsonObject altJson = new JsonObject();
			altJson = alt.saveToJson(altJson);
			json.add(String.valueOf(i), altJson);
			i++;
		}
		try {
			FileWriter fw = new FileWriter(Xatz.getFileMananger().altsDir);
			fw.write(json.toString());
			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadAlts() {
	    JSONParser parser = Xatz.getFileMananger().parser;
		Object obj = null;
		try {
			obj = parser.parse(new FileReader(Xatz.getFileMananger().altsDir));
			JSONObject json = (JSONObject) obj;
			for(int i = 0; i < 9999; i++) {
				JSONObject altObject = (JSONObject) json.get(String.valueOf(i));
				String mask = (String) altObject.get("name");
				String password = (String) altObject.get("password");
					Alt newAlt = new Alt(mask, password);
					Xatz.getAltManager().addAlt(newAlt);
				
			}
		} catch (Exception e) {
    
//			e.printStackTrace();
			System.out.println("Alt manager is feeling gay");
		}
	}
	
}