package dev.nexus.utils.game;

import dev.nexus.utils.Utils;
import net.minecraft.client.multiplayer.ServerData;

public final class ServerUtils implements Utils {
    public static String getRemoteIp() {
        String serverIp = "Idling";
        if (mc.isIntegratedServerRunning()) {
            serverIp = "Playing SinglePlayer";
        } else if (mc.theWorld != null && mc.theWorld.isRemote) {
            final ServerData serverData = mc.getCurrentServerData();
            if (serverData != null) {
                serverIp = "Playing on " + serverData.serverIP;
            }
        }
        return serverIp;
    }
}