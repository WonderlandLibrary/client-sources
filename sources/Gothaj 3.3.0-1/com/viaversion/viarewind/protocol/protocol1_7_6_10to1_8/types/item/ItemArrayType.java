package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.item;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class ItemArrayType extends Type<Item[]> {
   public ItemArrayType() {
      super(Item[].class);
   }

   public Item[] read(ByteBuf buffer) throws Exception {
      int amount = Type.SHORT.readPrimitive(buffer);
      Item[] items = new Item[amount];

      for (int i = 0; i < amount; i++) {
         items[i] = Types1_7_6_10.COMPRESSED_NBT_ITEM.read(buffer);
      }

      return items;
   }

   public void write(ByteBuf buffer, Item[] items) throws Exception {
      Type.SHORT.writePrimitive(buffer, (short)items.length);

      for (Item item : items) {
         Types1_7_6_10.COMPRESSED_NBT_ITEM.write(buffer, item);
      }
   }
}
