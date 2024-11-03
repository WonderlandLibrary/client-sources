package com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_20_3.nbt;

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
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;

public class NbtStyleSerializer_v1_20_3 implements ITypedSerializer<Tag, Style> {
   private final ITypedSerializer<Tag, AHoverEvent> hoverEventSerializer;

   public NbtStyleSerializer_v1_20_3(TextComponentCodec codec, ITypedSerializer<Tag, ATextComponent> textSerializer, SNbtSerializer<CompoundTag> sNbtSerializer) {
      this.hoverEventSerializer = new NbtHoverEventSerializer_v1_20_3(codec, textSerializer, sNbtSerializer);
   }

   public Tag serialize(Style object) {
      CompoundTag out = new CompoundTag();
      if (object.getColor() != null) {
         out.putString("color", object.getColor().serialize());
      }

      if (object.getBold() != null) {
         out.putBoolean("bold", object.isBold());
      }

      if (object.getItalic() != null) {
         out.putBoolean("italic", object.isItalic());
      }

      if (object.getUnderlined() != null) {
         out.putBoolean("underlined", object.isUnderlined());
      }

      if (object.getStrikethrough() != null) {
         out.putBoolean("strikethrough", object.isStrikethrough());
      }

      if (object.getObfuscated() != null) {
         out.putBoolean("obfuscated", object.isObfuscated());
      }

      if (object.getClickEvent() != null) {
         CompoundTag clickEvent = new CompoundTag();
         clickEvent.putString("action", object.getClickEvent().getAction().getName());
         clickEvent.putString("value", object.getClickEvent().getValue());
         out.put("clickEvent", clickEvent);
      }

      if (object.getHoverEvent() != null) {
         out.put("hoverEvent", this.hoverEventSerializer.serialize(object.getHoverEvent()));
      }

      if (object.getInsertion() != null) {
         out.putString("insertion", object.getInsertion());
      }

      if (object.getFont() != null) {
         out.putString("font", object.getFont().get());
      }

      return out;
   }

   public Style deserialize(Tag object) {
      if (!(object instanceof CompoundTag)) {
         throw new IllegalArgumentException("Nbt tag is not a compound tag");
      } else {
         CompoundTag tag = (CompoundTag)object;
         Style style = new Style();
         if (tag.contains("color")) {
            String color = CodecUtils.requiredString(tag, "color");
            TextFormatting formatting = TextFormatting.parse(color);
            if (formatting == null) {
               throw new IllegalArgumentException("Unknown color: " + color);
            }

            if (formatting.isRGBColor() && (formatting.getRgbValue() < 0 || formatting.getRgbValue() > 16777215)) {
               throw new IllegalArgumentException("Out of bounds RGB color: " + formatting.getRgbValue());
            }

            style.setFormatting(formatting);
         }

         style.setBold(CodecUtils.optionalBoolean(tag, "bold"));
         style.setItalic(CodecUtils.optionalBoolean(tag, "italic"));
         style.setUnderlined(CodecUtils.optionalBoolean(tag, "underlined"));
         style.setStrikethrough(CodecUtils.optionalBoolean(tag, "strikethrough"));
         style.setObfuscated(CodecUtils.optionalBoolean(tag, "obfuscated"));
         if (tag.contains("clickEvent")) {
            CompoundTag clickEvent = CodecUtils.requiredCompound(tag, "clickEvent");
            ClickEventAction action = ClickEventAction.getByName(CodecUtils.requiredString(clickEvent, "action"), false);
            if (action == null || ClickEventAction.TWITCH_USER_INFO.equals(action)) {
               throw new IllegalArgumentException(
                  "Unknown click event action: " + (clickEvent.get("action") instanceof StringTag ? clickEvent.<StringTag>get("action").getValue() : "")
               );
            }

            if (!action.isUserDefinable()) {
               throw new IllegalArgumentException("Click event action is not user definable: " + action);
            }

            style.setClickEvent(new ClickEvent(action, CodecUtils.requiredString(clickEvent, "value")));
         }

         if (tag.contains("hoverEvent")) {
            style.setHoverEvent(this.hoverEventSerializer.deserialize(CodecUtils.requiredCompound(tag, "hoverEvent")));
         }

         style.setInsertion(CodecUtils.optionalString(tag, "insertion"));
         if (tag.contains("font")) {
            style.setFont(Identifier.of(CodecUtils.requiredString(tag, "font")));
         }

         return style;
      }
   }
}
