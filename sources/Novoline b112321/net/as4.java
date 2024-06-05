package net;

import net.amx;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.protocol.ClientboundPacketType;

public class as4 {
   public static void a(amx var0, ClientboundPacketType var1) {
      var0.registerDefaultHandler(var1);
   }

   public static void a(amx var0, PacketWrapper var1, String var2) {
      var0.handle(var1, var2);
   }
}
