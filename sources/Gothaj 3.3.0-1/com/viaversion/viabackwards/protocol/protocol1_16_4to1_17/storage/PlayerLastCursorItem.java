package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;

public class PlayerLastCursorItem implements StorableObject {
   private Item lastCursorItem;

   public Item getLastCursorItem() {
      return copyItem(this.lastCursorItem);
   }

   public void setLastCursorItem(Item item) {
      this.lastCursorItem = copyItem(item);
   }

   public void setLastCursorItem(Item item, int amount) {
      this.lastCursorItem = copyItem(item);
      this.lastCursorItem.setAmount(amount);
   }

   public boolean isSet() {
      return this.lastCursorItem != null;
   }

   private static Item copyItem(Item item) {
      if (item == null) {
         return null;
      } else {
         Item copy = new DataItem(item);
         copy.setTag(copy.tag() == null ? null : copy.tag().copy());
         return copy;
      }
   }
}
