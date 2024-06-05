package net.minecraft.entity.monster;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.world.World;

public abstract class EntityGolem extends EntityCreature implements IAnimals {
   public EntityGolem(World var1) {
      super(var1);
   }

   public void fall(float var1, float var2) {
   }

   protected String getLivingSound() {
      return "none";
   }

   protected String getHurtSound() {
      return "none";
   }

   protected String getDeathSound() {
      return "none";
   }

   public int getTalkInterval() {
      return 120;
   }

   protected boolean canDespawn() {
      return false;
   }
}
