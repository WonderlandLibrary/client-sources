package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.storage;

import com.viaversion.viaversion.api.connection.StorableObject;

public class InventoryTracker1_16 implements StorableObject {
   private boolean inventoryOpen = false;

   public boolean isInventoryOpen() {
      return this.inventoryOpen;
   }

   public void setInventoryOpen(boolean inventoryOpen) {
      this.inventoryOpen = inventoryOpen;
   }
}
