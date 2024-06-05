package net.minecraft.entity;

import net.minecraft.block.BlockFence;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class EntityLeashKnot extends EntityHanging {
   public EntityLeashKnot(World var1) {
      super(var1);
   }

   public EntityLeashKnot(World var1, BlockPos var2) {
      super(var1, var2);
      this.setPosition((double)var2.getX() + 0.5D, (double)var2.getY() + 0.5D, (double)var2.getZ() + 0.5D);
      float var3 = 0.125F;
      float var4 = 0.1875F;
      float var5 = 0.25F;
      this.setEntityBoundingBox(new AxisAlignedBB(this.posX - 0.1875D, this.posY - 0.25D + 0.125D, this.posZ - 0.1875D, this.posX + 0.1875D, this.posY + 0.25D + 0.125D, this.posZ + 0.1875D));
   }

   protected void entityInit() {
      super.entityInit();
   }

   public void updateFacingWithBoundingBox(EnumFacing var1) {
   }

   public int getWidthPixels() {
      return 9;
   }

   public int getHeightPixels() {
      return 9;
   }

   public float getEyeHeight() {
      return -0.0625F;
   }

   public boolean isInRangeToRenderDist(double var1) {
      return var1 < 1024.0D;
   }

   public void onBroken(Entity var1) {
   }

   public boolean writeToNBTOptional(NBTTagCompound var1) {
      return false;
   }

   public void writeEntityToNBT(NBTTagCompound var1) {
   }

   public void readEntityFromNBT(NBTTagCompound var1) {
   }

   public boolean interactFirst(EntityPlayer var1) {
      ItemStack var2 = var1.getHeldItem();
      boolean var3 = false;
      if(var2.getItem() == Items.lead && !this.worldObj.isRemote) {
         double var4 = 7.0D;

         for(EntityLiving var7 : this.worldObj.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(this.posX - var4, this.posY - var4, this.posZ - var4, this.posX + var4, this.posY + var4, this.posZ + var4))) {
            if(var7.getLeashed() && var7.getLeashedToEntity() == var1) {
               var7.setLeashedToEntity(this, true);
               var3 = true;
            }
         }
      }

      if(!this.worldObj.isRemote) {
         this.setDead();
         if(var1.abilities.isCreative()) {
            double var9 = 7.0D;

            for(EntityLiving var11 : this.worldObj.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(this.posX - var9, this.posY - var9, this.posZ - var9, this.posX + var9, this.posY + var9, this.posZ + var9))) {
               if(var11.getLeashed() && var11.getLeashedToEntity() == this) {
                  var11.clearLeashed(true, false);
               }
            }
         }
      }

      return true;
   }

   public boolean onValidSurface() {
      return this.worldObj.getBlockState(this.hangingPosition).getBlock() instanceof BlockFence;
   }

   public static EntityLeashKnot createKnot(World var0, BlockPos var1) {
      EntityLeashKnot var2 = new EntityLeashKnot(var0, var1);
      var2.forceSpawn = true;
      var0.spawnEntityInWorld(var2);
      return var2;
   }

   public static EntityLeashKnot getKnotForPosition(World var0, BlockPos var1) {
      int var2 = var1.getX();
      int var3 = var1.getY();
      int var4 = var1.getZ();

      for(EntityLeashKnot var6 : var0.getEntitiesWithinAABB(EntityLeashKnot.class, new AxisAlignedBB((double)var2 - 1.0D, (double)var3 - 1.0D, (double)var4 - 1.0D, (double)var2 + 1.0D, (double)var3 + 1.0D, (double)var4 + 1.0D))) {
         if(var6.getHangingPosition().equals(var1)) {
            return var6;
         }
      }

      return null;
   }
}
