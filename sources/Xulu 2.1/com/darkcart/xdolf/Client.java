package com.darkcart.xdolf;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import com.darkcart.xdolf.clickgui.XuluGuiClick;
import com.darkcart.xdolf.fonts.Fonts;
import com.darkcart.xdolf.mods.Hacks;
import com.darkcart.xdolf.util.FileManager;
import com.darkcart.xdolf.util.FriendManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.StringUtils;

public class Client {

	public static ArrayList<Module> modules = new ArrayList<Module>();
	public static ArrayList<String> enabledModuleNames = new ArrayList<String>();
	public static Minecraft mc = Minecraft.getMinecraft();
	public static ScaledResolution gameResolution = new ScaledResolution(Wrapper.getMinecraft());
	public static ArrayList<String> friends = new ArrayList<String>();

	public static final String CLIENT_NAME = "§9Xulu";
	public static final String ANTICHEAT_PREFIX = "§9Xulu §cAnti-Cheat";
	public static final String CLIENT_VERSION = "2.1";
	
	public static HashMap<String, String> vTable = new HashMap<String, String>();

	public static void onStart() {

		try {
			System.out.println("[Xulu] Loading Xulu");
			vTable.put(StringUtils.stripControlCodes(CLIENT_NAME),
					StringUtils.stripControlCodes(CLIENT_NAME) + " v" + CLIENT_VERSION);
			vTable.put("minecraft", "Minecraft 1.11.2");

			Wrapper.hacks = new Hacks();
			Fonts.loadFonts();

			Collections.sort(Hacks.hackList, new Comparator<Module>() {

				@Override
				public int compare(Module m, Module m1) {
					if (m.getName() == null) {
						return (m1.getName() == null) ? 0 : +1;
					} else {
						return (m1.getName() == null) ? -1 : m.getName().compareTo(m1.getName());
					}
				}
			});

			Wrapper.friendManager = new FriendManager();
			Wrapper.fileManager = new FileManager();
			Wrapper.clickGui = new XuluGuiClick();

			System.out.println("[Xulu] Loading Xulu");
		} catch (Exception err) {
			System.out.println("[Xulu] Xulu failed to load. Contact SLiZ_D_2017" + err.toString());
			err.printStackTrace();

			String logString = "FT|CrashLog\r\n[PLAIN]\r\n---Begin plain text---\r\n";
			logString += "Console Log:\r\n";
			logString += "[Xulu] Failed to initialise Xulu! Expect problems! " + err.toString() + "\r\n\r\n";
			for (StackTraceElement ele : err.getStackTrace()) {
				logString += ele.getClassName() + "|" + ele.getLineNumber() + "  " + ele.toString() + "\r\n";
			}
			Wrapper.getFileManager().writeCrash(logString);
		}
	}

	public static String downloadString(String uri) {
		try {
			URL url = new URL(uri);
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

			String text = "";

			String line = "";
			while ((line = reader.readLine()) != null) {
				String curLine = line;
				text += curLine;
			}
			return text;
		} catch (Exception e) {
			return "Failed to retrieve string.";
		}
	}

	public static String wrap(String in, int len) {
		in = in.trim();
		if (in.length() < len)
			return in;
		if (in.substring(0, len).contains("\n"))
			return in.substring(0, in.indexOf("\n")).trim() + "\n\n" + wrap(in.substring(in.indexOf("\n") + 1), len);
		int place = Math.max(Math.max(in.lastIndexOf(" ", len), in.lastIndexOf("\t", len)), in.lastIndexOf("-", len));
		return in.substring(0, place).trim() + "\n" + wrap(in.substring(place), len);
	}
}