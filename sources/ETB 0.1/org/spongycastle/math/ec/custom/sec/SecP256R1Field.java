package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat256;




public class SecP256R1Field
{
  private static final long M = 4294967295L;
  static final int[] P = { -1, -1, -1, 0, 0, 0, 1, -1 };
  
  static final int[] PExt = { 1, 0, 0, -2, -1, -1, -2, 1, -2, 1, -2, 1, 1, -2, 2, -2 };
  private static final int P7 = -1;
  private static final int PExt15s1 = Integer.MAX_VALUE;
  
  public SecP256R1Field() {}
  
  public static void add(int[] x, int[] y, int[] z)
  {
    int c = Nat256.add(x, y, z);
    if ((c != 0) || ((z[7] == -1) && (Nat256.gte(z, P))))
    {
      addPInvTo(z);
    }
  }
  
  public static void addExt(int[] xx, int[] yy, int[] zz)
  {
    int c = Nat.add(16, xx, yy, zz);
    if ((c != 0) || ((zz[15] >>> 1 >= Integer.MAX_VALUE) && (Nat.gte(16, zz, PExt))))
    {
      Nat.subFrom(16, PExt, zz);
    }
  }
  
  public static void addOne(int[] x, int[] z)
  {
    int c = Nat.inc(8, x, z);
    if ((c != 0) || ((z[7] == -1) && (Nat256.gte(z, P))))
    {
      addPInvTo(z);
    }
  }
  
