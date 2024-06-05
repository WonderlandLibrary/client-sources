package optifine;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class PlayerConfigurationReceiver implements IFileDownloadListener {
   private String player = null;

   public void fileDownloadFinished(String var1, byte[] var2, Throwable var3) {
      if (var2 != null) {
         try {
            String var4 = new String(var2, "ASCII");
            JsonParser var5 = new JsonParser();
            JsonElement var6 = var5.parse(var4);
            PlayerConfigurationParser var7 = new PlayerConfigurationParser(this.player);
            PlayerConfiguration var8 = var7.parsePlayerConfiguration(var6);
            if (var8 != null) {
               var8.setInitialized(true);
               PlayerConfigurations.setPlayerConfiguration(this.player, var8);
            }
         } catch (Exception var9) {
            Config.dbg(String.valueOf((new StringBuilder("Error parsing configuration: ")).append(var1).append(", ").append(var9.getClass().getName()).append(": ").append(var9.getMessage())));
         }
      }

   }

   public PlayerConfigurationReceiver(String var1) {
      this.player = var1;
   }
}
