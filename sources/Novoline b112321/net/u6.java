package net;

import java.util.UUID;
import net.aSv;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class u6 {
   @aSv
   public double getX(Entity var1) {
      return var1.posX;
   }

   @aSv
   public double getY(Entity var1) {
      return var1.posY;
   }

   @aSv
   public double getZ(Entity var1) {
      return var1.posZ;
   }

   @aSv
   public double getPrevPosX(Entity var1) {
      return var1.prevPosX;
   }

   @aSv
   public double getPrevPosY(Entity var1) {
      return var1.prevPosY;
   }

   @aSv
   public double getPrevPosZ(Entity var1) {
      return var1.prevPosZ;
   }

   @aSv
   public double getHealth(Entity var1) {
      return (double)((EntityLivingBase)var1).getHealth();
   }

   @aSv
   public double getEyeHeight(Entity var1) {
      return (double)var1.getEyeHeight();
   }

   @aSv
   public String getName(Entity var1) {
      return var1.getName();
   }

   @aSv
   public ItemStack getHeldItem(EntityLivingBase var1) {
      return var1.getHeldItem();
   }

   @aSv
   public boolean isDead(Entity var1) {
      return var1.isDead;
   }

   @aSv
   public UUID getUUID(Entity var1) {
      return var1.getUniqueID();
   }
}
