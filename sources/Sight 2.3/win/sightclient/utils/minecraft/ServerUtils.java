package win.sightclient.utils.minecraft;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;

public class ServerUtils {

	public static String lastConnected = "";
	public static String lastConnectedGame = "";
	
    public static List<EntityPlayer> getTabPlayerList() {
        final List<EntityPlayer> list = new ArrayList<>();
        if (Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().theWorld == null) {
        	return list;
        }
        final List<NetworkPlayerInfo> players = GuiPlayerTabOverlay.getField175252A().sortedCopy(Minecraft.getMinecraft().thePlayer.sendQueue.getPlayerInfoMap());
        for (final NetworkPlayerInfo info : players) {
            if (info == null) {
                continue;
            }
            list.add(Minecraft.getMinecraft().theWorld.getPlayerEntityByName(info.getGameProfile().getName()));
        }
        return list;
    }
    
    public static boolean isOnHypixel() {
    	return !Minecraft.getMinecraft().isSingleplayer() && Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().thePlayer.isServerWorld() && lastConnected.toLowerCase().endsWith("hypixel.net") || lastConnected.toLowerCase().endsWith("hypixel.net.");
    }
    
    public static boolean isOnMineplex() {
    	return !Minecraft.getMinecraft().isSingleplayer() && Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().thePlayer.isServerWorld() && lastConnected.toLowerCase().endsWith("mineplex.com") || lastConnected.toLowerCase().endsWith("mineplex.com.");
    }
    
    public static boolean isOnUltraPrison() {
    	return !Minecraft.getMinecraft().isSingleplayer() && Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().thePlayer.isServerWorld() && lastConnected.equalsIgnoreCase("play.ultraprison.org") || lastConnected.toLowerCase().endsWith("play.ultraprison.org.");
    }
}
