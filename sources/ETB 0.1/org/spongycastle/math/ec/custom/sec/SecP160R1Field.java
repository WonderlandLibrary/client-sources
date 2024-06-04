package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat160;




public class SecP160R1Field
{
  private static final long M = 4294967295L;
  static final int[] P = { Integer.MAX_VALUE, -1, -1, -1, -1 };
  static final int[] PExt = { 1, 1073741825, 0, 0, 0, -2, -2, -1, -1, -1 };
  
  private static final int[] PExtInv = { -1, -1073741826, -1, -1, -1, 1, 1 };
  private static final int P4 = -1;
  private static final int PExt9 = -1;
  private static final int PInv = -2147483647;
  
  public SecP160R1Field() {}
  
  public static void add(int[] x, int[] y, int[] z) {
    int c = Nat160.add(x, y, z);
    if ((c != 0) || ((z[4] == -1) && (Nat160.gte(z, P))))
    {
      Nat.addWordTo(5, -2147483647, z);
    }
  }
  
  public static void addExt(int[] xx, int[] yy, int[] zz)
  {
    int c = Nat.add(10, xx, yy, zz);
    if ((c != 0) || ((zz[9] == -1) && (Nat.gte(10, zz, PExt))))
    {
      if (Nat.addTo(PExtInv.length, PExtInv, zz) != 0)
      {
        Nat.incAt(10, zz, PExtInv.length);
      }
    }
  }
  
  public static void addOne(int[] x, int[] z)
  {
    int c = Nat.inc(5, x, z);
    if ((c != 0) || ((z[4] == -1) && (Nat160.gte(z, P))))
    {
      Nat.addWordTo(5, -2147483647, z);
    }
  }
  
  public static int[] fromBigInteger(BigInteger x)
  {
    int[] z = Nat160.fromBigInteger(x);
    if ((z[4] == -1) && (Nat160.gte(z, P)))
    {
      Nat160.subFrom(P, z);
    }
    return z;
  }
  
  public static void half(int[] x, int[] z)
  {
    if ((x[0] & 0x1) == 0)
    {
      Nat.shiftDownBit(5, x, 0, z);
    }
    else
    {
      int c = Nat160.add(x, P, z);
      Nat.shiftDownBit(5, z, c);
    }
  }
  
  public static void multiply(int[] x, int[] y, int[] z)
  {
    int[] tt = Nat160.createExt();
    Nat160.mul(x, y, tt);
    reduce(tt, z);
  }
  
  public static void multiplyAddToExt(int[] x, int[] y, int[] zz)
  {
    int c = Nat160.mulAddTo(x, y, zz);
    if ((c != 0) || ((zz[9] == -1) && (Nat.gte(10, zz, PExt))))
    {
      if (Nat.addTo(PExtInv.length, PExtInv, zz) != 0)
      {
        Nat.incAt(10, zz, PExtInv.length);
      }
    }
  }
  
  public static void negate(int[] x, int[] z)
  {
    if (Nat160.isZero(x))
    {
      Nat160.zero(z);
    }
    else
    {
      Nat160.sub(P, x, z);
    }
  }
  
  public static void reduce(int[] xx, int[] z)
  {
    long x5 = xx[5] & 0xFFFFFFFF;long x6 = xx[6] & 0xFFFFFFFF;long x7 = xx[7] & 0xFFFFFFFF;long x8 = xx[8] & 0xFFFFFFFF;long x9 = xx[9] & 0xFFFFFFFF;
    
    long c = 0L;
    c += (xx[0] & 0xFFFFFFFF) + x5 + (x5 << 31);
    z[0] = ((int)c);c >>>= 32;
    c += (xx[1] & 0xFFFFFFFF) + x6 + (x6 << 31);
    z[1] = ((int)c);c >>>= 32;
    c += (xx[2] & 0xFFFFFFFF) + x7 + (x7 << 31);
    z[2] = ((int)c);c >>>= 32;
    c += (xx[3] & 0xFFFFFFFF) + x8 + (x8 << 31);
    z[3] = ((int)c);c >>>= 32;
    c += (xx[4] & 0xFFFFFFFF) + x9 + (x9 << 31);
    z[4] = ((int)c);c >>>= 32;
    


    reduce32((int)c, z);
  }
  
  public static void reduce32(int x, int[] z)
  {
    if (((x != 0) && (Nat160.mulWordsAdd(-2147483647, x, z, 0) != 0)) || ((z[4] == -1) && 
      (Nat160.gte(z, P))))
    {
      Nat.addWordTo(5, -2147483647, z);
    }
  }
  
  public static void square(int[] x, int[] z)
  {
    int[] tt = Nat160.createExt();
    Nat160.square(x, tt);
    reduce(tt, z);
  }
  


  public static void squareN(int[] x, int n, int[] z)
  {
    int[] tt = Nat160.createExt();
    Nat160.square(x, tt);
    reduce(tt, z);
    for (;;) {
      n--; if (n <= 0)
        break;
      Nat160.square(z, tt);
      reduce(tt, z);
    }
  }
  
  public static void subtract(int[] x, int[] y, int[] z)
  {
    int c = Nat160.sub(x, y, z);
    if (c != 0)
    {
      Nat.subWordFrom(5, -2147483647, z);
    }
  }
  
  public static void subtractExt(int[] xx, int[] yy, int[] zz)
  {
    int c = Nat.sub(10, xx, yy, zz);
    if (c != 0)
    {
      if (Nat.subFrom(PExtInv.length, PExtInv, zz) != 0)
      {
        Nat.decAt(10, zz, PExtInv.length);
      }
    }
  }
  
  public static void twice(int[] x, int[] z)
  {
    int c = Nat.shiftUpBit(5, x, 0, z);
    if ((c != 0) || ((z[4] == -1) && (Nat160.gte(z, P))))
    {
      Nat.addWordTo(5, -2147483647, z);
    }
  }
}
