package org.spongycastle.math.raw;

import java.math.BigInteger;

public abstract class Nat224
{
  private static final long M = 4294967295L;
  
  public Nat224() {}
  
  public static int add(int[] x, int[] y, int[] z)
  {
    long c = 0L;
    c += (x[0] & 0xFFFFFFFF) + (y[0] & 0xFFFFFFFF);
    z[0] = ((int)c);
    c >>>= 32;
    c += (x[1] & 0xFFFFFFFF) + (y[1] & 0xFFFFFFFF);
    z[1] = ((int)c);
    c >>>= 32;
    c += (x[2] & 0xFFFFFFFF) + (y[2] & 0xFFFFFFFF);
    z[2] = ((int)c);
    c >>>= 32;
    c += (x[3] & 0xFFFFFFFF) + (y[3] & 0xFFFFFFFF);
    z[3] = ((int)c);
    c >>>= 32;
    c += (x[4] & 0xFFFFFFFF) + (y[4] & 0xFFFFFFFF);
    z[4] = ((int)c);
    c >>>= 32;
    c += (x[5] & 0xFFFFFFFF) + (y[5] & 0xFFFFFFFF);
    z[5] = ((int)c);
    c >>>= 32;
    c += (x[6] & 0xFFFFFFFF) + (y[6] & 0xFFFFFFFF);
    z[6] = ((int)c);
    c >>>= 32;
    return (int)c;
  }
  
  public static int add(int[] x, int xOff, int[] y, int yOff, int[] z, int zOff)
  {
    long c = 0L;
    c += (x[(xOff + 0)] & 0xFFFFFFFF) + (y[(yOff + 0)] & 0xFFFFFFFF);
    z[(zOff + 0)] = ((int)c);
    c >>>= 32;
    c += (x[(xOff + 1)] & 0xFFFFFFFF) + (y[(yOff + 1)] & 0xFFFFFFFF);
    z[(zOff + 1)] = ((int)c);
    c >>>= 32;
    c += (x[(xOff + 2)] & 0xFFFFFFFF) + (y[(yOff + 2)] & 0xFFFFFFFF);
    z[(zOff + 2)] = ((int)c);
    c >>>= 32;
    c += (x[(xOff + 3)] & 0xFFFFFFFF) + (y[(yOff + 3)] & 0xFFFFFFFF);
    z[(zOff + 3)] = ((int)c);
    c >>>= 32;
    c += (x[(xOff + 4)] & 0xFFFFFFFF) + (y[(yOff + 4)] & 0xFFFFFFFF);
    z[(zOff + 4)] = ((int)c);
    c >>>= 32;
    c += (x[(xOff + 5)] & 0xFFFFFFFF) + (y[(yOff + 5)] & 0xFFFFFFFF);
    z[(zOff + 5)] = ((int)c);
    c >>>= 32;
    c += (x[(xOff + 6)] & 0xFFFFFFFF) + (y[(yOff + 6)] & 0xFFFFFFFF);
    z[(zOff + 6)] = ((int)c);
    c >>>= 32;
    return (int)c;
  }
  
  public static int addBothTo(int[] x, int[] y, int[] z)
  {
    long c = 0L;
    c += (x[0] & 0xFFFFFFFF) + (y[0] & 0xFFFFFFFF) + (z[0] & 0xFFFFFFFF);
    z[0] = ((int)c);
    c >>>= 32;
    c += (x[1] & 0xFFFFFFFF) + (y[1] & 0xFFFFFFFF) + (z[1] & 0xFFFFFFFF);
    z[1] = ((int)c);
    c >>>= 32;
    c += (x[2] & 0xFFFFFFFF) + (y[2] & 0xFFFFFFFF) + (z[2] & 0xFFFFFFFF);
    z[2] = ((int)c);
    c >>>= 32;
    c += (x[3] & 0xFFFFFFFF) + (y[3] & 0xFFFFFFFF) + (z[3] & 0xFFFFFFFF);
    z[3] = ((int)c);
    c >>>= 32;
    c += (x[4] & 0xFFFFFFFF) + (y[4] & 0xFFFFFFFF) + (z[4] & 0xFFFFFFFF);
    z[4] = ((int)c);
    c >>>= 32;
    c += (x[5] & 0xFFFFFFFF) + (y[5] & 0xFFFFFFFF) + (z[5] & 0xFFFFFFFF);
    z[5] = ((int)c);
    c >>>= 32;
    c += (x[6] & 0xFFFFFFFF) + (y[6] & 0xFFFFFFFF) + (z[6] & 0xFFFFFFFF);
    z[6] = ((int)c);
    c >>>= 32;
    return (int)c;
  }
  
  public static int addBothTo(int[] x, int xOff, int[] y, int yOff, int[] z, int zOff)
  {
    long c = 0L;
    c += (x[(xOff + 0)] & 0xFFFFFFFF) + (y[(yOff + 0)] & 0xFFFFFFFF) + (z[(zOff + 0)] & 0xFFFFFFFF);
    z[(zOff + 0)] = ((int)c);
    c >>>= 32;
    c += (x[(xOff + 1)] & 0xFFFFFFFF) + (y[(yOff + 1)] & 0xFFFFFFFF) + (z[(zOff + 1)] & 0xFFFFFFFF);
    z[(zOff + 1)] = ((int)c);
    c >>>= 32;
    c += (x[(xOff + 2)] & 0xFFFFFFFF) + (y[(yOff + 2)] & 0xFFFFFFFF) + (z[(zOff + 2)] & 0xFFFFFFFF);
    z[(zOff + 2)] = ((int)c);
    c >>>= 32;
    c += (x[(xOff + 3)] & 0xFFFFFFFF) + (y[(yOff + 3)] & 0xFFFFFFFF) + (z[(zOff + 3)] & 0xFFFFFFFF);
    z[(zOff + 3)] = ((int)c);
    c >>>= 32;
    c += (x[(xOff + 4)] & 0xFFFFFFFF) + (y[(yOff + 4)] & 0xFFFFFFFF) + (z[(zOff + 4)] & 0xFFFFFFFF);
    z[(zOff + 4)] = ((int)c);
    c >>>= 32;
    c += (x[(xOff + 5)] & 0xFFFFFFFF) + (y[(yOff + 5)] & 0xFFFFFFFF) + (z[(zOff + 5)] & 0xFFFFFFFF);
    z[(zOff + 5)] = ((int)c);
    c >>>= 32;
    c += (x[(xOff + 6)] & 0xFFFFFFFF) + (y[(yOff + 6)] & 0xFFFFFFFF) + (z[(zOff + 6)] & 0xFFFFFFFF);
    z[(zOff + 6)] = ((int)c);
    c >>>= 32;
    return (int)c;
  }
  
