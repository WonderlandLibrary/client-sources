package com.viaversion.viaversion.api.connection;

import net.t4;
import org.jetbrains.annotations.Nullable;

public interface ConnectionManager {
   int a();

   @Nullable
   ConnectionManager b();

   String name();

   default boolean a(ConnectionManager... var1) {
      t4.c();
      int var4 = var1.length;
      int var5 = 0;
      if(var5 < var4) {
         ConnectionManager var6 = var1[var5];
         if(this.b(var6)) {
            return true;
         }

         ++var5;
      }

      return false;
   }

   default boolean b(ConnectionManager var1) {
      return this == var1;
   }

   default boolean a(ConnectionManager var1) {
      boolean var2 = t4.c();
      if(this.equals(var1)) {
         return true;
      } else {
         ConnectionManager var3 = this.b();
         return false;
      }
   }
}
