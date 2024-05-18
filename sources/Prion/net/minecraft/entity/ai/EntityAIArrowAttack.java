package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;












public class EntityAIArrowAttack
  extends EntityAIBase
{
  private final EntityLiving entityHost;
  private final IRangedAttackMob rangedAttackEntityHost;
  private EntityLivingBase attackTarget;
  private int rangedAttackTime;
  private double entityMoveSpeed;
  private int field_75318_f;
  private int field_96561_g;
  private int maxRangedAttackTime;
  private float field_96562_i;
  private float maxAttackDistance;
  private static final String __OBFID = "CL_00001609";
  
  public EntityAIArrowAttack(IRangedAttackMob p_i1649_1_, double p_i1649_2_, int p_i1649_4_, float p_i1649_5_)
  {
    this(p_i1649_1_, p_i1649_2_, p_i1649_4_, p_i1649_4_, p_i1649_5_);
  }
  
  public EntityAIArrowAttack(IRangedAttackMob p_i1650_1_, double p_i1650_2_, int p_i1650_4_, int p_i1650_5_, float p_i1650_6_)
  {
    rangedAttackTime = -1;
    
    if (!(p_i1650_1_ instanceof EntityLivingBase))
    {
      throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
    }
    

    rangedAttackEntityHost = p_i1650_1_;
    entityHost = ((EntityLiving)p_i1650_1_);
    entityMoveSpeed = p_i1650_2_;
    field_96561_g = p_i1650_4_;
    maxRangedAttackTime = p_i1650_5_;
    field_96562_i = p_i1650_6_;
    maxAttackDistance = (p_i1650_6_ * p_i1650_6_);
    setMutexBits(3);
  }
  




  public boolean shouldExecute()
  {
    EntityLivingBase var1 = entityHost.getAttackTarget();
    
    if (var1 == null)
    {
      return false;
    }
    

    attackTarget = var1;
    return true;
  }
  




  public boolean continueExecuting()
  {
    return (shouldExecute()) || (!entityHost.getNavigator().noPath());
  }
  



  public void resetTask()
  {
    attackTarget = null;
    field_75318_f = 0;
    rangedAttackTime = -1;
  }
  



  public void updateTask()
  {
    double var1 = entityHost.getDistanceSq(attackTarget.posX, attackTarget.getEntityBoundingBox().minY, attackTarget.posZ);
    boolean var3 = entityHost.getEntitySenses().canSee(attackTarget);
    
    if (var3)
    {
      field_75318_f += 1;
    }
    else
    {
      field_75318_f = 0;
    }
    
    if ((var1 <= maxAttackDistance) && (field_75318_f >= 20))
    {
      entityHost.getNavigator().clearPathEntity();
    }
    else
    {
      entityHost.getNavigator().tryMoveToEntityLiving(attackTarget, entityMoveSpeed);
    }
    
    entityHost.getLookHelper().setLookPositionWithEntity(attackTarget, 30.0F, 30.0F);
    

    if (--rangedAttackTime == 0)
    {
      if ((var1 > maxAttackDistance) || (!var3))
      {
        return;
      }
      
      float var4 = MathHelper.sqrt_double(var1) / field_96562_i;
      float var5 = MathHelper.clamp_float(var4, 0.1F, 1.0F);
      rangedAttackEntityHost.attackEntityWithRangedAttack(attackTarget, var5);
      rangedAttackTime = MathHelper.floor_float(var4 * (maxRangedAttackTime - field_96561_g) + field_96561_g);
    }
    else if (rangedAttackTime < 0)
    {
      float var4 = MathHelper.sqrt_double(var1) / field_96562_i;
      rangedAttackTime = MathHelper.floor_float(var4 * (maxRangedAttackTime - field_96561_g) + field_96561_g);
    }
  }
}
