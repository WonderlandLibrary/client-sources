package net.minecraft.entity.item;

import java.util.Random;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;



public class EntityFireworkRocket
  extends Entity
{
  private int fireworkAge;
  private int lifetime;
  private static final String __OBFID = "CL_00001718";
  
  public EntityFireworkRocket(World worldIn)
  {
    super(worldIn);
    setSize(0.25F, 0.25F);
  }
  
  protected void entityInit()
  {
    dataWatcher.addObjectByDataType(8, 5);
  }
  




  public boolean isInRangeToRenderDist(double distance)
  {
    return distance < 4096.0D;
  }
  
  public EntityFireworkRocket(World worldIn, double p_i1763_2_, double p_i1763_4_, double p_i1763_6_, ItemStack p_i1763_8_)
  {
    super(worldIn);
    fireworkAge = 0;
    setSize(0.25F, 0.25F);
    setPosition(p_i1763_2_, p_i1763_4_, p_i1763_6_);
    int var9 = 1;
    
    if ((p_i1763_8_ != null) && (p_i1763_8_.hasTagCompound()))
    {
      dataWatcher.updateObject(8, p_i1763_8_);
      NBTTagCompound var10 = p_i1763_8_.getTagCompound();
      NBTTagCompound var11 = var10.getCompoundTag("Fireworks");
      
      if (var11 != null)
      {
        var9 += var11.getByte("Flight");
      }
    }
    
    motionX = (rand.nextGaussian() * 0.001D);
    motionZ = (rand.nextGaussian() * 0.001D);
    motionY = 0.05D;
    lifetime = (10 * var9 + rand.nextInt(6) + rand.nextInt(7));
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
    motionX *= 1.15D;
    motionZ *= 1.15D;
    motionY += 0.04D;
    moveEntity(motionX, motionY, motionZ);
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
    
    if ((fireworkAge == 0) && (!isSlient()))
    {
      worldObj.playSoundAtEntity(this, "fireworks.launch", 3.0F, 1.0F);
    }
    
    fireworkAge += 1;
    
    if ((worldObj.isRemote) && (fireworkAge % 2 < 2))
    {
      worldObj.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, posX, posY - 0.3D, posZ, rand.nextGaussian() * 0.05D, -motionY * 0.5D, rand.nextGaussian() * 0.05D, new int[0]);
    }
    
    if ((!worldObj.isRemote) && (fireworkAge > lifetime))
    {
      worldObj.setEntityState(this, (byte)17);
      setDead();
    }
  }
  
  public void handleHealthUpdate(byte p_70103_1_)
  {
    if ((p_70103_1_ == 17) && (worldObj.isRemote))
    {
      ItemStack var2 = dataWatcher.getWatchableObjectItemStack(8);
      NBTTagCompound var3 = null;
      
      if ((var2 != null) && (var2.hasTagCompound()))
      {
        var3 = var2.getTagCompound().getCompoundTag("Fireworks");
      }
      
      worldObj.makeFireworks(posX, posY, posZ, motionX, motionY, motionZ, var3);
    }
    
    super.handleHealthUpdate(p_70103_1_);
  }
  



  public void writeEntityToNBT(NBTTagCompound tagCompound)
  {
    tagCompound.setInteger("Life", fireworkAge);
    tagCompound.setInteger("LifeTime", lifetime);
    ItemStack var2 = dataWatcher.getWatchableObjectItemStack(8);
    
    if (var2 != null)
    {
      NBTTagCompound var3 = new NBTTagCompound();
      var2.writeToNBT(var3);
      tagCompound.setTag("FireworksItem", var3);
    }
  }
  



  public void readEntityFromNBT(NBTTagCompound tagCompund)
  {
    fireworkAge = tagCompund.getInteger("Life");
    lifetime = tagCompund.getInteger("LifeTime");
    NBTTagCompound var2 = tagCompund.getCompoundTag("FireworksItem");
    
    if (var2 != null)
    {
      ItemStack var3 = ItemStack.loadItemStackFromNBT(var2);
      
      if (var3 != null)
      {
        dataWatcher.updateObject(8, var3);
      }
    }
  }
  



  public float getBrightness(float p_70013_1_)
  {
    return super.getBrightness(p_70013_1_);
  }
  
  public int getBrightnessForRender(float p_70070_1_)
  {
    return super.getBrightnessForRender(p_70070_1_);
  }
  



  public boolean canAttackWithItem()
  {
    return false;
  }
}
