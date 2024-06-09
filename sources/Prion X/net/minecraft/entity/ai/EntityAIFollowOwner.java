package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityAIFollowOwner extends EntityAIBase
{
  private EntityTameable thePet;
  private EntityLivingBase theOwner;
  World theWorld;
  private double field_75336_f;
  private PathNavigate petPathfinder;
  private int field_75343_h;
  float maxDist;
  float minDist;
  private boolean field_75344_i;
  private static final String __OBFID = "CL_00001585";
  
  public EntityAIFollowOwner(EntityTameable p_i1625_1_, double p_i1625_2_, float p_i1625_4_, float p_i1625_5_)
  {
    thePet = p_i1625_1_;
    theWorld = worldObj;
    field_75336_f = p_i1625_2_;
    petPathfinder = p_i1625_1_.getNavigator();
    minDist = p_i1625_4_;
    maxDist = p_i1625_5_;
    setMutexBits(3);
    
    if (!(p_i1625_1_.getNavigator() instanceof PathNavigateGround))
    {
      throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
    }
  }
  



  public boolean shouldExecute()
  {
    EntityLivingBase var1 = thePet.func_180492_cm();
    
    if (var1 == null)
    {
      return false;
    }
    if (thePet.isSitting())
    {
      return false;
    }
    if (thePet.getDistanceSqToEntity(var1) < minDist * minDist)
    {
      return false;
    }
    

    theOwner = var1;
    return true;
  }
  




  public boolean continueExecuting()
  {
    return (!petPathfinder.noPath()) && (thePet.getDistanceSqToEntity(theOwner) > maxDist * maxDist) && (!thePet.isSitting());
  }
  



  public void startExecuting()
  {
    field_75343_h = 0;
    field_75344_i = ((PathNavigateGround)thePet.getNavigator()).func_179689_e();
    ((PathNavigateGround)thePet.getNavigator()).func_179690_a(false);
  }
  



  public void resetTask()
  {
    theOwner = null;
    petPathfinder.clearPathEntity();
    ((PathNavigateGround)thePet.getNavigator()).func_179690_a(true);
  }
  



  public void updateTask()
  {
    thePet.getLookHelper().setLookPositionWithEntity(theOwner, 10.0F, thePet.getVerticalFaceSpeed());
    
    if (!thePet.isSitting())
    {
      if (--field_75343_h <= 0)
      {
        field_75343_h = 10;
        
        if (!petPathfinder.tryMoveToEntityLiving(theOwner, field_75336_f))
        {
          if (!thePet.getLeashed())
          {
            if (thePet.getDistanceSqToEntity(theOwner) >= 144.0D)
            {
              int var1 = MathHelper.floor_double(theOwner.posX) - 2;
              int var2 = MathHelper.floor_double(theOwner.posZ) - 2;
              int var3 = MathHelper.floor_double(theOwner.getEntityBoundingBox().minY);
              
              for (int var4 = 0; var4 <= 4; var4++)
              {
                for (int var5 = 0; var5 <= 4; var5++)
                {
                  if (((var4 < 1) || (var5 < 1) || (var4 > 3) || (var5 > 3)) && (World.doesBlockHaveSolidTopSurface(theWorld, new BlockPos(var1 + var4, var3 - 1, var2 + var5))) && (!theWorld.getBlockState(new BlockPos(var1 + var4, var3, var2 + var5)).getBlock().isFullCube()) && (!theWorld.getBlockState(new BlockPos(var1 + var4, var3 + 1, var2 + var5)).getBlock().isFullCube()))
                  {
                    thePet.setLocationAndAngles(var1 + var4 + 0.5F, var3, var2 + var5 + 0.5F, thePet.rotationYaw, thePet.rotationPitch);
                    petPathfinder.clearPathEntity();
                    return;
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}
