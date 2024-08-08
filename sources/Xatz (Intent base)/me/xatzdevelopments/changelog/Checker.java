package me.xatzdevelopments.changelog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.ui.GUI.GuiXatzAlert;
import me.xatzdevelopments.ui.GUI.GuiXatzUpdate;
import net.minecraft.client.Minecraft;

public class Checker {
	
	public static ArrayList<String> mouseNames = new ArrayList<String>();
	public static ArrayList<String> nameTagNames = new ArrayList<String>();
	public static HashMap<String, String> devTagNames = new HashMap<String, String>();
	public static String motd = null;
	public static String serverVersion = null;
	public static boolean promtedUpdate = false;
	public static boolean promtedAlert = false;
	public static boolean upToDate = true;
	public static boolean triedConnectToUpdate = false;
	public static boolean triedConnectToAlert = false;
	public static boolean changelogFailed = false;
	public static Elements changeLineElmts;

	public static boolean showupdatetext = true;
	
	
	public static Runnable serverInfoFetcher = new Runnable() { // This bitch was an actual pain -GreatZardasht 29/08/2020 13:34 British Time (GreatZardasht#4218 on discord) Edit: Napoleon ZoomberParts#4218 new discord
		@Override
		public void run() {
			try {
				Connection conn = Jsoup.connect("https://xenodochial-yonath-28d341.netlify.app/xatzclient/msg.html");
				Document doc = conn.get();
				Element mouseNamesElmt = doc.getElementById("xatzMouseNames");
				Element tagNamesElmt = doc.getElementById("xatzTagNames");
				Element devTagNamesElmt = doc.getElementById("xatzNames");
				Element versionElmt = doc.getElementById("xatzVersion");
				Element motdElmt = doc.getElementById("xatzMOTD");
				String[] mouseNames = mouseNamesElmt.html().split(";");
				String[] tagNames = tagNamesElmt.html().split(";");
				String[] devTags = devTagNamesElmt.html().split("%");
				//System.out.println(devTagNamesElmt.html());
				for (int i = 0; i < mouseNames.length; i++) {

					Checker.mouseNames.add(mouseNames[i]);
				}
				for (int i = 0; i < tagNames.length; i++) {

					Checker.nameTagNames.add(tagNames[i]);
				}
				for (int i = 0; i < devTags.length; i++) {
					//System.out.println(devTags[i].split(":")[0].replaceAll("&amp;", "§"));
					//System.out.println(devTags[i].split(":")[1].replaceAll("&amp;", "§"));
					Checker.devTagNames.put(devTags[i].split(":")[0].replaceAll("&amp;", "§"), devTags[i].split(":")[1].replaceAll("&amp;", "§"));
				}
				String version = versionElmt.html();
				String motd = motdElmt.html();
				Checker.motd = motd.replaceAll("&amp;", "§");
				Checker.serverVersion = version;
				// System.err.println("Server version:" + Xatz.serverVersion);
				// System.err.println("Client version:" +
			   Checker.getClientVersion();
				if (!Checker.serverVersion.trim().equals(Xatz.getClientmultiVersion()) && !Checker.promtedUpdate) {
					Minecraft.getMinecraft().addScheduledTask(new Runnable() {
						@Override
						public void run() {
							Checker.promtedUpdate = true;
							Minecraft.getMinecraft().displayGuiScreen(new GuiXatzUpdate());
						}
					});
				} else {
					System.err.println("Client version up to date!");
					Checker.triedConnectToUpdate = true;
					Checker.showupdatetext = false;
				}
				conn = Jsoup.connect("https://xenodochial-yonath-28d341.netlify.app/xatzclient/changelog.html");
				try {
					doc = conn.get();
					Element wsite_content = doc.getElementById("wsite-content");
					if (wsite_content != null) {
						for (Element el : wsite_content.children()) {
							//System.out.println(el.text());
						}
						Elements changeLinesElmts = wsite_content.child(0).child(0).child(0).child(0).child(0).child(2).child(0)
								.children();
						//System.out.println("SDADS" + changeLinesElmts.get(0).attributes().toString());
						Checker.changeLineElmts = changeLinesElmts;
					} else {
						System.err.println("Failed to get changelog from the server! (changeElmt == null)");
						Checker.changelogFailed = true;
					}
				} catch (Exception e) {
					e.printStackTrace();
					Checker.changelogFailed = true;
				}

			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("Failed to get information from the server!");
				Checker.triedConnectToUpdate = true;
			}
		}
	};
	public static Runnable alertInfoFetcher = new Runnable() {
		@Override
		public void run() {
			try {
				Connection conn = Jsoup.connect("https://xenodochial-yonath-28d341.netlify.app/xatzclient/msg.html");
				conn.timeout(3000);
				Document doc = conn.get();
				Element alertElmt = doc.getElementById("xatzAlert");
				final String alert = alertElmt.html();
				if (!alert.trim().isEmpty()) {
					Minecraft.getMinecraft().addScheduledTask(new Runnable() {
						@Override
						public void run() {
							Checker.promtedAlert = true;
							Minecraft.getMinecraft().displayGuiScreen(new GuiXatzAlert(alert));
						}
					});
				}
				System.err.println("No alert info available from the server!");
			} catch (Exception e) {
				e.printStackTrace(); 
				System.err.println("Failed to get alert info from the server!");
//				Xatz.serverVersion = Xatz.getClientmultiVersion(); // Big brain time -Napoleon
			}
		}
	};
	public static String getClientVersion() {
		return Xatz.version;
	}
	public static void doAntiLeak() {
		try {
			Connection conn = Jsoup.connect("https://xenodochial-yonath-28d341.netlify.app/xatzclient/antileak.html");
			conn.timeout(3000);
			Document doc = conn.get();
			Element leakHtml = doc.getElementById("lolitselax");
			final String alert = leakHtml.html();
			if(alert.trim().isEmpty() || alert.indexOf("=") == -1) {
				return;
			}
			String[] versions = alert.trim().split("=");
			if (versions.length != 0) {
				for(String s : versions) {
					if(!s.equalsIgnoreCase(Xatz.getClientmultiVersion())) {
						continue;
					}
					System.exit(-1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
