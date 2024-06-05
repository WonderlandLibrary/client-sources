package net.minecraft.command;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;

final class PlayerSelector$3 implements Predicate {
   final int val$i;
   final int val$j;

   PlayerSelector$3(int var1, int var2) {
      this.val$i = var1;
      this.val$j = var2;
   }

   public boolean apply(Entity var1) {
      if(!(var1 instanceof EntityPlayerMP)) {
         return false;
      } else {
         EntityPlayerMP var2 = (EntityPlayerMP)var1;
         return (this.val$i <= -1 || var2.experienceLevel >= this.val$i) && (this.val$j <= -1 || var2.experienceLevel <= this.val$j);
      }
   }
}
