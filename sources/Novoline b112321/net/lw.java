package net;

import java.util.List;
import viaversion.viabackwards.api.entities.storage.MetaStorage;
import viaversion.viaversion.api.minecraft.metadata.Metadata;

public class lw {
   public static void a(MetaStorage var0, Metadata var1) {
      var0.add(var1);
   }

   public static Metadata a(MetaStorage var0, int var1) {
      return var0.get(var1);
   }

   public static List a(MetaStorage var0) {
      return var0.getMetaDataList();
   }

   public static void a(MetaStorage var0, List var1) {
      var0.setMetaDataList(var1);
   }

   public static void b(MetaStorage var0, int var1) {
      var0.delete(var1);
   }
}
