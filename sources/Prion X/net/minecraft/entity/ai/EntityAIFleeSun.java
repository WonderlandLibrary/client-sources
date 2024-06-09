package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.entity.EntityCreature;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityAIFleeSun extends EntityAIBase
{
  private EntityCreature theCreature;
  private double shelterX;
  private double shelterY;
  private double shelterZ;
  private double movementSpeed;
  private World theWorld;
  private static final String __OBFID = "CL_00001583";
  
  public EntityAIFleeSun(EntityCreature p_i1623_1_, double p_i1623_2_)
  {
    theCreature = p_i1623_1_;
    movementSpeed = p_i1623_2_;
    theWorld = worldObj;
    setMutexBits(1);
  }
  



  public boolean shouldExecute()
  {
    if (!theWorld.isDaytime())
    {
      return false;
    }
    if (!theCreature.isBurning())
    {
      return false;
    }
    if (!theWorld.isAgainstSky(new BlockPos(theCreature.posX, theCreature.getEntityBoundingBox().minY, theCreature.posZ)))
    {
      return false;
    }
    

    Vec3 var1 = findPossibleShelter();
    
    if (var1 == null)
    {
      return false;
    }
    

    shelterX = xCoord;
    shelterY = yCoord;
    shelterZ = zCoord;
    return true;
  }
  





  public boolean continueExecuting()
  {
    return !theCreature.getNavigator().noPath();
  }
  



  public void startExecuting()
  {
    theCreature.getNavigator().tryMoveToXYZ(shelterX, shelterY, shelterZ, movementSpeed);
  }
  
  private Vec3 findPossibleShelter()
  {
    Random var1 = theCreature.getRNG();
    BlockPos var2 = new BlockPos(theCreature.posX, theCreature.getEntityBoundingBox().minY, theCreature.posZ);
    
    for (int var3 = 0; var3 < 10; var3++)
    {
      BlockPos var4 = var2.add(var1.nextInt(20) - 10, var1.nextInt(6) - 3, var1.nextInt(20) - 10);
      
      if ((!theWorld.isAgainstSky(var4)) && (theCreature.func_180484_a(var4) < 0.0F))
      {
        return new Vec3(var4.getX(), var4.getY(), var4.getZ());
      }
    }
    
    return null;
  }
}
