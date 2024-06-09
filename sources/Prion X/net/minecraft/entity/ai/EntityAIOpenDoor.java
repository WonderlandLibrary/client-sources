package net.minecraft.entity.ai;

import net.minecraft.block.BlockDoor;
import net.minecraft.entity.EntityLiving;




public class EntityAIOpenDoor
  extends EntityAIDoorInteract
{
  boolean closeDoor;
  int closeDoorTemporisation;
  private static final String __OBFID = "CL_00001603";
  
  public EntityAIOpenDoor(EntityLiving p_i1644_1_, boolean p_i1644_2_)
  {
    super(p_i1644_1_);
    theEntity = p_i1644_1_;
    closeDoor = p_i1644_2_;
  }
  



  public boolean continueExecuting()
  {
    return (closeDoor) && (closeDoorTemporisation > 0) && (super.continueExecuting());
  }
  



  public void startExecuting()
  {
    closeDoorTemporisation = 20;
    doorBlock.func_176512_a(theEntity.worldObj, field_179507_b, true);
  }
  



  public void resetTask()
  {
    if (closeDoor)
    {
      doorBlock.func_176512_a(theEntity.worldObj, field_179507_b, false);
    }
  }
  



  public void updateTask()
  {
    closeDoorTemporisation -= 1;
    super.updateTask();
  }
}
