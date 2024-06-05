package net;

import net.aPh;
import net.acE;
import viaversion.viaversion.api.Via;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.api.minecraft.Position;

public abstract class abi {
   private static acE[] b;

   public abstract int connect(UserConnection var1, Position var2, int var3);

   public int getBlockData(UserConnection var1, Position var2) {
      return ((aPh)Via.getManager().f().b(aPh.class)).getBlockData(var1, var2.getX(), var2.getY(), var2.getZ());
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new acE[2]);
      }

   }
}
