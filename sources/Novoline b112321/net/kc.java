package net;

import viaversion.viabackwards.api.entities.storage.EntityTracker$ProtocolEntityTracker;
import viaversion.viabackwards.api.entities.storage.EntityTracker$StoredEntity;
import viaversion.viaversion.api.entities.EntityType;

public class kc {
   public static EntityTracker$StoredEntity c(EntityTracker$ProtocolEntityTracker var0, int var1) {
      return var0.getEntity(var1);
   }

   public static void a(EntityTracker$ProtocolEntityTracker var0, int var1, EntityType var2) {
      var0.trackEntityType(var1, var2);
   }

   public static EntityType b(EntityTracker$ProtocolEntityTracker var0, int var1) {
      return var0.getEntityType(var1);
   }

   public static void a(EntityTracker$ProtocolEntityTracker var0, int var1) {
      var0.removeEntity(var1);
   }
}
