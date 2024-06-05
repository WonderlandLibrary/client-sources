package net.minecraft.util;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

final class EntitySelectors$4 implements Predicate {
   public boolean apply(Entity var1) {
      return !(var1 instanceof EntityPlayer) || !((EntityPlayer)var1).isSpectator();
   }
}
