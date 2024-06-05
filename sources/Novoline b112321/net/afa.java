package net;

import net.minecraft.entity.Entity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.pathfinder.WalkNodeProcessor;

public class afa {
   private static boolean b;

   public static int a(IBlockAccess var0, Entity var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8, boolean var9, boolean var10) {
      boolean var11 = a();
      return WalkNodeProcessor.func_176170_a(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10);
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean a() {
      boolean var0 = b();
      return true;
   }

   static {
      if(a()) {
         b(true);
      }

   }
}
