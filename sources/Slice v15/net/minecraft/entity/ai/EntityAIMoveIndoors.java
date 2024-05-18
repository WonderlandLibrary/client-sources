package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.village.VillageDoorInfo;
import net.minecraft.world.World;

public class EntityAIMoveIndoors extends EntityAIBase
{
  private EntityCreature entityObj;
  private VillageDoorInfo doorInfo;
  private int insidePosX = -1;
  private int insidePosZ = -1;
  private static final String __OBFID = "CL_00001596";
  
  public EntityAIMoveIndoors(EntityCreature p_i1637_1_)
  {
    entityObj = p_i1637_1_;
    setMutexBits(1);
  }
  



  public boolean shouldExecute()
  {
    BlockPos var1 = new BlockPos(entityObj);
    
    if (((!entityObj.worldObj.isDaytime()) || ((entityObj.worldObj.isRaining()) && (!entityObj.worldObj.getBiomeGenForCoords(var1).canSpawnLightningBolt()))) && (!entityObj.worldObj.provider.getHasNoSky()))
    {
      if (entityObj.getRNG().nextInt(50) != 0)
      {
        return false;
      }
      if ((insidePosX != -1) && (entityObj.getDistanceSq(insidePosX, entityObj.posY, insidePosZ) < 4.0D))
      {
        return false;
      }
      

      net.minecraft.village.Village var2 = entityObj.worldObj.getVillageCollection().func_176056_a(var1, 14);
      
      if (var2 == null)
      {
        return false;
      }
      

      doorInfo = var2.func_179863_c(var1);
      return doorInfo != null;
    }
    



    return false;
  }
  




  public boolean continueExecuting()
  {
    return !entityObj.getNavigator().noPath();
  }
  



  public void startExecuting()
  {
    insidePosX = -1;
    BlockPos var1 = doorInfo.func_179856_e();
    int var2 = var1.getX();
    int var3 = var1.getY();
    int var4 = var1.getZ();
    
    if (entityObj.getDistanceSq(var1) > 256.0D)
    {
      Vec3 var5 = RandomPositionGenerator.findRandomTargetBlockTowards(entityObj, 14, 3, new Vec3(var2 + 0.5D, var3, var4 + 0.5D));
      
      if (var5 != null)
      {
        entityObj.getNavigator().tryMoveToXYZ(xCoord, yCoord, zCoord, 1.0D);
      }
    }
    else
    {
      entityObj.getNavigator().tryMoveToXYZ(var2 + 0.5D, var3, var4 + 0.5D, 1.0D);
    }
  }
  



  public void resetTask()
  {
    insidePosX = doorInfo.func_179856_e().getX();
    insidePosZ = doorInfo.func_179856_e().getZ();
    doorInfo = null;
  }
}
