package com.viaversion.viaversion.protocols.protocol1_12to1_11_1;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.util.Iterator;
import java.util.regex.Pattern;
import net.aRp;
import net.bgR;

public class ChatItemRewriter {
   private static final Pattern indexRemoval = Pattern.compile("(?<![\\w-.+])\\d+:(?=([^\"\\\\]*(\\\\.|\"([^\"\\\\]*\\\\.)*[^\"\\\\]*\"))*[^\"]*$)");

   public static void a(JsonElement var0, bgR var1) {
      PacketRemapper[] var2 = aRp.a();
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
                     if(var6.isJsonPrimitive() && var6.getAsJsonPrimitive().isString()) {
                        String var7 = indexRemoval.matcher(var6.getAsString()).replaceAll("");
                        var4.addProperty("value", var7);
                     }

                     if(var6.isJsonArray()) {
                        JsonArray var14 = new JsonArray();
                        Iterator var8 = var6.getAsJsonArray().iterator();
                        if(var8.hasNext()) {
                           JsonElement var9 = (JsonElement)var8.next();
                           if(var9.isJsonPrimitive() && var9.getAsJsonPrimitive().isString()) {
                              String var10 = indexRemoval.matcher(var9.getAsString()).replaceAll("");
                              var14.add(new JsonPrimitive(var10));
                           }
                        }

                        var4.add("value", var14);
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
         JsonArray var11 = (JsonArray)var0;
         Iterator var12 = var11.iterator();
         if(var12.hasNext()) {
            JsonElement var13 = (JsonElement)var12.next();
            a(var13, var1);
         }
      }

   }
}
