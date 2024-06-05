package net;

import net.aEb;
import net.acE;
import net.ayd;
import net.azW;
import net.lV;
import net.r;

public class aRg extends ayd {
   private static int j;

   public aRg() {
      int var10000 = b();
      super(azW.class, lV.class, r.class, aEb.class);
      int var1 = var10000;
      if(acE.b() == null) {
         ++var1;
         b(var1);
      }

   }

   protected void registerPackets() {
      b();
      this.cancelOutgoing(azW.CRAFT_RECIPE_RESPONSE);
      this.cancelIncoming(aEb.PREPARE_CRAFTING_GRID);
   }

   public static void b(int var0) {
      j = var0;
   }

   public static int b() {
      return j;
   }

   public static int a() {
      int var0 = b();
      return 18;
   }

   static {
      if(a() != 0) {
         b(102);
      }

   }
}
