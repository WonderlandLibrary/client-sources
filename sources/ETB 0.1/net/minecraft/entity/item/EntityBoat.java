package net.minecraft.entity.item;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityBoat extends Entity
{
  private boolean isBoatEmpty;
  private double speedMultiplier;
  private int boatPosRotationIncrements;
  private double boatX;
  private double boatY;
  private double boatZ;
  private double boatYaw;
  private double boatPitch;
  private double velocityX;
  private double velocityY;
  private double velocityZ;
  private static final String __OBFID = "CL_00001667";
  
  public EntityBoat(World worldIn)
  {
    super(worldIn);
    isBoatEmpty = true;
    speedMultiplier = 0.07D;
    preventEntitySpawning = true;
    setSize(1.5F, 0.6F);
  }
  




  protected boolean canTriggerWalking()
  {
    return false;
  }
  
  protected void entityInit()
  {
    dataWatcher.addObject(17, new Integer(0));
    dataWatcher.addObject(18, new Integer(1));
    dataWatcher.addObject(19, new Float(0.0F));
  }
  




  public AxisAlignedBB getCollisionBox(Entity entityIn)
  {
    return entityIn.getEntityBoundingBox();
  }
  



  public AxisAlignedBB getBoundingBox()
  {
    return getEntityBoundingBox();
  }
  



  public boolean canBePushed()
  {
    return true;
  }
  
  public EntityBoat(World worldIn, double p_i1705_2_, double p_i1705_4_, double p_i1705_6_)
  {
    this(worldIn);
    setPosition(p_i1705_2_, p_i1705_4_, p_i1705_6_);
    motionX = 0.0D;
    motionY = 0.0D;
    motionZ = 0.0D;
    prevPosX = p_i1705_2_;
    prevPosY = p_i1705_4_;
    prevPosZ = p_i1705_6_;
  }
  



  public double getMountedYOffset()
  {
    return height * 0.0D - 0.30000001192092896D;
  }
  



  public boolean attackEntityFrom(DamageSource source, float amount)
  {
    if (func_180431_b(source))
    {
      return false;
    }
    if ((!worldObj.isRemote) && (!isDead))
    {
      if ((riddenByEntity != null) && (riddenByEntity == source.getEntity()) && ((source instanceof net.minecraft.util.EntityDamageSourceIndirect)))
      {
        return false;
      }
      

      setForwardDirection(-getForwardDirection());
      setTimeSinceHit(10);
      setDamageTaken(getDamageTaken() + amount * 10.0F);
      setBeenAttacked();
      boolean var3 = ((source.getEntity() instanceof EntityPlayer)) && (getEntitycapabilities.isCreativeMode);
      
      if ((var3) || (getDamageTaken() > 40.0F))
      {
        if (riddenByEntity != null)
        {
          riddenByEntity.mountEntity(this);
        }
        
        if (!var3)
        {
          dropItemWithOffset(Items.boat, 1, 0.0F);
        }
        
        setDead();
      }
      
      return true;
    }
    


    return true;
  }
  




  public void performHurtAnimation()
  {
    setForwardDirection(-getForwardDirection());
    setTimeSinceHit(10);
    setDamageTaken(getDamageTaken() * 11.0F);
  }
  



  public boolean canBeCollidedWith()
  {
    return !isDead;
  }
  
  public void func_180426_a(double p_180426_1_, double p_180426_3_, double p_180426_5_, float p_180426_7_, float p_180426_8_, int p_180426_9_, boolean p_180426_10_)
  {
    if ((p_180426_10_) && (riddenByEntity != null))
    {
      prevPosX = (this.posX = p_180426_1_);
      prevPosY = (this.posY = p_180426_3_);
      prevPosZ = (this.posZ = p_180426_5_);
      rotationYaw = p_180426_7_;
      rotationPitch = p_180426_8_;
      boatPosRotationIncrements = 0;
      setPosition(p_180426_1_, p_180426_3_, p_180426_5_);
      motionX = (this.velocityX = 0.0D);
      motionY = (this.velocityY = 0.0D);
      motionZ = (this.velocityZ = 0.0D);
    }
    else
    {
      if (isBoatEmpty)
      {
        boatPosRotationIncrements = (p_180426_9_ + 5);
      }
      else
      {
        double var11 = p_180426_1_ - posX;
        double var13 = p_180426_3_ - posY;
        double var15 = p_180426_5_ - posZ;
        double var17 = var11 * var11 + var13 * var13 + var15 * var15;
        
        if (var17 <= 1.0D)
        {
          return;
        }
        
        boatPosRotationIncrements = 3;
      }
      
      boatX = p_180426_1_;
      boatY = p_180426_3_;
      boatZ = p_180426_5_;
      boatYaw = p_180426_7_;
      boatPitch = p_180426_8_;
      motionX = velocityX;
      motionY = velocityY;
      motionZ = velocityZ;
    }
  }
  



  public void setVelocity(double x, double y, double z)
  {
    velocityX = (this.motionX = x);
    velocityY = (this.motionY = y);
    velocityZ = (this.motionZ = z);
  }
  



  public void onUpdate()
  {
    super.onUpdate();
    
    if (getTimeSinceHit() > 0)
    {
      setTimeSinceHit(getTimeSinceHit() - 1);
    }
    
    if (getDamageTaken() > 0.0F)
    {
      setDamageTaken(getDamageTaken() - 1.0F);
    }
    
    prevPosX = posX;
    prevPosY = posY;
    prevPosZ = posZ;
    byte var1 = 5;
    double var2 = 0.0D;
    
    for (int var4 = 0; var4 < var1; var4++)
    {
      double var5 = getEntityBoundingBoxminY + (getEntityBoundingBoxmaxY - getEntityBoundingBoxminY) * (var4 + 0) / var1 - 0.125D;
      double var7 = getEntityBoundingBoxminY + (getEntityBoundingBoxmaxY - getEntityBoundingBoxminY) * (var4 + 1) / var1 - 0.125D;
      AxisAlignedBB var9 = new AxisAlignedBB(getEntityBoundingBoxminX, var5, getEntityBoundingBoxminZ, getEntityBoundingBoxmaxX, var7, getEntityBoundingBoxmaxZ);
      
      if (worldObj.isAABBInMaterial(var9, net.minecraft.block.material.Material.water))
      {
        var2 += 1.0D / var1;
      }
    }
    
    double var19 = Math.sqrt(motionX * motionX + motionZ * motionZ);
    



    if (var19 > 0.2975D)
    {
      double var6 = Math.cos(rotationYaw * 3.141592653589793D / 180.0D);
      double var8 = Math.sin(rotationYaw * 3.141592653589793D / 180.0D);
      
      for (int var10 = 0; var10 < 1.0D + var19 * 60.0D; var10++)
      {
        double var11 = rand.nextFloat() * 2.0F - 1.0F;
        double var13 = (rand.nextInt(2) * 2 - 1) * 0.7D;
        


        if (rand.nextBoolean())
        {
          double var15 = posX - var6 * var11 * 0.8D + var8 * var13;
          double var17 = posZ - var8 * var11 * 0.8D - var6 * var13;
          worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, var15, posY - 0.125D, var17, motionX, motionY, motionZ, new int[0]);
        }
        else
        {
          double var15 = posX + var6 + var8 * var11 * 0.7D;
          double var17 = posZ + var8 - var6 * var11 * 0.7D;
          worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, var15, posY - 0.125D, var17, motionX, motionY, motionZ, new int[0]);
        }
      }
    }
    



    if ((worldObj.isRemote) && (isBoatEmpty))
    {
      if (boatPosRotationIncrements > 0)
      {
        double var6 = posX + (boatX - posX) / boatPosRotationIncrements;
        double var8 = posY + (boatY - posY) / boatPosRotationIncrements;
        double var24 = posZ + (boatZ - posZ) / boatPosRotationIncrements;
        double var26 = MathHelper.wrapAngleTo180_double(boatYaw - rotationYaw);
        rotationYaw = ((float)(rotationYaw + var26 / boatPosRotationIncrements));
        rotationPitch = ((float)(rotationPitch + (boatPitch - rotationPitch) / boatPosRotationIncrements));
        boatPosRotationIncrements -= 1;
        setPosition(var6, var8, var24);
        setRotation(rotationYaw, rotationPitch);
      }
      else
      {
        double var6 = posX + motionX;
        double var8 = posY + motionY;
        double var24 = posZ + motionZ;
        setPosition(var6, var8, var24);
        
        if (onGround)
        {
          motionX *= 0.5D;
          motionY *= 0.5D;
          motionZ *= 0.5D;
        }
        
        motionX *= 0.9900000095367432D;
        motionY *= 0.949999988079071D;
        motionZ *= 0.9900000095367432D;
      }
    }
    else
    {
      if (var2 < 1.0D)
      {
        double var6 = var2 * 2.0D - 1.0D;
        motionY += 0.03999999910593033D * var6;
      }
      else
      {
        if (motionY < 0.0D)
        {
          motionY /= 2.0D;
        }
        
        motionY += 0.007000000216066837D;
      }
      
      if ((riddenByEntity instanceof EntityLivingBase))
      {
        EntityLivingBase var20 = (EntityLivingBase)riddenByEntity;
        float var21 = riddenByEntity.rotationYaw + -moveStrafing * 90.0F;
        motionX += -Math.sin(var21 * 3.1415927F / 180.0F) * speedMultiplier * moveForward * 0.05000000074505806D;
        motionZ += Math.cos(var21 * 3.1415927F / 180.0F) * speedMultiplier * moveForward * 0.05000000074505806D;
      }
      
      double var6 = Math.sqrt(motionX * motionX + motionZ * motionZ);
      
      if (var6 > 0.35D)
      {
        double var8 = 0.35D / var6;
        motionX *= var8;
        motionZ *= var8;
        var6 = 0.35D;
      }
      
      if ((var6 > var19) && (speedMultiplier < 0.35D))
      {
        speedMultiplier += (0.35D - speedMultiplier) / 35.0D;
        
        if (speedMultiplier > 0.35D)
        {
          speedMultiplier = 0.35D;
        }
      }
      else
      {
        speedMultiplier -= (speedMultiplier - 0.07D) / 35.0D;
        
        if (speedMultiplier < 0.07D)
        {
          speedMultiplier = 0.07D;
        }
      }
      


      for (int var22 = 0; var22 < 4; var22++)
      {
        int var23 = MathHelper.floor_double(posX + (var22 % 2 - 0.5D) * 0.8D);
        int var10 = MathHelper.floor_double(posZ + (var22 / 2 - 0.5D) * 0.8D);
        
        for (int var25 = 0; var25 < 2; var25++)
        {
          int var12 = MathHelper.floor_double(posY) + var25;
          BlockPos var27 = new BlockPos(var23, var12, var10);
          Block var14 = worldObj.getBlockState(var27).getBlock();
          
          if (var14 == Blocks.snow_layer)
          {
            worldObj.setBlockToAir(var27);
            isCollidedHorizontally = false;
          }
          else if (var14 == Blocks.waterlily)
          {
            worldObj.destroyBlock(var27, true);
            isCollidedHorizontally = false;
          }
        }
      }
      
      if (onGround)
      {
        motionX *= 0.5D;
        motionY *= 0.5D;
        motionZ *= 0.5D;
      }
      
      moveEntity(motionX, motionY, motionZ);
      
      if ((isCollidedHorizontally) && (var19 > 0.2D))
      {
        if ((!worldObj.isRemote) && (!isDead))
        {
          setDead();
          
          for (var22 = 0; var22 < 3; var22++)
          {
            dropItemWithOffset(Item.getItemFromBlock(Blocks.planks), 1, 0.0F);
          }
          
          for (var22 = 0; var22 < 2; var22++)
          {
            dropItemWithOffset(Items.stick, 1, 0.0F);
          }
        }
      }
      else
      {
        motionX *= 0.9900000095367432D;
        motionY *= 0.949999988079071D;
        motionZ *= 0.9900000095367432D;
      }
      
      rotationPitch = 0.0F;
      double var8 = rotationYaw;
      double var24 = prevPosX - posX;
      double var26 = prevPosZ - posZ;
      
      if (var24 * var24 + var26 * var26 > 0.001D)
      {
        var8 = (float)(Math.atan2(var26, var24) * 180.0D / 3.141592653589793D);
      }
      
      double var28 = MathHelper.wrapAngleTo180_double(var8 - rotationYaw);
      
      if (var28 > 20.0D)
      {
        var28 = 20.0D;
      }
      
      if (var28 < -20.0D)
      {
        var28 = -20.0D;
      }
      
      rotationYaw = ((float)(rotationYaw + var28));
      setRotation(rotationYaw, rotationPitch);
      
      if (!worldObj.isRemote)
      {
        List var16 = worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
        
        if ((var16 != null) && (!var16.isEmpty()))
        {
          for (int var29 = 0; var29 < var16.size(); var29++)
          {
            Entity var18 = (Entity)var16.get(var29);
            
            if ((var18 != riddenByEntity) && (var18.canBePushed()) && ((var18 instanceof EntityBoat)))
            {
              var18.applyEntityCollision(this);
            }
          }
        }
        
        if ((riddenByEntity != null) && (riddenByEntity.isDead))
        {
          riddenByEntity = null;
        }
      }
    }
  }
  
  public void updateRiderPosition()
  {
    if (riddenByEntity != null)
    {
      double var1 = Math.cos(rotationYaw * 3.141592653589793D / 180.0D) * 0.4D;
      double var3 = Math.sin(rotationYaw * 3.141592653589793D / 180.0D) * 0.4D;
      riddenByEntity.setPosition(posX + var1, posY + getMountedYOffset() + riddenByEntity.getYOffset(), posZ + var3);
    }
  }
  



  protected void writeEntityToNBT(NBTTagCompound tagCompound) {}
  



  protected void readEntityFromNBT(NBTTagCompound tagCompund) {}
  



  public boolean interactFirst(EntityPlayer playerIn)
  {
    if ((riddenByEntity != null) && ((riddenByEntity instanceof EntityPlayer)) && (riddenByEntity != playerIn))
    {
      return true;
    }
    

    if (!worldObj.isRemote)
    {
      playerIn.mountEntity(this);
    }
    
    return true;
  }
  

  protected void func_180433_a(double p_180433_1_, boolean p_180433_3_, Block p_180433_4_, BlockPos p_180433_5_)
  {
    if (p_180433_3_)
    {
      if (fallDistance > 3.0F)
      {
        fall(fallDistance, 1.0F);
        
        if ((!worldObj.isRemote) && (!isDead))
        {
          setDead();
          

          for (int var6 = 0; var6 < 3; var6++)
          {
            dropItemWithOffset(Item.getItemFromBlock(Blocks.planks), 1, 0.0F);
          }
          
          for (var6 = 0; var6 < 2; var6++)
          {
            dropItemWithOffset(Items.stick, 1, 0.0F);
          }
        }
        
        fallDistance = 0.0F;
      }
    }
    else if ((worldObj.getBlockState(new BlockPos(this).offsetDown()).getBlock().getMaterial() != net.minecraft.block.material.Material.water) && (p_180433_1_ < 0.0D))
    {
      fallDistance = ((float)(fallDistance - p_180433_1_));
    }
  }
  



  public void setDamageTaken(float p_70266_1_)
  {
    dataWatcher.updateObject(19, Float.valueOf(p_70266_1_));
  }
  



  public float getDamageTaken()
  {
    return dataWatcher.getWatchableObjectFloat(19);
  }
  



  public void setTimeSinceHit(int p_70265_1_)
  {
    dataWatcher.updateObject(17, Integer.valueOf(p_70265_1_));
  }
  



  public int getTimeSinceHit()
  {
    return dataWatcher.getWatchableObjectInt(17);
  }
  



  public void setForwardDirection(int p_70269_1_)
  {
    dataWatcher.updateObject(18, Integer.valueOf(p_70269_1_));
  }
  



  public int getForwardDirection()
  {
    return dataWatcher.getWatchableObjectInt(18);
  }
  



  public void setIsBoatEmpty(boolean p_70270_1_)
  {
    isBoatEmpty = p_70270_1_;
  }
}
