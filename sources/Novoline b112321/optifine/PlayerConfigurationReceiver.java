package optifine;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import net.aE2;
import optifine.Config;
import optifine.IFileDownloadListener;
import optifine.MatchBlock;
import optifine.PlayerConfiguration;
import optifine.PlayerConfigurationParser;

public class PlayerConfigurationReceiver implements IFileDownloadListener {
   private String player = null;

   public PlayerConfigurationReceiver(String var1) {
      this.player = var1;
   }

   public void fileDownloadFinished(String var1, byte[] var2, Throwable var3) {
      PacketRemapper[] var4 = MatchBlock.b();

      try {
         String var5 = new String(var2, "ASCII");
         JsonParser var6 = new JsonParser();
         JsonElement var7 = var6.parse(var5);
         PlayerConfigurationParser var8 = new PlayerConfigurationParser(this.player);
         PlayerConfiguration var9 = var8.parsePlayerConfiguration(var7);
         if(var9 != null) {
            var9.setInitialized(true);
            aE2.a(this.player, var9);
         }
      } catch (Exception var10) {
         Config.dbg("Error parsing configuration: " + var1 + ", " + var10.getClass().getName() + ": " + var10.getMessage());
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
