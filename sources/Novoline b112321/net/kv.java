package net;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.Iterator;
import net.amt;
import net.km;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.protocol.Protocol;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.InventoryPackets;

public class kv extends km {
   public kv(Protocol var1) {
      super(var1);
   }

   public kv() {
   }

   protected void a(JsonObject var1) {
      super.a(var1);
      amt.b();
      String var3 = var1.getAsJsonPrimitive("action").getAsString();
      if(var3.equals("show_item")) {
         JsonElement var4 = var1.get("value");
      }
   }

   protected void handleItem(Item var1) {
      InventoryPackets.toClient(var1);
   }

   protected String a(JsonElement var1) {
      boolean var2 = amt.b();
      if(var1.isJsonArray()) {
         Iterator var3 = var1.getAsJsonArray().iterator();
         if(var3.hasNext()) {
            JsonElement var4 = (JsonElement)var3.next();
            String var5 = this.a(var4);
            if(var5 != null) {
               return var5;
            }
         }
      }

      if(var1.isJsonObject()) {
         JsonPrimitive var6 = var1.getAsJsonObject().getAsJsonPrimitive("text");
         if(var6 != null) {
            return var6.getAsString();
         }
      }

      return var1.isJsonPrimitive()?var1.getAsJsonPrimitive().getAsString():null;
   }

   protected void handleTranslate(JsonObject var1, String var2) {
      amt.c();
      super.handleTranslate(var1, var2);
      String var4 = (String)Protocol1_13To1_12_2.MAPPINGS.getTranslateMapping().get(var2);
      if(var4 == null) {
         var4 = (String)Protocol1_13To1_12_2.MAPPINGS.getMojangTranslation().get(var2);
      }

      if(var4 != null) {
         var1.addProperty("translate", var4);
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
