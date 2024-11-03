package com.viaversion.viaversion.api.type.types.item;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class ItemShortArrayType1_13 extends BaseItemArrayType {
   public Item[] read(ByteBuf buffer) throws Exception {
      int amount = Type.SHORT.readPrimitive(buffer);
      Item[] array = new Item[amount];

      for (int i = 0; i < amount; i++) {
         array[i] = Type.ITEM1_13.read(buffer);
      }

      return array;
   }

   public void write(ByteBuf buffer, Item[] object) throws Exception {
      Type.SHORT.writePrimitive(buffer, (short)object.length);

      for (Item o : object) {
         Type.ITEM1_13.write(buffer, o);
      }
   }
}
