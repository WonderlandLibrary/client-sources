package net.minecraft.command;

import com.google.common.base.Predicate;
import net.minecraft.command.PlayerSelector;
import net.minecraft.entity.Entity;

final class PlayerSelector$9 implements Predicate {
   final int val$i;
   final int val$j;

   PlayerSelector$9(int var1, int var2) {
      this.val$i = var1;
      this.val$j = var2;
   }

   public boolean apply(Entity var1) {
      int var2 = PlayerSelector.func_179650_a((int)Math.floor((double)var1.rotationYaw));
      return this.val$i > this.val$j?var2 >= this.val$i || var2 <= this.val$j:var2 >= this.val$i && var2 <= this.val$j;
   }
}
