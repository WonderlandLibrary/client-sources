package Focus.Beta.UTILS;

import Focus.Beta.IMPL.Module.Module;
import net.minecraft.client.multiplayer.ServerData;

public class ServerUtils {
    private static boolean hypixel;
    private static boolean fakeHypixel;
    public static void checkHypixel(ServerData var0) {
        if(var0.serverIP.toLowerCase().contains("hypixel.net") && !fakeHypixel) {
            hypixel = true;
        }

        hypixel = false;
    }

    public static boolean isHypixel() {
        return hypixel || Module.mc.isSingleplayer();
    }

}
