package com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_3.json;

import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.mcstructs.core.Identifier;
import com.viaversion.viaversion.libs.mcstructs.core.TextFormatting;
import com.viaversion.viaversion.libs.mcstructs.snbt.SNbtSerializer;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import com.viaversion.viaversion.libs.mcstructs.text.Style;
import com.viaversion.viaversion.libs.mcstructs.text.events.click.ClickEvent;
import com.viaversion.viaversion.libs.mcstructs.text.events.click.ClickEventAction;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.AHoverEvent;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.ITypedSerializer;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.TextComponentCodec;
import com.viaversion.viaversion.libs.mcstructs.text.utils.CodecUtils;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;

public class JsonStyleSerializer_v1_20_3 implements ITypedSerializer<JsonElement, Style> {
   private final ITypedSerializer<JsonElement, AHoverEvent> hoverEventSerializer;

   public JsonStyleSerializer_v1_20_3(
      TextComponentCodec codec, ITypedSerializer<JsonElement, ATextComponent> textSerializer, SNbtSerializer<CompoundTag> sNbtSerializer
   ) {
      this.hoverEventSerializer = new JsonHoverEventSerializer_v1_20_3(codec, textSerializer, sNbtSerializer);
   }

   public JsonElement serialize(Style object) {
      JsonObject out = new JsonObject();
      if (object.getColor() != null) {
         out.addProperty("color", object.getColor().serialize());
      }

      if (object.getBold() != null) {
         out.addProperty("bold", object.isBold());
      }

      if (object.getItalic() != null) {
         out.addProperty("italic", object.isItalic());
      }

      if (object.getUnderlined() != null) {
         out.addProperty("underlined", object.isUnderlined());
      }

      if (object.getStrikethrough() != null) {
         out.addProperty("strikethrough", object.isStrikethrough());
      }

      if (object.getObfuscated() != null) {
         out.addProperty("obfuscated", object.isObfuscated());
      }

      if (object.getClickEvent() != null) {
         JsonObject clickEvent = new JsonObject();
         clickEvent.addProperty("action", object.getClickEvent().getAction().getName());
         clickEvent.addProperty("value", object.getClickEvent().getValue());
         out.add("clickEvent", clickEvent);
      }

      if (object.getHoverEvent() != null) {
         out.add("hoverEvent", this.hoverEventSerializer.serialize(object.getHoverEvent()));
      }

      if (object.getInsertion() != null) {
         out.addProperty("insertion", object.getInsertion());
      }

      if (object.getFont() != null) {
         out.addProperty("font", object.getFont().get());
      }

      return out;
   }

   public Style deserialize(JsonElement object) {
      if (!object.isJsonObject()) {
         throw new IllegalArgumentException("Json element is not a json object");
      } else {
         JsonObject obj = object.getAsJsonObject();
         Style style = new Style();
         if (obj.has("color")) {
            String color = CodecUtils.requiredString(obj, "color");
            TextFormatting formatting = TextFormatting.parse(color);
            if (formatting == null) {
               throw new IllegalArgumentException("Unknown color: " + color);
            }

            if (formatting.isRGBColor() && (formatting.getRgbValue() < 0 || formatting.getRgbValue() > 16777215)) {
               throw new IllegalArgumentException("Out of bounds RGB color: " + formatting.getRgbValue());
            }

            style.setFormatting(formatting);
         }

         style.setBold(CodecUtils.optionalBoolean(obj, "bold"));
         style.setItalic(CodecUtils.optionalBoolean(obj, "italic"));
         style.setUnderlined(CodecUtils.optionalBoolean(obj, "underlined"));
         style.setStrikethrough(CodecUtils.optionalBoolean(obj, "strikethrough"));
         style.setObfuscated(CodecUtils.optionalBoolean(obj, "obfuscated"));
         if (obj.has("clickEvent")) {
            JsonObject clickEvent = CodecUtils.requiredObject(obj, "clickEvent");
            ClickEventAction action = ClickEventAction.getByName(CodecUtils.requiredString(clickEvent, "action"), false);
            if (action == null || ClickEventAction.TWITCH_USER_INFO.equals(action)) {
               throw new IllegalArgumentException("Unknown click event action: " + clickEvent.get("action").getAsString());
            }

            if (!action.isUserDefinable()) {
               throw new IllegalArgumentException("Click event action is not user definable: " + action);
            }

            style.setClickEvent(new ClickEvent(action, CodecUtils.requiredString(clickEvent, "value")));
         }

         if (obj.has("hoverEvent")) {
            style.setHoverEvent(this.hoverEventSerializer.deserialize(CodecUtils.requiredObject(obj, "hoverEvent")));
         }

         style.setInsertion(CodecUtils.optionalString(obj, "insertion"));
         if (obj.has("font")) {
            style.setFont(Identifier.of(CodecUtils.requiredString(obj, "font")));
         }

         return style;
      }
   }
}
