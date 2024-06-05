package net.minecraft.client.multiplayer;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class ServerData {
   private static final String __OBFID = "CL_00000890";
   private ServerData.ServerResourceMode resourceMode;
   public int version = 47;
   public String populationInfo;
   public String playerList;
   public String serverName;
   private String serverIcon;
   public boolean field_78841_f;
   public String serverIP;
   public String gameVersion = "1.8";
   public String serverMOTD;
   public long pingToServer;

   public void setBase64EncodedIconData(String var1) {
      this.serverIcon = var1;
   }

   public static ServerData getServerDataFromNBTCompound(NBTTagCompound var0) {
      ServerData var1 = new ServerData(var0.getString("name"), var0.getString("ip"));
      if (var0.hasKey("icon", 8)) {
         var1.setBase64EncodedIconData(var0.getString("icon"));
      }

      if (var0.hasKey("acceptTextures", 1)) {
         if (var0.getBoolean("acceptTextures")) {
            var1.setResourceMode(ServerData.ServerResourceMode.ENABLED);
         } else {
            var1.setResourceMode(ServerData.ServerResourceMode.DISABLED);
         }
      } else {
         var1.setResourceMode(ServerData.ServerResourceMode.PROMPT);
      }

      return var1;
   }

   public void copyFrom(ServerData var1) {
      this.serverIP = var1.serverIP;
      this.serverName = var1.serverName;
      this.setResourceMode(var1.getResourceMode());
      this.serverIcon = var1.serverIcon;
   }

   public ServerData.ServerResourceMode getResourceMode() {
      return this.resourceMode;
   }

   public String getBase64EncodedIconData() {
      return this.serverIcon;
   }

   public void setResourceMode(ServerData.ServerResourceMode var1) {
      this.resourceMode = var1;
   }

   public ServerData(String var1, String var2) {
      this.resourceMode = ServerData.ServerResourceMode.PROMPT;
      this.serverName = var1;
      this.serverIP = var2;
   }

   public NBTTagCompound getNBTCompound() {
      NBTTagCompound var1 = new NBTTagCompound();
      var1.setString("name", this.serverName);
      var1.setString("ip", this.serverIP);
      if (this.serverIcon != null) {
         var1.setString("icon", this.serverIcon);
      }

      if (this.resourceMode == ServerData.ServerResourceMode.ENABLED) {
         var1.setBoolean("acceptTextures", true);
      } else if (this.resourceMode == ServerData.ServerResourceMode.DISABLED) {
         var1.setBoolean("acceptTextures", false);
      }

      return var1;
   }

   public static enum ServerResourceMode {
      PROMPT("PROMPT", 2, "prompt");

      private final IChatComponent motd;
      private static final String __OBFID = "CL_00001833";
      private static final ServerData.ServerResourceMode[] ENUM$VALUES = new ServerData.ServerResourceMode[]{ENABLED, DISABLED, PROMPT};
      ENABLED("ENABLED", 0, "enabled");

      private static final ServerData.ServerResourceMode[] $VALUES = new ServerData.ServerResourceMode[]{ENABLED, DISABLED, PROMPT};
      DISABLED("DISABLED", 1, "disabled");

      public IChatComponent getMotd() {
         return this.motd;
      }

      private ServerResourceMode(String var3, int var4, String var5) {
         this.motd = new ChatComponentTranslation(String.valueOf((new StringBuilder("addServer.resourcePack.")).append(var5)), new Object[0]);
      }
   }
}
