package com.viaversion.viaversion.protocols.protocol1_12to1_11_1;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.data.AchievementTranslationMapping;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import java.util.logging.Level;

public class TranslateRewriter {
   private static final ComponentRewriter<ClientboundPackets1_9_3> ACHIEVEMENT_TEXT_REWRITER = new ComponentRewriter<ClientboundPackets1_9_3>(
      null, ComponentRewriter.ReadType.JSON
   ) {
      @Override
      protected void handleTranslate(JsonObject object, String translate) {
         String text = AchievementTranslationMapping.get(translate);
         if (text != null) {
            object.addProperty("translate", text);
         }
      }

      @Override
      protected void handleHoverEvent(JsonObject hoverEvent) {
         String action = hoverEvent.getAsJsonPrimitive("action").getAsString();
         if (!action.equals("show_achievement")) {
            super.handleHoverEvent(hoverEvent);
         } else {
            JsonElement value = hoverEvent.get("value");
            String textValue;
            if (value.isJsonObject()) {
               textValue = value.getAsJsonObject().get("text").getAsString();
            } else {
               textValue = value.getAsJsonPrimitive().getAsString();
            }

            if (AchievementTranslationMapping.get(textValue) == null) {
               JsonObject invalidText = new JsonObject();
               invalidText.addProperty("text", "Invalid statistic/achievement!");
               invalidText.addProperty("color", "red");
               hoverEvent.addProperty("action", "show_text");
               hoverEvent.add("value", invalidText);
               super.handleHoverEvent(hoverEvent);
            } else {
               try {
                  JsonObject newLine = new JsonObject();
                  newLine.addProperty("text", "\n");
                  JsonArray baseArray = new JsonArray();
                  baseArray.add("");
                  JsonObject namePart = new JsonObject();
                  JsonObject typePart = new JsonObject();
                  baseArray.add(namePart);
                  baseArray.add(newLine);
                  baseArray.add(typePart);
                  if (textValue.startsWith("achievement")) {
                     namePart.addProperty("translate", textValue);
                     namePart.addProperty("color", AchievementTranslationMapping.isSpecial(textValue) ? "dark_purple" : "green");
                     typePart.addProperty("translate", "stats.tooltip.type.achievement");
                     JsonObject description = new JsonObject();
                     typePart.addProperty("italic", true);
                     description.addProperty("translate", value + ".desc");
                     baseArray.add(newLine);
                     baseArray.add(description);
                  } else if (textValue.startsWith("stat")) {
                     namePart.addProperty("translate", textValue);
                     namePart.addProperty("color", "gray");
                     typePart.addProperty("translate", "stats.tooltip.type.statistic");
                     typePart.addProperty("italic", true);
                  }

                  hoverEvent.addProperty("action", "show_text");
                  hoverEvent.add("value", baseArray);
               } catch (Exception var10) {
                  Via.getPlatform().getLogger().log(Level.WARNING, "Error rewriting show_achievement: " + hoverEvent, (Throwable)var10);
                  JsonObject invalidText = new JsonObject();
                  invalidText.addProperty("text", "Invalid statistic/achievement!");
                  invalidText.addProperty("color", "red");
                  hoverEvent.addProperty("action", "show_text");
                  hoverEvent.add("value", invalidText);
               }

               super.handleHoverEvent(hoverEvent);
            }
         }
      }
   };

   public static void toClient(JsonElement element, UserConnection user) {
      if (element instanceof JsonObject) {
         JsonObject obj = (JsonObject)element;
         JsonElement translate = obj.get("translate");
         if (translate != null && translate.getAsString().startsWith("chat.type.achievement")) {
            ACHIEVEMENT_TEXT_REWRITER.processText(obj);
         }
      }
   }
}
