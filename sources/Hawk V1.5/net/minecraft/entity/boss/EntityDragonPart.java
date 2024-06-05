package net.minecraft.entity.boss;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;

public class EntityDragonPart extends Entity {
   public final String field_146032_b;
   private static final String __OBFID = "CL_00001657";
   public final IEntityMultiPart entityDragonObj;

   protected void readEntityFromNBT(NBTTagCompound var1) {
   }

   protected void writeEntityToNBT(NBTTagCompound var1) {
   }

   public EntityDragonPart(IEntityMultiPart var1, String var2, float var3, float var4) {
      super(var1.func_82194_d());
      this.setSize(var3, var4);
      this.entityDragonObj = var1;
      this.field_146032_b = var2;
   }

   protected void entityInit() {
   }

   public boolean attackEntityFrom(DamageSource var1, float var2) {
      return this.func_180431_b(var1) ? false : this.entityDragonObj.attackEntityFromPart(this, var1, var2);
   }

   public boolean isEntityEqual(Entity var1) {
      return this == var1 || this.entityDragonObj == var1;
   }

   public boolean canBeCollidedWith() {
      return true;
   }
}