  public static int addTo(int[] x, int[] z)
  {
    long c = 0L;
    c += (x[0] & 0xFFFFFFFF) + (z[0] & 0xFFFFFFFF);
    z[0] = ((int)c);
    c >>>= 32;
    c += (x[1] & 0xFFFFFFFF) + (z[1] & 0xFFFFFFFF);
    z[1] = ((int)c);
    c >>>= 32;
    c += (x[2] & 0xFFFFFFFF) + (z[2] & 0xFFFFFFFF);
    z[2] = ((int)c);
    c >>>= 32;
    c += (x[3] & 0xFFFFFFFF) + (z[3] & 0xFFFFFFFF);
    z[3] = ((int)c);
    c >>>= 32;
    c += (x[4] & 0xFFFFFFFF) + (z[4] & 0xFFFFFFFF);
    z[4] = ((int)c);
    c >>>= 32;
    c += (x[5] & 0xFFFFFFFF) + (z[5] & 0xFFFFFFFF);
    z[5] = ((int)c);
    c >>>= 32;
    c += (x[6] & 0xFFFFFFFF) + (z[6] & 0xFFFFFFFF);
    z[6] = ((int)c);
    c >>>= 32;
    return (int)c;
  }
  
  public static int addTo(int[] x, int xOff, int[] z, int zOff, int cIn)
  {
    long c = cIn & 0xFFFFFFFF;
    c += (x[(xOff + 0)] & 0xFFFFFFFF) + (z[(zOff + 0)] & 0xFFFFFFFF);
    z[(zOff + 0)] = ((int)c);
    c >>>= 32;
    c += (x[(xOff + 1)] & 0xFFFFFFFF) + (z[(zOff + 1)] & 0xFFFFFFFF);
    z[(zOff + 1)] = ((int)c);
    c >>>= 32;
    c += (x[(xOff + 2)] & 0xFFFFFFFF) + (z[(zOff + 2)] & 0xFFFFFFFF);
    z[(zOff + 2)] = ((int)c);
    c >>>= 32;
    c += (x[(xOff + 3)] & 0xFFFFFFFF) + (z[(zOff + 3)] & 0xFFFFFFFF);
    z[(zOff + 3)] = ((int)c);
    c >>>= 32;
    c += (x[(xOff + 4)] & 0xFFFFFFFF) + (z[(zOff + 4)] & 0xFFFFFFFF);
    z[(zOff + 4)] = ((int)c);
    c >>>= 32;
    c += (x[(xOff + 5)] & 0xFFFFFFFF) + (z[(zOff + 5)] & 0xFFFFFFFF);
    z[(zOff + 5)] = ((int)c);
    c >>>= 32;
    c += (x[(xOff + 6)] & 0xFFFFFFFF) + (z[(zOff + 6)] & 0xFFFFFFFF);
    z[(zOff + 6)] = ((int)c);
    c >>>= 32;
    return (int)c;
  }
  
  public static int addToEachOther(int[] u, int uOff, int[] v, int vOff)
  {
    long c = 0L;
    c += (u[(uOff + 0)] & 0xFFFFFFFF) + (v[(vOff + 0)] & 0xFFFFFFFF);
    u[(uOff + 0)] = ((int)c);
    v[(vOff + 0)] = ((int)c);
    c >>>= 32;
    c += (u[(uOff + 1)] & 0xFFFFFFFF) + (v[(vOff + 1)] & 0xFFFFFFFF);
    u[(uOff + 1)] = ((int)c);
    v[(vOff + 1)] = ((int)c);
    c >>>= 32;
    c += (u[(uOff + 2)] & 0xFFFFFFFF) + (v[(vOff + 2)] & 0xFFFFFFFF);
    u[(uOff + 2)] = ((int)c);
    v[(vOff + 2)] = ((int)c);
    c >>>= 32;
    c += (u[(uOff + 3)] & 0xFFFFFFFF) + (v[(vOff + 3)] & 0xFFFFFFFF);
    u[(uOff + 3)] = ((int)c);
    v[(vOff + 3)] = ((int)c);
    c >>>= 32;
    c += (u[(uOff + 4)] & 0xFFFFFFFF) + (v[(vOff + 4)] & 0xFFFFFFFF);
    u[(uOff + 4)] = ((int)c);
    v[(vOff + 4)] = ((int)c);
    c >>>= 32;
    c += (u[(uOff + 5)] & 0xFFFFFFFF) + (v[(vOff + 5)] & 0xFFFFFFFF);
    u[(uOff + 5)] = ((int)c);
    v[(vOff + 5)] = ((int)c);
    c >>>= 32;
    c += (u[(uOff + 6)] & 0xFFFFFFFF) + (v[(vOff + 6)] & 0xFFFFFFFF);
    u[(uOff + 6)] = ((int)c);
    v[(vOff + 6)] = ((int)c);
    c >>>= 32;
    return (int)c;
  }
  
  public static void copy(int[] x, int[] z)
  {
    z[0] = x[0];
    z[1] = x[1];
    z[2] = x[2];
    z[3] = x[3];
    z[4] = x[4];
    z[5] = x[5];
    z[6] = x[6];
  }
  
  public static int[] create()
  {
    return new int[7];
  }
  
  public static int[] createExt()
  {
    return new int[14];
  }
  
  public static boolean diff(int[] x, int xOff, int[] y, int yOff, int[] z, int zOff)
  {
    boolean pos = gte(x, xOff, y, yOff);
    if (pos)
    {
      sub(x, xOff, y, yOff, z, zOff);
    }
    else
    {
      sub(y, yOff, x, xOff, z, zOff);
    }
    return pos;
  }
  
  public static boolean eq(int[] x, int[] y)
  {
    for (int i = 6; i >= 0; i--)
    {
      if (x[i] != y[i])
      {
        return false;
      }
    }
    return true;
  }
  
  public static int[] fromBigInteger(BigInteger x)
  {
    if ((x.signum() < 0) || (x.bitLength() > 224))
    {
      throw new IllegalArgumentException();
    }
    
    int[] z = create();
    int i = 0;
    while (x.signum() != 0)
    {
      z[(i++)] = x.intValue();
      x = x.shiftRight(32);
    }
    return z;
  }
  
  public static int getBit(int[] x, int bit)
  {
    if (bit == 0)
    {
      return x[0] & 0x1;
    }
    int w = bit >> 5;
    if ((w < 0) || (w >= 7))
    {
      return 0;
    }
    int b = bit & 0x1F;
    return x[w] >>> b & 0x1;
  }
  
  public static boolean gte(int[] x, int[] y)
  {
    for (int i = 6; i >= 0; i--)
    {
      int x_i = x[i] ^ 0x80000000;
      int y_i = y[i] ^ 0x80000000;
      if (x_i < y_i)
        return false;
      if (x_i > y_i)
        return true;
    }
    return true;
  }
  
