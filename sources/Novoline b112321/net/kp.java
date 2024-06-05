package net;

import com.google.gson.JsonObject;
import net.km;
import viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.WorldPackets;

final class kp extends km {
   protected void handleTranslate(JsonObject var1, String var2) {
      WorldPackets.b();
      super.handleTranslate(var1, var2);
      if(var2.startsWith("block.") && var2.endsWith(".name")) {
         var1.addProperty("translate", var2.substring(0, var2.length() - 5));
      }

   }
}