  public static int[] fromBigInteger(BigInteger x)
  {
    int[] z = Nat256.fromBigInteger(x);
    if ((z[7] == -1) && (Nat256.gte(z, P)))
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
      int c = Nat256.add(x, P, z);
      Nat.shiftDownBit(8, z, c);
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
    int c = Nat256.mulAddTo(x, y, zz);
    if ((c != 0) || ((zz[15] >>> 1 >= Integer.MAX_VALUE) && (Nat.gte(16, zz, PExt))))
    {
      Nat.subFrom(16, PExt, zz);
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
    long xx08 = xx[8] & 0xFFFFFFFF;long xx09 = xx[9] & 0xFFFFFFFF;long xx10 = xx[10] & 0xFFFFFFFF;long xx11 = xx[11] & 0xFFFFFFFF;
    long xx12 = xx[12] & 0xFFFFFFFF;long xx13 = xx[13] & 0xFFFFFFFF;long xx14 = xx[14] & 0xFFFFFFFF;long xx15 = xx[15] & 0xFFFFFFFF;
    
    long n = 6L;
    
    xx08 -= 6L;
    
    long t0 = xx08 + xx09;
    long t1 = xx09 + xx10;
    long t2 = xx10 + xx11 - xx15;
    long t3 = xx11 + xx12;
    long t4 = xx12 + xx13;
    long t5 = xx13 + xx14;
    long t6 = xx14 + xx15;
    long t7 = t5 - t0;
    
    long cc = 0L;
    cc += (xx[0] & 0xFFFFFFFF) - t3 - t7;
    z[0] = ((int)cc);
    cc >>= 32;
    cc += (xx[1] & 0xFFFFFFFF) + t1 - t4 - t6;
    z[1] = ((int)cc);
    cc >>= 32;
    cc += (xx[2] & 0xFFFFFFFF) + t2 - t5;
    z[2] = ((int)cc);
    cc >>= 32;
    cc += (xx[3] & 0xFFFFFFFF) + (t3 << 1) + t7 - t6;
    z[3] = ((int)cc);
    cc >>= 32;
    cc += (xx[4] & 0xFFFFFFFF) + (t4 << 1) + xx14 - t1;
    z[4] = ((int)cc);
    cc >>= 32;
    cc += (xx[5] & 0xFFFFFFFF) + (t5 << 1) - t2;
    z[5] = ((int)cc);
    cc >>= 32;
    cc += (xx[6] & 0xFFFFFFFF) + (t6 << 1) + t7;
    z[6] = ((int)cc);
    cc >>= 32;
    cc += (xx[7] & 0xFFFFFFFF) + (xx15 << 1) + xx08 - t2 - t4;
    z[7] = ((int)cc);
    cc >>= 32;
    cc += 6L;
    


    reduce32((int)cc, z);
  }
  
  public static void reduce32(int x, int[] z)
  {
    long cc = 0L;
    
    if (x != 0)
    {
      long xx08 = x & 0xFFFFFFFF;
      
      cc += (z[0] & 0xFFFFFFFF) + xx08;
      z[0] = ((int)cc);
      cc >>= 32;
      if (cc != 0L)
      {
        cc += (z[1] & 0xFFFFFFFF);
        z[1] = ((int)cc);
        cc >>= 32;
        cc += (z[2] & 0xFFFFFFFF);
        z[2] = ((int)cc);
        cc >>= 32;
      }
      cc += (z[3] & 0xFFFFFFFF) - xx08;
      z[3] = ((int)cc);
      cc >>= 32;
      if (cc != 0L)
      {
        cc += (z[4] & 0xFFFFFFFF);
        z[4] = ((int)cc);
        cc >>= 32;
        cc += (z[5] & 0xFFFFFFFF);
        z[5] = ((int)cc);
        cc >>= 32;
      }
      cc += (z[6] & 0xFFFFFFFF) - xx08;
      z[6] = ((int)cc);
      cc >>= 32;
      cc += (z[7] & 0xFFFFFFFF) + xx08;
      z[7] = ((int)cc);
      cc >>= 32;
    }
    


    if ((cc != 0L) || ((z[7] == -1) && (Nat256.gte(z, P))))
    {
      addPInvTo(z);
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
      subPInvFrom(z);
    }
  }
  
  public static void subtractExt(int[] xx, int[] yy, int[] zz)
  {
    int c = Nat.sub(16, xx, yy, zz);
    if (c != 0)
    {
      Nat.addTo(16, PExt, zz);
    }
  }
  
  public static void twice(int[] x, int[] z)
  {
    int c = Nat.shiftUpBit(8, x, 0, z);
    if ((c != 0) || ((z[7] == -1) && (Nat256.gte(z, P))))
    {
      addPInvTo(z);
    }
  }
  
  private static void addPInvTo(int[] z)
  {
    long c = (z[0] & 0xFFFFFFFF) + 1L;
    z[0] = ((int)c);
    c >>= 32;
    if (c != 0L)
    {
      c += (z[1] & 0xFFFFFFFF);
      z[1] = ((int)c);
      c >>= 32;
      c += (z[2] & 0xFFFFFFFF);
      z[2] = ((int)c);
      c >>= 32;
    }
    c += (z[3] & 0xFFFFFFFF) - 1L;
    z[3] = ((int)c);
    c >>= 32;
    if (c != 0L)
    {
      c += (z[4] & 0xFFFFFFFF);
      z[4] = ((int)c);
      c >>= 32;
      c += (z[5] & 0xFFFFFFFF);
      z[5] = ((int)c);
      c >>= 32;
    }
    c += (z[6] & 0xFFFFFFFF) - 1L;
    z[6] = ((int)c);
    c >>= 32;
    c += (z[7] & 0xFFFFFFFF) + 1L;
    z[7] = ((int)c);
  }
  

  private static void subPInvFrom(int[] z)
  {
    long c = (z[0] & 0xFFFFFFFF) - 1L;
    z[0] = ((int)c);
    c >>= 32;
    if (c != 0L)
    {
      c += (z[1] & 0xFFFFFFFF);
      z[1] = ((int)c);
      c >>= 32;
      c += (z[2] & 0xFFFFFFFF);
      z[2] = ((int)c);
      c >>= 32;
    }
    c += (z[3] & 0xFFFFFFFF) + 1L;
    z[3] = ((int)c);
    c >>= 32;
    if (c != 0L)
    {
      c += (z[4] & 0xFFFFFFFF);
      z[4] = ((int)c);
      c >>= 32;
      c += (z[5] & 0xFFFFFFFF);
      z[5] = ((int)c);
      c >>= 32;
    }
    c += (z[6] & 0xFFFFFFFF) + 1L;
    z[6] = ((int)c);
    c >>= 32;
    c += (z[7] & 0xFFFFFFFF) - 1L;
    z[7] = ((int)c);
  }
}