  public static boolean gte(int[] x, int xOff, int[] y, int yOff)
  {
    for (int i = 6; i >= 0; i--)
    {
      int x_i = x[(xOff + i)] ^ 0x80000000;
      int y_i = y[(yOff + i)] ^ 0x80000000;
      if (x_i < y_i)
        return false;
      if (x_i > y_i)
        return true;
    }
    return true;
  }
  
  public static boolean isOne(int[] x)
  {
    if (x[0] != 1)
    {
      return false;
    }
    for (int i = 1; i < 7; i++)
    {
      if (x[i] != 0)
      {
        return false;
      }
    }
    return true;
  }
  
  public static boolean isZero(int[] x)
  {
    for (int i = 0; i < 7; i++)
    {
      if (x[i] != 0)
      {
        return false;
      }
    }
    return true;
  }
  
  public static void mul(int[] x, int[] y, int[] zz)
  {
    long y_0 = y[0] & 0xFFFFFFFF;
    long y_1 = y[1] & 0xFFFFFFFF;
    long y_2 = y[2] & 0xFFFFFFFF;
    long y_3 = y[3] & 0xFFFFFFFF;
    long y_4 = y[4] & 0xFFFFFFFF;
    long y_5 = y[5] & 0xFFFFFFFF;
    long y_6 = y[6] & 0xFFFFFFFF;
    

    long c = 0L;long x_0 = x[0] & 0xFFFFFFFF;
    c += x_0 * y_0;
    zz[0] = ((int)c);
    c >>>= 32;
    c += x_0 * y_1;
    zz[1] = ((int)c);
    c >>>= 32;
    c += x_0 * y_2;
    zz[2] = ((int)c);
    c >>>= 32;
    c += x_0 * y_3;
    zz[3] = ((int)c);
    c >>>= 32;
    c += x_0 * y_4;
    zz[4] = ((int)c);
    c >>>= 32;
    c += x_0 * y_5;
    zz[5] = ((int)c);
    c >>>= 32;
    c += x_0 * y_6;
    zz[6] = ((int)c);
    c >>>= 32;
    zz[7] = ((int)c);
    

    for (int i = 1; i < 7; i++)
    {
      long c = 0L;long x_i = x[i] & 0xFFFFFFFF;
      c += x_i * y_0 + (zz[(i + 0)] & 0xFFFFFFFF);
      zz[(i + 0)] = ((int)c);
      c >>>= 32;
      c += x_i * y_1 + (zz[(i + 1)] & 0xFFFFFFFF);
      zz[(i + 1)] = ((int)c);
      c >>>= 32;
      c += x_i * y_2 + (zz[(i + 2)] & 0xFFFFFFFF);
      zz[(i + 2)] = ((int)c);
      c >>>= 32;
      c += x_i * y_3 + (zz[(i + 3)] & 0xFFFFFFFF);
      zz[(i + 3)] = ((int)c);
      c >>>= 32;
      c += x_i * y_4 + (zz[(i + 4)] & 0xFFFFFFFF);
      zz[(i + 4)] = ((int)c);
      c >>>= 32;
      c += x_i * y_5 + (zz[(i + 5)] & 0xFFFFFFFF);
      zz[(i + 5)] = ((int)c);
      c >>>= 32;
      c += x_i * y_6 + (zz[(i + 6)] & 0xFFFFFFFF);
      zz[(i + 6)] = ((int)c);
      c >>>= 32;
      zz[(i + 7)] = ((int)c);
    }
  }
  
  public static void mul(int[] x, int xOff, int[] y, int yOff, int[] zz, int zzOff)
  {
    long y_0 = y[(yOff + 0)] & 0xFFFFFFFF;
    long y_1 = y[(yOff + 1)] & 0xFFFFFFFF;
    long y_2 = y[(yOff + 2)] & 0xFFFFFFFF;
    long y_3 = y[(yOff + 3)] & 0xFFFFFFFF;
    long y_4 = y[(yOff + 4)] & 0xFFFFFFFF;
    long y_5 = y[(yOff + 5)] & 0xFFFFFFFF;
    long y_6 = y[(yOff + 6)] & 0xFFFFFFFF;
    

    long c = 0L;long x_0 = x[(xOff + 0)] & 0xFFFFFFFF;
    c += x_0 * y_0;
    zz[(zzOff + 0)] = ((int)c);
    c >>>= 32;
    c += x_0 * y_1;
    zz[(zzOff + 1)] = ((int)c);
    c >>>= 32;
    c += x_0 * y_2;
    zz[(zzOff + 2)] = ((int)c);
    c >>>= 32;
    c += x_0 * y_3;
    zz[(zzOff + 3)] = ((int)c);
    c >>>= 32;
    c += x_0 * y_4;
    zz[(zzOff + 4)] = ((int)c);
    c >>>= 32;
    c += x_0 * y_5;
    zz[(zzOff + 5)] = ((int)c);
    c >>>= 32;
    c += x_0 * y_6;
    zz[(zzOff + 6)] = ((int)c);
    c >>>= 32;
    zz[(zzOff + 7)] = ((int)c);
    

    for (int i = 1; i < 7; i++)
    {
      zzOff++;
      long c = 0L;long x_i = x[(xOff + i)] & 0xFFFFFFFF;
      c += x_i * y_0 + (zz[(zzOff + 0)] & 0xFFFFFFFF);
      zz[(zzOff + 0)] = ((int)c);
      c >>>= 32;
      c += x_i * y_1 + (zz[(zzOff + 1)] & 0xFFFFFFFF);
      zz[(zzOff + 1)] = ((int)c);
      c >>>= 32;
      c += x_i * y_2 + (zz[(zzOff + 2)] & 0xFFFFFFFF);
      zz[(zzOff + 2)] = ((int)c);
      c >>>= 32;
      c += x_i * y_3 + (zz[(zzOff + 3)] & 0xFFFFFFFF);
      zz[(zzOff + 3)] = ((int)c);
      c >>>= 32;
      c += x_i * y_4 + (zz[(zzOff + 4)] & 0xFFFFFFFF);
      zz[(zzOff + 4)] = ((int)c);
      c >>>= 32;
      c += x_i * y_5 + (zz[(zzOff + 5)] & 0xFFFFFFFF);
      zz[(zzOff + 5)] = ((int)c);
      c >>>= 32;
      c += x_i * y_6 + (zz[(zzOff + 6)] & 0xFFFFFFFF);
      zz[(zzOff + 6)] = ((int)c);
      c >>>= 32;
      zz[(zzOff + 7)] = ((int)c);
    }
  }
  
