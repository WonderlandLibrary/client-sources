package net.minecraft.command;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;

final class PlayerSelector$4 implements Predicate {
   final int val$i;

   PlayerSelector$4(int var1) {
      this.val$i = var1;
   }

   public boolean apply(Entity var1) {
      if(!(var1 instanceof EntityPlayerMP)) {
         return false;
      } else {
         EntityPlayerMP var2 = (EntityPlayerMP)var1;
         return var2.theItemInWorldManager.getGameType().getID() == this.val$i;
      }
   }
}
