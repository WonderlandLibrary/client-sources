package net.minecraft.realms;

import net.minecraft.client.multiplayer.ServerAddress;

public class RealmsServerAddress {
   private final int port;
   private final String host;
   private static final String __OBFID = "CL_00001864";

   public String getHost() {
      return this.host;
   }

   public int getPort() {
      return this.port;
   }

   protected RealmsServerAddress(String var1, int var2) {
      this.host = var1;
      this.port = var2;
   }

   public static RealmsServerAddress parseString(String var0) {
      ServerAddress var1 = ServerAddress.func_78860_a(var0);
      return new RealmsServerAddress(var1.getIP(), var1.getPort());
   }
}
