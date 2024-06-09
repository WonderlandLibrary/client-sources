package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityMinecartEmpty extends EntityMinecart
{
  private static final String __OBFID = "CL_00001677";
  
  public EntityMinecartEmpty(World worldIn)
  {
    super(worldIn);
  }
  
  public EntityMinecartEmpty(World worldIn, double p_i1723_2_, double p_i1723_4_, double p_i1723_6_)
  {
    super(worldIn, p_i1723_2_, p_i1723_4_, p_i1723_6_);
  }
  



  public boolean interactFirst(EntityPlayer playerIn)
  {
    if ((riddenByEntity != null) && ((riddenByEntity instanceof EntityPlayer)) && (riddenByEntity != playerIn))
    {
      return true;
    }
    if ((riddenByEntity != null) && (riddenByEntity != playerIn))
    {
      return false;
    }
    

    if (!worldObj.isRemote)
    {
      playerIn.mountEntity(this);
    }
    
    return true;
  }
  




  public void onActivatorRailPass(int p_96095_1_, int p_96095_2_, int p_96095_3_, boolean p_96095_4_)
  {
    if (p_96095_4_)
    {
      if (riddenByEntity != null)
      {
        riddenByEntity.mountEntity(null);
      }
      
      if (getRollingAmplitude() == 0)
      {
        setRollingDirection(-getRollingDirection());
        setRollingAmplitude(10);
        setDamage(50.0F);
        setBeenAttacked();
      }
    }
  }
  
  public EntityMinecart.EnumMinecartType func_180456_s()
  {
    return EntityMinecart.EnumMinecartType.RIDEABLE;
  }
}