  public static int mulAddTo(int[] x, int[] y, int[] zz)
  {
    long y_0 = y[0] & 0xFFFFFFFF;
    long y_1 = y[1] & 0xFFFFFFFF;
    long y_2 = y[2] & 0xFFFFFFFF;
    long y_3 = y[3] & 0xFFFFFFFF;
    long y_4 = y[4] & 0xFFFFFFFF;
    long y_5 = y[5] & 0xFFFFFFFF;
    long y_6 = y[6] & 0xFFFFFFFF;
    
    long zc = 0L;
    for (int i = 0; i < 7; i++)
    {
      long c = 0L;long x_i = x[i] & 0xFFFFFFFF;
      c += x_i * y_0 + (zz[(i + 0)] & 0xFFFFFFFF);
      zz[(i + 0)] = ((int)c);
      c >>>= 32;
      c += x_i * y_1 + (zz[(i + 1)] & 0xFFFFFFFF);
      zz[(i + 1)] = ((int)c);
      c >>>= 32;
      c += x_i * y_2 + (zz[(i + 2)] & 0xFFFFFFFF);
      zz[(i + 2)] = ((int)c);
      c >>>= 32;
      c += x_i * y_3 + (zz[(i + 3)] & 0xFFFFFFFF);
      zz[(i + 3)] = ((int)c);
      c >>>= 32;
      c += x_i * y_4 + (zz[(i + 4)] & 0xFFFFFFFF);
      zz[(i + 4)] = ((int)c);
      c >>>= 32;
      c += x_i * y_5 + (zz[(i + 5)] & 0xFFFFFFFF);
      zz[(i + 5)] = ((int)c);
      c >>>= 32;
      c += x_i * y_6 + (zz[(i + 6)] & 0xFFFFFFFF);
      zz[(i + 6)] = ((int)c);
      c >>>= 32;
      c += zc + (zz[(i + 7)] & 0xFFFFFFFF);
      zz[(i + 7)] = ((int)c);
      zc = c >>> 32;
    }
    return (int)zc;
  }
  
  public static int mulAddTo(int[] x, int xOff, int[] y, int yOff, int[] zz, int zzOff)
  {
    long y_0 = y[(yOff + 0)] & 0xFFFFFFFF;
    long y_1 = y[(yOff + 1)] & 0xFFFFFFFF;
    long y_2 = y[(yOff + 2)] & 0xFFFFFFFF;
    long y_3 = y[(yOff + 3)] & 0xFFFFFFFF;
    long y_4 = y[(yOff + 4)] & 0xFFFFFFFF;
    long y_5 = y[(yOff + 5)] & 0xFFFFFFFF;
    long y_6 = y[(yOff + 6)] & 0xFFFFFFFF;
    
    long zc = 0L;
    for (int i = 0; i < 7; i++)
    {
      long c = 0L;long x_i = x[(xOff + i)] & 0xFFFFFFFF;
      c += x_i * y_0 + (zz[(zzOff + 0)] & 0xFFFFFFFF);
      zz[(zzOff + 0)] = ((int)c);
      c >>>= 32;
      c += x_i * y_1 + (zz[(zzOff + 1)] & 0xFFFFFFFF);
      zz[(zzOff + 1)] = ((int)c);
      c >>>= 32;
      c += x_i * y_2 + (zz[(zzOff + 2)] & 0xFFFFFFFF);
      zz[(zzOff + 2)] = ((int)c);
      c >>>= 32;
      c += x_i * y_3 + (zz[(zzOff + 3)] & 0xFFFFFFFF);
      zz[(zzOff + 3)] = ((int)c);
      c >>>= 32;
      c += x_i * y_4 + (zz[(zzOff + 4)] & 0xFFFFFFFF);
      zz[(zzOff + 4)] = ((int)c);
      c >>>= 32;
      c += x_i * y_5 + (zz[(zzOff + 5)] & 0xFFFFFFFF);
      zz[(zzOff + 5)] = ((int)c);
      c >>>= 32;
      c += x_i * y_6 + (zz[(zzOff + 6)] & 0xFFFFFFFF);
      zz[(zzOff + 6)] = ((int)c);
      c >>>= 32;
      c += zc + (zz[(zzOff + 7)] & 0xFFFFFFFF);
      zz[(zzOff + 7)] = ((int)c);
      zc = c >>> 32;
      zzOff++;
    }
    return (int)zc;
  }
  


  public static long mul33Add(int w, int[] x, int xOff, int[] y, int yOff, int[] z, int zOff)
  {
    long c = 0L;long wVal = w & 0xFFFFFFFF;
    long x0 = x[(xOff + 0)] & 0xFFFFFFFF;
    c += wVal * x0 + (y[(yOff + 0)] & 0xFFFFFFFF);
    z[(zOff + 0)] = ((int)c);
    c >>>= 32;
    long x1 = x[(xOff + 1)] & 0xFFFFFFFF;
    c += wVal * x1 + x0 + (y[(yOff + 1)] & 0xFFFFFFFF);
    z[(zOff + 1)] = ((int)c);
    c >>>= 32;
    long x2 = x[(xOff + 2)] & 0xFFFFFFFF;
    c += wVal * x2 + x1 + (y[(yOff + 2)] & 0xFFFFFFFF);
    z[(zOff + 2)] = ((int)c);
    c >>>= 32;
    long x3 = x[(xOff + 3)] & 0xFFFFFFFF;
    c += wVal * x3 + x2 + (y[(yOff + 3)] & 0xFFFFFFFF);
    z[(zOff + 3)] = ((int)c);
    c >>>= 32;
    long x4 = x[(xOff + 4)] & 0xFFFFFFFF;
    c += wVal * x4 + x3 + (y[(yOff + 4)] & 0xFFFFFFFF);
    z[(zOff + 4)] = ((int)c);
    c >>>= 32;
    long x5 = x[(xOff + 5)] & 0xFFFFFFFF;
    c += wVal * x5 + x4 + (y[(yOff + 5)] & 0xFFFFFFFF);
    z[(zOff + 5)] = ((int)c);
    c >>>= 32;
    long x6 = x[(xOff + 6)] & 0xFFFFFFFF;
    c += wVal * x6 + x5 + (y[(yOff + 6)] & 0xFFFFFFFF);
    z[(zOff + 6)] = ((int)c);
    c >>>= 32;
    c += x6;
    return c;
  }
  
  public static int mulByWord(int x, int[] z)
  {
    long c = 0L;long xVal = x & 0xFFFFFFFF;
    c += xVal * (z[0] & 0xFFFFFFFF);
    z[0] = ((int)c);
    c >>>= 32;
    c += xVal * (z[1] & 0xFFFFFFFF);
    z[1] = ((int)c);
    c >>>= 32;
    c += xVal * (z[2] & 0xFFFFFFFF);
    z[2] = ((int)c);
    c >>>= 32;
    c += xVal * (z[3] & 0xFFFFFFFF);
    z[3] = ((int)c);
    c >>>= 32;
    c += xVal * (z[4] & 0xFFFFFFFF);
    z[4] = ((int)c);
    c >>>= 32;
    c += xVal * (z[5] & 0xFFFFFFFF);
    z[5] = ((int)c);
    c >>>= 32;
    c += xVal * (z[6] & 0xFFFFFFFF);
    z[6] = ((int)c);
    c >>>= 32;
    return (int)c;
  }
  
