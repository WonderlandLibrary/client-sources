package net.minecraft.command;

import com.google.common.base.Predicate;
import net.minecraft.command.PlayerSelector;
import net.minecraft.entity.Entity;

final class PlayerSelector$10 implements Predicate {
   final int val$k;
   final int val$l;

   PlayerSelector$10(int var1, int var2) {
      this.val$k = var1;
      this.val$l = var2;
   }

   public boolean apply(Entity var1) {
      int var2 = PlayerSelector.func_179650_a((int)Math.floor((double)var1.rotationPitch));
      return this.val$k > this.val$l?var2 >= this.val$k || var2 <= this.val$l:var2 >= this.val$k && var2 <= this.val$l;
   }
}
