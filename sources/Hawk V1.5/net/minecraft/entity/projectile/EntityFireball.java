package net.minecraft.entity.projectile;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class EntityFireball extends Entity {
   public double accelerationZ;
   public EntityLivingBase shootingEntity;
   public double accelerationY;
   public double accelerationX;
   private boolean inGround;
   private Block field_145796_h;
   private int field_145795_e = -1;
   private int field_145793_f = -1;
   private int ticksAlive;
   private int field_145794_g = -1;
   private static final String __OBFID = "CL_00001717";
   private int ticksInAir;

   public EntityFireball(World var1, EntityLivingBase var2, double var3, double var5, double var7) {
      super(var1);
      this.shootingEntity = var2;
      this.setSize(1.0F, 1.0F);
      this.setLocationAndAngles(var2.posX, var2.posY, var2.posZ, var2.rotationYaw, var2.rotationPitch);
      this.setPosition(this.posX, this.posY, this.posZ);
      this.motionX = this.motionY = this.motionZ = 0.0D;
      var3 += this.rand.nextGaussian() * 0.4D;
      var5 += this.rand.nextGaussian() * 0.4D;
      var7 += this.rand.nextGaussian() * 0.4D;
      double var9 = (double)MathHelper.sqrt_double(var3 * var3 + var5 * var5 + var7 * var7);
      this.accelerationX = var3 / var9 * 0.1D;
      this.accelerationY = var5 / var9 * 0.1D;
      this.accelerationZ = var7 / var9 * 0.1D;
   }

   public void readEntityFromNBT(NBTTagCompound var1) {
      this.field_145795_e = var1.getShort("xTile");
      this.field_145793_f = var1.getShort("yTile");
      this.field_145794_g = var1.getShort("zTile");
      if (var1.hasKey("inTile", 8)) {
         this.field_145796_h = Block.getBlockFromName(var1.getString("inTile"));
      } else {
         this.field_145796_h = Block.getBlockById(var1.getByte("inTile") & 255);
      }

      this.inGround = var1.getByte("inGround") == 1;
      if (var1.hasKey("direction", 9)) {
         NBTTagList var2 = var1.getTagList("direction", 6);
         this.motionX = var2.getDouble(0);
         this.motionY = var2.getDouble(1);
         this.motionZ = var2.getDouble(2);
      } else {
         this.setDead();
      }

   }

   public EntityFireball(World var1) {
      super(var1);
      this.setSize(1.0F, 1.0F);
   }

   public int getBrightnessForRender(float var1) {
      return 15728880;
   }

   protected void entityInit() {
   }

   protected float getMotionFactor() {
      return 0.95F;
   }

   public float getBrightness(float var1) {
      return 1.0F;
   }

   public void writeEntityToNBT(NBTTagCompound var1) {
      var1.setShort("xTile", (short)this.field_145795_e);
      var1.setShort("yTile", (short)this.field_145793_f);
      var1.setShort("zTile", (short)this.field_145794_g);
      ResourceLocation var2 = (ResourceLocation)Block.blockRegistry.getNameForObject(this.field_145796_h);
      var1.setString("inTile", var2 == null ? "" : var2.toString());
      var1.setByte("inGround", (byte)(this.inGround ? 1 : 0));
      var1.setTag("direction", this.newDoubleNBTList(new double[]{this.motionX, this.motionY, this.motionZ}));
   }

   public void onUpdate() {
      if (!this.worldObj.isRemote && (this.shootingEntity != null && this.shootingEntity.isDead || !this.worldObj.isBlockLoaded(new BlockPos(this)))) {
         this.setDead();
      } else {
         super.onUpdate();
         this.setFire(1);
         if (this.inGround) {
            if (this.worldObj.getBlockState(new BlockPos(this.field_145795_e, this.field_145793_f, this.field_145794_g)).getBlock() == this.field_145796_h) {
               ++this.ticksAlive;
               if (this.ticksAlive == 600) {
                  this.setDead();
               }

               return;
            }

            this.inGround = false;
            this.motionX *= (double)(this.rand.nextFloat() * 0.2F);
            this.motionY *= (double)(this.rand.nextFloat() * 0.2F);
            this.motionZ *= (double)(this.rand.nextFloat() * 0.2F);
            this.ticksAlive = 0;
            this.ticksInAir = 0;
         } else {
            ++this.ticksInAir;
         }

         Vec3 var1 = new Vec3(this.posX, this.posY, this.posZ);
         Vec3 var2 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
         MovingObjectPosition var3 = this.worldObj.rayTraceBlocks(var1, var2);
         var1 = new Vec3(this.posX, this.posY, this.posZ);
         var2 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
         if (var3 != null) {
            var2 = new Vec3(var3.hitVec.xCoord, var3.hitVec.yCoord, var3.hitVec.zCoord);
         }

         Entity var4 = null;
         List var5 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
         double var6 = 0.0D;

         for(int var8 = 0; var8 < var5.size(); ++var8) {
            Entity var9 = (Entity)var5.get(var8);
            if (var9.canBeCollidedWith() && (!var9.isEntityEqual(this.shootingEntity) || this.ticksInAir >= 25)) {
               float var10 = 0.3F;
               AxisAlignedBB var11 = var9.getEntityBoundingBox().expand((double)var10, (double)var10, (double)var10);
               MovingObjectPosition var12 = var11.calculateIntercept(var1, var2);
               if (var12 != null) {
                  double var13 = var1.distanceTo(var12.hitVec);
                  if (var13 < var6 || var6 == 0.0D) {
                     var4 = var9;
                     var6 = var13;
                  }
               }
            }
         }

         if (var4 != null) {
            var3 = new MovingObjectPosition(var4);
         }

         if (var3 != null) {
            this.onImpact(var3);
         }

         this.posX += this.motionX;
         this.posY += this.motionY;
         this.posZ += this.motionZ;
         float var15 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
         this.rotationYaw = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0D / 3.141592653589793D) + 90.0F;

         for(this.rotationPitch = (float)(Math.atan2((double)var15, this.motionY) * 180.0D / 3.141592653589793D) - 90.0F; this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
         }

         while(this.rotationPitch - this.prevRotationPitch >= 180.0F) {
            this.prevRotationPitch += 360.0F;
         }

         while(this.rotationYaw - this.prevRotationYaw < -180.0F) {
            this.prevRotationYaw -= 360.0F;
         }

         while(this.rotationYaw - this.prevRotationYaw >= 180.0F) {
            this.prevRotationYaw += 360.0F;
         }

         this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
         this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
         float var16 = this.getMotionFactor();
         if (this.isInWater()) {
            for(int var17 = 0; var17 < 4; ++var17) {
               float var18 = 0.25F;
               this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * (double)var18, this.posY - this.motionY * (double)var18, this.posZ - this.motionZ * (double)var18, this.motionX, this.motionY, this.motionZ);
            }

            var16 = 0.8F;
         }

         this.motionX += this.accelerationX;
         this.motionY += this.accelerationY;
         this.motionZ += this.accelerationZ;
         this.motionX *= (double)var16;
         this.motionY *= (double)var16;
         this.motionZ *= (double)var16;
         this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
         this.setPosition(this.posX, this.posY, this.posZ);
      }

   }

   public boolean isInRangeToRenderDist(double var1) {
      double var3 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
      var3 *= 64.0D;
      return var1 < var3 * var3;
   }

   protected abstract void onImpact(MovingObjectPosition var1);

   public boolean attackEntityFrom(DamageSource var1, float var2) {
      if (this.func_180431_b(var1)) {
         return false;
      } else {
         this.setBeenAttacked();
         if (var1.getEntity() != null) {
            Vec3 var3 = var1.getEntity().getLookVec();
            if (var3 != null) {
               this.motionX = var3.xCoord;
               this.motionY = var3.yCoord;
               this.motionZ = var3.zCoord;
               this.accelerationX = this.motionX * 0.1D;
               this.accelerationY = this.motionY * 0.1D;
               this.accelerationZ = this.motionZ * 0.1D;
            }

            if (var1.getEntity() instanceof EntityLivingBase) {
               this.shootingEntity = (EntityLivingBase)var1.getEntity();
            }

            return true;
         } else {
            return false;
         }
      }
   }

   public float getCollisionBorderSize() {
      return 1.0F;
   }

   public boolean canBeCollidedWith() {
      return true;
   }

   public EntityFireball(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(var1);
      this.setSize(1.0F, 1.0F);
      this.setLocationAndAngles(var2, var4, var6, this.rotationYaw, this.rotationPitch);
      this.setPosition(var2, var4, var6);
      double var14 = (double)MathHelper.sqrt_double(var8 * var8 + var10 * var10 + var12 * var12);
      this.accelerationX = var8 / var14 * 0.1D;
      this.accelerationY = var10 / var14 * 0.1D;
      this.accelerationZ = var12 / var14 * 0.1D;
   }
}
