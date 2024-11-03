package com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_8;

import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonSerializationContext;
import com.viaversion.viaversion.libs.gson.JsonSerializer;
import com.viaversion.viaversion.libs.mcstructs.snbt.SNbtSerializer;
import com.viaversion.viaversion.libs.mcstructs.text.Style;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.impl.TextHoverEvent;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.TextComponentSerializer;
import java.lang.reflect.Type;

public class StyleSerializer_v1_8 implements JsonSerializer<Style> {
   private final TextComponentSerializer textComponentSerializer;
   private final SNbtSerializer<?> sNbtSerializer;

   public StyleSerializer_v1_8(TextComponentSerializer textComponentSerializer, SNbtSerializer<?> sNbtSerializer) {
      this.textComponentSerializer = textComponentSerializer;
      this.sNbtSerializer = sNbtSerializer;
   }

   public JsonElement serialize(Style src, Type typeOfSrc, JsonSerializationContext context) {
      if (src.isEmpty()) {
         return null;
      } else {
         JsonObject serializedStyle = new JsonObject();
         if (src.getBold() != null) {
            serializedStyle.addProperty("bold", src.isBold());
         }

         if (src.getItalic() != null) {
            serializedStyle.addProperty("italic", src.isItalic());
         }

         if (src.getUnderlined() != null) {
            serializedStyle.addProperty("underlined", src.isUnderlined());
         }

         if (src.getStrikethrough() != null) {
            serializedStyle.addProperty("strikethrough", src.isStrikethrough());
         }

         if (src.getObfuscated() != null) {
            serializedStyle.addProperty("obfuscated", src.isObfuscated());
         }

         if (src.getColor() != null && !src.getColor().isRGBColor()) {
            serializedStyle.addProperty("color", src.getColor().serialize());
         }

         if (src.getInsertion() != null) {
            serializedStyle.add("insertion", context.serialize(src.getInsertion()));
         }

         if (src.getClickEvent() != null) {
            JsonObject clickEvent = new JsonObject();
            clickEvent.addProperty("action", src.getClickEvent().getAction().getName());
            clickEvent.addProperty("value", src.getClickEvent().getValue());
            serializedStyle.add("clickEvent", clickEvent);
         }

         if (src.getHoverEvent() instanceof TextHoverEvent) {
            JsonObject hoverEvent = new JsonObject();
            hoverEvent.addProperty("action", src.getHoverEvent().getAction().getName());
            hoverEvent.add("value", context.serialize(src.getHoverEvent().toLegacy(this.textComponentSerializer, this.sNbtSerializer).getText()));
            serializedStyle.add("hoverEvent", hoverEvent);
         }

         return serializedStyle;
      }
   }
}
