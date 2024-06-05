package net;

import viaversion.viabackwards.api.entities.meta.MetaHandler;
import viaversion.viabackwards.api.entities.meta.MetaHandlerSettings;
import viaversion.viaversion.api.entities.EntityType;
import viaversion.viaversion.api.minecraft.metadata.Metadata;

public class agB {
   private static String[] b;

   public static void a(MetaHandlerSettings var0, MetaHandler var1) {
      var0.handle(var1);
   }

   public static MetaHandlerSettings a(MetaHandlerSettings var0, EntityType var1, boolean var2, int var3) {
      return var0.filter(var1, var2, var3);
   }

   public static void b(MetaHandlerSettings var0) {
      var0.removed();
   }

   public static MetaHandlerSettings a(MetaHandlerSettings var0, EntityType var1, boolean var2) {
      return var0.filter(var1, var2);
   }

   public static MetaHandlerSettings a(MetaHandlerSettings var0, EntityType var1, int var2) {
      return var0.filter(var1, var2);
   }

   public static MetaHandlerSettings a(MetaHandlerSettings var0, EntityType var1) {
      return var0.filter(var1);
   }

   public static void a(MetaHandlerSettings var0, int var1) {
      var0.handleIndexChange(var1);
   }

   public static boolean a(MetaHandlerSettings var0, EntityType var1, Metadata var2) {
      return var0.isGucci(var1, var2);
   }

   public static MetaHandler a(MetaHandlerSettings var0) {
      return var0.getHandler();
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new String[4]);
      }

   }
}
