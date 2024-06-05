package net.minecraft.entity.monster;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityGiantZombie extends EntityMob {
   private static final String __OBFID = "CL_00001690";

   public float getEyeHeight() {
      return 10.440001F;
   }

   public EntityGiantZombie(World var1) {
      super(var1);
      this.setSize(this.width * 6.0F, this.height * 6.0F);
   }

   public float func_180484_a(BlockPos var1) {
      return this.worldObj.getLightBrightness(var1) - 0.5F;
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(50.0D);
   }
}