  public static int mulByWordAddTo(int x, int[] y, int[] z)
  {
    long c = 0L;long xVal = x & 0xFFFFFFFF;
    c += xVal * (z[0] & 0xFFFFFFFF) + (y[0] & 0xFFFFFFFF);
    z[0] = ((int)c);
    c >>>= 32;
    c += xVal * (z[1] & 0xFFFFFFFF) + (y[1] & 0xFFFFFFFF);
    z[1] = ((int)c);
    c >>>= 32;
    c += xVal * (z[2] & 0xFFFFFFFF) + (y[2] & 0xFFFFFFFF);
    z[2] = ((int)c);
    c >>>= 32;
    c += xVal * (z[3] & 0xFFFFFFFF) + (y[3] & 0xFFFFFFFF);
    z[3] = ((int)c);
    c >>>= 32;
    c += xVal * (z[4] & 0xFFFFFFFF) + (y[4] & 0xFFFFFFFF);
    z[4] = ((int)c);
    c >>>= 32;
    c += xVal * (z[5] & 0xFFFFFFFF) + (y[5] & 0xFFFFFFFF);
    z[5] = ((int)c);
    c >>>= 32;
    c += xVal * (z[6] & 0xFFFFFFFF) + (y[6] & 0xFFFFFFFF);
    z[6] = ((int)c);
    c >>>= 32;
    return (int)c;
  }
  
  public static int mulWordAddTo(int x, int[] y, int yOff, int[] z, int zOff)
  {
    long c = 0L;long xVal = x & 0xFFFFFFFF;
    c += xVal * (y[(yOff + 0)] & 0xFFFFFFFF) + (z[(zOff + 0)] & 0xFFFFFFFF);
    z[(zOff + 0)] = ((int)c);
    c >>>= 32;
    c += xVal * (y[(yOff + 1)] & 0xFFFFFFFF) + (z[(zOff + 1)] & 0xFFFFFFFF);
    z[(zOff + 1)] = ((int)c);
    c >>>= 32;
    c += xVal * (y[(yOff + 2)] & 0xFFFFFFFF) + (z[(zOff + 2)] & 0xFFFFFFFF);
    z[(zOff + 2)] = ((int)c);
    c >>>= 32;
    c += xVal * (y[(yOff + 3)] & 0xFFFFFFFF) + (z[(zOff + 3)] & 0xFFFFFFFF);
    z[(zOff + 3)] = ((int)c);
    c >>>= 32;
    c += xVal * (y[(yOff + 4)] & 0xFFFFFFFF) + (z[(zOff + 4)] & 0xFFFFFFFF);
    z[(zOff + 4)] = ((int)c);
    c >>>= 32;
    c += xVal * (y[(yOff + 5)] & 0xFFFFFFFF) + (z[(zOff + 5)] & 0xFFFFFFFF);
    z[(zOff + 5)] = ((int)c);
    c >>>= 32;
    c += xVal * (y[(yOff + 6)] & 0xFFFFFFFF) + (z[(zOff + 6)] & 0xFFFFFFFF);
    z[(zOff + 6)] = ((int)c);
    c >>>= 32;
    return (int)c;
  }
  



  public static int mul33DWordAdd(int x, long y, int[] z, int zOff)
  {
    long c = 0L;long xVal = x & 0xFFFFFFFF;
    long y00 = y & 0xFFFFFFFF;
    c += xVal * y00 + (z[(zOff + 0)] & 0xFFFFFFFF);
    z[(zOff + 0)] = ((int)c);
    c >>>= 32;
    long y01 = y >>> 32;
    c += xVal * y01 + y00 + (z[(zOff + 1)] & 0xFFFFFFFF);
    z[(zOff + 1)] = ((int)c);
    c >>>= 32;
    c += y01 + (z[(zOff + 2)] & 0xFFFFFFFF);
    z[(zOff + 2)] = ((int)c);
    c >>>= 32;
    c += (z[(zOff + 3)] & 0xFFFFFFFF);
    z[(zOff + 3)] = ((int)c);
    c >>>= 32;
    return c == 0L ? 0 : Nat.incAt(7, z, zOff, 4);
  }
  



  public static int mul33WordAdd(int x, int y, int[] z, int zOff)
  {
    long c = 0L;long xVal = x & 0xFFFFFFFF;long yVal = y & 0xFFFFFFFF;
    c += yVal * xVal + (z[(zOff + 0)] & 0xFFFFFFFF);
    z[(zOff + 0)] = ((int)c);
    c >>>= 32;
    c += yVal + (z[(zOff + 1)] & 0xFFFFFFFF);
    z[(zOff + 1)] = ((int)c);
    c >>>= 32;
    c += (z[(zOff + 2)] & 0xFFFFFFFF);
    z[(zOff + 2)] = ((int)c);
    c >>>= 32;
    return c == 0L ? 0 : Nat.incAt(7, z, zOff, 3);
  }
  

  public static int mulWordDwordAdd(int x, long y, int[] z, int zOff)
  {
    long c = 0L;long xVal = x & 0xFFFFFFFF;
    c += xVal * (y & 0xFFFFFFFF) + (z[(zOff + 0)] & 0xFFFFFFFF);
    z[(zOff + 0)] = ((int)c);
    c >>>= 32;
    c += xVal * (y >>> 32) + (z[(zOff + 1)] & 0xFFFFFFFF);
    z[(zOff + 1)] = ((int)c);
    c >>>= 32;
    c += (z[(zOff + 2)] & 0xFFFFFFFF);
    z[(zOff + 2)] = ((int)c);
    c >>>= 32;
    return c == 0L ? 0 : Nat.incAt(7, z, zOff, 3);
  }
  
  public static int mulWord(int x, int[] y, int[] z, int zOff)
  {
    long c = 0L;long xVal = x & 0xFFFFFFFF;
    int i = 0;
    do
    {
      c += xVal * (y[i] & 0xFFFFFFFF);
      z[(zOff + i)] = ((int)c);
      c >>>= 32;
      
      i++; } while (i < 7);
    return (int)c;
  }
  
