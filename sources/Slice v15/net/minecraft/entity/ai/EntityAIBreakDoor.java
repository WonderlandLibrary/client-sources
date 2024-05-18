package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.block.BlockDoor;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

public class EntityAIBreakDoor extends EntityAIDoorInteract
{
  private int breakingTime;
  private int field_75358_j = -1;
  private static final String __OBFID = "CL_00001577";
  
  public EntityAIBreakDoor(EntityLiving p_i1618_1_)
  {
    super(p_i1618_1_);
  }
  



  public boolean shouldExecute()
  {
    if (!super.shouldExecute())
    {
      return false;
    }
    if (!theEntity.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"))
    {
      return false;
    }
    

    BlockDoor var10000 = doorBlock;
    return !BlockDoor.func_176514_f(theEntity.worldObj, field_179507_b);
  }
  




  public void startExecuting()
  {
    super.startExecuting();
    breakingTime = 0;
  }
  



  public boolean continueExecuting()
  {
    double var1 = theEntity.getDistanceSq(field_179507_b);
    

    if (breakingTime <= 240)
    {
      BlockDoor var10000 = doorBlock;
      
      if ((!BlockDoor.func_176514_f(theEntity.worldObj, field_179507_b)) && (var1 < 4.0D))
      {
        boolean var3 = true;
        return var3;
      }
    }
    
    boolean var3 = false;
    return var3;
  }
  



  public void resetTask()
  {
    super.resetTask();
    theEntity.worldObj.sendBlockBreakProgress(theEntity.getEntityId(), field_179507_b, -1);
  }
  



  public void updateTask()
  {
    super.updateTask();
    
    if (theEntity.getRNG().nextInt(20) == 0)
    {
      theEntity.worldObj.playAuxSFX(1010, field_179507_b, 0);
    }
    
    breakingTime += 1;
    int var1 = (int)(breakingTime / 240.0F * 10.0F);
    
    if (var1 != field_75358_j)
    {
      theEntity.worldObj.sendBlockBreakProgress(theEntity.getEntityId(), field_179507_b, var1);
      field_75358_j = var1;
    }
    
    if ((breakingTime == 240) && (theEntity.worldObj.getDifficulty() == net.minecraft.world.EnumDifficulty.HARD))
    {
      theEntity.worldObj.setBlockToAir(field_179507_b);
      theEntity.worldObj.playAuxSFX(1012, field_179507_b, 0);
      theEntity.worldObj.playAuxSFX(2001, field_179507_b, net.minecraft.block.Block.getIdFromBlock(doorBlock));
    }
  }
}
