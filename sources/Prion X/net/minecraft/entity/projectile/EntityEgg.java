package net.minecraft.entity.projectile;

import java.util.Random;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityEgg extends EntityThrowable
{
  private static final String __OBFID = "CL_00001724";
  
  public EntityEgg(World worldIn)
  {
    super(worldIn);
  }
  
  public EntityEgg(World worldIn, EntityLivingBase p_i1780_2_)
  {
    super(worldIn, p_i1780_2_);
  }
  
  public EntityEgg(World worldIn, double p_i1781_2_, double p_i1781_4_, double p_i1781_6_)
  {
    super(worldIn, p_i1781_2_, p_i1781_4_, p_i1781_6_);
  }
  



  protected void onImpact(MovingObjectPosition p_70184_1_)
  {
    if (entityHit != null)
    {
      entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 0.0F);
    }
    
    if ((!worldObj.isRemote) && (rand.nextInt(8) == 0))
    {
      byte var2 = 1;
      
      if (rand.nextInt(32) == 0)
      {
        var2 = 4;
      }
      
      for (int var3 = 0; var3 < var2; var3++)
      {
        EntityChicken var4 = new EntityChicken(worldObj);
        var4.setGrowingAge(41536);
        var4.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
        worldObj.spawnEntityInWorld(var4);
      }
    }
    
    double var5 = 0.08D;
    
    for (int var6 = 0; var6 < 8; var6++)
    {
      worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, posX, posY, posZ, (rand.nextFloat() - 0.5D) * 0.08D, (rand.nextFloat() - 0.5D) * 0.08D, (rand.nextFloat() - 0.5D) * 0.08D, new int[] { Item.getIdFromItem(net.minecraft.init.Items.egg) });
    }
    
    if (!worldObj.isRemote)
    {
      setDead();
    }
  }
}
