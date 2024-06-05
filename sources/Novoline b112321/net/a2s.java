package net;

import net.acE;
import net.ayd;
import viaversion.viabackwards.api.data.BackwardsMappings;
import viaversion.viaversion.api.protocol.ClientboundPacketType;

public class a2s {
   public static BackwardsMappings a(ayd var0) {
      return var0.getMappingData();
   }

   public static void a(ayd var0, ClientboundPacketType var1, acE var2) {
      var0.a(var1, var2);
   }
}
