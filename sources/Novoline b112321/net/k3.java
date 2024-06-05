package net;

import com.google.gson.JsonObject;
import net.km;
import viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.AdvancementTranslations;
import viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.ChatPackets1_12;

class k3 extends km {
   final ChatPackets1_12 c;

   k3(ChatPackets1_12 var1) {
      this.c = var1;
   }

   protected void handleTranslate(JsonObject var1, String var2) {
      String var3 = AdvancementTranslations.get(var2);
      var1.addProperty("translate", var3);
   }
}
