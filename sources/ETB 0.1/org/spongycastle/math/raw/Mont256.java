package org.spongycastle.math.raw;

public abstract class Mont256
{
  private static final long M = 4294967295L;
  
  public Mont256() {}
  
  public static int inverse32(int x) {
    int z = x;
    z *= (2 - x * z);
    z *= (2 - x * z);
    z *= (2 - x * z);
    z *= (2 - x * z);
    
    return z;
  }
  
  public static void multAdd(int[] x, int[] y, int[] z, int[] m, int mInv32)
  {
    int z_8 = 0;
    long y_0 = y[0] & 0xFFFFFFFF;
    
    for (int i = 0; i < 8; i++)
    {
      long z_0 = z[0] & 0xFFFFFFFF;
      long x_i = x[i] & 0xFFFFFFFF;
      
      long prod1 = x_i * y_0;
      long carry = (prod1 & 0xFFFFFFFF) + z_0;
      
      long t = (int)carry * mInv32 & 0xFFFFFFFF;
      
      long prod2 = t * (m[0] & 0xFFFFFFFF);
      carry += (prod2 & 0xFFFFFFFF);
      
      carry = (carry >>> 32) + (prod1 >>> 32) + (prod2 >>> 32);
      
      for (int j = 1; j < 8; j++)
      {
        prod1 = x_i * (y[j] & 0xFFFFFFFF);
        prod2 = t * (m[j] & 0xFFFFFFFF);
        
        carry += (prod1 & 0xFFFFFFFF) + (prod2 & 0xFFFFFFFF) + (z[j] & 0xFFFFFFFF);
        z[(j - 1)] = ((int)carry);
        carry = (carry >>> 32) + (prod1 >>> 32) + (prod2 >>> 32);
      }
      
      carry += (z_8 & 0xFFFFFFFF);
      z[7] = ((int)carry);
      z_8 = (int)(carry >>> 32);
    }
    
    if ((z_8 != 0) || (Nat256.gte(z, m)))
    {
      Nat256.sub(z, m, z);
    }
  }
  


  public static void multAddXF(int[] x, int[] y, int[] z, int[] m)
  {
    int z_8 = 0;
    long y_0 = y[0] & 0xFFFFFFFF;
    
    for (int i = 0; i < 8; i++)
    {
      long x_i = x[i] & 0xFFFFFFFF;
      
      long carry = x_i * y_0 + (z[0] & 0xFFFFFFFF);
      long t = carry & 0xFFFFFFFF;
      carry = (carry >>> 32) + t;
      
      for (int j = 1; j < 8; j++)
      {
        long prod1 = x_i * (y[j] & 0xFFFFFFFF);
        long prod2 = t * (m[j] & 0xFFFFFFFF);
        
        carry += (prod1 & 0xFFFFFFFF) + (prod2 & 0xFFFFFFFF) + (z[j] & 0xFFFFFFFF);
        z[(j - 1)] = ((int)carry);
        carry = (carry >>> 32) + (prod1 >>> 32) + (prod2 >>> 32);
      }
      
      carry += (z_8 & 0xFFFFFFFF);
      z[7] = ((int)carry);
      z_8 = (int)(carry >>> 32);
    }
    
    if ((z_8 != 0) || (Nat256.gte(z, m)))
    {
      Nat256.sub(z, m, z);
    }
  }
  
  public static void reduce(int[] z, int[] m, int mInv32)
  {
    for (int i = 0; i < 8; i++)
    {
      int z_0 = z[0];
      
      long t = z_0 * mInv32 & 0xFFFFFFFF;
      
      long carry = t * (m[0] & 0xFFFFFFFF) + (z_0 & 0xFFFFFFFF);
      
      carry >>>= 32;
      
      for (int j = 1; j < 8; j++)
      {
        carry += t * (m[j] & 0xFFFFFFFF) + (z[j] & 0xFFFFFFFF);
        z[(j - 1)] = ((int)carry);
        carry >>>= 32;
      }
      
      z[7] = ((int)carry);
    }
    

    if (Nat256.gte(z, m))
    {
      Nat256.sub(z, m, z);
    }
  }
  


  public static void reduceXF(int[] z, int[] m)
  {
    for (int i = 0; i < 8; i++)
    {
      int z_0 = z[0];
      
      long t = z_0 & 0xFFFFFFFF;
      long carry = t;
      
      for (int j = 1; j < 8; j++)
      {
        carry += t * (m[j] & 0xFFFFFFFF) + (z[j] & 0xFFFFFFFF);
        z[(j - 1)] = ((int)carry);
        carry >>>= 32;
      }
      
      z[7] = ((int)carry);
    }
    

    if (Nat256.gte(z, m))
    {
      Nat256.sub(z, m, z);
    }
  }
}
