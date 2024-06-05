package xyz.vergoclient.util.main;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;

public class ServerUtils {

	public static Timer timer;

	public static Minecraft mc = Minecraft.getMinecraft();
	
	public static boolean isOnHypixel() {
		
		try {
			
			if (!mc.isSingleplayer() && mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel.net")) {
				return true;
			}
		} catch (Exception e) {
			
		}
		
		return false;
		
	}

	public static String getServerIP() {
		return mc.getCurrentServerData() == null ? null : mc.getCurrentServerData().serverIP.toLowerCase();
	}

	public static boolean isSinglePlayer(){
		return mc.isIntegratedServerRunning();
	}

	public static NetworkPlayerInfo networkPlayerInfo;

	public static int getHypixelNetworkPing() {
		NetworkPlayerInfo networkPlayerInfo = mc.getNetHandler().getPlayerInfo(Minecraft.getMinecraft().thePlayer.getUniqueID());
		return networkPlayerInfo == null ? 0 : networkPlayerInfo.getResponseTime();
	}

}
