package com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_18;

import com.viaversion.viaversion.libs.mcstructs.core.Identifier;
import com.viaversion.viaversion.libs.mcstructs.snbt.SNbtSerializer;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.AHoverEvent;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.HoverEventAction;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.impl.EntityHoverEvent;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.TextComponentSerializer;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.v1_16.HoverEventDeserializer_v1_16;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import java.util.UUID;

public class HoverEventDeserializer_v1_18 extends HoverEventDeserializer_v1_16 {
   public HoverEventDeserializer_v1_18(TextComponentSerializer textComponentSerializer, SNbtSerializer<?> sNbtSerializer) {
      super(textComponentSerializer, sNbtSerializer);
   }

   @Override
   protected AHoverEvent deserializeLegacy(HoverEventAction action, ATextComponent text) {
      if (action == HoverEventAction.SHOW_ENTITY) {
         try {
            CompoundTag rawEntity = (CompoundTag)this.sNbtSerializer.deserialize(text.asUnformattedString());
            ATextComponent name = this.textComponentSerializer
               .deserialize(rawEntity.get("name") instanceof StringTag ? rawEntity.<StringTag>get("name").getValue() : "");
            Identifier entityType = Identifier.of(rawEntity.get("type") instanceof StringTag ? rawEntity.<StringTag>get("type").getValue() : "");
            UUID uuid = UUID.fromString(rawEntity.get("id") instanceof StringTag ? rawEntity.<StringTag>get("id").getValue() : "");
            return new EntityHoverEvent(action, entityType, uuid, name);
         } catch (Exception var7) {
            return null;
         }
      } else {
         return super.deserializeLegacy(action, text);
      }
   }
}
