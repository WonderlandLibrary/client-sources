package com.viaversion.viabackwards.api.entities.storage;

import com.viaversion.viabackwards.api.BackwardsProtocol;

public class EntityObjectData extends EntityData {
   private final int objectData;

   public EntityObjectData(BackwardsProtocol<?, ?, ?, ?> protocol, String key, int id, int replacementId, int objectData) {
      super(protocol, key, id, replacementId);
      this.objectData = objectData;
   }

   @Override
   public boolean isObjectType() {
      return true;
   }

   @Override
   public int objectData() {
      return this.objectData;
   }
}
