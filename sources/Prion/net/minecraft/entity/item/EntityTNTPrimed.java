package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityTNTPrimed
  extends Entity
{
  public int fuse;
  private EntityLivingBase tntPlacedBy;
  private static final String __OBFID = "CL_00001681";
  
  public EntityTNTPrimed(World worldIn)
  {
    super(worldIn);
    preventEntitySpawning = true;
    setSize(0.98F, 0.98F);
  }
  
  public EntityTNTPrimed(World worldIn, double p_i1730_2_, double p_i1730_4_, double p_i1730_6_, EntityLivingBase p_i1730_8_)
  {
    this(worldIn);
    setPosition(p_i1730_2_, p_i1730_4_, p_i1730_6_);
    float var9 = (float)(Math.random() * 3.141592653589793D * 2.0D);
    motionX = (-(float)Math.sin(var9) * 0.02F);
    motionY = 0.20000000298023224D;
    motionZ = (-(float)Math.cos(var9) * 0.02F);
    fuse = 80;
    prevPosX = p_i1730_2_;
    prevPosY = p_i1730_4_;
    prevPosZ = p_i1730_6_;
    tntPlacedBy = p_i1730_8_;
  }
  


  protected void entityInit() {}
  


  protected boolean canTriggerWalking()
  {
    return false;
  }
  



  public boolean canBeCollidedWith()
  {
    return !isDead;
  }
  



  public void onUpdate()
  {
    prevPosX = posX;
    prevPosY = posY;
    prevPosZ = posZ;
    motionY -= 0.03999999910593033D;
    moveEntity(motionX, motionY, motionZ);
    motionX *= 0.9800000190734863D;
    motionY *= 0.9800000190734863D;
    motionZ *= 0.9800000190734863D;
    
    if (onGround)
    {
      motionX *= 0.699999988079071D;
      motionZ *= 0.699999988079071D;
      motionY *= -0.5D;
    }
    
    if (fuse-- <= 0)
    {
      setDead();
      
      if (!worldObj.isRemote)
      {
        explode();
      }
    }
    else
    {
      handleWaterMovement();
      worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX, posY + 0.5D, posZ, 0.0D, 0.0D, 0.0D, new int[0]);
    }
  }
  
  private void explode()
  {
    float var1 = 4.0F;
    worldObj.createExplosion(this, posX, posY + height / 2.0F, posZ, var1, true);
  }
  



  protected void writeEntityToNBT(NBTTagCompound tagCompound)
  {
    tagCompound.setByte("Fuse", (byte)fuse);
  }
  



  protected void readEntityFromNBT(NBTTagCompound tagCompund)
  {
    fuse = tagCompund.getByte("Fuse");
  }
  



  public EntityLivingBase getTntPlacedBy()
  {
    return tntPlacedBy;
  }
  
  public float getEyeHeight()
  {
    return 0.0F;
  }
}
