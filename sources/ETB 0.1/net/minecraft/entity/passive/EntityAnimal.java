package net.minecraft.entity.passive;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class EntityAnimal extends EntityAgeable implements IAnimals
{
  protected net.minecraft.block.Block field_175506_bl;
  private int inLove;
  private EntityPlayer playerInLove;
  private static final String __OBFID = "CL_00001638";
  
  public EntityAnimal(World worldIn)
  {
    super(worldIn);
    field_175506_bl = net.minecraft.init.Blocks.grass;
  }
  
  protected void updateAITasks()
  {
    if (getGrowingAge() != 0)
    {
      inLove = 0;
    }
    
    super.updateAITasks();
  }
  




  public void onLivingUpdate()
  {
    super.onLivingUpdate();
    
    if (getGrowingAge() != 0)
    {
      inLove = 0;
    }
    
    if (inLove > 0)
    {
      inLove -= 1;
      
      if (inLove % 10 == 0)
      {
        double var1 = rand.nextGaussian() * 0.02D;
        double var3 = rand.nextGaussian() * 0.02D;
        double var5 = rand.nextGaussian() * 0.02D;
        worldObj.spawnParticle(EnumParticleTypes.HEART, posX + rand.nextFloat() * width * 2.0F - width, posY + 0.5D + rand.nextFloat() * height, posZ + rand.nextFloat() * width * 2.0F - width, var1, var3, var5, new int[0]);
      }
    }
  }
  



  public boolean attackEntityFrom(DamageSource source, float amount)
  {
    if (func_180431_b(source))
    {
      return false;
    }
    

    inLove = 0;
    return super.attackEntityFrom(source, amount);
  }
  

  public float func_180484_a(BlockPos p_180484_1_)
  {
    return worldObj.getBlockState(p_180484_1_.offsetDown()).getBlock() == net.minecraft.init.Blocks.grass ? 10.0F : worldObj.getLightBrightness(p_180484_1_) - 0.5F;
  }
  



  public void writeEntityToNBT(NBTTagCompound tagCompound)
  {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setInteger("InLove", inLove);
  }
  



  public void readEntityFromNBT(NBTTagCompound tagCompund)
  {
    super.readEntityFromNBT(tagCompund);
    inLove = tagCompund.getInteger("InLove");
  }
  



  public boolean getCanSpawnHere()
  {
    int var1 = MathHelper.floor_double(posX);
    int var2 = MathHelper.floor_double(getEntityBoundingBoxminY);
    int var3 = MathHelper.floor_double(posZ);
    BlockPos var4 = new BlockPos(var1, var2, var3);
    return (worldObj.getBlockState(var4.offsetDown()).getBlock() == field_175506_bl) && (worldObj.getLight(var4) > 8) && (super.getCanSpawnHere());
  }
  



  public int getTalkInterval()
  {
    return 120;
  }
  



  protected boolean canDespawn()
  {
    return false;
  }
  



  protected int getExperiencePoints(EntityPlayer p_70693_1_)
  {
    return 1 + worldObj.rand.nextInt(3);
  }
  




  public boolean isBreedingItem(ItemStack p_70877_1_)
  {
    return p_70877_1_ != null;
  }
  



  public boolean interact(EntityPlayer p_70085_1_)
  {
    ItemStack var2 = inventory.getCurrentItem();
    
    if (var2 != null)
    {
      if ((isBreedingItem(var2)) && (getGrowingAge() == 0) && (inLove <= 0))
      {
        func_175505_a(p_70085_1_, var2);
        setInLove(p_70085_1_);
        return true;
      }
      
      if ((isChild()) && (isBreedingItem(var2)))
      {
        func_175505_a(p_70085_1_, var2);
        func_175501_a((int)(-getGrowingAge() / 20 * 0.1F), true);
        return true;
      }
    }
    
    return super.interact(p_70085_1_);
  }
  
  protected void func_175505_a(EntityPlayer p_175505_1_, ItemStack p_175505_2_)
  {
    if (!capabilities.isCreativeMode)
    {
      stackSize -= 1;
      
      if (stackSize <= 0)
      {
        inventory.setInventorySlotContents(inventory.currentItem, null);
      }
    }
  }
  
  public void setInLove(EntityPlayer p_146082_1_)
  {
    inLove = 600;
    playerInLove = p_146082_1_;
    worldObj.setEntityState(this, (byte)18);
  }
  
  public EntityPlayer func_146083_cb()
  {
    return playerInLove;
  }
  



  public boolean isInLove()
  {
    return inLove > 0;
  }
  
  public void resetInLove()
  {
    inLove = 0;
  }
  



  public boolean canMateWith(EntityAnimal p_70878_1_)
  {
    return p_70878_1_ != this;
  }
  
  public void handleHealthUpdate(byte p_70103_1_)
  {
    if (p_70103_1_ == 18)
    {
      for (int var2 = 0; var2 < 7; var2++)
      {
        double var3 = rand.nextGaussian() * 0.02D;
        double var5 = rand.nextGaussian() * 0.02D;
        double var7 = rand.nextGaussian() * 0.02D;
        worldObj.spawnParticle(EnumParticleTypes.HEART, posX + rand.nextFloat() * width * 2.0F - width, posY + 0.5D + rand.nextFloat() * height, posZ + rand.nextFloat() * width * 2.0F - width, var3, var5, var7, new int[0]);
      }
      
    }
    else {
      super.handleHealthUpdate(p_70103_1_);
    }
  }
}
