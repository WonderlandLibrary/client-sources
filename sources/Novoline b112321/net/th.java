package net;

import net.minecraft.network.login.INetHandlerLoginClient;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.login.server.S01PacketEncryptionRequest;
import net.minecraft.network.login.server.S02PacketLoginSuccess;
import net.minecraft.network.login.server.S03PacketEnableCompression;

public class th {
   private static String b;

   public static void a(INetHandlerLoginClient var0, S02PacketLoginSuccess var1) {
      var0.handleLoginSuccess(var1);
   }

   public static void a(INetHandlerLoginClient var0, S03PacketEnableCompression var1) {
      var0.handleEnableCompression(var1);
   }

   public static void a(INetHandlerLoginClient var0, S00PacketDisconnect var1) {
      var0.handleDisconnect(var1);
   }

   public static void a(INetHandlerLoginClient var0, S01PacketEncryptionRequest var1) {
      var0.handleEncryptionRequest(var1);
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() != null) {
         b("DklTm");
      }

   }
}
