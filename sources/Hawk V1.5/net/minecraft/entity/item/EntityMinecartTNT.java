package net.minecraft.entity.item;

import net.minecraft.block.BlockRailBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityMinecartTNT extends EntityMinecart {
   private int minecartTNTFuse = -1;
   private static final String __OBFID = "CL_00001680";

   public void onUpdate() {
      super.onUpdate();
      if (this.minecartTNTFuse > 0) {
         --this.minecartTNTFuse;
         this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
      } else if (this.minecartTNTFuse == 0) {
         this.explodeCart(this.motionX * this.motionX + this.motionZ * this.motionZ);
      }

      if (this.isCollidedHorizontally) {
         double var1 = this.motionX * this.motionX + this.motionZ * this.motionZ;
         if (var1 >= 0.009999999776482582D) {
            this.explodeCart(var1);
         }
      }

   }

   protected void explodeCart(double var1) {
      if (!this.worldObj.isRemote) {
         double var3 = Math.sqrt(var1);
         if (var3 > 5.0D) {
            var3 = 5.0D;
         }

         this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (float)(4.0D + this.rand.nextDouble() * 1.5D * var3), true);
         this.setDead();
      }

   }

   public void handleHealthUpdate(byte var1) {
      if (var1 == 10) {
         this.ignite();
      } else {
         super.handleHealthUpdate(var1);
      }

   }

   public EntityMinecartTNT(World var1) {
      super(var1);
   }

   public float getExplosionResistance(Explosion var1, World var2, BlockPos var3, IBlockState var4) {
      return !this.isIgnited() || !BlockRailBase.func_176563_d(var4) && !BlockRailBase.func_176562_d(var2, var3.offsetUp()) ? super.getExplosionResistance(var1, var2, var3, var4) : 0.0F;
   }

   public void killMinecart(DamageSource var1) {
      super.killMinecart(var1);
      double var2 = this.motionX * this.motionX + this.motionZ * this.motionZ;
      if (!var1.isExplosion()) {
         this.entityDropItem(new ItemStack(Blocks.tnt, 1), 0.0F);
      }

      if (var1.isFireDamage() || var1.isExplosion() || var2 >= 0.009999999776482582D) {
         this.explodeCart(var2);
      }

   }

   public EntityMinecartTNT(World var1, double var2, double var4, double var6) {
      super(var1, var2, var4, var6);
   }

   public boolean isIgnited() {
      return this.minecartTNTFuse > -1;
   }

   public boolean func_174816_a(Explosion var1, World var2, BlockPos var3, IBlockState var4, float var5) {
      return !this.isIgnited() || !BlockRailBase.func_176563_d(var4) && !BlockRailBase.func_176562_d(var2, var3.offsetUp()) ? super.func_174816_a(var1, var2, var3, var4, var5) : false;
   }

   public void fall(float var1, float var2) {
      if (var1 >= 3.0F) {
         float var3 = var1 / 10.0F;
         this.explodeCart((double)(var3 * var3));
      }

      super.fall(var1, var2);
   }

   public int func_94104_d() {
      return this.minecartTNTFuse;
   }

   public void onActivatorRailPass(int var1, int var2, int var3, boolean var4) {
      if (var4 && this.minecartTNTFuse < 0) {
         this.ignite();
      }

   }

   public boolean attackEntityFrom(DamageSource var1, float var2) {
      Entity var3 = var1.getSourceOfDamage();
      if (var3 instanceof EntityArrow) {
         EntityArrow var4 = (EntityArrow)var3;
         if (var4.isBurning()) {
            this.explodeCart(var4.motionX * var4.motionX + var4.motionY * var4.motionY + var4.motionZ * var4.motionZ);
         }
      }

      return super.attackEntityFrom(var1, var2);
   }

   public IBlockState func_180457_u() {
      return Blocks.tnt.getDefaultState();
   }

   protected void writeEntityToNBT(NBTTagCompound var1) {
      super.writeEntityToNBT(var1);
      var1.setInteger("TNTFuse", this.minecartTNTFuse);
   }

   public EntityMinecart.EnumMinecartType func_180456_s() {
      return EntityMinecart.EnumMinecartType.TNT;
   }

   protected void readEntityFromNBT(NBTTagCompound var1) {
      super.readEntityFromNBT(var1);
      if (var1.hasKey("TNTFuse", 99)) {
         this.minecartTNTFuse = var1.getInteger("TNTFuse");
      }

   }

   public void ignite() {
      this.minecartTNTFuse = 80;
      if (!this.worldObj.isRemote) {
         this.worldObj.setEntityState(this, (byte)10);
         if (!this.isSlient()) {
            this.worldObj.playSoundAtEntity(this, "game.tnt.primed", 1.0F, 1.0F);
         }
      }

   }
}
