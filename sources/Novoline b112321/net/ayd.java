package net;

import org.jetbrains.annotations.Nullable;
import viaversion.viabackwards.api.data.BackwardsMappings;
import viaversion.viaversion.api.protocol.Protocol;
import viaversion.viaversion.api.protocol.ProtocolRegistry;

public abstract class ayd extends Protocol {
   private static String[] i;

   protected ayd() {
   }

   protected ayd(@Nullable Class var1, @Nullable Class var2, @Nullable Class var3, @Nullable Class var4) {
      a();
      super(var1, var2, var3, var4);
   }

   protected void a(Class var1, Runnable var2) {
      ProtocolRegistry.addMappingLoaderFuture(this.getClass(), var1, var2);
   }

   public boolean hasMappingDataToLoad() {
      return false;
   }

   public BackwardsMappings getMappingData() {
      return null;
   }

   public static void b(String[] var0) {
      i = var0;
   }

   public static String[] a() {
      return i;
   }

   static {
      if(a() == null) {
         b(new String[1]);
      }

   }
}
