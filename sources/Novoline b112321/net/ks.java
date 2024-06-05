package net;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.aRp;
import net.km;
import viaversion.viaversion.api.Via;
import viaversion.viaversion.protocols.protocol1_12to1_11_1.data.AchievementTranslationMapping;

final class ks extends km {
   protected void handleTranslate(JsonObject var1, String var2) {
      String var3 = AchievementTranslationMapping.get(var2);
      var1.addProperty("translate", var3);
   }

   protected void a(JsonObject var1) {
      aRp.a();
      String var3 = var1.getAsJsonPrimitive("action").getAsString();
      if(!var3.equals("show_achievement")) {
         super.a(var1);
      } else {
         JsonElement var5 = var1.get("value");
         if(var5.isJsonObject()) {
            String var4 = var5.getAsJsonObject().get("text").getAsString();
         }

         String var12 = var5.getAsJsonPrimitive().getAsString();
         if(AchievementTranslationMapping.get(var12) == null) {
            JsonObject var13 = new JsonObject();
            var13.addProperty("text", "Invalid statistic/achievement!");
            var13.addProperty("color", "red");
            var1.addProperty("action", "show_text");
            var1.add("value", var13);
            super.a(var1);
         } else {
            try {
               JsonObject var6 = new JsonObject();
               var6.addProperty("text", "\n");
               JsonArray var14 = new JsonArray();
               var14.add("");
               JsonObject var8 = new JsonObject();
               JsonObject var9 = new JsonObject();
               var14.add(var8);
               var14.add(var6);
               var14.add(var9);
               if(var12.startsWith("achievement")) {
                  var8.addProperty("translate", var12);
                  var8.addProperty("color", AchievementTranslationMapping.isSpecial(var12)?"dark_purple":"green");
                  var9.addProperty("translate", "stats.tooltip.type.achievement");
                  JsonObject var10 = new JsonObject();
                  var9.addProperty("italic", Boolean.valueOf(true));
                  var10.addProperty("translate", var5 + ".desc");
                  var14.add(var6);
                  var14.add(var10);
               }

               if(var12.startsWith("stat")) {
                  var8.addProperty("translate", var12);
                  var8.addProperty("color", "gray");
                  var9.addProperty("translate", "stats.tooltip.type.statistic");
                  var9.addProperty("italic", Boolean.valueOf(true));
               }

               var1.addProperty("action", "show_text");
               var1.add("value", var14);
            } catch (Exception var11) {
               Via.getPlatform().getLogger().warning("Error rewriting show_achievement: " + var1);
               var11.printStackTrace();
               JsonObject var7 = new JsonObject();
               var7.addProperty("text", "Invalid statistic/achievement!");
               var7.addProperty("color", "red");
               var1.addProperty("action", "show_text");
               var1.add("value", var7);
            }

            super.a(var1);
         }
      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
