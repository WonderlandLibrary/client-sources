package com.viaversion.viabackwards.api.entities.storage;

import com.viaversion.viabackwards.api.entities.storage.EntityData;

public class EntityObjectData extends EntityData {
   private final boolean isObject;
   private final int objectData;

   public EntityObjectData(int var1, boolean var2, int var3, int var4) {
      super(var1, var3);
      this.isObject = var2;
      this.objectData = var4;
   }

   public boolean isObjectType() {
      return this.isObject;
   }

   public int objectData() {
      return this.objectData;
   }
}
