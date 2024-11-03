package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.chat;

import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.util.ComponentUtil;

public class TranslatableRewriter1_16 extends TranslatableRewriter<ClientboundPackets1_16> {
   private static final TranslatableRewriter1_16.ChatColor[] COLORS = new TranslatableRewriter1_16.ChatColor[]{
      new TranslatableRewriter1_16.ChatColor("black", 0),
      new TranslatableRewriter1_16.ChatColor("dark_blue", 170),
      new TranslatableRewriter1_16.ChatColor("dark_green", 43520),
      new TranslatableRewriter1_16.ChatColor("dark_aqua", 43690),
      new TranslatableRewriter1_16.ChatColor("dark_red", 11141120),
      new TranslatableRewriter1_16.ChatColor("dark_purple", 11141290),
      new TranslatableRewriter1_16.ChatColor("gold", 16755200),
      new TranslatableRewriter1_16.ChatColor("gray", 11184810),
      new TranslatableRewriter1_16.ChatColor("dark_gray", 5592405),
      new TranslatableRewriter1_16.ChatColor("blue", 5592575),
      new TranslatableRewriter1_16.ChatColor("green", 5635925),
      new TranslatableRewriter1_16.ChatColor("aqua", 5636095),
      new TranslatableRewriter1_16.ChatColor("red", 16733525),
      new TranslatableRewriter1_16.ChatColor("light_purple", 16733695),
      new TranslatableRewriter1_16.ChatColor("yellow", 16777045),
      new TranslatableRewriter1_16.ChatColor("white", 16777215)
   };

   public TranslatableRewriter1_16(Protocol1_15_2To1_16 protocol) {
      super(protocol, ComponentRewriter.ReadType.JSON);
   }

   @Override
   public void processText(JsonElement value) {
      super.processText(value);
      if (value != null && value.isJsonObject()) {
         JsonObject object = value.getAsJsonObject();
         JsonPrimitive color = object.getAsJsonPrimitive("color");
         if (color != null) {
            String colorName = color.getAsString();
            if (!colorName.isEmpty() && colorName.charAt(0) == '#') {
               int rgb = Integer.parseInt(colorName.substring(1), 16);
               String closestChatColor = this.getClosestChatColor(rgb);
               object.addProperty("color", closestChatColor);
            }
         }

         JsonObject hoverEvent = object.getAsJsonObject("hoverEvent");
         if (hoverEvent != null && hoverEvent.has("contents")) {
            JsonObject convertedObject = (JsonObject)ComponentUtil.convertJson(
               object, ComponentUtil.SerializerVersion.V1_16, ComponentUtil.SerializerVersion.V1_15
            );
            object.add("hoverEvent", convertedObject.getAsJsonObject("hoverEvent"));
         }
      }
   }

   private String getClosestChatColor(int rgb) {
      int r = rgb >> 16 & 0xFF;
      int g = rgb >> 8 & 0xFF;
      int b = rgb & 0xFF;
      TranslatableRewriter1_16.ChatColor closest = null;
      int smallestDiff = 0;

      for (TranslatableRewriter1_16.ChatColor color : COLORS) {
         if (color.rgb == rgb) {
            return color.colorName;
         }

         int rAverage = (color.r + r) / 2;
         int rDiff = color.r - r;
         int gDiff = color.g - g;
         int bDiff = color.b - b;
         int diff = (2 + (rAverage >> 8)) * rDiff * rDiff + 4 * gDiff * gDiff + (2 + (255 - rAverage >> 8)) * bDiff * bDiff;
         if (closest == null || diff < smallestDiff) {
            closest = color;
            smallestDiff = diff;
         }
      }

      return closest.colorName;
   }

   private static final class ChatColor {
      private final String colorName;
      private final int rgb;
      private final int r;
      private final int g;
      private final int b;

      ChatColor(String colorName, int rgb) {
         this.colorName = colorName;
         this.rgb = rgb;
         this.r = rgb >> 16 & 0xFF;
         this.g = rgb >> 8 & 0xFF;
         this.b = rgb & 0xFF;
      }
   }
}
