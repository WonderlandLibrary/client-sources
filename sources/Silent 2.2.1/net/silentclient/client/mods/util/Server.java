package net.silentclient.client.mods.util;

import net.minecraft.client.Minecraft;

import java.util.regex.Pattern;

public class Server {
	public static boolean hypixel = false;
	public static boolean ruHypixel = false;

	public static boolean isHypixel() {
		return hypixel;
	}
	public static void setHypixel(boolean hypixel) {
		Server.hypixel = hypixel;
	}

	public static boolean isRuHypixel() {
		return ruHypixel;
	}
	public static void setRuHypixel(boolean ruHypixel) {
		Server.ruHypixel = ruHypixel;
	}

	public static boolean checkIsHypixel() {
		if(Minecraft.getMinecraft().isSingleplayer()) {
			return false;
		}
		try {
			String serverIp = Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase();
			final String regex = "^(?:.*\\.)?hypixel\\.(?:net|io)\\.?";
			final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);

			return pattern.matcher(serverIp).matches();
		} catch (Exception err) {
			return false;
		}
	}
	
	public static boolean checkIsRuHypixel() {
		if(Minecraft.getMinecraft().isSingleplayer()) {
			return false;
		}
		try {
			String serverIp = Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase();
			final String regex = "^(?:.*\\.)?ruhypixel\\.(?:net)\\.?";
			final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);

			return pattern.matcher(serverIp).matches();
		} catch (Exception err) {
			return false;
		}
	}
}
