package net.minecraft.util;


public class Vec3
{
  public final double xCoord;
  
  public final double yCoord;
  
  public final double zCoord;
  
  private static final String __OBFID = "CL_00000612";
  

  public Vec3(double x, double y, double z)
  {
    if (x == -0.0D)
    {
      x = 0.0D;
    }
    
    if (y == -0.0D)
    {
      y = 0.0D;
    }
    
    if (z == -0.0D)
    {
      z = 0.0D;
    }
    
    xCoord = x;
    yCoord = y;
    zCoord = z;
  }
  



  public Vec3 subtractReverse(Vec3 vec)
  {
    return new Vec3(xCoord - xCoord, yCoord - yCoord, zCoord - zCoord);
  }
  



  public Vec3 normalize()
  {
    double var1 = MathHelper.sqrt_double(xCoord * xCoord + yCoord * yCoord + zCoord * zCoord);
    return var1 < 1.0E-4D ? new Vec3(0.0D, 0.0D, 0.0D) : new Vec3(xCoord / var1, yCoord / var1, zCoord / var1);
  }
  
  public double dotProduct(Vec3 vec)
  {
    return xCoord * xCoord + yCoord * yCoord + zCoord * zCoord;
  }
  



  public Vec3 crossProduct(Vec3 vec)
  {
    return new Vec3(yCoord * zCoord - zCoord * yCoord, zCoord * xCoord - xCoord * zCoord, xCoord * yCoord - yCoord * xCoord);
  }
  
  public Vec3 subtract(Vec3 p_178788_1_)
  {
    return subtract(xCoord, yCoord, zCoord);
  }
  
  public Vec3 subtract(double p_178786_1_, double p_178786_3_, double p_178786_5_)
  {
    return addVector(-p_178786_1_, -p_178786_3_, -p_178786_5_);
  }
  
  public Vec3 add(Vec3 p_178787_1_)
  {
    return addVector(xCoord, yCoord, zCoord);
  }
  




  public Vec3 addVector(double x, double y, double z)
  {
    return new Vec3(xCoord + x, yCoord + y, zCoord + z);
  }
  



  public double distanceTo(Vec3 vec)
  {
    double var2 = xCoord - xCoord;
    double var4 = yCoord - yCoord;
    double var6 = zCoord - zCoord;
    return MathHelper.sqrt_double(var2 * var2 + var4 * var4 + var6 * var6);
  }
  



  public double squareDistanceTo(Vec3 vec)
  {
    double var2 = xCoord - xCoord;
    double var4 = yCoord - yCoord;
    double var6 = zCoord - zCoord;
    return var2 * var2 + var4 * var4 + var6 * var6;
  }
  



  public double lengthVector()
  {
    return MathHelper.sqrt_double(xCoord * xCoord + yCoord * yCoord + zCoord * zCoord);
  }
  




  public Vec3 getIntermediateWithXValue(Vec3 vec, double x)
  {
    double var4 = xCoord - xCoord;
    double var6 = yCoord - yCoord;
    double var8 = zCoord - zCoord;
    
    if (var4 * var4 < 1.0000000116860974E-7D)
    {
      return null;
    }
    

    double var10 = (x - xCoord) / var4;
    return (var10 >= 0.0D) && (var10 <= 1.0D) ? new Vec3(xCoord + var4 * var10, yCoord + var6 * var10, zCoord + var8 * var10) : null;
  }
  





  public Vec3 getIntermediateWithYValue(Vec3 vec, double y)
  {
    double var4 = xCoord - xCoord;
    double var6 = yCoord - yCoord;
    double var8 = zCoord - zCoord;
    
    if (var6 * var6 < 1.0000000116860974E-7D)
    {
      return null;
    }
    

    double var10 = (y - yCoord) / var6;
    return (var10 >= 0.0D) && (var10 <= 1.0D) ? new Vec3(xCoord + var4 * var10, yCoord + var6 * var10, zCoord + var8 * var10) : null;
  }
  





  public Vec3 getIntermediateWithZValue(Vec3 vec, double z)
  {
    double var4 = xCoord - xCoord;
    double var6 = yCoord - yCoord;
    double var8 = zCoord - zCoord;
    
    if (var8 * var8 < 1.0000000116860974E-7D)
    {
      return null;
    }
    

    double var10 = (z - zCoord) / var8;
    return (var10 >= 0.0D) && (var10 <= 1.0D) ? new Vec3(xCoord + var4 * var10, yCoord + var6 * var10, zCoord + var8 * var10) : null;
  }
  

  public String toString()
  {
    return "(" + xCoord + ", " + yCoord + ", " + zCoord + ")";
  }
  
  public Vec3 rotatePitch(float p_178789_1_)
  {
    float var2 = MathHelper.cos(p_178789_1_);
    float var3 = MathHelper.sin(p_178789_1_);
    double var4 = xCoord;
    double var6 = yCoord * var2 + zCoord * var3;
    double var8 = zCoord * var2 - yCoord * var3;
    return new Vec3(var4, var6, var8);
  }
  
  public Vec3 rotateYaw(float p_178785_1_)
  {
    float var2 = MathHelper.cos(p_178785_1_);
    float var3 = MathHelper.sin(p_178785_1_);
    double var4 = xCoord * var2 + zCoord * var3;
    double var6 = yCoord;
    double var8 = zCoord * var2 - xCoord * var3;
    return new Vec3(var4, var6, var8);
  }
}
