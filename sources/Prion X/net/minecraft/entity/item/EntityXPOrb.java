package net.minecraft.entity.item;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;



public class EntityXPOrb
  extends Entity
{
  public int xpColor;
  public int xpOrbAge;
  public int field_70532_c;
  private int xpOrbHealth = 5;
  

  private int xpValue;
  
  private EntityPlayer closestPlayer;
  
  private int xpTargetColor;
  
  private static final String __OBFID = "CL_00001544";
  

  public EntityXPOrb(World worldIn, double p_i1585_2_, double p_i1585_4_, double p_i1585_6_, int p_i1585_8_)
  {
    super(worldIn);
    setSize(0.5F, 0.5F);
    setPosition(p_i1585_2_, p_i1585_4_, p_i1585_6_);
    rotationYaw = ((float)(Math.random() * 360.0D));
    motionX = ((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F);
    motionY = ((float)(Math.random() * 0.2D) * 2.0F);
    motionZ = ((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F);
    xpValue = p_i1585_8_;
  }
  




  protected boolean canTriggerWalking()
  {
    return false;
  }
  
  public EntityXPOrb(World worldIn)
  {
    super(worldIn);
    setSize(0.25F, 0.25F);
  }
  
  protected void entityInit() {}
  
  public int getBrightnessForRender(float p_70070_1_)
  {
    float var2 = 0.5F;
    var2 = MathHelper.clamp_float(var2, 0.0F, 1.0F);
    int var3 = super.getBrightnessForRender(p_70070_1_);
    int var4 = var3 & 0xFF;
    int var5 = var3 >> 16 & 0xFF;
    var4 += (int)(var2 * 15.0F * 16.0F);
    
    if (var4 > 240)
    {
      var4 = 240;
    }
    
    return var4 | var5 << 16;
  }
  



  public void onUpdate()
  {
    super.onUpdate();
    
    if (field_70532_c > 0)
    {
      field_70532_c -= 1;
    }
    
    prevPosX = posX;
    prevPosY = posY;
    prevPosZ = posZ;
    motionY -= 0.029999999329447746D;
    
    if (worldObj.getBlockState(new BlockPos(this)).getBlock().getMaterial() == Material.lava)
    {
      motionY = 0.20000000298023224D;
      motionX = ((rand.nextFloat() - rand.nextFloat()) * 0.2F);
      motionZ = ((rand.nextFloat() - rand.nextFloat()) * 0.2F);
      playSound("random.fizz", 0.4F, 2.0F + rand.nextFloat() * 0.4F);
    }
    
    pushOutOfBlocks(posX, (getEntityBoundingBoxminY + getEntityBoundingBoxmaxY) / 2.0D, posZ);
    double var1 = 8.0D;
    
    if (xpTargetColor < xpColor - 20 + getEntityId() % 100)
    {
      if ((closestPlayer == null) || (closestPlayer.getDistanceSqToEntity(this) > var1 * var1))
      {
        closestPlayer = worldObj.getClosestPlayerToEntity(this, var1);
      }
      
      xpTargetColor = xpColor;
    }
    
    if ((closestPlayer != null) && (closestPlayer.func_175149_v()))
    {
      closestPlayer = null;
    }
    
    if (closestPlayer != null)
    {
      double var3 = (closestPlayer.posX - posX) / var1;
      double var5 = (closestPlayer.posY + closestPlayer.getEyeHeight() - posY) / var1;
      double var7 = (closestPlayer.posZ - posZ) / var1;
      double var9 = Math.sqrt(var3 * var3 + var5 * var5 + var7 * var7);
      double var11 = 1.0D - var9;
      
      if (var11 > 0.0D)
      {
        var11 *= var11;
        motionX += var3 / var9 * var11 * 0.1D;
        motionY += var5 / var9 * var11 * 0.1D;
        motionZ += var7 / var9 * var11 * 0.1D;
      }
    }
    
    moveEntity(motionX, motionY, motionZ);
    float var13 = 0.98F;
    
    if (onGround)
    {
      var13 = worldObj.getBlockState(new BlockPos(MathHelper.floor_double(posX), MathHelper.floor_double(getEntityBoundingBoxminY) - 1, MathHelper.floor_double(posZ))).getBlock().slipperiness * 0.98F;
    }
    
    motionX *= var13;
    motionY *= 0.9800000190734863D;
    motionZ *= var13;
    
    if (onGround)
    {
      motionY *= -0.8999999761581421D;
    }
    
    xpColor += 1;
    xpOrbAge += 1;
    
    if (xpOrbAge >= 6000)
    {
      setDead();
    }
  }
  



  public boolean handleWaterMovement()
  {
    return worldObj.handleMaterialAcceleration(getEntityBoundingBox(), Material.water, this);
  }
  




  protected void dealFireDamage(int amount)
  {
    attackEntityFrom(DamageSource.inFire, amount);
  }
  



  public boolean attackEntityFrom(DamageSource source, float amount)
  {
    if (func_180431_b(source))
    {
      return false;
    }
    

    setBeenAttacked();
    xpOrbHealth = ((int)(xpOrbHealth - amount));
    
    if (xpOrbHealth <= 0)
    {
      setDead();
    }
    
    return false;
  }
  




  public void writeEntityToNBT(NBTTagCompound tagCompound)
  {
    tagCompound.setShort("Health", (short)(byte)xpOrbHealth);
    tagCompound.setShort("Age", (short)xpOrbAge);
    tagCompound.setShort("Value", (short)xpValue);
  }
  



  public void readEntityFromNBT(NBTTagCompound tagCompund)
  {
    xpOrbHealth = (tagCompund.getShort("Health") & 0xFF);
    xpOrbAge = tagCompund.getShort("Age");
    xpValue = tagCompund.getShort("Value");
  }
  



  public void onCollideWithPlayer(EntityPlayer entityIn)
  {
    if (!worldObj.isRemote)
    {
      if ((field_70532_c == 0) && (xpCooldown == 0))
      {
        xpCooldown = 2;
        worldObj.playSoundAtEntity(entityIn, "random.orb", 0.1F, 0.5F * ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.8F));
        entityIn.onItemPickup(this, 1);
        entityIn.addExperience(xpValue);
        setDead();
      }
    }
  }
  



  public int getXpValue()
  {
    return xpValue;
  }
  




  public int getTextureByXP()
  {
    return xpValue >= 3 ? 1 : xpValue >= 7 ? 2 : xpValue >= 17 ? 3 : xpValue >= 37 ? 4 : xpValue >= 73 ? 5 : xpValue >= 149 ? 6 : xpValue >= 307 ? 7 : xpValue >= 617 ? 8 : xpValue >= 1237 ? 9 : xpValue >= 2477 ? 10 : 0;
  }
  



  public static int getXPSplit(int p_70527_0_)
  {
    return p_70527_0_ >= 3 ? 3 : p_70527_0_ >= 7 ? 7 : p_70527_0_ >= 17 ? 17 : p_70527_0_ >= 37 ? 37 : p_70527_0_ >= 73 ? 73 : p_70527_0_ >= 149 ? 149 : p_70527_0_ >= 307 ? 307 : p_70527_0_ >= 617 ? 617 : p_70527_0_ >= 1237 ? 1237 : p_70527_0_ >= 2477 ? 2477 : 1;
  }
  



  public boolean canAttackWithItem()
  {
    return false;
  }
}
