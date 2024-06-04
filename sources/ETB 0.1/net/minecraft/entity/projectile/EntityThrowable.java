package net.minecraft.entity.projectile;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.RegistryNamespacedDefaultedByKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class EntityThrowable extends Entity implements net.minecraft.entity.IProjectile
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
    thrower = p_i1777_2_;
    setSize(0.25F, 0.25F);
    setLocationAndAngles(posX, posY + p_i1777_2_.getEyeHeight(), posZ, rotationYaw, rotationPitch);
    posX -= MathHelper.cos(rotationYaw / 180.0F * 3.1415927F) * 0.16F;
    posY -= 0.10000000149011612D;
    posZ -= MathHelper.sin(rotationYaw / 180.0F * 3.1415927F) * 0.16F;
    setPosition(posX, posY, posZ);
    float var3 = 0.4F;
    motionX = (-MathHelper.sin(rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(rotationPitch / 180.0F * 3.1415927F) * var3);
    motionZ = (MathHelper.cos(rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(rotationPitch / 180.0F * 3.1415927F) * var3);
    motionY = (-MathHelper.sin((rotationPitch + func_70183_g()) / 180.0F * 3.1415927F) * var3);
    setThrowableHeading(motionX, motionY, motionZ, func_70182_d(), 1.0F);
  }
  
  public EntityThrowable(World worldIn, double p_i1778_2_, double p_i1778_4_, double p_i1778_6_)
  {
    super(worldIn);
    ticksInGround = 0;
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
    p_70186_1_ += rand.nextGaussian() * 0.007499999832361937D * p_70186_8_;
    p_70186_3_ += rand.nextGaussian() * 0.007499999832361937D * p_70186_8_;
    p_70186_5_ += rand.nextGaussian() * 0.007499999832361937D * p_70186_8_;
    p_70186_1_ *= p_70186_7_;
    p_70186_3_ *= p_70186_7_;
    p_70186_5_ *= p_70186_7_;
    motionX = p_70186_1_;
    motionY = p_70186_3_;
    motionZ = p_70186_5_;
    float var10 = MathHelper.sqrt_double(p_70186_1_ * p_70186_1_ + p_70186_5_ * p_70186_5_);
    prevRotationYaw = (this.rotationYaw = (float)(Math.atan2(p_70186_1_, p_70186_5_) * 180.0D / 3.141592653589793D));
    prevRotationPitch = (this.rotationPitch = (float)(Math.atan2(p_70186_3_, var10) * 180.0D / 3.141592653589793D));
    ticksInGround = 0;
  }
  



  public void setVelocity(double x, double y, double z)
  {
    motionX = x;
    motionY = y;
    motionZ = z;
    
    if ((prevRotationPitch == 0.0F) && (prevRotationYaw == 0.0F))
    {
      float var7 = MathHelper.sqrt_double(x * x + z * z);
      prevRotationYaw = (this.rotationYaw = (float)(Math.atan2(x, z) * 180.0D / 3.141592653589793D));
      prevRotationPitch = (this.rotationPitch = (float)(Math.atan2(y, var7) * 180.0D / 3.141592653589793D));
    }
  }
  



  public void onUpdate()
  {
    lastTickPosX = posX;
    lastTickPosY = posY;
    lastTickPosZ = posZ;
    super.onUpdate();
    
    if (throwableShake > 0)
    {
      throwableShake -= 1;
    }
    
    if (field_174854_a)
    {
      if (worldObj.getBlockState(new net.minecraft.util.BlockPos(xTile, yTile, zTile)).getBlock() == field_174853_f)
      {
        ticksInGround += 1;
        
        if (ticksInGround == 1200)
        {
          setDead();
        }
        
        return;
      }
      
      field_174854_a = false;
      motionX *= rand.nextFloat() * 0.2F;
      motionY *= rand.nextFloat() * 0.2F;
      motionZ *= rand.nextFloat() * 0.2F;
      ticksInGround = 0;
      ticksInAir = 0;
    }
    else
    {
      ticksInAir += 1;
    }
    
    Vec3 var1 = new Vec3(posX, posY, posZ);
    Vec3 var2 = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
    MovingObjectPosition var3 = worldObj.rayTraceBlocks(var1, var2);
    var1 = new Vec3(posX, posY, posZ);
    var2 = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
    
    if (var3 != null)
    {
      var2 = new Vec3(hitVec.xCoord, hitVec.yCoord, hitVec.zCoord);
    }
    
    if (!worldObj.isRemote)
    {
      Entity var4 = null;
      List var5 = worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
      double var6 = 0.0D;
      EntityLivingBase var8 = getThrower();
      
      for (int var9 = 0; var9 < var5.size(); var9++)
      {
        Entity var10 = (Entity)var5.get(var9);
        
        if ((var10.canBeCollidedWith()) && ((var10 != var8) || (ticksInAir >= 5)))
        {
          float var11 = 0.3F;
          AxisAlignedBB var12 = var10.getEntityBoundingBox().expand(var11, var11, var11);
          MovingObjectPosition var13 = var12.calculateIntercept(var1, var2);
          
          if (var13 != null)
          {
            double var14 = var1.distanceTo(hitVec);
            
            if ((var14 < var6) || (var6 == 0.0D))
            {
              var4 = var10;
              var6 = var14;
            }
          }
        }
      }
      
      if (var4 != null)
      {
        var3 = new MovingObjectPosition(var4);
      }
    }
    
    if (var3 != null)
    {
      if ((typeOfHit == net.minecraft.util.MovingObjectPosition.MovingObjectType.BLOCK) && (worldObj.getBlockState(var3.func_178782_a()).getBlock() == net.minecraft.init.Blocks.portal))
      {
        setInPortal();
      }
      else
      {
        onImpact(var3);
      }
    }
    
    posX += motionX;
    posY += motionY;
    posZ += motionZ;
    float var16 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
    rotationYaw = ((float)(Math.atan2(motionX, motionZ) * 180.0D / 3.141592653589793D));
    
    for (rotationPitch = ((float)(Math.atan2(motionY, var16) * 180.0D / 3.141592653589793D)); rotationPitch - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F) {}
    



    while (rotationPitch - prevRotationPitch >= 180.0F)
    {
      prevRotationPitch += 360.0F;
    }
    
    while (rotationYaw - prevRotationYaw < -180.0F)
    {
      prevRotationYaw -= 360.0F;
    }
    
    while (rotationYaw - prevRotationYaw >= 180.0F)
    {
      prevRotationYaw += 360.0F;
    }
    
    rotationPitch = (prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F);
    rotationYaw = (prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F);
    float var17 = 0.99F;
    float var18 = getGravityVelocity();
    
    if (isInWater())
    {
      for (int var7 = 0; var7 < 4; var7++)
      {
        float var19 = 0.25F;
        worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, posX - motionX * var19, posY - motionY * var19, posZ - motionZ * var19, motionX, motionY, motionZ, new int[0]);
      }
      
      var17 = 0.8F;
    }
    
    motionX *= var17;
    motionY *= var17;
    motionZ *= var17;
    motionY -= var18;
    setPosition(posX, posY, posZ);
  }
  



  protected float getGravityVelocity()
  {
    return 0.03F;
  }
  



  protected abstract void onImpact(MovingObjectPosition paramMovingObjectPosition);
  



  public void writeEntityToNBT(NBTTagCompound tagCompound)
  {
    tagCompound.setShort("xTile", (short)xTile);
    tagCompound.setShort("yTile", (short)yTile);
    tagCompound.setShort("zTile", (short)zTile);
    ResourceLocation var2 = (ResourceLocation)Block.blockRegistry.getNameForObject(field_174853_f);
    tagCompound.setString("inTile", var2 == null ? "" : var2.toString());
    tagCompound.setByte("shake", (byte)throwableShake);
    tagCompound.setByte("inGround", (byte)(field_174854_a ? 1 : 0));
    
    if (((throwerName == null) || (throwerName.length() == 0)) && ((thrower instanceof EntityPlayer)))
    {
      throwerName = thrower.getName();
    }
    
    tagCompound.setString("ownerName", throwerName == null ? "" : throwerName);
  }
  



  public void readEntityFromNBT(NBTTagCompound tagCompund)
  {
    xTile = tagCompund.getShort("xTile");
    yTile = tagCompund.getShort("yTile");
    zTile = tagCompund.getShort("zTile");
    
    if (tagCompund.hasKey("inTile", 8))
    {
      field_174853_f = Block.getBlockFromName(tagCompund.getString("inTile"));
    }
    else
    {
      field_174853_f = Block.getBlockById(tagCompund.getByte("inTile") & 0xFF);
    }
    
    throwableShake = (tagCompund.getByte("shake") & 0xFF);
    field_174854_a = (tagCompund.getByte("inGround") == 1);
    throwerName = tagCompund.getString("ownerName");
    
    if ((throwerName != null) && (throwerName.length() == 0))
    {
      throwerName = null;
    }
  }
  
  public EntityLivingBase getThrower()
  {
    if ((thrower == null) && (throwerName != null) && (throwerName.length() > 0))
    {
      thrower = worldObj.getPlayerEntityByName(throwerName);
    }
    
    return thrower;
  }
}
