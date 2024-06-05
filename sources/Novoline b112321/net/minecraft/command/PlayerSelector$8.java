package net.minecraft.command;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;

final class PlayerSelector$8 implements Predicate {
   final BlockPos val$p_180698_1_;
   final int val$i;
   final int val$k;
   final int val$j;
   final int val$l;

   PlayerSelector$8(BlockPos var1, int var2, int var3, int var4, int var5) {
      this.val$p_180698_1_ = var1;
      this.val$i = var2;
      this.val$k = var3;
      this.val$j = var4;
      this.val$l = var5;
   }

   public boolean apply(Entity var1) {
      int var2 = (int)var1.getDistanceSqToCenter(this.val$p_180698_1_);
      return (this.val$i < 0 || var2 >= this.val$k) && (this.val$j < 0 || var2 <= this.val$l);
   }
}
