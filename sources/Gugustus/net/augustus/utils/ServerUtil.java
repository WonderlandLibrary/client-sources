package net.augustus.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

public class ServerUtil {
    public static String getRemoteIp() {
        String serverIp = "MainMenu";
        if (Minecraft.getMinecraft().isSingleplayer()) {
            serverIp = "SinglePlayer";
        } else if (Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().theWorld.isRemote) {
            ServerData serverData = Minecraft.getMinecraft().getCurrentServerData();
            if (serverData != null)
                serverIp = serverData.serverIP;
        }
        return serverIp;
    }
}