  public static void square(int[] x, int[] zz)
  {
    long x_0 = x[0] & 0xFFFFFFFF;
    

    int c = 0;
    
    int i = 6;int j = 14;
    do
    {
      long xVal = x[(i--)] & 0xFFFFFFFF;
      long p = xVal * xVal;
      zz[(--j)] = (c << 31 | (int)(p >>> 33));
      zz[(--j)] = ((int)(p >>> 1));
      c = (int)p;
    }
    while (i > 0);
    

    long p = x_0 * x_0;
    long zz_1 = c << 31 & 0xFFFFFFFF | p >>> 33;
    zz[0] = ((int)p);
    c = (int)(p >>> 32) & 0x1;
    


    long x_1 = x[1] & 0xFFFFFFFF;
    long zz_2 = zz[2] & 0xFFFFFFFF;
    

    zz_1 += x_1 * x_0;
    int w = (int)zz_1;
    zz[1] = (w << 1 | c);
    c = w >>> 31;
    zz_2 += (zz_1 >>> 32);
    

    long x_2 = x[2] & 0xFFFFFFFF;
    long zz_3 = zz[3] & 0xFFFFFFFF;
    long zz_4 = zz[4] & 0xFFFFFFFF;
    
    zz_2 += x_2 * x_0;
    w = (int)zz_2;
    zz[2] = (w << 1 | c);
    c = w >>> 31;
    zz_3 += (zz_2 >>> 32) + x_2 * x_1;
    zz_4 += (zz_3 >>> 32);
    zz_3 &= 0xFFFFFFFF;
    

    long x_3 = x[3] & 0xFFFFFFFF;
    long zz_5 = (zz[5] & 0xFFFFFFFF) + (zz_4 >>> 32);zz_4 &= 0xFFFFFFFF;
    long zz_6 = (zz[6] & 0xFFFFFFFF) + (zz_5 >>> 32);zz_5 &= 0xFFFFFFFF;
    
    zz_3 += x_3 * x_0;
    w = (int)zz_3;
    zz[3] = (w << 1 | c);
    c = w >>> 31;
    zz_4 += (zz_3 >>> 32) + x_3 * x_1;
    zz_5 += (zz_4 >>> 32) + x_3 * x_2;
    zz_4 &= 0xFFFFFFFF;
    zz_6 += (zz_5 >>> 32);
    zz_5 &= 0xFFFFFFFF;
    

    long x_4 = x[4] & 0xFFFFFFFF;
    long zz_7 = (zz[7] & 0xFFFFFFFF) + (zz_6 >>> 32);zz_6 &= 0xFFFFFFFF;
    long zz_8 = (zz[8] & 0xFFFFFFFF) + (zz_7 >>> 32);zz_7 &= 0xFFFFFFFF;
    
    zz_4 += x_4 * x_0;
    w = (int)zz_4;
    zz[4] = (w << 1 | c);
    c = w >>> 31;
    zz_5 += (zz_4 >>> 32) + x_4 * x_1;
    zz_6 += (zz_5 >>> 32) + x_4 * x_2;
    zz_5 &= 0xFFFFFFFF;
    zz_7 += (zz_6 >>> 32) + x_4 * x_3;
    zz_6 &= 0xFFFFFFFF;
    zz_8 += (zz_7 >>> 32);
    zz_7 &= 0xFFFFFFFF;
    

    long x_5 = x[5] & 0xFFFFFFFF;
    long zz_9 = (zz[9] & 0xFFFFFFFF) + (zz_8 >>> 32);zz_8 &= 0xFFFFFFFF;
    long zz_10 = (zz[10] & 0xFFFFFFFF) + (zz_9 >>> 32);zz_9 &= 0xFFFFFFFF;
    
    zz_5 += x_5 * x_0;
    w = (int)zz_5;
    zz[5] = (w << 1 | c);
    c = w >>> 31;
    zz_6 += (zz_5 >>> 32) + x_5 * x_1;
    zz_7 += (zz_6 >>> 32) + x_5 * x_2;
    zz_6 &= 0xFFFFFFFF;
    zz_8 += (zz_7 >>> 32) + x_5 * x_3;
    zz_7 &= 0xFFFFFFFF;
    zz_9 += (zz_8 >>> 32) + x_5 * x_4;
    zz_8 &= 0xFFFFFFFF;
    zz_10 += (zz_9 >>> 32);
    zz_9 &= 0xFFFFFFFF;
    

    long x_6 = x[6] & 0xFFFFFFFF;
    long zz_11 = (zz[11] & 0xFFFFFFFF) + (zz_10 >>> 32);zz_10 &= 0xFFFFFFFF;
    long zz_12 = (zz[12] & 0xFFFFFFFF) + (zz_11 >>> 32);zz_11 &= 0xFFFFFFFF;
    
    zz_6 += x_6 * x_0;
    w = (int)zz_6;
    zz[6] = (w << 1 | c);
    c = w >>> 31;
    zz_7 += (zz_6 >>> 32) + x_6 * x_1;
    zz_8 += (zz_7 >>> 32) + x_6 * x_2;
    zz_9 += (zz_8 >>> 32) + x_6 * x_3;
    zz_10 += (zz_9 >>> 32) + x_6 * x_4;
    zz_11 += (zz_10 >>> 32) + x_6 * x_5;
    zz_12 += (zz_11 >>> 32);
    

    w = (int)zz_7;
    zz[7] = (w << 1 | c);
    c = w >>> 31;
    w = (int)zz_8;
    zz[8] = (w << 1 | c);
    c = w >>> 31;
    w = (int)zz_9;
    zz[9] = (w << 1 | c);
    c = w >>> 31;
    w = (int)zz_10;
    zz[10] = (w << 1 | c);
    c = w >>> 31;
    w = (int)zz_11;
    zz[11] = (w << 1 | c);
    c = w >>> 31;
    w = (int)zz_12;
    zz[12] = (w << 1 | c);
    c = w >>> 31;
    w = zz[13] + (int)(zz_12 >>> 32);
    zz[13] = (w << 1 | c);
  }
  
