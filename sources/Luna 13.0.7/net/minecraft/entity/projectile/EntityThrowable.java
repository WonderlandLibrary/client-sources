package net.minecraft.entity.projectile;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.RegistryNamespacedDefaultedByKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class EntityThrowable
  extends Entity
  implements IProjectile
{
  private int xTile = -1;
  private int yTile = -1;
  private int zTile = -1;
  private Block field_174853_f;
  protected boolean field_174854_a;
  public int throwableShake;
  private EntityLivingBase thrower;
  private String throwerName;
  private int ticksInGround;
  private int ticksInAir;
  private static final String __OBFID = "CL_00001723";
  
  public EntityThrowable(World worldIn)
  {
    super(worldIn);
    setSize(0.25F, 0.25F);
  }
  
  protected void entityInit() {}
  
  public boolean isInRangeToRenderDist(double distance)
  {
    double var3 = getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
    var3 *= 64.0D;
    return distance < var3 * var3;
  }
  
  public EntityThrowable(World worldIn, EntityLivingBase p_i1777_2_)
  {
    super(worldIn);
    this.thrower = p_i1777_2_;
    setSize(0.25F, 0.25F);
    setLocationAndAngles(p_i1777_2_.posX, p_i1777_2_.posY + p_i1777_2_.getEyeHeight(), p_i1777_2_.posZ, p_i1777_2_.rotationYaw, p_i1777_2_.rotationPitch);
    this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * 0.16F;
    this.posY -= 0.10000000149011612D;
    this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * 0.16F;
    setPosition(this.posX, this.posY, this.posZ);
    float var3 = 0.4F;
    this.motionX = (-MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F) * var3);
    this.motionZ = (MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F) * var3);
    this.motionY = (-MathHelper.sin((this.rotationPitch + func_70183_g()) / 180.0F * 3.1415927F) * var3);
    setThrowableHeading(this.motionX, this.motionY, this.motionZ, func_70182_d(), 1.0F);
  }
  
  public EntityThrowable(World worldIn, double p_i1778_2_, double p_i1778_4_, double p_i1778_6_)
  {
    super(worldIn);
    this.ticksInGround = 0;
    setSize(0.25F, 0.25F);
    setPosition(p_i1778_2_, p_i1778_4_, p_i1778_6_);
  }
  
  protected float func_70182_d()
  {
    return 1.5F;
  }
  
  protected float func_70183_g()
  {
    return 0.0F;
  }
  
  public void setThrowableHeading(double p_70186_1_, double p_70186_3_, double p_70186_5_, float p_70186_7_, float p_70186_8_)
  {
    float var9 = MathHelper.sqrt_double(p_70186_1_ * p_70186_1_ + p_70186_3_ * p_70186_3_ + p_70186_5_ * p_70186_5_);
    p_70186_1_ /= var9;
    p_70186_3_ /= var9;
    p_70186_5_ /= var9;
    p_70186_1_ += this.rand.nextGaussian() * 0.007499999832361937D * p_70186_8_;
    p_70186_3_ += this.rand.nextGaussian() * 0.007499999832361937D * p_70186_8_;
    p_70186_5_ += this.rand.nextGaussian() * 0.007499999832361937D * p_70186_8_;
    p_70186_1_ *= p_70186_7_;
    p_70186_3_ *= p_70186_7_;
    p_70186_5_ *= p_70186_7_;
    this.motionX = p_70186_1_;
    this.motionY = p_70186_3_;
    this.motionZ = p_70186_5_;
    float var10 = MathHelper.sqrt_double(p_70186_1_ * p_70186_1_ + p_70186_5_ * p_70186_5_);
    this.prevRotationYaw = (this.rotationYaw = (float)(Math.atan2(p_70186_1_, p_70186_5_) * 180.0D / 3.141592653589793D));
    this.prevRotationPitch = (this.rotationPitch = (float)(Math.atan2(p_70186_3_, var10) * 180.0D / 3.141592653589793D));
    this.ticksInGround = 0;
  }
  
  public void setVelocity(double x, double y, double z)
  {
    this.motionX = x;
    this.motionY = y;
    this.motionZ = z;
    if ((this.prevRotationPitch == 0.0F) && (this.prevRotationYaw == 0.0F))
    {
      float var7 = MathHelper.sqrt_double(x * x + z * z);
      this.prevRotationYaw = (this.rotationYaw = (float)(Math.atan2(x, z) * 180.0D / 3.141592653589793D));
      this.prevRotationPitch = (this.rotationPitch = (float)(Math.atan2(y, var7) * 180.0D / 3.141592653589793D));
    }
  }
  
  public void onUpdate()
  {
    this.lastTickPosX = this.posX;
    this.lastTickPosY = this.posY;
    this.lastTickPosZ = this.posZ;
    super.onUpdate();
    if (this.throwableShake > 0) {
      this.throwableShake -= 1;
    }
    if (this.field_174854_a)
    {
      if (this.worldObj.getBlockState(new BlockPos(this.xTile, this.yTile, this.zTile)).getBlock() == this.field_174853_f)
      {
        this.ticksInGround += 1;
        if (this.ticksInGround == 1200) {
          setDead();
        }
        return;
      }
      this.field_174854_a = false;
      this.motionX *= this.rand.nextFloat() * 0.2F;
      this.motionY *= this.rand.nextFloat() * 0.2F;
      this.motionZ *= this.rand.nextFloat() * 0.2F;
      this.ticksInGround = 0;
      this.ticksInAir = 0;
    }
    else
    {
      this.ticksInAir += 1;
    }
    Vec3 var1 = new Vec3(this.posX, this.posY, this.posZ);
    Vec3 var2 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
    MovingObjectPosition var3 = this.worldObj.rayTraceBlocks(var1, var2);
    var1 = new Vec3(this.posX, this.posY, this.posZ);
    var2 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
    if (var3 != null) {
      var2 = new Vec3(var3.hitVec.xCoord, var3.hitVec.yCoord, var3.hitVec.zCoord);
    }
    if (!this.worldObj.isRemote)
    {
      Entity var4 = null;
      List var5 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
      double var6 = 0.0D;
      EntityLivingBase var8 = getThrower();
      for (int var9 = 0; var9 < var5.size(); var9++)
      {
        Entity var10 = (Entity)var5.get(var9);
        if ((var10.canBeCollidedWith()) && ((var10 != var8) || (this.ticksInAir >= 5)))
        {
          float var11 = 0.3F;
          AxisAlignedBB var12 = var10.getEntityBoundingBox().expand(var11, var11, var11);
          MovingObjectPosition var13 = var12.calculateIntercept(var1, var2);
          if (var13 != null)
          {
            double var14 = var1.distanceTo(var13.hitVec);
            if ((var14 < var6) || (var6 == 0.0D))
            {
              var4 = var10;
              var6 = var14;
            }
          }
        }
      }
      if (var4 != null) {
        var3 = new MovingObjectPosition(var4);
      }
    }
    if (var3 != null) {
      if ((var3.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) && (this.worldObj.getBlockState(var3.func_178782_a()).getBlock() == Blocks.portal)) {
        setInPortal();
      } else {
        onImpact(var3);
      }
    }
    this.posX += this.motionX;
    this.posY += this.motionY;
    this.posZ += this.motionZ;
    float var16 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
    this.rotationYaw = ((float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / 3.141592653589793D));
    for (this.rotationPitch = ((float)(Math.atan2(this.motionY, var16) * 180.0D / 3.141592653589793D)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {}
    while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
      this.prevRotationPitch += 360.0F;
    }
    while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
      this.prevRotationYaw -= 360.0F;
    }
    while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
      this.prevRotationYaw += 360.0F;
    }
    this.rotationPitch = (this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F);
    this.rotationYaw = (this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F);
    float var17 = 0.99F;
    float var18 = getGravityVelocity();
    if (isInWater())
    {
      for (int var7 = 0; var7 < 4; var7++)
      {
        float var19 = 0.25F;
        this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * var19, this.posY - this.motionY * var19, this.posZ - this.motionZ * var19, this.motionX, this.motionY, this.motionZ, new int[0]);
      }
      var17 = 0.8F;
    }
    this.motionX *= var17;
    this.motionY *= var17;
    this.motionZ *= var17;
    this.motionY -= var18;
    setPosition(this.posX, this.posY, this.posZ);
  }
  
  protected float getGravityVelocity()
  {
    return 0.03F;
  }
  
  protected abstract void onImpact(MovingObjectPosition paramMovingObjectPosition);
  
  public void writeEntityToNBT(NBTTagCompound tagCompound)
  {
    tagCompound.setShort("xTile", (short)this.xTile);
    tagCompound.setShort("yTile", (short)this.yTile);
    tagCompound.setShort("zTile", (short)this.zTile);
    ResourceLocation var2 = (ResourceLocation)Block.blockRegistry.getNameForObject(this.field_174853_f);
    tagCompound.setString("inTile", var2 == null ? "" : var2.toString());
    tagCompound.setByte("shake", (byte)this.throwableShake);
    tagCompound.setByte("inGround", (byte)(this.field_174854_a ? 1 : 0));
    if (((this.throwerName == null) || (this.throwerName.length() == 0)) && ((this.thrower instanceof EntityPlayer))) {
      this.throwerName = this.thrower.getName();
    }
    tagCompound.setString("ownerName", this.throwerName == null ? "" : this.throwerName);
  }
  
  public void readEntityFromNBT(NBTTagCompound tagCompund)
  {
    this.xTile = tagCompund.getShort("xTile");
    this.yTile = tagCompund.getShort("yTile");
    this.zTile = tagCompund.getShort("zTile");
    if (tagCompund.hasKey("inTile", 8)) {
      this.field_174853_f = Block.getBlockFromName(tagCompund.getString("inTile"));
    } else {
      this.field_174853_f = Block.getBlockById(tagCompund.getByte("inTile") & 0xFF);
    }
    this.throwableShake = (tagCompund.getByte("shake") & 0xFF);
    this.field_174854_a = (tagCompund.getByte("inGround") == 1);
    this.throwerName = tagCompund.getString("ownerName");
    if ((this.throwerName != null) && (this.throwerName.length() == 0)) {
      this.throwerName = null;
    }
  }
  
  public EntityLivingBase getThrower()
  {
    if ((this.thrower == null) && (this.throwerName != null) && (this.throwerName.length() > 0)) {
      this.thrower = this.worldObj.getPlayerEntityByName(this.throwerName);
    }
    return this.thrower;
  }
}
