package net.minecraft.command;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;

final class PlayerSelector$11 implements Predicate {
   final AxisAlignedBB val$axisalignedbb;

   PlayerSelector$11(AxisAlignedBB var1) {
      this.val$axisalignedbb = var1;
   }

   public boolean apply(Entity var1) {
      return var1.posX >= this.val$axisalignedbb.minX && var1.posY >= this.val$axisalignedbb.minY && var1.posZ >= this.val$axisalignedbb.minZ && var1.posX < this.val$axisalignedbb.maxX && var1.posY < this.val$axisalignedbb.maxY && var1.posZ < this.val$axisalignedbb.maxZ;
   }
}
