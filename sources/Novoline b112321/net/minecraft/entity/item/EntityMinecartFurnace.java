package net.minecraft.entity.item;

import net.minecraft.block.BlockFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecart$EnumMinecartType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityMinecartFurnace extends EntityMinecart {
   private int fuel;
   public double pushX;
   public double pushZ;

   public EntityMinecartFurnace(World var1) {
      super(var1);
   }

   public EntityMinecartFurnace(World var1, double var2, double var4, double var6) {
      super(var1, var2, var4, var6);
   }

   public EntityMinecart$EnumMinecartType getMinecartType() {
      return EntityMinecart$EnumMinecartType.FURNACE;
   }

   protected void entityInit() {
      super.entityInit();
      this.I.b(16, Byte.valueOf((byte)0));
   }

   public void onUpdate() {
      super.onUpdate();
      if(this.fuel > 0) {
         --this.fuel;
      }

      if(this.fuel <= 0) {
         this.pushX = this.pushZ = 0.0D;
      }

      this.setMinecartPowered(this.fuel > 0);
      if(this.isMinecartPowered() && this.rand.nextInt(4) == 0) {
         this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY + 0.8D, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
      }

   }

   protected double getMaximumSpeed() {
      return 0.2D;
   }

   public void killMinecart(DamageSource var1) {
      super.killMinecart(var1);
      if(!var1.isExplosion() && this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
         this.entityDropItem(new ItemStack(Blocks.furnace, 1), 0.0F);
      }

   }

   protected void func_180460_a(BlockPos var1, IBlockState var2) {
      super.func_180460_a(var1, var2);
      double var3 = this.pushX * this.pushX + this.pushZ * this.pushZ;
      if(var3 > 1.0E-4D && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.001D) {
         var3 = (double)MathHelper.sqrt_double(var3);
         this.pushX /= var3;
         this.pushZ /= var3;
         if(this.pushX * this.motionX + this.pushZ * this.motionZ < 0.0D) {
            this.pushX = 0.0D;
            this.pushZ = 0.0D;
         } else {
            double var5 = var3 / this.getMaximumSpeed();
            this.pushX *= var5;
            this.pushZ *= var5;
         }
      }

   }

   protected void applyDrag() {
      double var1 = this.pushX * this.pushX + this.pushZ * this.pushZ;
      if(var1 > 1.0E-4D) {
         var1 = (double)MathHelper.sqrt_double(var1);
         this.pushX /= var1;
         this.pushZ /= var1;
         double var3 = 1.0D;
         this.motionX *= 0.800000011920929D;
         this.motionY *= 0.0D;
         this.motionZ *= 0.800000011920929D;
         this.motionX += this.pushX * var3;
         this.motionZ += this.pushZ * var3;
      } else {
         this.motionX *= 0.9800000190734863D;
         this.motionY *= 0.0D;
         this.motionZ *= 0.9800000190734863D;
      }

      super.applyDrag();
   }

   public boolean interactFirst(EntityPlayer var1) {
      ItemStack var2 = var1.inventory.getCurrentItem();
      if(var2.getItem() == Items.coal) {
         if(!var1.abilities.isCreative() && --var2.stackSize == 0) {
            var1.inventory.setInventorySlotContents(var1.inventory.currentItem, (ItemStack)null);
         }

         this.fuel += 3600;
      }

      this.pushX = this.posX - var1.posX;
      this.pushZ = this.posZ - var1.posZ;
      return true;
   }

   protected void writeEntityToNBT(NBTTagCompound var1) {
      super.writeEntityToNBT(var1);
      var1.setDouble("PushX", this.pushX);
      var1.setDouble("PushZ", this.pushZ);
      var1.setShort("Fuel", (short)this.fuel);
   }

   protected void readEntityFromNBT(NBTTagCompound var1) {
      super.readEntityFromNBT(var1);
      this.pushX = var1.getDouble("PushX");
      this.pushZ = var1.getDouble("PushZ");
      this.fuel = var1.getShort("Fuel");
   }

   protected boolean isMinecartPowered() {
      return (this.I.g(16) & 1) != 0;
   }

   protected void setMinecartPowered(boolean var1) {
      this.I.a(16, Byte.valueOf((byte)(this.I.g(16) | 1)));
   }

   public IBlockState getDefaultDisplayTile() {
      return (this.isMinecartPowered()?Blocks.lit_furnace:Blocks.furnace).getDefaultState().withProperty(BlockFurnace.FACING, EnumFacing.NORTH);
   }
}
