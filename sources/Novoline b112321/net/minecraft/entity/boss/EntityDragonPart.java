package net.minecraft.entity.boss;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;

public class EntityDragonPart extends Entity {
   public final IEntityMultiPart entityDragonObj;
   public final String partName;

   public EntityDragonPart(IEntityMultiPart var1, String var2, float var3, float var4) {
      super(var1.getWorld());
      this.setSize(var3, var4);
      this.entityDragonObj = var1;
      this.partName = var2;
   }

   protected void entityInit() {
   }

   protected void readEntityFromNBT(NBTTagCompound var1) {
   }

   protected void writeEntityToNBT(NBTTagCompound var1) {
   }

   public boolean canBeCollidedWith() {
      return true;
   }

   public boolean attackEntityFrom(DamageSource var1, float var2) {
      return !this.isEntityInvulnerable(var1) && this.entityDragonObj.attackEntityFromPart(this, var1, var2);
   }

   public boolean isEntityEqual(Entity var1) {
      return this == var1 || this.entityDragonObj == var1;
   }
}
