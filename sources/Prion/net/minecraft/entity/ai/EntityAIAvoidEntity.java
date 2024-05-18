package net.minecraft.entity.ai;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.Vec3;

public class EntityAIAvoidEntity extends EntityAIBase
{
  public final Predicate field_179509_a = new Predicate()
  {
    private static final String __OBFID = "CL_00001575";
    
    public boolean func_180419_a(Entity p_180419_1_) {
      return (p_180419_1_.isEntityAlive()) && (theEntity.getEntitySenses().canSee(p_180419_1_));
    }
    
    public boolean apply(Object p_apply_1_) {
      return func_180419_a((Entity)p_apply_1_);
    }
  };
  
  protected EntityCreature theEntity;
  
  private double farSpeed;
  
  private double nearSpeed;
  
  protected Entity closestLivingEntity;
  
  private float field_179508_f;
  
  private PathEntity entityPathEntity;
  private PathNavigate entityPathNavigate;
  private Predicate field_179510_i;
  private static final String __OBFID = "CL_00001574";
  
  public EntityAIAvoidEntity(EntityCreature p_i45890_1_, Predicate p_i45890_2_, float p_i45890_3_, double p_i45890_4_, double p_i45890_6_)
  {
    theEntity = p_i45890_1_;
    field_179510_i = p_i45890_2_;
    field_179508_f = p_i45890_3_;
    farSpeed = p_i45890_4_;
    nearSpeed = p_i45890_6_;
    entityPathNavigate = p_i45890_1_.getNavigator();
    setMutexBits(1);
  }
  



  public boolean shouldExecute()
  {
    List var1 = theEntity.worldObj.func_175674_a(theEntity, theEntity.getEntityBoundingBox().expand(field_179508_f, 3.0D, field_179508_f), Predicates.and(new Predicate[] { IEntitySelector.field_180132_d, field_179509_a, field_179510_i }));
    
    if (var1.isEmpty())
    {
      return false;
    }
    

    closestLivingEntity = ((Entity)var1.get(0));
    Vec3 var2 = RandomPositionGenerator.findRandomTargetBlockAwayFrom(theEntity, 16, 7, new Vec3(closestLivingEntity.posX, closestLivingEntity.posY, closestLivingEntity.posZ));
    
    if (var2 == null)
    {
      return false;
    }
    if (closestLivingEntity.getDistanceSq(xCoord, yCoord, zCoord) < closestLivingEntity.getDistanceSqToEntity(theEntity))
    {
      return false;
    }
    

    entityPathEntity = entityPathNavigate.getPathToXYZ(xCoord, yCoord, zCoord);
    return entityPathEntity == null ? false : entityPathEntity.isDestinationSame(var2);
  }
  





  public boolean continueExecuting()
  {
    return !entityPathNavigate.noPath();
  }
  



  public void startExecuting()
  {
    entityPathNavigate.setPath(entityPathEntity, farSpeed);
  }
  



  public void resetTask()
  {
    closestLivingEntity = null;
  }
  



  public void updateTask()
  {
    if (theEntity.getDistanceSqToEntity(closestLivingEntity) < 49.0D)
    {
      theEntity.getNavigator().setSpeed(nearSpeed);
    }
    else
    {
      theEntity.getNavigator().setSpeed(farSpeed);
    }
  }
}
