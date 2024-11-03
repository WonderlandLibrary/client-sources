package vestige.util.network;

import vestige.util.IMinecraft;

public class ServerUtil implements IMinecraft {
   public static boolean isOnHypixel() {
      return mc.getCurrentServerData() != null && mc.thePlayer != null ? getCurrentServer().endsWith("hypixel.net") : false;
   }

   public static String getCurrentServer() {
      return mc.isSingleplayer() ? "Singleplayer" : mc.getCurrentServerData().serverIP.toLowerCase();
   }
}
