package client.metaware.impl.utils.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

public class ServerUtil {

    public static boolean onServer(final String server) {
        final ServerData serverData = Minecraft.getMinecraft().getCurrentServerData();
        return serverData != null && serverData.serverIP.toLowerCase().contains(server);
    }

}
