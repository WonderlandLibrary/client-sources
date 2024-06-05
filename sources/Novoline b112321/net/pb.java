package net;

import net.Du;
import net.RT;
import net.a4u;
import net.minecraft.entity.player.EntityPlayer;

public class pb {
   private static int[] b;

   public static void a(EntityPlayer var0, RT var1, Du var2) {
      a4u.a(var0, var1, var2);
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new int[4]);
      }

   }
}
