package com.viaversion.viaversion.libs.mcstructs.text.events.hover.impl;

import com.viaversion.viaversion.libs.mcstructs.core.Identifier;
import com.viaversion.viaversion.libs.mcstructs.snbt.SNbtSerializer;
import com.viaversion.viaversion.libs.mcstructs.snbt.exceptions.SNbtSerializeException;
import com.viaversion.viaversion.libs.mcstructs.text.components.StringComponent;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.AHoverEvent;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.HoverEventAction;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.TextComponentSerializer;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import java.util.Objects;
import javax.annotation.Nullable;

public class ItemHoverEvent extends AHoverEvent {
   private final Identifier item;
   private final int count;
   private final CompoundTag nbt;

   public ItemHoverEvent(HoverEventAction action, Identifier item, int count, CompoundTag nbt) {
      super(action);
      this.item = item;
      this.count = count;
      this.nbt = nbt;
   }

   public Identifier getItem() {
      return this.item;
   }

   public int getCount() {
      return this.count;
   }

   @Nullable
   public CompoundTag getNbt() {
      return this.nbt;
   }

   @Override
   public TextHoverEvent toLegacy(TextComponentSerializer textComponentSerializer, SNbtSerializer<?> sNbtSerializer) {
      CompoundTag tag = new CompoundTag();
      tag.putString("id", this.item.getValue());
      tag.putByte("Count", (byte)this.count);
      if (this.nbt != null) {
         tag.put("tag", this.nbt);
      }

      try {
         return new TextHoverEvent(this.getAction(), new StringComponent(sNbtSerializer.serialize(tag)));
      } catch (SNbtSerializeException var5) {
         throw new RuntimeException("This should never happen! Please report to the developer immediately!", var5);
      }
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ItemHoverEvent that = (ItemHoverEvent)o;
         return this.count == that.count && this.getAction() == that.getAction() && Objects.equals(this.item, that.item) && Objects.equals(this.nbt, that.nbt);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getAction(), this.item, this.count, this.nbt);
   }

   @Override
   public String toString() {
      return "ItemHoverEvent{action=" + this.getAction() + ", item=" + this.item + ", count=" + this.count + ", nbt=" + this.nbt + "}";
   }
}
