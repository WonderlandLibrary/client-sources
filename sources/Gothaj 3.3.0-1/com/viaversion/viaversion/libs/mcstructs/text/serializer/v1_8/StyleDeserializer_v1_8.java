package com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_8;

import com.viaversion.viaversion.libs.gson.JsonDeserializationContext;
import com.viaversion.viaversion.libs.gson.JsonDeserializer;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.libs.mcstructs.core.TextFormatting;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import com.viaversion.viaversion.libs.mcstructs.text.Style;
import com.viaversion.viaversion.libs.mcstructs.text.events.click.ClickEvent;
import com.viaversion.viaversion.libs.mcstructs.text.events.click.ClickEventAction;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.HoverEventAction;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.impl.TextHoverEvent;
import java.lang.reflect.Type;

public class StyleDeserializer_v1_8 implements JsonDeserializer<Style> {
   public Style deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
      if (!json.isJsonObject()) {
         return null;
      } else {
         JsonObject rawStyle = json.getAsJsonObject();
         if (rawStyle == null) {
            return null;
         } else {
            Style style = new Style();
            if (rawStyle.has("bold")) {
               style.setBold(rawStyle.get("bold").getAsBoolean());
            }

            if (rawStyle.has("italic")) {
               style.setItalic(rawStyle.get("italic").getAsBoolean());
            }

            if (rawStyle.has("underlined")) {
               style.setUnderlined(rawStyle.get("underlined").getAsBoolean());
            }

            if (rawStyle.has("strikethrough")) {
               style.setStrikethrough(rawStyle.get("strikethrough").getAsBoolean());
            }

            if (rawStyle.has("obfuscated")) {
               style.setObfuscated(rawStyle.get("obfuscated").getAsBoolean());
            }

            if (rawStyle.has("color")) {
               style.setFormatting(TextFormatting.getByName(rawStyle.get("color").getAsString()));
            }

            if (rawStyle.has("insertion")) {
               style.setInsertion(rawStyle.get("insertion").getAsString());
            }

            if (rawStyle.has("clickEvent")) {
               JsonObject rawClickEvent = rawStyle.getAsJsonObject("clickEvent");
               if (rawClickEvent != null) {
                  JsonPrimitive rawAction = rawClickEvent.getAsJsonPrimitive("action");
                  JsonPrimitive rawValue = rawClickEvent.getAsJsonPrimitive("value");
                  ClickEventAction action = null;
                  String value = null;
                  if (rawAction != null) {
                     action = ClickEventAction.getByName(rawAction.getAsString());
                  }

                  if (rawValue != null) {
                     value = rawValue.getAsString();
                  }

                  if (action != null && value != null && action.isUserDefinable()) {
                     style.setClickEvent(new ClickEvent(action, value));
                  }
               }
            }

            if (rawStyle.has("hoverEvent")) {
               JsonObject rawHoverEvent = rawStyle.getAsJsonObject("hoverEvent");
               if (rawHoverEvent != null) {
                  JsonPrimitive rawActionx = rawHoverEvent.getAsJsonPrimitive("action");
                  HoverEventAction actionx = null;
                  ATextComponent valuex = context.deserialize(rawHoverEvent.get("value"), ATextComponent.class);
                  if (rawActionx != null) {
                     actionx = HoverEventAction.getByName(rawActionx.getAsString());
                  }

                  if (actionx != null && valuex != null && actionx.isUserDefinable()) {
                     style.setHoverEvent(new TextHoverEvent(actionx, valuex));
                  }
               }
            }

            return style;
         }
      }
   }
}
