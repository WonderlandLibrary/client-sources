package net;

import net.UW;
import net.a2V;
import net.agu;
import net.apo;
import net.as0;
import net.avq;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;

public final class avp extends as0 {
   public avp(UW var1) {
      super(var1, "AntiCactus", "Anti Cactus", a2V.PLAYER, "");
   }

   @agu
   private void a(apo var1) {
      int[] var2 = avq.a();
      if(var1.b() == Blocks.cactus) {
         var1.a(new AxisAlignedBB(var1.a(), var1.a().a(1, 1, 1)));
      }

   }
}