  public static void square(int[] x, int xOff, int[] zz, int zzOff)
  {
    long x_0 = x[(xOff + 0)] & 0xFFFFFFFF;
    

    int c = 0;
    
    int i = 6;int j = 14;
    do
    {
      long xVal = x[(xOff + i--)] & 0xFFFFFFFF;
      long p = xVal * xVal;
      zz[(zzOff + --j)] = (c << 31 | (int)(p >>> 33));
      zz[(zzOff + --j)] = ((int)(p >>> 1));
      c = (int)p;
    }
    while (i > 0);
    

    long p = x_0 * x_0;
    long zz_1 = c << 31 & 0xFFFFFFFF | p >>> 33;
    zz[(zzOff + 0)] = ((int)p);
    c = (int)(p >>> 32) & 0x1;
    


    long x_1 = x[(xOff + 1)] & 0xFFFFFFFF;
    long zz_2 = zz[(zzOff + 2)] & 0xFFFFFFFF;
    

    zz_1 += x_1 * x_0;
    int w = (int)zz_1;
    zz[(zzOff + 1)] = (w << 1 | c);
    c = w >>> 31;
    zz_2 += (zz_1 >>> 32);
    

    long x_2 = x[(xOff + 2)] & 0xFFFFFFFF;
    long zz_3 = zz[(zzOff + 3)] & 0xFFFFFFFF;
    long zz_4 = zz[(zzOff + 4)] & 0xFFFFFFFF;
    
    zz_2 += x_2 * x_0;
    w = (int)zz_2;
    zz[(zzOff + 2)] = (w << 1 | c);
    c = w >>> 31;
    zz_3 += (zz_2 >>> 32) + x_2 * x_1;
    zz_4 += (zz_3 >>> 32);
    zz_3 &= 0xFFFFFFFF;
    

    long x_3 = x[(xOff + 3)] & 0xFFFFFFFF;
    long zz_5 = (zz[(zzOff + 5)] & 0xFFFFFFFF) + (zz_4 >>> 32);zz_4 &= 0xFFFFFFFF;
    long zz_6 = (zz[(zzOff + 6)] & 0xFFFFFFFF) + (zz_5 >>> 32);zz_5 &= 0xFFFFFFFF;
    
    zz_3 += x_3 * x_0;
    w = (int)zz_3;
    zz[(zzOff + 3)] = (w << 1 | c);
    c = w >>> 31;
    zz_4 += (zz_3 >>> 32) + x_3 * x_1;
    zz_5 += (zz_4 >>> 32) + x_3 * x_2;
    zz_4 &= 0xFFFFFFFF;
    zz_6 += (zz_5 >>> 32);
    zz_5 &= 0xFFFFFFFF;
    

    long x_4 = x[(xOff + 4)] & 0xFFFFFFFF;
    long zz_7 = (zz[(zzOff + 7)] & 0xFFFFFFFF) + (zz_6 >>> 32);zz_6 &= 0xFFFFFFFF;
    long zz_8 = (zz[(zzOff + 8)] & 0xFFFFFFFF) + (zz_7 >>> 32);zz_7 &= 0xFFFFFFFF;
    
    zz_4 += x_4 * x_0;
    w = (int)zz_4;
    zz[(zzOff + 4)] = (w << 1 | c);
    c = w >>> 31;
    zz_5 += (zz_4 >>> 32) + x_4 * x_1;
    zz_6 += (zz_5 >>> 32) + x_4 * x_2;
    zz_5 &= 0xFFFFFFFF;
    zz_7 += (zz_6 >>> 32) + x_4 * x_3;
    zz_6 &= 0xFFFFFFFF;
    zz_8 += (zz_7 >>> 32);
    zz_7 &= 0xFFFFFFFF;
    

    long x_5 = x[(xOff + 5)] & 0xFFFFFFFF;
    long zz_9 = (zz[(zzOff + 9)] & 0xFFFFFFFF) + (zz_8 >>> 32);zz_8 &= 0xFFFFFFFF;
    long zz_10 = (zz[(zzOff + 10)] & 0xFFFFFFFF) + (zz_9 >>> 32);zz_9 &= 0xFFFFFFFF;
    
    zz_5 += x_5 * x_0;
    w = (int)zz_5;
    zz[(zzOff + 5)] = (w << 1 | c);
    c = w >>> 31;
    zz_6 += (zz_5 >>> 32) + x_5 * x_1;
    zz_7 += (zz_6 >>> 32) + x_5 * x_2;
    zz_6 &= 0xFFFFFFFF;
    zz_8 += (zz_7 >>> 32) + x_5 * x_3;
    zz_7 &= 0xFFFFFFFF;
    zz_9 += (zz_8 >>> 32) + x_5 * x_4;
    zz_8 &= 0xFFFFFFFF;
    zz_10 += (zz_9 >>> 32);
    zz_9 &= 0xFFFFFFFF;
    

    long x_6 = x[(xOff + 6)] & 0xFFFFFFFF;
    long zz_11 = (zz[(zzOff + 11)] & 0xFFFFFFFF) + (zz_10 >>> 32);zz_10 &= 0xFFFFFFFF;
    long zz_12 = (zz[(zzOff + 12)] & 0xFFFFFFFF) + (zz_11 >>> 32);zz_11 &= 0xFFFFFFFF;
    
    zz_6 += x_6 * x_0;
    w = (int)zz_6;
    zz[(zzOff + 6)] = (w << 1 | c);
    c = w >>> 31;
    zz_7 += (zz_6 >>> 32) + x_6 * x_1;
    zz_8 += (zz_7 >>> 32) + x_6 * x_2;
    zz_9 += (zz_8 >>> 32) + x_6 * x_3;
    zz_10 += (zz_9 >>> 32) + x_6 * x_4;
    zz_11 += (zz_10 >>> 32) + x_6 * x_5;
    zz_12 += (zz_11 >>> 32);
    

    w = (int)zz_7;
    zz[(zzOff + 7)] = (w << 1 | c);
    c = w >>> 31;
    w = (int)zz_8;
    zz[(zzOff + 8)] = (w << 1 | c);
    c = w >>> 31;
    w = (int)zz_9;
    zz[(zzOff + 9)] = (w << 1 | c);
    c = w >>> 31;
    w = (int)zz_10;
    zz[(zzOff + 10)] = (w << 1 | c);
    c = w >>> 31;
    w = (int)zz_11;
    zz[(zzOff + 11)] = (w << 1 | c);
    c = w >>> 31;
    w = (int)zz_12;
    zz[(zzOff + 12)] = (w << 1 | c);
    c = w >>> 31;
    w = zz[(zzOff + 13)] + (int)(zz_12 >>> 32);
    zz[(zzOff + 13)] = (w << 1 | c);
  }
  
  public static int sub(int[] x, int[] y, int[] z)
  {
    long c = 0L;
    c += (x[0] & 0xFFFFFFFF) - (y[0] & 0xFFFFFFFF);
    z[0] = ((int)c);
    c >>= 32;
    c += (x[1] & 0xFFFFFFFF) - (y[1] & 0xFFFFFFFF);
    z[1] = ((int)c);
    c >>= 32;
    c += (x[2] & 0xFFFFFFFF) - (y[2] & 0xFFFFFFFF);
    z[2] = ((int)c);
    c >>= 32;
    c += (x[3] & 0xFFFFFFFF) - (y[3] & 0xFFFFFFFF);
    z[3] = ((int)c);
    c >>= 32;
    c += (x[4] & 0xFFFFFFFF) - (y[4] & 0xFFFFFFFF);
    z[4] = ((int)c);
    c >>= 32;
    c += (x[5] & 0xFFFFFFFF) - (y[5] & 0xFFFFFFFF);
    z[5] = ((int)c);
    c >>= 32;
    c += (x[6] & 0xFFFFFFFF) - (y[6] & 0xFFFFFFFF);
    z[6] = ((int)c);
    c >>= 32;
    return (int)c;
  }
  
