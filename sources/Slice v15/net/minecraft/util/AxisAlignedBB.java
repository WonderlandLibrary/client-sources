package net.minecraft.util;

public class AxisAlignedBB
{
  public final double minX;
  public final double minY;
  public final double minZ;
  public final double maxX;
  public double maxY;
  public final double maxZ;
  private static final String __OBFID = "CL_00000607";
  
  public AxisAlignedBB(double x1, double y1, double z1, double x2, double y2, double z2)
  {
    minX = Math.min(x1, x2);
    minY = Math.min(y1, y2);
    minZ = Math.min(z1, z2);
    maxX = Math.max(x1, x2);
    maxY = Math.max(y1, y2);
    maxZ = Math.max(z1, z2);
  }
  
  public AxisAlignedBB(BlockPos p_i45554_1_, BlockPos p_i45554_2_)
  {
    minX = p_i45554_1_.getX();
    minY = p_i45554_1_.getY();
    minZ = p_i45554_1_.getZ();
    maxX = p_i45554_2_.getX();
    maxY = p_i45554_2_.getY();
    maxZ = p_i45554_2_.getZ();
  }
  



  public AxisAlignedBB addCoord(double x, double y, double z)
  {
    double var7 = minX;
    double var9 = minY;
    double var11 = minZ;
    double var13 = maxX;
    double var15 = maxY;
    double var17 = maxZ;
    
    if (x < 0.0D)
    {
      var7 += x;
    }
    else if (x > 0.0D)
    {
      var13 += x;
    }
    
    if (y < 0.0D)
    {
      var9 += y;
    }
    else if (y > 0.0D)
    {
      var15 += y;
    }
    
    if (z < 0.0D)
    {
      var11 += z;
    }
    else if (z > 0.0D)
    {
      var17 += z;
    }
    
    return new AxisAlignedBB(var7, var9, var11, var13, var15, var17);
  }
  




  public AxisAlignedBB expand(double x, double y, double z)
  {
    double var7 = minX - x;
    double var9 = minY - y;
    double var11 = minZ - z;
    double var13 = maxX + x;
    double var15 = maxY + y;
    double var17 = maxZ + z;
    return new AxisAlignedBB(var7, var9, var11, var13, var15, var17);
  }
  
  public AxisAlignedBB union(AxisAlignedBB other)
  {
    double var2 = Math.min(minX, minX);
    double var4 = Math.min(minY, minY);
    double var6 = Math.min(minZ, minZ);
    double var8 = Math.max(maxX, maxX);
    double var10 = Math.max(maxY, maxY);
    double var12 = Math.max(maxZ, maxZ);
    return new AxisAlignedBB(var2, var4, var6, var8, var10, var12);
  }
  



  public static AxisAlignedBB fromBounds(double p_178781_0_, double p_178781_2_, double p_178781_4_, double p_178781_6_, double p_178781_8_, double p_178781_10_)
  {
    double var12 = Math.min(p_178781_0_, p_178781_6_);
    double var14 = Math.min(p_178781_2_, p_178781_8_);
    double var16 = Math.min(p_178781_4_, p_178781_10_);
    double var18 = Math.max(p_178781_0_, p_178781_6_);
    double var20 = Math.max(p_178781_2_, p_178781_8_);
    double var22 = Math.max(p_178781_4_, p_178781_10_);
    return new AxisAlignedBB(var12, var14, var16, var18, var20, var22);
  }
  



  public AxisAlignedBB offset(double x, double y, double z)
  {
    return new AxisAlignedBB(minX + x, minY + y, minZ + z, maxX + x, maxY + y, maxZ + z);
  }
  





  public double calculateXOffset(AxisAlignedBB other, double p_72316_2_)
  {
    if ((maxY > minY) && (minY < maxY) && (maxZ > minZ) && (minZ < maxZ))
    {


      if ((p_72316_2_ > 0.0D) && (maxX <= minX))
      {
        double var4 = minX - maxX;
        
        if (var4 < p_72316_2_)
        {
          p_72316_2_ = var4;
        }
      }
      else if ((p_72316_2_ < 0.0D) && (minX >= maxX))
      {
        double var4 = maxX - minX;
        
        if (var4 > p_72316_2_)
        {
          p_72316_2_ = var4;
        }
      }
      
      return p_72316_2_;
    }
    

    return p_72316_2_;
  }
  






  public double calculateYOffset(AxisAlignedBB other, double p_72323_2_)
  {
    if ((maxX > minX) && (minX < maxX) && (maxZ > minZ) && (minZ < maxZ))
    {


      if ((p_72323_2_ > 0.0D) && (maxY <= minY))
      {
        double var4 = minY - maxY;
        
        if (var4 < p_72323_2_)
        {
          p_72323_2_ = var4;
        }
      }
      else if ((p_72323_2_ < 0.0D) && (minY >= maxY))
      {
        double var4 = maxY - minY;
        
        if (var4 > p_72323_2_)
        {
          p_72323_2_ = var4;
        }
      }
      
      return p_72323_2_;
    }
    

    return p_72323_2_;
  }
  






