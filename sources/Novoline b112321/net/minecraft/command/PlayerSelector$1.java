package net.minecraft.command;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

final class PlayerSelector$1 implements Predicate {
   public boolean apply(Entity var1) {
      return var1 instanceof EntityPlayer;
   }
}
