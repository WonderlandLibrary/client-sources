package net.minecraft.entity.item;

import net.minecraft.block.BlockFire;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityEnderCrystal extends Entity
{
  public int innerRotation;
  public int health;
  private static final String __OBFID = "CL_00001658";
  
  public EntityEnderCrystal(World worldIn)
  {
    super(worldIn);
    preventEntitySpawning = true;
    setSize(2.0F, 2.0F);
    health = 5;
    innerRotation = rand.nextInt(100000);
  }
  
  public EntityEnderCrystal(World worldIn, double p_i1699_2_, double p_i1699_4_, double p_i1699_6_)
  {
    this(worldIn);
    setPosition(p_i1699_2_, p_i1699_4_, p_i1699_6_);
  }
  




  protected boolean canTriggerWalking()
  {
    return false;
  }
  
  protected void entityInit()
  {
    dataWatcher.addObject(8, Integer.valueOf(health));
  }
  



  public void onUpdate()
  {
    prevPosX = posX;
    prevPosY = posY;
    prevPosZ = posZ;
    innerRotation += 1;
    dataWatcher.updateObject(8, Integer.valueOf(health));
    int var1 = MathHelper.floor_double(posX);
    int var2 = MathHelper.floor_double(posY);
    int var3 = MathHelper.floor_double(posZ);
    
    if (((worldObj.provider instanceof net.minecraft.world.WorldProviderEnd)) && (worldObj.getBlockState(new BlockPos(var1, var2, var3)).getBlock() != Blocks.fire))
    {
      worldObj.setBlockState(new BlockPos(var1, var2, var3), Blocks.fire.getDefaultState());
    }
  }
  



  protected void writeEntityToNBT(NBTTagCompound tagCompound) {}
  



  protected void readEntityFromNBT(NBTTagCompound tagCompund) {}
  



  public boolean canBeCollidedWith()
  {
    return true;
  }
  



  public boolean attackEntityFrom(DamageSource source, float amount)
  {
    if (func_180431_b(source))
    {
      return false;
    }
    

    if ((!isDead) && (!worldObj.isRemote))
    {
      health = 0;
      
      if (health <= 0)
      {
        setDead();
        
        if (!worldObj.isRemote)
        {
          worldObj.createExplosion(null, posX, posY, posZ, 6.0F, true);
        }
      }
    }
    
    return true;
  }
}
