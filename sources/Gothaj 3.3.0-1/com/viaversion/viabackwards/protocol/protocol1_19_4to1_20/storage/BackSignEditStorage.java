package com.viaversion.viabackwards.protocol.protocol1_19_4to1_20.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.Position;

public final class BackSignEditStorage implements StorableObject {
   private final Position position;

   public BackSignEditStorage(Position position) {
      this.position = position;
   }

   public Position position() {
      return this.position;
   }
}
