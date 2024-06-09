package net.minecraft.entity.projectile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityWitherSkull extends EntityFireball
{
  private static final String __OBFID = "CL_00001728";
  
  public EntityWitherSkull(World worldIn)
  {
    super(worldIn);
    setSize(0.3125F, 0.3125F);
  }
  
  public EntityWitherSkull(World worldIn, EntityLivingBase p_i1794_2_, double p_i1794_3_, double p_i1794_5_, double p_i1794_7_)
  {
    super(worldIn, p_i1794_2_, p_i1794_3_, p_i1794_5_, p_i1794_7_);
    setSize(0.3125F, 0.3125F);
  }
  



  protected float getMotionFactor()
  {
    return isInvulnerable() ? 0.73F : super.getMotionFactor();
  }
  
  public EntityWitherSkull(World worldIn, double p_i1795_2_, double p_i1795_4_, double p_i1795_6_, double p_i1795_8_, double p_i1795_10_, double p_i1795_12_)
  {
    super(worldIn, p_i1795_2_, p_i1795_4_, p_i1795_6_, p_i1795_8_, p_i1795_10_, p_i1795_12_);
    setSize(0.3125F, 0.3125F);
  }
  



  public boolean isBurning()
  {
    return false;
  }
  



  public float getExplosionResistance(Explosion p_180428_1_, World worldIn, BlockPos p_180428_3_, IBlockState p_180428_4_)
  {
    float var5 = super.getExplosionResistance(p_180428_1_, worldIn, p_180428_3_, p_180428_4_);
    
    if ((isInvulnerable()) && (p_180428_4_.getBlock() != Blocks.bedrock) && (p_180428_4_.getBlock() != Blocks.end_portal) && (p_180428_4_.getBlock() != Blocks.end_portal_frame) && (p_180428_4_.getBlock() != Blocks.command_block))
    {
      var5 = Math.min(0.8F, var5);
    }
    
    return var5;
  }
  



  protected void onImpact(MovingObjectPosition p_70227_1_)
  {
    if (!worldObj.isRemote)
    {
      if (entityHit != null)
      {
        if (shootingEntity != null)
        {
          if (entityHit.attackEntityFrom(DamageSource.causeMobDamage(shootingEntity), 8.0F))
          {
            if (!entityHit.isEntityAlive())
            {
              shootingEntity.heal(5.0F);
            }
            else
            {
              func_174815_a(shootingEntity, entityHit);
            }
            
          }
        }
        else {
          entityHit.attackEntityFrom(DamageSource.magic, 5.0F);
        }
        
        if ((entityHit instanceof EntityLivingBase))
        {
          byte var2 = 0;
          
          if (worldObj.getDifficulty() == EnumDifficulty.NORMAL)
          {
            var2 = 10;
          }
          else if (worldObj.getDifficulty() == EnumDifficulty.HARD)
          {
            var2 = 40;
          }
          
          if (var2 > 0)
          {
            ((EntityLivingBase)entityHit).addPotionEffect(new net.minecraft.potion.PotionEffect(witherid, 20 * var2, 1));
          }
        }
      }
      
      worldObj.newExplosion(this, posX, posY, posZ, 1.0F, false, worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
      setDead();
    }
  }
  



  public boolean canBeCollidedWith()
  {
    return false;
  }
  



  public boolean attackEntityFrom(DamageSource source, float amount)
  {
    return false;
  }
  
  protected void entityInit()
  {
    dataWatcher.addObject(10, Byte.valueOf((byte)0));
  }
  



  public boolean isInvulnerable()
  {
    return dataWatcher.getWatchableObjectByte(10) == 1;
  }
  



  public void setInvulnerable(boolean p_82343_1_)
  {
    dataWatcher.updateObject(10, Byte.valueOf((byte)(p_82343_1_ ? 1 : 0)));
  }
}
