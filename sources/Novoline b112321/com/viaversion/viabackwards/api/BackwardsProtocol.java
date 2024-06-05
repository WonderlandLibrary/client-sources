package com.viaversion.viabackwards.api;

import com.viaversion.viabackwards.api.data.BackwardsMappings;
import com.viaversion.viaversion.protocol.ProtocolManagerImpl;
import net.ayx;
import org.jetbrains.annotations.Nullable;

public abstract class BackwardsProtocol extends ayx {
   private static String[] i;

   protected BackwardsProtocol() {
   }

   protected BackwardsProtocol(@Nullable Class var1, @Nullable Class var2, @Nullable Class var3, @Nullable Class var4) {
      a();
      super(var1, var2, var3, var4);
   }

   protected void executeAsyncAfterLoaded(Class var1, Runnable var2) {
      ProtocolManagerImpl.a(this.getClass(), var1, var2);
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
