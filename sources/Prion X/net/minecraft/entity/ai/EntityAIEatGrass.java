package net.minecraft.entity.ai;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.minecraft.block.Block;
import net.minecraft.block.state.pattern.BlockStateHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class EntityAIEatGrass extends EntityAIBase
{
  private static final Predicate field_179505_b = BlockStateHelper.forBlock(Blocks.tallgrass).func_177637_a(net.minecraft.block.BlockTallGrass.field_176497_a, Predicates.equalTo(net.minecraft.block.BlockTallGrass.EnumType.GRASS));
  

  private EntityLiving grassEaterEntity;
  
  private World entityWorld;
  
  int eatingGrassTimer;
  
  private static final String __OBFID = "CL_00001582";
  

  public EntityAIEatGrass(EntityLiving p_i45314_1_)
  {
    grassEaterEntity = p_i45314_1_;
    entityWorld = worldObj;
    setMutexBits(7);
  }
  



  public boolean shouldExecute()
  {
    if (grassEaterEntity.getRNG().nextInt(grassEaterEntity.isChild() ? 50 : 1000) != 0)
    {
      return false;
    }
    

    BlockPos var1 = new BlockPos(grassEaterEntity.posX, grassEaterEntity.posY, grassEaterEntity.posZ);
    return field_179505_b.apply(entityWorld.getBlockState(var1));
  }
  




  public void startExecuting()
  {
    eatingGrassTimer = 40;
    entityWorld.setEntityState(grassEaterEntity, (byte)10);
    grassEaterEntity.getNavigator().clearPathEntity();
  }
  



  public void resetTask()
  {
    eatingGrassTimer = 0;
  }
  



  public boolean continueExecuting()
  {
    return eatingGrassTimer > 0;
  }
  



  public int getEatingGrassTimer()
  {
    return eatingGrassTimer;
  }
  



  public void updateTask()
  {
    eatingGrassTimer = Math.max(0, eatingGrassTimer - 1);
    
    if (eatingGrassTimer == 4)
    {
      BlockPos var1 = new BlockPos(grassEaterEntity.posX, grassEaterEntity.posY, grassEaterEntity.posZ);
      
      if (field_179505_b.apply(entityWorld.getBlockState(var1)))
      {
        if (entityWorld.getGameRules().getGameRuleBooleanValue("mobGriefing"))
        {
          entityWorld.destroyBlock(var1, false);
        }
        
        grassEaterEntity.eatGrassBonus();
      }
      else
      {
        BlockPos var2 = var1.offsetDown();
        
        if (entityWorld.getBlockState(var2).getBlock() == Blocks.grass)
        {
          if (entityWorld.getGameRules().getGameRuleBooleanValue("mobGriefing"))
          {
            entityWorld.playAuxSFX(2001, var2, Block.getIdFromBlock(Blocks.grass));
            entityWorld.setBlockState(var2, Blocks.dirt.getDefaultState(), 2);
          }
          
          grassEaterEntity.eatGrassBonus();
        }
      }
    }
  }
}
