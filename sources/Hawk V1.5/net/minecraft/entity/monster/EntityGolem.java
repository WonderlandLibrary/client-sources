package net.minecraft.entity.monster;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.world.World;

public abstract class EntityGolem extends EntityCreature implements IAnimals {
   private static final String __OBFID = "CL_00001644";

   protected String getHurtSound() {
      return "none";
   }

   public EntityGolem(World var1) {
      super(var1);
   }

   protected String getLivingSound() {
      return "none";
   }

   protected String getDeathSound() {
      return "none";
   }

   protected boolean canDespawn() {
      return false;
   }

   public int getTalkInterval() {
      return 120;
   }

   public void fall(float var1, float var2) {
   }
}
