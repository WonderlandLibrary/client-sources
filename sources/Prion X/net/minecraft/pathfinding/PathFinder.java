package net.minecraft.pathfinding;

import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.pathfinder.NodeProcessor;

public class PathFinder
{
  private Path path = new Path();
  

  private PathPoint[] pathOptions = new PathPoint[32];
  private NodeProcessor field_176190_c;
  private static final String __OBFID = "CL_00000576";
  
  public PathFinder(NodeProcessor p_i45557_1_)
  {
    field_176190_c = p_i45557_1_;
  }
  
  public PathEntity func_176188_a(IBlockAccess p_176188_1_, Entity p_176188_2_, Entity p_176188_3_, float p_176188_4_)
  {
    return func_176189_a(p_176188_1_, p_176188_2_, posX, getEntityBoundingBoxminY, posZ, p_176188_4_);
  }
  
  public PathEntity func_180782_a(IBlockAccess p_180782_1_, Entity p_180782_2_, BlockPos p_180782_3_, float p_180782_4_)
  {
    return func_176189_a(p_180782_1_, p_180782_2_, p_180782_3_.getX() + 0.5F, p_180782_3_.getY() + 0.5F, p_180782_3_.getZ() + 0.5F, p_180782_4_);
  }
  
  private PathEntity func_176189_a(IBlockAccess p_176189_1_, Entity p_176189_2_, double p_176189_3_, double p_176189_5_, double p_176189_7_, float p_176189_9_)
  {
    path.clearPath();
    field_176190_c.func_176162_a(p_176189_1_, p_176189_2_);
    PathPoint var10 = field_176190_c.func_176161_a(p_176189_2_);
    PathPoint var11 = field_176190_c.func_176160_a(p_176189_2_, p_176189_3_, p_176189_5_, p_176189_7_);
    PathEntity var12 = func_176187_a(p_176189_2_, var10, var11, p_176189_9_);
    field_176190_c.func_176163_a();
    return var12;
  }
  
  private PathEntity func_176187_a(Entity p_176187_1_, PathPoint p_176187_2_, PathPoint p_176187_3_, float p_176187_4_)
  {
    totalPathDistance = 0.0F;
    distanceToNext = p_176187_2_.distanceToSquared(p_176187_3_);
    distanceToTarget = distanceToNext;
    path.clearPath();
    path.addPoint(p_176187_2_);
    PathPoint var5 = p_176187_2_;
    int var7;
    int var8; for (; !path.isPathEmpty(); 
        















        var8 < var7)
    {
      PathPoint var6 = path.dequeue();
      
      if (var6.equals(p_176187_3_))
      {
        return createEntityPath(p_176187_2_, p_176187_3_);
      }
      
      if (var6.distanceToSquared(p_176187_3_) < var5.distanceToSquared(p_176187_3_))
      {
        var5 = var6;
      }
      
      visited = true;
      var7 = field_176190_c.func_176164_a(pathOptions, p_176187_1_, var6, p_176187_3_, p_176187_4_);
      
      var8 = 0; continue;
      
      PathPoint var9 = pathOptions[var8];
      float var10 = totalPathDistance + var6.distanceToSquared(var9);
      
      if ((var10 < p_176187_4_ * 2.0F) && ((!var9.isAssigned()) || (var10 < totalPathDistance)))
      {
        previous = var6;
        totalPathDistance = var10;
        distanceToNext = var9.distanceToSquared(p_176187_3_);
        
        if (var9.isAssigned())
        {
          path.changeDistance(var9, totalPathDistance + distanceToNext);
        }
        else
        {
          distanceToTarget = (totalPathDistance + distanceToNext);
          path.addPoint(var9);
        }
      }
      var8++;
    }
    





















    if (var5 == p_176187_2_)
    {
      return null;
    }
    

    return createEntityPath(p_176187_2_, var5);
  }
  




  private PathEntity createEntityPath(PathPoint p_75853_1_, PathPoint p_75853_2_)
  {
    int var3 = 1;
    

    for (PathPoint var4 = p_75853_2_; previous != null; var4 = previous)
    {
      var3++;
    }
    
    PathPoint[] var5 = new PathPoint[var3];
    var4 = p_75853_2_;
    var3--;
    
    for (var5[var3] = p_75853_2_; previous != null; var5[var3] = var4)
    {
      var4 = previous;
      var3--;
    }
    
    return new PathEntity(var5);
  }
}
