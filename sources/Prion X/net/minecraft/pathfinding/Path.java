package net.minecraft.pathfinding;


public class Path
{
  private PathPoint[] pathPoints = new PathPoint['Ѐ'];
  
  private int count;
  
  private static final String __OBFID = "CL_00000573";
  

  public Path() {}
  
  public PathPoint addPoint(PathPoint point)
  {
    if (index >= 0)
    {
      throw new IllegalStateException("OW KNOWS!");
    }
    

    if (count == pathPoints.length)
    {
      PathPoint[] var2 = new PathPoint[count << 1];
      System.arraycopy(pathPoints, 0, var2, 0, count);
      pathPoints = var2;
    }
    
    pathPoints[count] = point;
    index = count;
    sortBack(count++);
    return point;
  }
  




  public void clearPath()
  {
    count = 0;
  }
  



  public PathPoint dequeue()
  {
    PathPoint var1 = pathPoints[0];
    pathPoints[0] = pathPoints[(--count)];
    pathPoints[count] = null;
    
    if (count > 0)
    {
      sortForward(0);
    }
    
    index = -1;
    return var1;
  }
  



  public void changeDistance(PathPoint p_75850_1_, float p_75850_2_)
  {
    float var3 = distanceToTarget;
    distanceToTarget = p_75850_2_;
    
    if (p_75850_2_ < var3)
    {
      sortBack(index);
    }
    else
    {
      sortForward(index);
    }
  }
  



  private void sortBack(int p_75847_1_)
  {
    PathPoint var2 = pathPoints[p_75847_1_];
    
    int var4;
    for (float var3 = distanceToTarget; p_75847_1_ > 0; p_75847_1_ = var4)
    {
      var4 = p_75847_1_ - 1 >> 1;
      PathPoint var5 = pathPoints[var4];
      
      if (var3 >= distanceToTarget) {
        break;
      }
      

      pathPoints[p_75847_1_] = var5;
      index = p_75847_1_;
    }
    
    pathPoints[p_75847_1_] = var2;
    index = p_75847_1_;
  }
  



  private void sortForward(int p_75846_1_)
  {
    PathPoint var2 = pathPoints[p_75846_1_];
    float var3 = distanceToTarget;
    
    for (;;)
    {
      int var4 = 1 + (p_75846_1_ << 1);
      int var5 = var4 + 1;
      
      if (var4 >= count) {
        break;
      }
      

      PathPoint var6 = pathPoints[var4];
      float var7 = distanceToTarget;
      float var9;
      PathPoint var8;
      float var9;
      if (var5 >= count)
      {
        PathPoint var8 = null;
        var9 = Float.POSITIVE_INFINITY;
      }
      else
      {
        var8 = pathPoints[var5];
        var9 = distanceToTarget;
      }
      
      if (var7 < var9)
      {
        if (var7 >= var3) {
          break;
        }
        

        pathPoints[p_75846_1_] = var6;
        index = p_75846_1_;
        p_75846_1_ = var4;
      }
      else
      {
        if (var9 >= var3) {
          break;
        }
        

        pathPoints[p_75846_1_] = var8;
        index = p_75846_1_;
        p_75846_1_ = var5;
      }
    }
    
    pathPoints[p_75846_1_] = var2;
    index = p_75846_1_;
  }
  



  public boolean isPathEmpty()
  {
    return count == 0;
  }
}
