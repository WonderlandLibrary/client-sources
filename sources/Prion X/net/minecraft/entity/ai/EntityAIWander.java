package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.util.Vec3;

public class EntityAIWander extends EntityAIBase
{
  private EntityCreature entity;
  private double xPosition;
  private double yPosition;
  private double zPosition;
  private double speed;
  private int field_179481_f;
  private boolean field_179482_g;
  private static final String __OBFID = "CL_00001608";
  
  public EntityAIWander(EntityCreature p_i1648_1_, double p_i1648_2_)
  {
    this(p_i1648_1_, p_i1648_2_, 120);
  }
  
  public EntityAIWander(EntityCreature p_i45887_1_, double p_i45887_2_, int p_i45887_4_)
  {
    entity = p_i45887_1_;
    speed = p_i45887_2_;
    field_179481_f = p_i45887_4_;
    setMutexBits(1);
  }
  



  public boolean shouldExecute()
  {
    if (!field_179482_g)
    {
      if (entity.getAge() >= 100)
      {
        return false;
      }
      
      if (entity.getRNG().nextInt(field_179481_f) != 0)
      {
        return false;
      }
    }
    
    Vec3 var1 = RandomPositionGenerator.findRandomTarget(entity, 10, 7);
    
    if (var1 == null)
    {
      return false;
    }
    

    xPosition = xCoord;
    yPosition = yCoord;
    zPosition = zCoord;
    field_179482_g = false;
    return true;
  }
  




  public boolean continueExecuting()
  {
    return !entity.getNavigator().noPath();
  }
  



  public void startExecuting()
  {
    entity.getNavigator().tryMoveToXYZ(xPosition, yPosition, zPosition, speed);
  }
  
  public void func_179480_f()
  {
    field_179482_g = true;
  }
  
  public void func_179479_b(int p_179479_1_)
  {
    field_179481_f = p_179479_1_;
  }
}
