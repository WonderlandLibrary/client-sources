package net.minecraft.world;

import com.google.common.base.Predicate;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.WorldServer;

class WorldServer$1 implements Predicate {
   final WorldServer this$0;

   WorldServer$1(WorldServer var1) {
      this.this$0 = var1;
   }

   public boolean apply(EntityLivingBase var1) {
      return var1.isEntityAlive() && this.this$0.canSeeSky(var1.getPosition());
   }
}
