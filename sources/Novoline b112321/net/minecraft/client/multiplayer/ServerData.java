package net.minecraft.client.multiplayer;

import net.minecraft.client.multiplayer.ServerData$ServerResourceMode;
import net.minecraft.nbt.NBTTagCompound;

public class ServerData {
   public String serverName;
   public String serverIP;
   public String h;
   public String serverMOTD;
   public long b;
   public int version = 47;
   public String gameVersion = "1.8.8";
   public boolean field_78841_f;
   public String playerList;
   private ServerData$ServerResourceMode resourceMode = ServerData$ServerResourceMode.PROMPT;
   private String serverIcon;
   private boolean field_181042_l;

   public ServerData(String var1, String var2, boolean var3) {
      this.serverName = var1;
      this.serverIP = var2;
      this.field_181042_l = var3;
   }

   public NBTTagCompound getNBTCompound() {
      NBTTagCompound var1 = new NBTTagCompound();
      var1.setString("name", this.serverName);
      var1.setString("ip", this.serverIP);
      if(this.serverIcon != null) {
         var1.setString("icon", this.serverIcon);
      }

      if(this.resourceMode == ServerData$ServerResourceMode.ENABLED) {
         var1.setBoolean("acceptTextures", true);
      } else if(this.resourceMode == ServerData$ServerResourceMode.DISABLED) {
         var1.setBoolean("acceptTextures", false);
      }

      return var1;
   }

   public ServerData$ServerResourceMode getResourceMode() {
      return this.resourceMode;
   }

   public void setResourceMode(ServerData$ServerResourceMode var1) {
      this.resourceMode = var1;
   }

   public static ServerData getServerDataFromNBTCompound(NBTTagCompound var0) {
      ServerData var1 = new ServerData(var0.getString("name"), var0.getString("ip"), false);
      if(var0.hasKey("icon", 8)) {
         var1.setBase64EncodedIconData(var0.getString("icon"));
      }

      if(var0.hasKey("acceptTextures", 1)) {
         if(var0.getBoolean("acceptTextures")) {
            var1.setResourceMode(ServerData$ServerResourceMode.ENABLED);
         } else {
            var1.setResourceMode(ServerData$ServerResourceMode.DISABLED);
         }
      } else {
         var1.setResourceMode(ServerData$ServerResourceMode.PROMPT);
      }

      return var1;
   }

   public String getBase64EncodedIconData() {
      return this.serverIcon;
   }

   public void setBase64EncodedIconData(String var1) {
      this.serverIcon = var1;
   }

   public boolean func_181041_d() {
      return this.field_181042_l;
   }

   public void copyFrom(ServerData var1) {
      this.serverIP = var1.serverIP;
      this.serverName = var1.serverName;
      this.setResourceMode(var1.getResourceMode());
      this.serverIcon = var1.serverIcon;
      this.field_181042_l = var1.field_181042_l;
   }
}