  public double calculateZOffset(AxisAlignedBB other, double p_72322_2_)
  {
    if ((maxX > minX) && (minX < maxX) && (maxY > minY) && (minY < maxY))
    {


      if ((p_72322_2_ > 0.0D) && (maxZ <= minZ))
      {
        double var4 = minZ - maxZ;
        
        if (var4 < p_72322_2_)
        {
          p_72322_2_ = var4;
        }
      }
      else if ((p_72322_2_ < 0.0D) && (minZ >= maxZ))
      {
        double var4 = maxZ - minZ;
        
        if (var4 > p_72322_2_)
        {
          p_72322_2_ = var4;
        }
      }
      
      return p_72322_2_;
    }
    

    return p_72322_2_;
  }
  




  public boolean intersectsWith(AxisAlignedBB other)
  {
    return (maxZ > minZ) && (minZ < maxZ);
  }
  



  public boolean isVecInside(Vec3 vec)
  {
    return (zCoord > minZ) && (zCoord < maxZ);
  }
  



  public double getAverageEdgeLength()
  {
    double var1 = maxX - minX;
    double var3 = maxY - minY;
    double var5 = maxZ - minZ;
    return (var1 + var3 + var5) / 3.0D;
  }
  



  public AxisAlignedBB contract(double x, double y, double z)
  {
    double var7 = minX + x;
    double var9 = minY + y;
    double var11 = minZ + z;
    double var13 = maxX - x;
    double var15 = maxY - y;
    double var17 = maxZ - z;
    return new AxisAlignedBB(var7, var9, var11, var13, var15, var17);
  }
  
  public MovingObjectPosition calculateIntercept(Vec3 p_72327_1_, Vec3 p_72327_2_)
  {
    Vec3 var3 = p_72327_1_.getIntermediateWithXValue(p_72327_2_, minX);
    Vec3 var4 = p_72327_1_.getIntermediateWithXValue(p_72327_2_, maxX);
    Vec3 var5 = p_72327_1_.getIntermediateWithYValue(p_72327_2_, minY);
    Vec3 var6 = p_72327_1_.getIntermediateWithYValue(p_72327_2_, maxY);
    Vec3 var7 = p_72327_1_.getIntermediateWithZValue(p_72327_2_, minZ);
    Vec3 var8 = p_72327_1_.getIntermediateWithZValue(p_72327_2_, maxZ);
    
    if (!isVecInYZ(var3))
    {
      var3 = null;
    }
    
    if (!isVecInYZ(var4))
    {
      var4 = null;
    }
    
    if (!isVecInXZ(var5))
    {
      var5 = null;
    }
    
    if (!isVecInXZ(var6))
    {
      var6 = null;
    }
    
    if (!isVecInXY(var7))
    {
      var7 = null;
    }
    
    if (!isVecInXY(var8))
    {
      var8 = null;
    }
    
    Vec3 var9 = null;
    
    if (var3 != null)
    {
      var9 = var3;
    }
    
    if ((var4 != null) && ((var9 == null) || (p_72327_1_.squareDistanceTo(var4) < p_72327_1_.squareDistanceTo(var9))))
    {
      var9 = var4;
    }
    
    if ((var5 != null) && ((var9 == null) || (p_72327_1_.squareDistanceTo(var5) < p_72327_1_.squareDistanceTo(var9))))
    {
      var9 = var5;
    }
    
    if ((var6 != null) && ((var9 == null) || (p_72327_1_.squareDistanceTo(var6) < p_72327_1_.squareDistanceTo(var9))))
    {
      var9 = var6;
    }
    
    if ((var7 != null) && ((var9 == null) || (p_72327_1_.squareDistanceTo(var7) < p_72327_1_.squareDistanceTo(var9))))
    {
      var9 = var7;
    }
    
    if ((var8 != null) && ((var9 == null) || (p_72327_1_.squareDistanceTo(var8) < p_72327_1_.squareDistanceTo(var9))))
    {
      var9 = var8;
    }
    
    if (var9 == null)
    {
      return null;
    }
    

    EnumFacing var10 = null;
    
    if (var9 == var3)
    {
      var10 = EnumFacing.WEST;
    }
    else if (var9 == var4)
    {
      var10 = EnumFacing.EAST;
    }
    else if (var9 == var5)
    {
      var10 = EnumFacing.DOWN;
    }
    else if (var9 == var6)
    {
      var10 = EnumFacing.UP;
    }
    else if (var9 == var7)
    {
      var10 = EnumFacing.NORTH;
    }
    else
    {
      var10 = EnumFacing.SOUTH;
    }
    
    return new MovingObjectPosition(var9, var10);
  }
  




  private boolean isVecInYZ(Vec3 vec)
  {
    return vec != null;
  }
  



  private boolean isVecInXZ(Vec3 vec)
  {
    return vec != null;
  }
  



  private boolean isVecInXY(Vec3 vec)
  {
    return vec != null;
  }
  
  public String toString()
  {
    return "box[" + minX + ", " + minY + ", " + minZ + " -> " + maxX + ", " + maxY + ", " + maxZ + "]";
  }
}
