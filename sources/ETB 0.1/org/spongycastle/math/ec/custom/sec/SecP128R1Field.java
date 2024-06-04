package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat128;
import org.spongycastle.math.raw.Nat256;




public class SecP128R1Field
{
  private static final long M = 4294967295L;
  static final int[] P = { -1, -1, -1, -3 };
  static final int[] PExt = { 1, 0, 0, 4, -2, -1, 3, -4 };
  
  private static final int[] PExtInv = { -1, -1, -1, -5, 1, 0, -4, 3 };
  private static final int P3s1 = 2147483646;
  private static final int PExt7s1 = 2147483646;
  
  public SecP128R1Field() {}
  
  public static void add(int[] x, int[] y, int[] z) {
    int c = Nat128.add(x, y, z);
    if ((c != 0) || ((z[3] >>> 1 >= 2147483646) && (Nat128.gte(z, P))))
    {
      addPInvTo(z);
    }
  }
  
  public static void addExt(int[] xx, int[] yy, int[] zz)
  {
    int c = Nat256.add(xx, yy, zz);
    if ((c != 0) || ((zz[7] >>> 1 >= 2147483646) && (Nat256.gte(zz, PExt))))
    {
      Nat.addTo(PExtInv.length, PExtInv, zz);
    }
  }
  
  public static void addOne(int[] x, int[] z)
  {
    int c = Nat.inc(4, x, z);
    if ((c != 0) || ((z[3] >>> 1 >= 2147483646) && (Nat128.gte(z, P))))
    {
      addPInvTo(z);
    }
  }
  
  public static int[] fromBigInteger(BigInteger x)
  {
    int[] z = Nat128.fromBigInteger(x);
    if ((z[3] >>> 1 >= 2147483646) && (Nat128.gte(z, P)))
    {
      Nat128.subFrom(P, z);
    }
    return z;
  }
  
  public static void half(int[] x, int[] z)
  {
    if ((x[0] & 0x1) == 0)
    {
      Nat.shiftDownBit(4, x, 0, z);
    }
    else
    {
      int c = Nat128.add(x, P, z);
      Nat.shiftDownBit(4, z, c);
    }
  }
  
  public static void multiply(int[] x, int[] y, int[] z)
  {
    int[] tt = Nat128.createExt();
    Nat128.mul(x, y, tt);
    reduce(tt, z);
  }
  
  public static void multiplyAddToExt(int[] x, int[] y, int[] zz)
  {
    int c = Nat128.mulAddTo(x, y, zz);
    if ((c != 0) || ((zz[7] >>> 1 >= 2147483646) && (Nat256.gte(zz, PExt))))
    {
      Nat.addTo(PExtInv.length, PExtInv, zz);
    }
  }
  
  public static void negate(int[] x, int[] z)
  {
    if (Nat128.isZero(x))
    {
      Nat128.zero(z);
    }
    else
    {
      Nat128.sub(P, x, z);
    }
  }
  
  public static void reduce(int[] xx, int[] z)
  {
    long x0 = xx[0] & 0xFFFFFFFF;long x1 = xx[1] & 0xFFFFFFFF;long x2 = xx[2] & 0xFFFFFFFF;long x3 = xx[3] & 0xFFFFFFFF;
    long x4 = xx[4] & 0xFFFFFFFF;long x5 = xx[5] & 0xFFFFFFFF;long x6 = xx[6] & 0xFFFFFFFF;long x7 = xx[7] & 0xFFFFFFFF;
    
    x3 += x7;x6 += (x7 << 1);
    x2 += x6;x5 += (x6 << 1);
    x1 += x5;x4 += (x5 << 1);
    x0 += x4;x3 += (x4 << 1);
    
    z[0] = ((int)x0);x1 += (x0 >>> 32);
    z[1] = ((int)x1);x2 += (x1 >>> 32);
    z[2] = ((int)x2);x3 += (x2 >>> 32);
    z[3] = ((int)x3);
    
    reduce32((int)(x3 >>> 32), z);
  }
  
  public static void reduce32(int x, int[] z)
  {
    while (x != 0)
    {
      long x4 = x & 0xFFFFFFFF;
      
      long c = (z[0] & 0xFFFFFFFF) + x4;
      z[0] = ((int)c);c >>= 32;
      if (c != 0L)
      {
        c += (z[1] & 0xFFFFFFFF);
        z[1] = ((int)c);c >>= 32;
        c += (z[2] & 0xFFFFFFFF);
        z[2] = ((int)c);c >>= 32;
      }
      c += (z[3] & 0xFFFFFFFF) + (x4 << 1);
      z[3] = ((int)c);c >>= 32;
      


      x = (int)c;
    }
  }
  
  public static void square(int[] x, int[] z)
  {
    int[] tt = Nat128.createExt();
    Nat128.square(x, tt);
    reduce(tt, z);
  }
  


  public static void squareN(int[] x, int n, int[] z)
  {
    int[] tt = Nat128.createExt();
    Nat128.square(x, tt);
    reduce(tt, z);
    for (;;) {
      n--; if (n <= 0)
        break;
      Nat128.square(z, tt);
      reduce(tt, z);
    }
  }
  
  public static void subtract(int[] x, int[] y, int[] z)
  {
    int c = Nat128.sub(x, y, z);
    if (c != 0)
    {
      subPInvFrom(z);
    }
  }
  
  public static void subtractExt(int[] xx, int[] yy, int[] zz)
  {
    int c = Nat.sub(10, xx, yy, zz);
    if (c != 0)
    {
      Nat.subFrom(PExtInv.length, PExtInv, zz);
    }
  }
  
  public static void twice(int[] x, int[] z)
  {
    int c = Nat.shiftUpBit(4, x, 0, z);
    if ((c != 0) || ((z[3] >>> 1 >= 2147483646) && (Nat128.gte(z, P))))
    {
      addPInvTo(z);
    }
  }
  
  private static void addPInvTo(int[] z)
  {
    long c = (z[0] & 0xFFFFFFFF) + 1L;
    z[0] = ((int)c);c >>= 32;
    if (c != 0L)
    {
      c += (z[1] & 0xFFFFFFFF);
      z[1] = ((int)c);c >>= 32;
      c += (z[2] & 0xFFFFFFFF);
      z[2] = ((int)c);c >>= 32;
    }
    c += (z[3] & 0xFFFFFFFF) + 2L;
    z[3] = ((int)c);
  }
  
  private static void subPInvFrom(int[] z)
  {
    long c = (z[0] & 0xFFFFFFFF) - 1L;
    z[0] = ((int)c);c >>= 32;
    if (c != 0L)
    {
      c += (z[1] & 0xFFFFFFFF);
      z[1] = ((int)c);c >>= 32;
      c += (z[2] & 0xFFFFFFFF);
      z[2] = ((int)c);c >>= 32;
    }
    c += (z[3] & 0xFFFFFFFF) - 2L;
    z[3] = ((int)c);
  }
}
