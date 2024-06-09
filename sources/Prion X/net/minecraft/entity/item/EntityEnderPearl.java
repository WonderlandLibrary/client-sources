package net.minecraft.entity.item;

import java.util.Random;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityEnderPearl extends EntityThrowable
{
  private static final String __OBFID = "CL_00001725";
  
  public EntityEnderPearl(World worldIn, EntityLivingBase p_i1783_2_)
  {
    super(worldIn, p_i1783_2_);
  }
  
  public EntityEnderPearl(World worldIn, double p_i1784_2_, double p_i1784_4_, double p_i1784_6_)
  {
    super(worldIn, p_i1784_2_, p_i1784_4_, p_i1784_6_);
  }
  



  protected void onImpact(MovingObjectPosition p_70184_1_)
  {
    EntityLivingBase var2 = getThrower();
    
    if (entityHit != null)
    {
      entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, var2), 0.0F);
    }
    
    for (int var3 = 0; var3 < 32; var3++)
    {
      worldObj.spawnParticle(EnumParticleTypes.PORTAL, posX, posY + rand.nextDouble() * 2.0D, posZ, rand.nextGaussian(), 0.0D, rand.nextGaussian(), new int[0]);
    }
    
    if (!worldObj.isRemote)
    {
      if ((var2 instanceof EntityPlayerMP))
      {
        EntityPlayerMP var5 = (EntityPlayerMP)var2;
        
        if ((playerNetServerHandler.getNetworkManager().isChannelOpen()) && (worldObj == worldObj) && (!var5.isPlayerSleeping()))
        {
          if ((rand.nextFloat() < 0.05F) && (worldObj.getGameRules().getGameRuleBooleanValue("doMobSpawning")))
          {
            EntityEndermite var4 = new EntityEndermite(worldObj);
            var4.setSpawnedByPlayer(true);
            var4.setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
            worldObj.spawnEntityInWorld(var4);
          }
          
          if (var2.isRiding())
          {
            var2.mountEntity(null);
          }
          
          var2.setPositionAndUpdate(posX, posY, posZ);
          fallDistance = 0.0F;
          var2.attackEntityFrom(DamageSource.fall, 5.0F);
        }
      }
      
      setDead();
    }
  }
  



  public void onUpdate()
  {
    EntityLivingBase var1 = getThrower();
    
    if ((var1 != null) && ((var1 instanceof net.minecraft.entity.player.EntityPlayer)) && (!var1.isEntityAlive()))
    {
      setDead();
    }
    else
    {
      super.onUpdate();
    }
  }
}
