package net.minecraft.entity.monster;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityZombie$1;

class EntityZombie$GroupData implements IEntityLivingData {
   public boolean isChild;
   public boolean isVillager;

   private EntityZombie$GroupData(boolean var1, boolean var2) {
      this.isChild = false;
      this.isVillager = false;
      this.isChild = var1;
      this.isVillager = var2;
   }

   EntityZombie$GroupData(boolean var1, boolean var2, EntityZombie$1 var3) {
      this(var1, var2);
   }
}
