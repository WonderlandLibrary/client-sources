package com.viaversion.viaversion.api.type.types.item;

import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ItemType1_8 extends Type<Item> {
   public ItemType1_8() {
      super(Item.class);
   }

   @Nullable
   public Item read(ByteBuf buffer) throws Exception {
      short id = buffer.readShort();
      if (id < 0) {
         return null;
      } else {
         Item item = new DataItem();
         item.setIdentifier(id);
         item.setAmount(buffer.readByte());
         item.setData(buffer.readShort());
         item.setTag(Type.NAMED_COMPOUND_TAG.read(buffer));
         return item;
      }
   }

   public void write(ByteBuf buffer, @Nullable Item object) throws Exception {
      if (object == null) {
         buffer.writeShort(-1);
      } else {
         buffer.writeShort(object.identifier());
         buffer.writeByte(object.amount());
         buffer.writeShort(object.data());
         Type.NAMED_COMPOUND_TAG.write(buffer, object.tag());
      }
   }
}
