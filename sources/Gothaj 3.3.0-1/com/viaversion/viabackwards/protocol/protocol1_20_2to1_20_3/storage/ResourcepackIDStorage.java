package com.viaversion.viabackwards.protocol.protocol1_20_2to1_20_3.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import java.util.UUID;

public final class ResourcepackIDStorage implements StorableObject {
   private final UUID uuid;

   public ResourcepackIDStorage(UUID uuid) {
      this.uuid = uuid;
   }

   public UUID uuid() {
      return this.uuid;
   }

   @Override
   public boolean clearOnServerSwitch() {
      return false;
   }
}
