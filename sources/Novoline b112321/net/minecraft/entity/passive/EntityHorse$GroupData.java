package net.minecraft.entity.passive;

import net.minecraft.entity.IEntityLivingData;

public class EntityHorse$GroupData implements IEntityLivingData {
   public int horseType;
   public int horseVariant;

   public EntityHorse$GroupData(int var1, int var2) {
      this.horseType = var1;
      this.horseVariant = var2;
   }
}
