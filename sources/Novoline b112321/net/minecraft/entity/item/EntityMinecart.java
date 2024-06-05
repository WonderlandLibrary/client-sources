package net.minecraft.entity.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRailBase$EnumRailDirection;
import net.minecraft.block.BlockRailPowered;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityMinecartCommandBlock;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.entity.item.EntityMinecart$1;
import net.minecraft.entity.item.EntityMinecart$EnumMinecartType;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.item.EntityMinecartEmpty;
import net.minecraft.entity.item.EntityMinecartFurnace;
import net.minecraft.entity.item.EntityMinecartHopper;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public abstract class EntityMinecart extends Entity implements IWorldNameable {
   private boolean isInReverse;
   private String entityName;
   private static final int[][][] matrix = new int[][][]{{{0, 0, -1}, {0, 0, 1}}, {{-1, 0, 0}, {1, 0, 0}}, {{-1, -1, 0}, {1, 0, 0}}, {{-1, 0, 0}, {1, -1, 0}}, {{0, 0, -1}, {0, -1, 1}}, {{0, -1, -1}, {0, 0, 1}}, {{0, 0, 1}, {1, 0, 0}}, {{0, 0, 1}, {-1, 0, 0}}, {{0, 0, -1}, {-1, 0, 0}}, {{0, 0, -1}, {1, 0, 0}}};
   private int turnProgress;
   private double minecartX;
   private double minecartY;
   private double minecartZ;
   private double minecartYaw;
   private double minecartPitch;
   private double velocityX;
   private double velocityY;
   private double velocityZ;

   public EntityMinecart(World var1) {
      super(var1);
      this.preventEntitySpawning = true;
      this.setSize(0.98F, 0.7F);
   }

   public static EntityMinecart func_180458_a(World var0, double var1, double var3, double var5, EntityMinecart$EnumMinecartType var7) {
      switch(EntityMinecart$1.$SwitchMap$net$minecraft$entity$item$EntityMinecart$EnumMinecartType[var7.ordinal()]) {
      case 1:
         return new EntityMinecartChest(var0, var1, var3, var5);
      case 2:
         return new EntityMinecartFurnace(var0, var1, var3, var5);
      case 3:
         return new EntityMinecartTNT(var0, var1, var3, var5);
      case 4:
         return new EntityMinecartMobSpawner(var0, var1, var3, var5);
      case 5:
         return new EntityMinecartHopper(var0, var1, var3, var5);
      case 6:
         return new EntityMinecartCommandBlock(var0, var1, var3, var5);
      default:
         return new EntityMinecartEmpty(var0, var1, var3, var5);
      }
   }

   protected boolean canTriggerWalking() {
      return false;
   }

   protected void entityInit() {
      this.I.b(17, Integer.valueOf(0));
      this.I.b(18, Integer.valueOf(1));
      this.I.b(19, Float.valueOf(0.0F));
      this.I.b(20, Integer.valueOf(0));
      this.I.b(21, Integer.valueOf(6));
      this.I.b(22, Byte.valueOf((byte)0));
   }

   public AxisAlignedBB getCollisionBox(Entity var1) {
      return var1.canBePushed()?var1.getEntityBoundingBox():null;
   }

   public AxisAlignedBB getCollisionBoundingBox() {
      return null;
   }

   public boolean canBePushed() {
      return true;
   }

   public EntityMinecart(World var1, double var2, double var4, double var6) {
      this(var1);
      this.setPosition(var2, var4, var6);
      this.motionX = 0.0D;
      this.motionY = 0.0D;
      this.motionZ = 0.0D;
      this.prevPosX = var2;
      this.prevPosY = var4;
      this.prevPosZ = var6;
   }

   public double getMountedYOffset() {
      return 0.0D;
   }

   public boolean attackEntityFrom(DamageSource var1, float var2) {
      if(!this.worldObj.isRemote && !this.isDead) {
         if(this.isEntityInvulnerable(var1)) {
            return false;
         } else {
            this.setRollingDirection(-this.getRollingDirection());
            this.setRollingAmplitude(10);
            this.setBeenAttacked();
            this.setDamage(this.getDamage() + var2 * 10.0F);
            boolean var3 = var1.getEntity() instanceof EntityPlayer && ((EntityPlayer)var1.getEntity()).abilities.isCreative();
            if(this.getDamage() > 40.0F) {
               if(this.riddenByEntity != null) {
                  this.riddenByEntity.mountEntity((Entity)null);
               }

               if(!this.hasCustomName()) {
                  this.setDead();
               } else {
                  this.killMinecart(var1);
               }
            }

            return true;
         }
      } else {
         return true;
      }
   }

   public void killMinecart(DamageSource var1) {
      this.setDead();
      if(this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
         ItemStack var2 = new ItemStack(Items.minecart, 1);
         if(this.entityName != null) {
            var2.setStackDisplayName(this.entityName);
         }

         this.entityDropItem(var2, 0.0F);
      }

   }

   public void performHurtAnimation() {
      this.setRollingDirection(-this.getRollingDirection());
      this.setRollingAmplitude(10);
      this.setDamage(this.getDamage() + this.getDamage() * 10.0F);
   }

   public boolean canBeCollidedWith() {
      return !this.isDead;
   }

   public void setDead() {
      super.setDead();
   }

   public void onUpdate() {
      if(this.getRollingAmplitude() > 0) {
         this.setRollingAmplitude(this.getRollingAmplitude() - 1);
      }

      if(this.getDamage() > 0.0F) {
         this.setDamage(this.getDamage() - 1.0F);
      }

      if(this.posY < -64.0D) {
         this.kill();
      }

      if(!this.worldObj.isRemote && this.worldObj instanceof WorldServer) {
         this.worldObj.theProfiler.startSection("portal");
         MinecraftServer var1 = ((WorldServer)this.worldObj).getMinecraftServer();
         int var2 = this.getMaxInPortalTime();
         if(this.inPortal) {
            if(var1.getAllowNether()) {
               if(this.ridingEntity == null && this.portalCounter++ >= var2) {
                  this.portalCounter = var2;
                  this.timeUntilPortal = this.getPortalCoolDown();
                  byte var3;
                  if(this.worldObj.provider.getDimensionId() == -1) {
                     var3 = 0;
                  } else {
                     var3 = -1;
                  }

                  this.travelToDimension(var3);
               }

               this.inPortal = false;
            }
         } else {
            if(this.portalCounter > 0) {
               this.portalCounter -= 4;
            }

            if(this.portalCounter < 0) {
               this.portalCounter = 0;
            }
         }

         if(this.timeUntilPortal > 0) {
            --this.timeUntilPortal;
         }

         this.worldObj.theProfiler.endSection();
      }

      if(this.worldObj.isRemote) {
         if(this.turnProgress > 0) {
            double var14 = this.posX + (this.minecartX - this.posX) / (double)this.turnProgress;
            double var17 = this.posY + (this.minecartY - this.posY) / (double)this.turnProgress;
            double var5 = this.posZ + (this.minecartZ - this.posZ) / (double)this.turnProgress;
            double var7 = MathHelper.wrapAngleTo180_double(this.minecartYaw - (double)this.rotationYaw);
            this.rotationYaw = (float)((double)this.rotationYaw + var7 / (double)this.turnProgress);
            this.rotationPitch = (float)((double)this.rotationPitch + (this.minecartPitch - (double)this.rotationPitch) / (double)this.turnProgress);
            --this.turnProgress;
            this.setPosition(var14, var17, var5);
         } else {
            this.setPosition(this.posX, this.posY, this.posZ);
         }

         this.setRotation(this.rotationYaw, this.rotationPitch);
      } else {
         this.prevPosX = this.posX;
         this.prevPosY = this.posY;
         this.prevPosZ = this.posZ;
         this.motionY -= 0.03999999910593033D;
         int var15 = MathHelper.floor_double(this.posX);
         int var16 = MathHelper.floor_double(this.posY);
         int var18 = MathHelper.floor_double(this.posZ);
         if(BlockRailBase.isRailBlock(this.worldObj, new BlockPos(var15, var16 - 1, var18))) {
            --var16;
         }

         BlockPos var4 = new BlockPos(var15, var16, var18);
         IBlockState var19 = this.worldObj.getBlockState(var4);
         if(BlockRailBase.isRailBlock(var19)) {
            this.func_180460_a(var4, var19);
            if(var19.getBlock() == Blocks.activator_rail) {
               this.onActivatorRailPass(var15, var16, var18, ((Boolean)var19.getValue(BlockRailPowered.POWERED)).booleanValue());
            }
         } else {
            this.moveDerailedMinecart();
         }

         this.doBlockCollisions();
         this.rotationPitch = 0.0F;
         double var6 = this.prevPosX - this.posX;
         double var8 = this.prevPosZ - this.posZ;
         if(var6 * var6 + var8 * var8 > 0.001D) {
            this.rotationYaw = (float)(MathHelper.func_181159_b(var8, var6) * 180.0D / 3.141592653589793D);
            if(this.isInReverse) {
               this.rotationYaw += 180.0F;
            }
         }

         double var10 = (double)MathHelper.wrapAngleTo180_float(this.rotationYaw - this.prevRotationYaw);
         if(var10 < -170.0D || var10 >= 170.0D) {
            this.rotationYaw += 180.0F;
            this.isInReverse = !this.isInReverse;
         }

         this.setRotation(this.rotationYaw, this.rotationPitch);

         for(Entity var13 : this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(0.20000000298023224D, 0.0D, 0.20000000298023224D))) {
            if(var13 != this.riddenByEntity && var13.canBePushed() && var13 instanceof EntityMinecart) {
               var13.applyEntityCollision(this);
            }
         }

         if(this.riddenByEntity != null && this.riddenByEntity.isDead) {
            if(this.riddenByEntity.ridingEntity == this) {
               this.riddenByEntity.ridingEntity = null;
            }

            this.riddenByEntity = null;
         }

         this.handleWaterMovement();
      }

   }

   protected double getMaximumSpeed() {
      return 0.4D;
   }

   public void onActivatorRailPass(int var1, int var2, int var3, boolean var4) {
   }

   protected void moveDerailedMinecart() {
      double var1 = this.getMaximumSpeed();
      this.motionX = MathHelper.clamp_double(this.motionX, -var1, var1);
      this.motionZ = MathHelper.clamp_double(this.motionZ, -var1, var1);
      if(this.onGround) {
         this.motionX *= 0.5D;
         this.motionY *= 0.5D;
         this.motionZ *= 0.5D;
      }

      this.moveEntity(this.motionX, this.motionY, this.motionZ);
      if(!this.onGround) {
         this.motionX *= 0.949999988079071D;
         this.motionY *= 0.949999988079071D;
         this.motionZ *= 0.949999988079071D;
      }

   }

   protected void func_180460_a(BlockPos var1, IBlockState var2) {
      this.fallDistance = 0.0F;
      Vec3 var3 = this.func_70489_a(this.posX, this.posY, this.posZ);
      this.posY = (double)var1.getY();
      boolean var4 = false;
      boolean var5 = false;
      BlockRailBase var6 = (BlockRailBase)var2.getBlock();
      if(var6 == Blocks.golden_rail) {
         var4 = ((Boolean)var2.getValue(BlockRailPowered.POWERED)).booleanValue();
         var5 = true;
      }

      BlockRailBase$EnumRailDirection var7 = (BlockRailBase$EnumRailDirection)var2.getValue(var6.getShapeProperty());
      switch(EntityMinecart$1.$SwitchMap$net$minecraft$block$BlockRailBase$EnumRailDirection[var7.ordinal()]) {
      case 1:
         this.motionX -= 0.0078125D;
         ++this.posY;
         break;
      case 2:
         this.motionX += 0.0078125D;
         ++this.posY;
         break;
      case 3:
         this.motionZ += 0.0078125D;
         ++this.posY;
         break;
      case 4:
         this.motionZ -= 0.0078125D;
         ++this.posY;
      }

      int[][] var8 = matrix[var7.getMetadata()];
      double var9 = (double)(var8[1][0] - var8[0][0]);
      double var11 = (double)(var8[1][2] - var8[0][2]);
      double var13 = Math.sqrt(var9 * var9 + var11 * var11);
      double var15 = this.motionX * var9 + this.motionZ * var11;
      if(var15 < 0.0D) {
         var9 = -var9;
         var11 = -var11;
      }

      double var17 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
      if(var17 > 2.0D) {
         var17 = 2.0D;
      }

      this.motionX = var17 * var9 / var13;
      this.motionZ = var17 * var11 / var13;
      if(this.riddenByEntity instanceof EntityLivingBase) {
         double var19 = (double)((EntityLivingBase)this.riddenByEntity).moveForward;
         if(var19 > 0.0D) {
            double var21 = (double)(-MathHelper.sin((double)(this.riddenByEntity.rotationYaw * 3.1415927F / 180.0F)));
            double var23 = (double)MathHelper.cos((double)(this.riddenByEntity.rotationYaw * 3.1415927F / 180.0F));
            double var25 = this.motionX * this.motionX + this.motionZ * this.motionZ;
            if(var25 < 0.01D) {
               this.motionX += var21 * 0.1D;
               this.motionZ += var23 * 0.1D;
               var5 = false;
            }
         }
      }

      double var49 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
      if(var49 < 0.03D) {
         this.motionX *= 0.0D;
         this.motionY *= 0.0D;
         this.motionZ *= 0.0D;
      } else {
         this.motionX *= 0.5D;
         this.motionY *= 0.0D;
         this.motionZ *= 0.5D;
      }

      double var51 = (double)var1.getX() + 0.5D + (double)var8[0][0] * 0.5D;
      double var52 = (double)var1.getZ() + 0.5D + (double)var8[0][2] * 0.5D;
      double var53 = (double)var1.getX() + 0.5D + (double)var8[1][0] * 0.5D;
      double var27 = (double)var1.getZ() + 0.5D + (double)var8[1][2] * 0.5D;
      var9 = var53 - var51;
      var11 = var27 - var52;
      if(var9 == 0.0D) {
         this.posX = (double)var1.getX() + 0.5D;
         var49 = this.posZ - (double)var1.getZ();
      } else if(var11 == 0.0D) {
         this.posZ = (double)var1.getZ() + 0.5D;
         var49 = this.posX - (double)var1.getX();
      } else {
         double var29 = this.posX - var51;
         double var31 = this.posZ - var52;
         var49 = (var29 * var9 + var31 * var11) * 2.0D;
      }

      this.posX = var51 + var9 * var49;
      this.posZ = var52 + var11 * var49;
      this.setPosition(this.posX, this.posY, this.posZ);
      double var54 = this.motionX;
      double var56 = this.motionZ;
      if(this.riddenByEntity != null) {
         var54 *= 0.75D;
         var56 *= 0.75D;
      }

      double var33 = this.getMaximumSpeed();
      var54 = MathHelper.clamp_double(var54, -var33, var33);
      var56 = MathHelper.clamp_double(var56, -var33, var33);
      this.moveEntity(var54, 0.0D, var56);
      if(var8[0][1] != 0 && MathHelper.floor_double(this.posX) - var1.getX() == var8[0][0] && MathHelper.floor_double(this.posZ) - var1.getZ() == var8[0][2]) {
         this.setPosition(this.posX, this.posY + (double)var8[0][1], this.posZ);
      } else if(var8[1][1] != 0 && MathHelper.floor_double(this.posX) - var1.getX() == var8[1][0] && MathHelper.floor_double(this.posZ) - var1.getZ() == var8[1][2]) {
         this.setPosition(this.posX, this.posY + (double)var8[1][1], this.posZ);
      }

      this.applyDrag();
      Vec3 var35 = this.func_70489_a(this.posX, this.posY, this.posZ);
      double var36 = (var3.yCoord - var35.yCoord) * 0.05D;
      var17 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
      if(var17 > 0.0D) {
         this.motionX = this.motionX / var17 * (var17 + var36);
         this.motionZ = this.motionZ / var17 * (var17 + var36);
      }

      this.setPosition(this.posX, var35.yCoord, this.posZ);
      int var58 = MathHelper.floor_double(this.posX);
      int var37 = MathHelper.floor_double(this.posZ);
      if(var58 != var1.getX() || var37 != var1.getZ()) {
         var17 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
         this.motionX = var17 * (double)(var58 - var1.getX());
         this.motionZ = var17 * (double)(var37 - var1.getZ());
      }

      double var38 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
      if(var38 > 0.01D) {
         double var40 = 0.06D;
         this.motionX += this.motionX / var38 * var40;
         this.motionZ += this.motionZ / var38 * var40;
      } else if(var7 == BlockRailBase$EnumRailDirection.EAST_WEST) {
         if(this.worldObj.getBlockState(var1.west()).getBlock().isNormalCube()) {
            this.motionX = 0.02D;
         } else if(this.worldObj.getBlockState(var1.east()).getBlock().isNormalCube()) {
            this.motionX = -0.02D;
         }
      } else if(var7 == BlockRailBase$EnumRailDirection.NORTH_SOUTH) {
         if(this.worldObj.getBlockState(var1.north()).getBlock().isNormalCube()) {
            this.motionZ = 0.02D;
         } else if(this.worldObj.getBlockState(var1.south()).getBlock().isNormalCube()) {
            this.motionZ = -0.02D;
         }
      }

   }

   protected void applyDrag() {
      if(this.riddenByEntity != null) {
         this.motionX *= 0.996999979019165D;
         this.motionY *= 0.0D;
         this.motionZ *= 0.996999979019165D;
      } else {
         this.motionX *= 0.9599999785423279D;
         this.motionY *= 0.0D;
         this.motionZ *= 0.9599999785423279D;
      }

   }

   public void setPosition(double var1, double var3, double var5) {
      this.posX = var1;
      this.posY = var3;
      this.posZ = var5;
      float var7 = this.width / 2.0F;
      float var8 = this.height;
      this.setEntityBoundingBox(new AxisAlignedBB(var1 - (double)var7, var3, var5 - (double)var7, var1 + (double)var7, var3 + (double)var8, var5 + (double)var7));
   }

   public Vec3 func_70495_a(double var1, double var3, double var5, double var7) {
      int var9 = MathHelper.floor_double(var1);
      int var10 = MathHelper.floor_double(var3);
      int var11 = MathHelper.floor_double(var5);
      if(BlockRailBase.isRailBlock(this.worldObj, new BlockPos(var9, var10 - 1, var11))) {
         --var10;
      }

      IBlockState var12 = this.worldObj.getBlockState(new BlockPos(var9, var10, var11));
      if(BlockRailBase.isRailBlock(var12)) {
         BlockRailBase$EnumRailDirection var13 = (BlockRailBase$EnumRailDirection)var12.getValue(((BlockRailBase)var12.getBlock()).getShapeProperty());
         var3 = (double)var10;
         if(var13.isAscending()) {
            var3 = (double)(var10 + 1);
         }

         int[][] var14 = matrix[var13.getMetadata()];
         double var15 = (double)(var14[1][0] - var14[0][0]);
         double var17 = (double)(var14[1][2] - var14[0][2]);
         double var19 = Math.sqrt(var15 * var15 + var17 * var17);
         var15 = var15 / var19;
         var17 = var17 / var19;
         var1 = var1 + var15 * var7;
         var5 = var5 + var17 * var7;
         if(var14[0][1] != 0 && MathHelper.floor_double(var1) - var9 == var14[0][0] && MathHelper.floor_double(var5) - var11 == var14[0][2]) {
            var3 += (double)var14[0][1];
         } else if(var14[1][1] != 0 && MathHelper.floor_double(var1) - var9 == var14[1][0] && MathHelper.floor_double(var5) - var11 == var14[1][2]) {
            var3 += (double)var14[1][1];
         }

         return this.func_70489_a(var1, var3, var5);
      } else {
         return null;
      }
   }

   public Vec3 func_70489_a(double var1, double var3, double var5) {
      int var7 = MathHelper.floor_double(var1);
      int var8 = MathHelper.floor_double(var3);
      int var9 = MathHelper.floor_double(var5);
      if(BlockRailBase.isRailBlock(this.worldObj, new BlockPos(var7, var8 - 1, var9))) {
         --var8;
      }

      IBlockState var10 = this.worldObj.getBlockState(new BlockPos(var7, var8, var9));
      if(BlockRailBase.isRailBlock(var10)) {
         BlockRailBase$EnumRailDirection var11 = (BlockRailBase$EnumRailDirection)var10.getValue(((BlockRailBase)var10.getBlock()).getShapeProperty());
         int[][] var12 = matrix[var11.getMetadata()];
         double var15 = (double)var7 + 0.5D + (double)var12[0][0] * 0.5D;
         double var17 = (double)var8 + 0.0625D + (double)var12[0][1] * 0.5D;
         double var19 = (double)var9 + 0.5D + (double)var12[0][2] * 0.5D;
         double var21 = (double)var7 + 0.5D + (double)var12[1][0] * 0.5D;
         double var23 = (double)var8 + 0.0625D + (double)var12[1][1] * 0.5D;
         double var25 = (double)var9 + 0.5D + (double)var12[1][2] * 0.5D;
         double var27 = var21 - var15;
         double var29 = (var23 - var17) * 2.0D;
         double var31 = var25 - var19;
         double var13;
         if(var27 == 0.0D) {
            var13 = var5 - (double)var9;
         } else if(var31 == 0.0D) {
            var13 = var1 - (double)var7;
         } else {
            double var33 = var1 - var15;
            double var35 = var5 - var19;
            var13 = (var33 * var27 + var35 * var31) * 2.0D;
         }

         var1 = var15 + var27 * var13;
         var3 = var17 + var29 * var13;
         var5 = var19 + var31 * var13;
         if(var29 < 0.0D) {
            ++var3;
         }

         if(var29 > 0.0D) {
            var3 += 0.5D;
         }

         return new Vec3(var1, var3, var5);
      } else {
         return null;
      }
   }

   protected void readEntityFromNBT(NBTTagCompound var1) {
      if(var1.getBoolean("CustomDisplayTile")) {
         int var2 = var1.getInteger("DisplayData");
         if(var1.hasKey("DisplayTile", 8)) {
            Block var3 = Block.getBlockFromName(var1.getString("DisplayTile"));
            this.func_174899_a(Blocks.air.getDefaultState());
         } else {
            Block var4 = Block.getBlockById(var1.getInteger("DisplayTile"));
            this.func_174899_a(Blocks.air.getDefaultState());
         }

         this.setDisplayTileOffset(var1.getInteger("DisplayOffset"));
      }

      if(var1.hasKey("CustomName", 8) && !var1.getString("CustomName").isEmpty()) {
         this.entityName = var1.getString("CustomName");
      }

   }

   protected void writeEntityToNBT(NBTTagCompound var1) {
      if(this.hasDisplayTile()) {
         var1.setBoolean("CustomDisplayTile", true);
         IBlockState var2 = this.getDisplayTile();
         ResourceLocation var3 = (ResourceLocation)Block.blockRegistry.getNameForObject(var2.getBlock());
         var1.setString("DisplayTile", "");
         var1.setInteger("DisplayData", var2.getBlock().getMetaFromState(var2));
         var1.setInteger("DisplayOffset", this.getDisplayTileOffset());
      }

      if(this.entityName != null && !this.entityName.isEmpty()) {
         var1.setString("CustomName", this.entityName);
      }

   }

   public void applyEntityCollision(Entity var1) {
      if(!this.worldObj.isRemote && !var1.noClip && !this.noClip && var1 != this.riddenByEntity) {
         if(var1 instanceof EntityLivingBase && !(var1 instanceof EntityPlayer) && !(var1 instanceof EntityIronGolem) && this.getMinecartType() == EntityMinecart$EnumMinecartType.RIDEABLE && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.01D && this.riddenByEntity == null && var1.ridingEntity == null) {
            var1.mountEntity(this);
         }

         double var2 = var1.posX - this.posX;
         double var4 = var1.posZ - this.posZ;
         double var6 = var2 * var2 + var4 * var4;
         if(var6 >= 9.999999747378752E-5D) {
            var6 = (double)MathHelper.sqrt_double(var6);
            var2 = var2 / var6;
            var4 = var4 / var6;
            double var8 = 1.0D / var6;
            if(var8 > 1.0D) {
               var8 = 1.0D;
            }

            var2 = var2 * var8;
            var4 = var4 * var8;
            var2 = var2 * 0.10000000149011612D;
            var4 = var4 * 0.10000000149011612D;
            var2 = var2 * (double)(1.0F - this.entityCollisionReduction);
            var4 = var4 * (double)(1.0F - this.entityCollisionReduction);
            var2 = var2 * 0.5D;
            var4 = var4 * 0.5D;
            if(var1 instanceof EntityMinecart) {
               double var10 = var1.posX - this.posX;
               double var12 = var1.posZ - this.posZ;
               Vec3 var14 = (new Vec3(var10, 0.0D, var12)).normalize();
               Vec3 var15 = (new Vec3((double)MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F), 0.0D, (double)MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F))).normalize();
               double var16 = Math.abs(var14.dotProduct(var15));
               if(var16 < 0.800000011920929D) {
                  return;
               }

               double var18 = var1.motionX + this.motionX;
               double var20 = var1.motionZ + this.motionZ;
               if(((EntityMinecart)var1).getMinecartType() == EntityMinecart$EnumMinecartType.FURNACE && this.getMinecartType() != EntityMinecart$EnumMinecartType.FURNACE) {
                  this.motionX *= 0.20000000298023224D;
                  this.motionZ *= 0.20000000298023224D;
                  this.addVelocity(var1.motionX - var2, 0.0D, var1.motionZ - var4);
                  var1.motionX *= 0.949999988079071D;
                  var1.motionZ *= 0.949999988079071D;
               } else if(((EntityMinecart)var1).getMinecartType() != EntityMinecart$EnumMinecartType.FURNACE && this.getMinecartType() == EntityMinecart$EnumMinecartType.FURNACE) {
                  var1.motionX *= 0.20000000298023224D;
                  var1.motionZ *= 0.20000000298023224D;
                  var1.addVelocity(this.motionX + var2, 0.0D, this.motionZ + var4);
                  this.motionX *= 0.949999988079071D;
                  this.motionZ *= 0.949999988079071D;
               } else {
                  var18 = var18 / 2.0D;
                  var20 = var20 / 2.0D;
                  this.motionX *= 0.20000000298023224D;
                  this.motionZ *= 0.20000000298023224D;
                  this.addVelocity(var18 - var2, 0.0D, var20 - var4);
                  var1.motionX *= 0.20000000298023224D;
                  var1.motionZ *= 0.20000000298023224D;
                  var1.addVelocity(var18 + var2, 0.0D, var20 + var4);
               }
            } else {
               this.addVelocity(-var2, 0.0D, -var4);
               var1.addVelocity(var2 / 4.0D, 0.0D, var4 / 4.0D);
            }
         }
      }

   }

   public void setPositionAndRotation2(double var1, double var3, double var5, float var7, float var8, int var9, boolean var10) {
      this.minecartX = var1;
      this.minecartY = var3;
      this.minecartZ = var5;
      this.minecartYaw = (double)var7;
      this.minecartPitch = (double)var8;
      this.turnProgress = var9 + 2;
      this.motionX = this.velocityX;
      this.motionY = this.velocityY;
      this.motionZ = this.velocityZ;
   }

   public void setVelocity(double var1, double var3, double var5) {
      this.velocityX = this.motionX = var1;
      this.velocityY = this.motionY = var3;
      this.velocityZ = this.motionZ = var5;
   }

   public float getDamage() {
      return this.I.b(19);
   }

   public void setDamage(float var1) {
      this.I.a(19, Float.valueOf(var1));
   }

   public int getRollingAmplitude() {
      return this.I.c(17);
   }

   public void setRollingAmplitude(int var1) {
      this.I.a(17, Integer.valueOf(var1));
   }

   public int getRollingDirection() {
      return this.I.c(18);
   }

   public void setRollingDirection(int var1) {
      this.I.a(18, Integer.valueOf(var1));
   }

   public abstract EntityMinecart$EnumMinecartType getMinecartType();

   public IBlockState getDisplayTile() {
      return !this.hasDisplayTile()?this.getDefaultDisplayTile():Block.getStateById(this.k().c(20));
   }

   public IBlockState getDefaultDisplayTile() {
      return Blocks.air.getDefaultState();
   }

   public int getDisplayTileOffset() {
      return !this.hasDisplayTile()?this.getDefaultDisplayTileOffset():this.k().c(21);
   }

   public void setDisplayTileOffset(int var1) {
      this.k().a(21, Integer.valueOf(var1));
      this.setHasDisplayTile(true);
   }

   public int getDefaultDisplayTileOffset() {
      return 6;
   }

   public void func_174899_a(IBlockState var1) {
      this.k().a(20, Integer.valueOf(Block.getStateId(var1)));
      this.setHasDisplayTile(true);
   }

   public boolean hasDisplayTile() {
      return this.k().g(22) == 1;
   }

   public void setHasDisplayTile(boolean var1) {
      this.k().a(22, Byte.valueOf((byte)1));
   }

   public String getName() {
      return this.entityName != null?this.entityName:super.getName();
   }

   public boolean hasCustomName() {
      return this.entityName != null;
   }

   public String getCustomNameTag() {
      return this.entityName;
   }

   public void setCustomNameTag(String var1) {
      this.entityName = var1;
   }

   public IChatComponent getDisplayName() {
      if(this.hasCustomName()) {
         ChatComponentText var2 = new ChatComponentText(this.entityName);
         var2.getChatStyle().setChatHoverEvent(this.getHoverEvent());
         var2.getChatStyle().setInsertion(this.getUniqueID().toString());
         return var2;
      } else {
         ChatComponentTranslation var1 = new ChatComponentTranslation(this.getName(), new Object[0]);
         var1.getChatStyle().setChatHoverEvent(this.getHoverEvent());
         var1.getChatStyle().setInsertion(this.getUniqueID().toString());
         return var1;
      }
   }
}
