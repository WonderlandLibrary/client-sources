package net;

import viaversion.viabackwards.api.entities.storage.EntityData;
import viaversion.viabackwards.api.entities.storage.EntityData$MetaCreator;

public class ayp {
   public static EntityData b(EntityData var0, String var1) {
      return var0.mobName(var1);
   }

   public static EntityData a(EntityData var0, EntityData$MetaCreator var1) {
      return var0.spawnMetadata(var1);
   }

   public static EntityData a(EntityData var0, String var1) {
      return var0.jsonName(var1);
   }

   public static int b(EntityData var0) {
      return var0.getReplacementId();
   }

   public static int a(EntityData var0) {
      return var0.getObjectData();
   }

   public static boolean e(EntityData var0) {
      return var0.hasBaseMeta();
   }

   public static EntityData$MetaCreator c(EntityData var0) {
      return var0.getDefaultMeta();
   }

   public static Object d(EntityData var0) {
      return var0.getMobName();
   }
}
