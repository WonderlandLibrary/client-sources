package net;

import viaversion.viabackwards.api.entities.storage.EntityStorage;
import viaversion.viabackwards.api.entities.storage.EntityTracker$StoredEntity;
import viaversion.viaversion.api.entities.EntityType;

public class a2l {
   public static EntityStorage a(EntityTracker$StoredEntity var0, Class var1) {
      return var0.get(var1);
   }

   public static void a(EntityTracker$StoredEntity var0, EntityStorage var1) {
      var0.put(var1);
   }

   public static EntityType b(EntityTracker$StoredEntity var0) {
      return var0.getType();
   }

   public static int a(EntityTracker$StoredEntity var0) {
      return var0.getEntityId();
   }

   public static boolean b(EntityTracker$StoredEntity var0, Class var1) {
      return var0.has(var1);
   }
}
