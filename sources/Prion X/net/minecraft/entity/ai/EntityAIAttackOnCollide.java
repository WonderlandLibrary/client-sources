package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;









public class EntityAIAttackOnCollide
  extends EntityAIBase
{
  World worldObj;
  protected EntityCreature attacker;
  int attackTick;
  double speedTowardsTarget;
  boolean longMemory;
  PathEntity entityPathEntity;
  Class classTarget;
  private int field_75445_i;
  private double field_151497_i;
  private double field_151495_j;
  private double field_151496_k;
  private static final String __OBFID = "CL_00001595";
  
  public EntityAIAttackOnCollide(EntityCreature p_i1635_1_, Class p_i1635_2_, double p_i1635_3_, boolean p_i1635_5_)
  {
    this(p_i1635_1_, p_i1635_3_, p_i1635_5_);
    classTarget = p_i1635_2_;
  }
  
  public EntityAIAttackOnCollide(EntityCreature p_i1636_1_, double p_i1636_2_, boolean p_i1636_4_)
  {
    attacker = p_i1636_1_;
    worldObj = worldObj;
    speedTowardsTarget = p_i1636_2_;
    longMemory = p_i1636_4_;
    setMutexBits(3);
  }
  



  public boolean shouldExecute()
  {
    EntityLivingBase var1 = attacker.getAttackTarget();
    
    if (var1 == null)
    {
      return false;
    }
    if (!var1.isEntityAlive())
    {
      return false;
    }
    if ((classTarget != null) && (!classTarget.isAssignableFrom(var1.getClass())))
    {
      return false;
    }
    

    entityPathEntity = attacker.getNavigator().getPathToEntityLiving(var1);
    return entityPathEntity != null;
  }
  




  public boolean continueExecuting()
  {
    EntityLivingBase var1 = attacker.getAttackTarget();
    return !longMemory ? true : attacker.getNavigator().noPath() ? false : !var1.isEntityAlive() ? false : var1 == null ? false : attacker.func_180485_d(new BlockPos(var1));
  }
  



  public void startExecuting()
  {
    attacker.getNavigator().setPath(entityPathEntity, speedTowardsTarget);
    field_75445_i = 0;
  }
  



  public void resetTask()
  {
    attacker.getNavigator().clearPathEntity();
  }
  



  public void updateTask()
  {
    EntityLivingBase var1 = attacker.getAttackTarget();
    attacker.getLookHelper().setLookPositionWithEntity(var1, 30.0F, 30.0F);
    double var2 = attacker.getDistanceSq(posX, getEntityBoundingBoxminY, posZ);
    double var4 = func_179512_a(var1);
    field_75445_i -= 1;
    
    if (((longMemory) || (attacker.getEntitySenses().canSee(var1))) && (field_75445_i <= 0) && (((field_151497_i == 0.0D) && (field_151495_j == 0.0D) && (field_151496_k == 0.0D)) || (var1.getDistanceSq(field_151497_i, field_151495_j, field_151496_k) >= 1.0D) || (attacker.getRNG().nextFloat() < 0.05F)))
    {
      field_151497_i = posX;
      field_151495_j = getEntityBoundingBoxminY;
      field_151496_k = posZ;
      field_75445_i = (4 + attacker.getRNG().nextInt(7));
      
      if (var2 > 1024.0D)
      {
        field_75445_i += 10;
      }
      else if (var2 > 256.0D)
      {
        field_75445_i += 5;
      }
      
      if (!attacker.getNavigator().tryMoveToEntityLiving(var1, speedTowardsTarget))
      {
        field_75445_i += 15;
      }
    }
    
    attackTick = Math.max(attackTick - 1, 0);
    
    if ((var2 <= var4) && (attackTick <= 0))
    {
      attackTick = 20;
      
      if (attacker.getHeldItem() != null)
      {
        attacker.swingItem();
      }
      
      attacker.attackEntityAsMob(var1);
    }
  }
  
  protected double func_179512_a(EntityLivingBase p_179512_1_)
  {
    return attacker.width * 2.0F * attacker.width * 2.0F + width;
  }
}
