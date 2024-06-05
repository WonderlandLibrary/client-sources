package com.viaversion.viaversion.protocols.protocol1_12to1_11_1.storage;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import net.cL;

public class ItemTransaction {
   private final short windowId;
   private final short slotId;
   private final short actionId;

   public ItemTransaction(short var1, short var2, short var3) {
      cL.a();
      this.windowId = var1;
      this.slotId = var2;
      this.actionId = var3;
      if(PacketRemapper.b() == null) {
         cL.b("FPxA3b");
      }

   }

   public short getWindowId() {
      return this.windowId;
   }

   public short getSlotId() {
      return this.slotId;
   }

   public short getActionId() {
      return this.actionId;
   }

   public String toString() {
      String var1 = cL.a();
      return "ItemTransaction{windowId=" + this.windowId + ", slotId=" + this.slotId + ", actionId=" + this.actionId + '}';
   }
}
