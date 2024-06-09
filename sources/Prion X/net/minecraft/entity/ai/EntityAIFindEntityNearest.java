package net.minecraft.entity.ai;

import com.google.common.base.Predicate;
import java.util.List;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityAIFindEntityNearest extends EntityAIBase
{
  private static final Logger field_179444_a = ;
  private EntityLiving field_179442_b;
  private final Predicate field_179443_c;
  private final EntityAINearestAttackableTarget.Sorter field_179440_d;
  private EntityLivingBase field_179441_e;
  private Class field_179439_f;
  private static final String __OBFID = "CL_00002250";
  
  public EntityAIFindEntityNearest(EntityLiving p_i45884_1_, Class p_i45884_2_)
  {
    field_179442_b = p_i45884_1_;
    field_179439_f = p_i45884_2_;
    
    if ((p_i45884_1_ instanceof EntityCreature))
    {
      field_179444_a.warn("Use NearestAttackableTargetGoal.class for PathfinerMob mobs!");
    }
    
    field_179443_c = new Predicate()
    {
      private static final String __OBFID = "CL_00002249";
      
      public boolean func_179876_a(EntityLivingBase p_179876_1_) {
        double var2 = func_179438_f();
        
        if (p_179876_1_.isSneaking())
        {
          var2 *= 0.800000011920929D;
        }
        
        return p_179876_1_.getDistanceToEntity(field_179442_b) > var2 ? false : p_179876_1_.isInvisible() ? false : EntityAITarget.func_179445_a(field_179442_b, p_179876_1_, false, true);
      }
      
      public boolean apply(Object p_apply_1_) {
        return func_179876_a((EntityLivingBase)p_apply_1_);
      }
    };
    field_179440_d = new EntityAINearestAttackableTarget.Sorter(p_i45884_1_);
  }
  



  public boolean shouldExecute()
  {
    double var1 = func_179438_f();
    List var3 = field_179442_b.worldObj.func_175647_a(field_179439_f, field_179442_b.getEntityBoundingBox().expand(var1, 4.0D, var1), field_179443_c);
    java.util.Collections.sort(var3, field_179440_d);
    
    if (var3.isEmpty())
    {
      return false;
    }
    

    field_179441_e = ((EntityLivingBase)var3.get(0));
    return true;
  }
  




  public boolean continueExecuting()
  {
    EntityLivingBase var1 = field_179442_b.getAttackTarget();
    
    if (var1 == null)
    {
      return false;
    }
    if (!var1.isEntityAlive())
    {
      return false;
    }
    

    double var2 = func_179438_f();
    return field_179442_b.getDistanceSqToEntity(var1) <= var2 * var2;
  }
  




  public void startExecuting()
  {
    field_179442_b.setAttackTarget(field_179441_e);
    super.startExecuting();
  }
  



  public void resetTask()
  {
    field_179442_b.setAttackTarget(null);
    super.startExecuting();
  }
  
  protected double func_179438_f()
  {
    IAttributeInstance var1 = field_179442_b.getEntityAttribute(SharedMonsterAttributes.followRange);
    return var1 == null ? 16.0D : var1.getAttributeValue();
  }
}
