package net;

import java.util.List;
import net.a66;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.protocol.Protocol;
import viaversion.viaversion.api.protocol.ProtocolPipeline;
import viaversion.viaversion.packets.Direction;

public class n {
   public static void a(ProtocolPipeline var0, Direction var1, a66 var2, PacketWrapper var3) {
      var0.a(var1, var2, var3);
   }

   public static boolean a(ProtocolPipeline var0, Class var1) {
      return var0.contains(var1);
   }

   public static List a(ProtocolPipeline var0) {
      return var0.pipes();
   }

   public static Protocol b(ProtocolPipeline var0, Class var1) {
      return var0.getProtocol(var1);
   }

   public static void a(ProtocolPipeline var0, Protocol var1) {
      var0.add(var1);
   }
}
