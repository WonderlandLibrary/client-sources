package net;

import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.configurations.holder.ModuleHolder;
import com.google.common.reflect.TypeToken;

public class aak {
   private static int[] b;

   public static AbstractModule a(ModuleHolder var0) {
      return var0.getModule();
   }

   public static TypeToken b(ModuleHolder var0) {
      return var0.getTypeToken();
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new int[2]);
      }

   }
}
