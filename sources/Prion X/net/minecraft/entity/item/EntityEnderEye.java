package net.minecraft.entity.item;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;



public class EntityEnderEye
  extends Entity
{
  private double targetX;
  private double targetY;
  private double targetZ;
  private int despawnTimer;
  private boolean shatterOrDrop;
  private static final String __OBFID = "CL_00001716";
  
  public EntityEnderEye(World worldIn)
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
  
  public EntityEnderEye(World worldIn, double p_i1758_2_, double p_i1758_4_, double p_i1758_6_)
  {
    super(worldIn);
    despawnTimer = 0;
    setSize(0.25F, 0.25F);
    setPosition(p_i1758_2_, p_i1758_4_, p_i1758_6_);
  }
  
  public void func_180465_a(BlockPos p_180465_1_)
  {
    double var2 = p_180465_1_.getX();
    int var4 = p_180465_1_.getY();
    double var5 = p_180465_1_.getZ();
    double var7 = var2 - posX;
    double var9 = var5 - posZ;
    float var11 = MathHelper.sqrt_double(var7 * var7 + var9 * var9);
    
    if (var11 > 12.0F)
    {
      targetX = (posX + var7 / var11 * 12.0D);
      targetZ = (posZ + var9 / var11 * 12.0D);
      targetY = (posY + 8.0D);
    }
    else
    {
      targetX = var2;
      targetY = var4;
      targetZ = var5;
    }
    
    despawnTimer = 0;
    shatterOrDrop = (rand.nextInt(5) > 0);
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
    posX += motionX;
    posY += motionY;
    posZ += motionZ;
    float var1 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
    rotationYaw = ((float)(Math.atan2(motionX, motionZ) * 180.0D / 3.141592653589793D));
    
    for (rotationPitch = ((float)(Math.atan2(motionY, var1) * 180.0D / 3.141592653589793D)); rotationPitch - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F) {}
    



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
    
    if (!worldObj.isRemote)
    {
      double var2 = targetX - posX;
      double var4 = targetZ - posZ;
      float var6 = (float)Math.sqrt(var2 * var2 + var4 * var4);
      float var7 = (float)Math.atan2(var4, var2);
      double var8 = var1 + (var6 - var1) * 0.0025D;
      
      if (var6 < 1.0F)
      {
        var8 *= 0.8D;
        motionY *= 0.8D;
      }
      
      motionX = (Math.cos(var7) * var8);
      motionZ = (Math.sin(var7) * var8);
      
      if (posY < targetY)
      {
        motionY += (1.0D - motionY) * 0.014999999664723873D;
      }
      else
      {
        motionY += (-1.0D - motionY) * 0.014999999664723873D;
      }
    }
    
    float var10 = 0.25F;
    
    if (isInWater())
    {
      for (int var3 = 0; var3 < 4; var3++)
      {
        worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, posX - motionX * var10, posY - motionY * var10, posZ - motionZ * var10, motionX, motionY, motionZ, new int[0]);
      }
      
    }
    else {
      worldObj.spawnParticle(EnumParticleTypes.PORTAL, posX - motionX * var10 + rand.nextDouble() * 0.6D - 0.3D, posY - motionY * var10 - 0.5D, posZ - motionZ * var10 + rand.nextDouble() * 0.6D - 0.3D, motionX, motionY, motionZ, new int[0]);
    }
    
    if (!worldObj.isRemote)
    {
      setPosition(posX, posY, posZ);
      despawnTimer += 1;
      
      if ((despawnTimer > 80) && (!worldObj.isRemote))
      {
        setDead();
        
        if (shatterOrDrop)
        {
          worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, new ItemStack(Items.ender_eye)));
        }
        else
        {
          worldObj.playAuxSFX(2003, new BlockPos(this), 0);
        }
      }
    }
  }
  



  public void writeEntityToNBT(NBTTagCompound tagCompound) {}
  



  public void readEntityFromNBT(NBTTagCompound tagCompund) {}
  



  public float getBrightness(float p_70013_1_)
  {
    return 1.0F;
  }
  
  public int getBrightnessForRender(float p_70070_1_)
  {
    return 15728880;
  }
  



  public boolean canAttackWithItem()
  {
    return false;
  }
}
