package net.minecraft.entity.projectile;

import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityPotion extends EntityThrowable {
   private ItemStack potionDamage;

   public EntityPotion(World var1) {
      super(var1);
   }

   public EntityPotion(World var1, EntityLivingBase var2, int var3) {
      this(var1, var2, new ItemStack(Items.potionitem, 1, var3));
   }

   public EntityPotion(World var1, EntityLivingBase var2, ItemStack var3) {
      super(var1, var2);
      this.potionDamage = var3;
   }

   public EntityPotion(World var1, double var2, double var4, double var6, int var8) {
      this(var1, var2, var4, var6, new ItemStack(Items.potionitem, 1, var8));
   }

   public EntityPotion(World var1, double var2, double var4, double var6, ItemStack var8) {
      super(var1, var2, var4, var6);
      this.potionDamage = var8;
   }

   protected float getGravityVelocity() {
      return 0.05F;
   }

   protected float getVelocity() {
      return 0.5F;
   }

   protected float getInaccuracy() {
      return -20.0F;
   }

   public void setPotionDamage(int var1) {
      if(this.potionDamage == null) {
         this.potionDamage = new ItemStack(Items.potionitem, 1, 0);
      }

      this.potionDamage.setItemDamage(var1);
   }

   public int getPotionDamage() {
      if(this.potionDamage == null) {
         this.potionDamage = new ItemStack(Items.potionitem, 1, 0);
      }

      return this.potionDamage.getMetadata();
   }

   protected void onImpact(MovingObjectPosition var1) {
      if(!this.worldObj.isRemote) {
         List var2 = Items.potionitem.getEffects(this.potionDamage);
         if(!var2.isEmpty()) {
            AxisAlignedBB var3 = this.getEntityBoundingBox().expand(4.0D, 2.0D, 4.0D);
            List var4 = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, var3);
            if(!var4.isEmpty()) {
               for(EntityLivingBase var6 : var4) {
                  double var7 = this.getDistanceSqToEntity(var6);
                  if(var7 < 16.0D) {
                     double var9 = 1.0D - Math.sqrt(var7) / 4.0D;
                     if(var6 == var1.entityHit) {
                        var9 = 1.0D;
                     }

                     for(PotionEffect var12 : var2) {
                        int var13 = var12.getPotionID();
                        if(Potion.potionTypes[var13].isInstant()) {
                           Potion.potionTypes[var13].affectEntity(this, this.getThrower(), var6, var12.getAmplifier(), var9);
                        } else {
                           int var14 = (int)(var9 * (double)var12.getDuration() + 0.5D);
                           if(var14 > 20) {
                              var6.addPotionEffect(new PotionEffect(var13, var14, var12.getAmplifier()));
                           }
                        }
                     }
                  }
               }
            }
         }

         this.worldObj.playAuxSFX(2002, new BlockPos(this), this.getPotionDamage());
         this.setDead();
      }

   }

   public void readEntityFromNBT(NBTTagCompound var1) {
      super.readEntityFromNBT(var1);
      if(var1.hasKey("Potion", 10)) {
         this.potionDamage = ItemStack.loadItemStackFromNBT(var1.getCompoundTag("Potion"));
      } else {
         this.setPotionDamage(var1.getInteger("potionValue"));
      }

      if(this.potionDamage == null) {
         this.setDead();
      }

   }

   public void writeEntityToNBT(NBTTagCompound var1) {
      super.writeEntityToNBT(var1);
      if(this.potionDamage != null) {
         var1.setTag("Potion", this.potionDamage.writeToNBT(new NBTTagCompound()));
      }

   }
}
