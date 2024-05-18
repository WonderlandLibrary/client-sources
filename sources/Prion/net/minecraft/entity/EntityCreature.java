package net.minecraft.entity;

import java.util.UUID;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public abstract class EntityCreature extends EntityLiving
{
  public static final UUID field_110179_h = UUID.fromString("E199AD21-BA8A-4C53-8D13-6182D5C69D3A");
  public static final AttributeModifier field_110181_i = new AttributeModifier(field_110179_h, "Fleeing speed bonus", 2.0D, 2).setSaved(false);
  
  private BlockPos homePosition;
  
  private float maximumHomeDistance;
  private net.minecraft.entity.ai.EntityAIBase aiBase;
  private boolean field_110180_bt;
  private static final String __OBFID = "CL_00001558";
  
  public EntityCreature(World worldIn)
  {
    super(worldIn);
    homePosition = BlockPos.ORIGIN;
    maximumHomeDistance = -1.0F;
    aiBase = new net.minecraft.entity.ai.EntityAIMoveTowardsRestriction(this, 1.0D);
  }
  
  public float func_180484_a(BlockPos p_180484_1_)
  {
    return 0.0F;
  }
  



  public boolean getCanSpawnHere()
  {
    return (super.getCanSpawnHere()) && (func_180484_a(new BlockPos(posX, getEntityBoundingBoxminY, posZ)) >= 0.0F);
  }
  



  public boolean hasPath()
  {
    return !navigator.noPath();
  }
  
  public boolean isWithinHomeDistanceCurrentPosition()
  {
    return func_180485_d(new BlockPos(this));
  }
  
  public boolean func_180485_d(BlockPos p_180485_1_)
  {
    return maximumHomeDistance == -1.0F;
  }
  
  public void func_175449_a(BlockPos p_175449_1_, int p_175449_2_)
  {
    homePosition = p_175449_1_;
    maximumHomeDistance = p_175449_2_;
  }
  
  public BlockPos func_180486_cf()
  {
    return homePosition;
  }
  
  public float getMaximumHomeDistance()
  {
    return maximumHomeDistance;
  }
  
  public void detachHome()
  {
    maximumHomeDistance = -1.0F;
  }
  



  public boolean hasHome()
  {
    return maximumHomeDistance != -1.0F;
  }
  



  protected void updateLeashedState()
  {
    super.updateLeashedState();
    
    if ((getLeashed()) && (getLeashedToEntity() != null) && (getLeashedToEntityworldObj == worldObj))
    {
      Entity var1 = getLeashedToEntity();
      func_175449_a(new BlockPos((int)posX, (int)posY, (int)posZ), 5);
      float var2 = getDistanceToEntity(var1);
      
      if (((this instanceof EntityTameable)) && (((EntityTameable)this).isSitting()))
      {
        if (var2 > 10.0F)
        {
          clearLeashed(true, true);
        }
        
        return;
      }
      
      if (!field_110180_bt)
      {
        tasks.addTask(2, aiBase);
        
        if ((getNavigator() instanceof PathNavigateGround))
        {
          ((PathNavigateGround)getNavigator()).func_179690_a(false);
        }
        
        field_110180_bt = true;
      }
      
      func_142017_o(var2);
      
      if (var2 > 4.0F)
      {
        getNavigator().tryMoveToEntityLiving(var1, 1.0D);
      }
      
      if (var2 > 6.0F)
      {
        double var3 = (posX - posX) / var2;
        double var5 = (posY - posY) / var2;
        double var7 = (posZ - posZ) / var2;
        motionX += var3 * Math.abs(var3) * 0.4D;
        motionY += var5 * Math.abs(var5) * 0.4D;
        motionZ += var7 * Math.abs(var7) * 0.4D;
      }
      
      if (var2 > 10.0F)
      {
        clearLeashed(true, true);
      }
    }
    else if ((!getLeashed()) && (field_110180_bt))
    {
      field_110180_bt = false;
      tasks.removeTask(aiBase);
      
      if ((getNavigator() instanceof PathNavigateGround))
      {
        ((PathNavigateGround)getNavigator()).func_179690_a(true);
      }
      
      detachHome();
    }
  }
  
  protected void func_142017_o(float p_142017_1_) {}
}