  public static int sub(int[] x, int xOff, int[] y, int yOff, int[] z, int zOff)
  {
    long c = 0L;
    c += (x[(xOff + 0)] & 0xFFFFFFFF) - (y[(yOff + 0)] & 0xFFFFFFFF);
    z[(zOff + 0)] = ((int)c);
    c >>= 32;
    c += (x[(xOff + 1)] & 0xFFFFFFFF) - (y[(yOff + 1)] & 0xFFFFFFFF);
    z[(zOff + 1)] = ((int)c);
    c >>= 32;
    c += (x[(xOff + 2)] & 0xFFFFFFFF) - (y[(yOff + 2)] & 0xFFFFFFFF);
    z[(zOff + 2)] = ((int)c);
    c >>= 32;
    c += (x[(xOff + 3)] & 0xFFFFFFFF) - (y[(yOff + 3)] & 0xFFFFFFFF);
    z[(zOff + 3)] = ((int)c);
    c >>= 32;
    c += (x[(xOff + 4)] & 0xFFFFFFFF) - (y[(yOff + 4)] & 0xFFFFFFFF);
    z[(zOff + 4)] = ((int)c);
    c >>= 32;
    c += (x[(xOff + 5)] & 0xFFFFFFFF) - (y[(yOff + 5)] & 0xFFFFFFFF);
    z[(zOff + 5)] = ((int)c);
    c >>= 32;
    c += (x[(xOff + 6)] & 0xFFFFFFFF) - (y[(yOff + 6)] & 0xFFFFFFFF);
    z[(zOff + 6)] = ((int)c);
    c >>= 32;
    return (int)c;
  }
  
  public static int subBothFrom(int[] x, int[] y, int[] z)
  {
    long c = 0L;
    c += (z[0] & 0xFFFFFFFF) - (x[0] & 0xFFFFFFFF) - (y[0] & 0xFFFFFFFF);
    z[0] = ((int)c);
    c >>= 32;
    c += (z[1] & 0xFFFFFFFF) - (x[1] & 0xFFFFFFFF) - (y[1] & 0xFFFFFFFF);
    z[1] = ((int)c);
    c >>= 32;
    c += (z[2] & 0xFFFFFFFF) - (x[2] & 0xFFFFFFFF) - (y[2] & 0xFFFFFFFF);
    z[2] = ((int)c);
    c >>= 32;
    c += (z[3] & 0xFFFFFFFF) - (x[3] & 0xFFFFFFFF) - (y[3] & 0xFFFFFFFF);
    z[3] = ((int)c);
    c >>= 32;
    c += (z[4] & 0xFFFFFFFF) - (x[4] & 0xFFFFFFFF) - (y[4] & 0xFFFFFFFF);
    z[4] = ((int)c);
    c >>= 32;
    c += (z[5] & 0xFFFFFFFF) - (x[5] & 0xFFFFFFFF) - (y[5] & 0xFFFFFFFF);
    z[5] = ((int)c);
    c >>= 32;
    c += (z[6] & 0xFFFFFFFF) - (x[6] & 0xFFFFFFFF) - (y[6] & 0xFFFFFFFF);
    z[6] = ((int)c);
    c >>= 32;
    return (int)c;
  }
  
  public static int subFrom(int[] x, int[] z)
  {
    long c = 0L;
    c += (z[0] & 0xFFFFFFFF) - (x[0] & 0xFFFFFFFF);
    z[0] = ((int)c);
    c >>= 32;
    c += (z[1] & 0xFFFFFFFF) - (x[1] & 0xFFFFFFFF);
    z[1] = ((int)c);
    c >>= 32;
    c += (z[2] & 0xFFFFFFFF) - (x[2] & 0xFFFFFFFF);
    z[2] = ((int)c);
    c >>= 32;
    c += (z[3] & 0xFFFFFFFF) - (x[3] & 0xFFFFFFFF);
    z[3] = ((int)c);
    c >>= 32;
    c += (z[4] & 0xFFFFFFFF) - (x[4] & 0xFFFFFFFF);
    z[4] = ((int)c);
    c >>= 32;
    c += (z[5] & 0xFFFFFFFF) - (x[5] & 0xFFFFFFFF);
    z[5] = ((int)c);
    c >>= 32;
    c += (z[6] & 0xFFFFFFFF) - (x[6] & 0xFFFFFFFF);
    z[6] = ((int)c);
    c >>= 32;
    return (int)c;
  }
  
  public static int subFrom(int[] x, int xOff, int[] z, int zOff)
  {
    long c = 0L;
    c += (z[(zOff + 0)] & 0xFFFFFFFF) - (x[(xOff + 0)] & 0xFFFFFFFF);
    z[(zOff + 0)] = ((int)c);
    c >>= 32;
    c += (z[(zOff + 1)] & 0xFFFFFFFF) - (x[(xOff + 1)] & 0xFFFFFFFF);
    z[(zOff + 1)] = ((int)c);
    c >>= 32;
    c += (z[(zOff + 2)] & 0xFFFFFFFF) - (x[(xOff + 2)] & 0xFFFFFFFF);
    z[(zOff + 2)] = ((int)c);
    c >>= 32;
    c += (z[(zOff + 3)] & 0xFFFFFFFF) - (x[(xOff + 3)] & 0xFFFFFFFF);
    z[(zOff + 3)] = ((int)c);
    c >>= 32;
    c += (z[(zOff + 4)] & 0xFFFFFFFF) - (x[(xOff + 4)] & 0xFFFFFFFF);
    z[(zOff + 4)] = ((int)c);
    c >>= 32;
    c += (z[(zOff + 5)] & 0xFFFFFFFF) - (x[(xOff + 5)] & 0xFFFFFFFF);
    z[(zOff + 5)] = ((int)c);
    c >>= 32;
    c += (z[(zOff + 6)] & 0xFFFFFFFF) - (x[(xOff + 6)] & 0xFFFFFFFF);
    z[(zOff + 6)] = ((int)c);
    c >>= 32;
    return (int)c;
  }
  
  public static BigInteger toBigInteger(int[] x)
  {
    byte[] bs = new byte[28];
    for (int i = 0; i < 7; i++)
    {
      int x_i = x[i];
      if (x_i != 0)
      {
        org.spongycastle.util.Pack.intToBigEndian(x_i, bs, 6 - i << 2);
      }
    }
    return new BigInteger(1, bs);
  }
  
  public static void zero(int[] z)
  {
    z[0] = 0;
    z[1] = 0;
    z[2] = 0;
    z[3] = 0;
    z[4] = 0;
    z[5] = 0;
    z[6] = 0;
  }
}
