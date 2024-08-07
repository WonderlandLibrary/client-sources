package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class EntityAIBreakDoor
  extends EntityAIDoorInteract
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
    if (!super.shouldExecute()) {
      return false;
    }
    if (!this.theEntity.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
      return false;
    }
    BlockDoor var10000 = this.doorBlock;
    return !BlockDoor.func_176514_f(this.theEntity.worldObj, this.field_179507_b);
  }
  
  public void startExecuting()
  {
    super.startExecuting();
    this.breakingTime = 0;
  }
  
  public boolean continueExecuting()
  {
    double var1 = this.theEntity.getDistanceSq(this.field_179507_b);
    if (this.breakingTime <= 240)
    {
      BlockDoor var10000 = this.doorBlock;
      if ((!BlockDoor.func_176514_f(this.theEntity.worldObj, this.field_179507_b)) && (var1 < 4.0D))
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
    this.theEntity.worldObj.sendBlockBreakProgress(this.theEntity.getEntityId(), this.field_179507_b, -1);
  }
  
  public void updateTask()
  {
    super.updateTask();
    if (this.theEntity.getRNG().nextInt(20) == 0) {
      this.theEntity.worldObj.playAuxSFX(1010, this.field_179507_b, 0);
    }
    this.breakingTime += 1;
    int var1 = (int)(this.breakingTime / 240.0F * 10.0F);
    if (var1 != this.field_75358_j)
    {
      this.theEntity.worldObj.sendBlockBreakProgress(this.theEntity.getEntityId(), this.field_179507_b, var1);
      this.field_75358_j = var1;
    }
    if ((this.breakingTime == 240) && (this.theEntity.worldObj.getDifficulty() == EnumDifficulty.HARD))
    {
      this.theEntity.worldObj.setBlockToAir(this.field_179507_b);
      this.theEntity.worldObj.playAuxSFX(1012, this.field_179507_b, 0);
      this.theEntity.worldObj.playAuxSFX(2001, this.field_179507_b, Block.getIdFromBlock(this.doorBlock));
    }
  }
}
