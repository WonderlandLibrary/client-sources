package net.minecraft.command;

import com.google.common.collect.ComparisonChain;
import java.util.Comparator;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;

final class PlayerSelector$12 implements Comparator {
   final BlockPos val$p_179658_5_;

   PlayerSelector$12(BlockPos var1) {
      this.val$p_179658_5_ = var1;
   }

   public int compare(Entity var1, Entity var2) {
      return ComparisonChain.start().compare(var1.getDistanceSq(this.val$p_179658_5_), var2.getDistanceSq(this.val$p_179658_5_)).result();
   }
}
