package org.spongycastle.math.ec.custom.djb;

import java.math.BigInteger;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat256;




public class Curve25519Field
{
  private static final long M = 4294967295L;
  static final int[] P = { -19, -1, -1, -1, -1, -1, -1, Integer.MAX_VALUE };
  
  private static final int P7 = Integer.MAX_VALUE;
  private static final int[] PExt = { 361, 0, 0, 0, 0, 0, 0, 0, -19, -1, -1, -1, -1, -1, -1, 1073741823 };
  private static final int PInv = 19;
  
  public Curve25519Field() {}
  
  public static void add(int[] x, int[] y, int[] z)
  {
    Nat256.add(x, y, z);
    if (Nat256.gte(z, P))
    {
      subPFrom(z);
    }
  }
  
  public static void addExt(int[] xx, int[] yy, int[] zz)
  {
    Nat.add(16, xx, yy, zz);
    if (Nat.gte(16, zz, PExt))
    {
      subPExtFrom(zz);
    }
  }
  
  public static void addOne(int[] x, int[] z)
  {
    Nat.inc(8, x, z);
    if (Nat256.gte(z, P))
    {
      subPFrom(z);
    }
  }
  
  public static int[] fromBigInteger(BigInteger x)
  {
    int[] z = Nat256.fromBigInteger(x);
    while (Nat256.gte(z, P))
    {
      Nat256.subFrom(P, z);
    }
    return z;
  }
  
  public static void half(int[] x, int[] z)
  {
    if ((x[0] & 0x1) == 0)
    {
      Nat.shiftDownBit(8, x, 0, z);
    }
    else
    {
      Nat256.add(x, P, z);
      Nat.shiftDownBit(8, z, 0);
    }
  }
  
  public static void multiply(int[] x, int[] y, int[] z)
  {
    int[] tt = Nat256.createExt();
    Nat256.mul(x, y, tt);
    reduce(tt, z);
  }
  
  public static void multiplyAddToExt(int[] x, int[] y, int[] zz)
  {
    Nat256.mulAddTo(x, y, zz);
    if (Nat.gte(16, zz, PExt))
    {
      subPExtFrom(zz);
    }
  }
  
  public static void negate(int[] x, int[] z)
  {
    if (Nat256.isZero(x))
    {
      Nat256.zero(z);
    }
    else
    {
      Nat256.sub(P, x, z);
    }
  }
  


  public static void reduce(int[] xx, int[] z)
  {
    int xx07 = xx[7];
    Nat.shiftUpBit(8, xx, 8, xx07, z, 0);
    int c = Nat256.mulByWordAddTo(19, xx, z) << 1;
    int z7 = z[7];
    c += (z7 >>> 31) - (xx07 >>> 31);
    z7 &= 0x7FFFFFFF;
    z7 += Nat.addWordTo(7, c * 19, z);
    z[7] = z7;
    if (Nat256.gte(z, P))
    {
      subPFrom(z);
    }
  }
  


  public static void reduce27(int x, int[] z)
  {
    int z7 = z[7];
    int c = x << 1 | z7 >>> 31;
    z7 &= 0x7FFFFFFF;
    z7 += Nat.addWordTo(7, c * 19, z);
    z[7] = z7;
    if (Nat256.gte(z, P))
    {
      subPFrom(z);
    }
  }
  
  public static void square(int[] x, int[] z)
  {
    int[] tt = Nat256.createExt();
    Nat256.square(x, tt);
    reduce(tt, z);
  }
  


  public static void squareN(int[] x, int n, int[] z)
  {
    int[] tt = Nat256.createExt();
    Nat256.square(x, tt);
    reduce(tt, z);
    for (;;) {
      n--; if (n <= 0)
        break;
      Nat256.square(z, tt);
      reduce(tt, z);
    }
  }
  
  public static void subtract(int[] x, int[] y, int[] z)
  {
    int c = Nat256.sub(x, y, z);
    if (c != 0)
    {
      addPTo(z);
    }
  }
  
  public static void subtractExt(int[] xx, int[] yy, int[] zz)
  {
    int c = Nat.sub(16, xx, yy, zz);
    if (c != 0)
    {
      addPExtTo(zz);
    }
  }
  
  public static void twice(int[] x, int[] z)
  {
    Nat.shiftUpBit(8, x, 0, z);
    if (Nat256.gte(z, P))
    {
      subPFrom(z);
    }
  }
  
  private static int addPTo(int[] z)
  {
    long c = (z[0] & 0xFFFFFFFF) - 19L;
    z[0] = ((int)c);
    c >>= 32;
    if (c != 0L)
    {
      c = Nat.decAt(7, z, 1);
    }
    c += (z[7] & 0xFFFFFFFF) + 2147483648L;
    z[7] = ((int)c);
    c >>= 32;
    return (int)c;
  }
  
  private static int addPExtTo(int[] zz)
  {
    long c = (zz[0] & 0xFFFFFFFF) + (PExt[0] & 0xFFFFFFFF);
    zz[0] = ((int)c);
    c >>= 32;
    if (c != 0L)
    {
      c = Nat.incAt(8, zz, 1);
    }
    c += (zz[8] & 0xFFFFFFFF) - 19L;
    zz[8] = ((int)c);
    c >>= 32;
    if (c != 0L)
    {
      c = Nat.decAt(15, zz, 9);
    }
    c += (zz[15] & 0xFFFFFFFF) + (PExt[15] + 1 & 0xFFFFFFFF);
    zz[15] = ((int)c);
    c >>= 32;
    return (int)c;
  }
  
  private static int subPFrom(int[] z)
  {
    long c = (z[0] & 0xFFFFFFFF) + 19L;
    z[0] = ((int)c);
    c >>= 32;
    if (c != 0L)
    {
      c = Nat.incAt(7, z, 1);
    }
    c += (z[7] & 0xFFFFFFFF) - 2147483648L;
    z[7] = ((int)c);
    c >>= 32;
    return (int)c;
  }
  
  private static int subPExtFrom(int[] zz)
  {
    long c = (zz[0] & 0xFFFFFFFF) - (PExt[0] & 0xFFFFFFFF);
    zz[0] = ((int)c);
    c >>= 32;
    if (c != 0L)
    {
      c = Nat.decAt(8, zz, 1);
    }
    c += (zz[8] & 0xFFFFFFFF) + 19L;
    zz[8] = ((int)c);
    c >>= 32;
    if (c != 0L)
    {
      c = Nat.incAt(15, zz, 9);
    }
    c += (zz[15] & 0xFFFFFFFF) - (PExt[15] + 1 & 0xFFFFFFFF);
    zz[15] = ((int)c);
    c >>= 32;
    return (int)c;
  }
}
