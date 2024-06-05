package com.viaversion.viaversion.protocols.protocol1_12to1_11_1;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import net.aRp;
import net.bgR;
import net.km;
import net.ks;

public class TranslateRewriter {
   private static final km a = new ks();

   public static void a(JsonElement var0, bgR var1) {
      PacketRemapper[] var2 = aRp.a();
      if(var0 instanceof JsonObject) {
         JsonObject var3 = (JsonObject)var0;
         JsonElement var4 = var3.get("translate");
         if(var4 != null && var4.getAsString().startsWith("chat.type.achievement")) {
            a.a((JsonElement)var3);
         }
      }

   }
}
