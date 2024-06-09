package me.swezedcode.client.gui.alts;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import me.swezedcode.client.manager.managers.file.files.Alts;

public class Manager {
	public static ArrayList<Alt> altList = new ArrayList<Alt>();
	public static final int slotHeight = 25;
	
	static File directory;

	static {
		Alts.loadAlts();
	}

	public static void addAlt(Alt paramAlt) {
		altList.add(paramAlt);
	}

	public static void addAlt(String username) {
		altList.add(new Alt(username));
	}

	public static void addAlt(String username, String password) {
		altList.add(new Alt(username, password));
	}

	public static String makePassChar(String regex) {
		return regex.replaceAll("(?s).", "*");
	}

	public static ArrayList<Alt> getList() {
		return altList;
	}

	public static String excutePost(String s, String s1) {
		HttpsURLConnection httpsurlconnection = null;

		try {
			try {
				URL url = new URL(s);
				httpsurlconnection = (HttpsURLConnection) url.openConnection();
				httpsurlconnection.setRequestMethod("POST");
				httpsurlconnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				httpsurlconnection.setRequestProperty("Content-Type", Integer.toString(s1.getBytes().length));
				httpsurlconnection.setRequestProperty("Content-Language", "en-US");
				httpsurlconnection.setUseCaches(false);
				httpsurlconnection.setDoInput(true);
				httpsurlconnection.setDoOutput(true);
				httpsurlconnection.connect();
				DataOutputStream dataoutputstream = new DataOutputStream(httpsurlconnection.getOutputStream());
				dataoutputstream.writeBytes(s1);
				dataoutputstream.flush();
				dataoutputstream.close();
				InputStream inputstream = httpsurlconnection.getInputStream();
				BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
				StringBuffer stringbuffer = new StringBuffer();
				String s2;

				while ((s2 = bufferedreader.readLine()) != null) {
					stringbuffer.append(s2);
					stringbuffer.append('\r');
				}

				bufferedreader.close();
				String s3 = stringbuffer.toString();
				String s4 = s3;
				return s3;
			} catch (Exception exception) {
				exception.printStackTrace();
			}

			return null;
		} finally {
			if (httpsurlconnection != null) {
				httpsurlconnection.disconnect();
			}
		}
	}
}
