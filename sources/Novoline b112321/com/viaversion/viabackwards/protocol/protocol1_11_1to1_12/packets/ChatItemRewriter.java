package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.Iterator;
import net.aqw;
import net.bgR;

public class ChatItemRewriter {
   public static void a(JsonElement var0, bgR var1) {
      String var2 = aqw.a();
      if(var0 instanceof JsonObject) {
         label13: {
            JsonObject var3 = (JsonObject)var0;
            if(var3.has("hoverEvent")) {
               if(!(var3.get("hoverEvent") instanceof JsonObject)) {
                  break label13;
               }

               JsonObject var4 = (JsonObject)var3.get("hoverEvent");
               if(var4.has("action") && var4.has("value")) {
                  String var5 = var4.get("action").getAsString();
                  if(var5.equals("show_item") || var5.equals("show_entity")) {
                     JsonElement var6 = var4.get("value");
                     if(var6.isJsonArray()) {
                        JsonArray var7 = new JsonArray();
                        byte var8 = 0;
                        Iterator var9 = var6.getAsJsonArray().iterator();
                        if(var9.hasNext()) {
                           JsonElement var10 = (JsonElement)var9.next();
                           if(var10.isJsonPrimitive() && var10.getAsJsonPrimitive().isString()) {
                              String var11 = var8 + ":" + var10.getAsString();
                              var7.add(new JsonPrimitive(var11));
                           }
                        }

                        var4.add("value", var7);
                     }
                  }
               }
            }

            if(var3.has("extra")) {
               a(var3.get("extra"), var1);
            }
         }
      }

      if(var0 instanceof JsonArray) {
         JsonArray var12 = (JsonArray)var0;
         Iterator var13 = var12.iterator();
         if(var13.hasNext()) {
            JsonElement var14 = (JsonElement)var13.next();
            a(var14, var1);
         }
      }

   }
}
