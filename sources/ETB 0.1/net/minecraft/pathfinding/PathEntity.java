package net.minecraft.pathfinding;

import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;






public class PathEntity
{
  private final PathPoint[] points;
  private int currentPathIndex;
  private int pathLength;
  private static final String __OBFID = "CL_00000575";
  
  public PathEntity(PathPoint[] p_i2136_1_)
  {
    points = p_i2136_1_;
    pathLength = p_i2136_1_.length;
  }
  



  public void incrementPathIndex()
  {
    currentPathIndex += 1;
  }
  



  public boolean isFinished()
  {
    return currentPathIndex >= pathLength;
  }
  



  public PathPoint getFinalPathPoint()
  {
    return pathLength > 0 ? points[(pathLength - 1)] : null;
  }
  



  public PathPoint getPathPointFromIndex(int p_75877_1_)
  {
    return points[p_75877_1_];
  }
  
  public int getCurrentPathLength()
  {
    return pathLength;
  }
  
  public void setCurrentPathLength(int p_75871_1_)
  {
    pathLength = p_75871_1_;
  }
  
  public int getCurrentPathIndex()
  {
    return currentPathIndex;
  }
  
  public void setCurrentPathIndex(int p_75872_1_)
  {
    currentPathIndex = p_75872_1_;
  }
  



  public Vec3 getVectorFromIndex(Entity p_75881_1_, int p_75881_2_)
  {
    double var3 = points[p_75881_2_].xCoord + (int)(width + 1.0F) * 0.5D;
    double var5 = points[p_75881_2_].yCoord;
    double var7 = points[p_75881_2_].zCoord + (int)(width + 1.0F) * 0.5D;
    return new Vec3(var3, var5, var7);
  }
  



  public Vec3 getPosition(Entity p_75878_1_)
  {
    return getVectorFromIndex(p_75878_1_, currentPathIndex);
  }
  



  public boolean isSamePath(PathEntity p_75876_1_)
  {
    if (p_75876_1_ == null)
    {
      return false;
    }
    if (points.length != points.length)
    {
      return false;
    }
    

    for (int var2 = 0; var2 < points.length; var2++)
    {
      if ((points[var2].xCoord != points[var2].xCoord) || (points[var2].yCoord != points[var2].yCoord) || (points[var2].zCoord != points[var2].zCoord))
      {
        return false;
      }
    }
    
    return true;
  }
  




  public boolean isDestinationSame(Vec3 p_75880_1_)
  {
    PathPoint var2 = getFinalPathPoint();
    return var2 != null;
  }
}
