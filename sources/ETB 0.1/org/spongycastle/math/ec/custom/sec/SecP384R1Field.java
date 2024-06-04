package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat384;




public class SecP384R1Field
{
  private static final long M = 4294967295L;
  static final int[] P = { -1, 0, 0, -1, -2, -1, -1, -1, -1, -1, -1, -1 };
  
  static final int[] PExt = { 1, -2, 0, 2, 0, -2, 0, 2, 1, 0, 0, 0, -2, 1, 0, -2, -3, -1, -1, -1, -1, -1, -1, -1 };
  

  private static final int[] PExtInv = { -1, 1, -1, -3, -1, 1, -1, -3, -2, -1, -1, -1, 1, -2, -1, 1, 2 };
  private static final int P11 = -1;
  private static final int PExt23 = -1;
  
  public SecP384R1Field() {}
  
  public static void add(int[] x, int[] y, int[] z)
  {
    int c = Nat.add(12, x, y, z);
    if ((c != 0) || ((z[11] == -1) && (Nat.gte(12, z, P))))
    {
      addPInvTo(z);
    }
  }
  
  public static void addExt(int[] xx, int[] yy, int[] zz)
  {
    int c = Nat.add(24, xx, yy, zz);
    if ((c != 0) || ((zz[23] == -1) && (Nat.gte(24, zz, PExt))))
    {
      if (Nat.addTo(PExtInv.length, PExtInv, zz) != 0)
      {
        Nat.incAt(24, zz, PExtInv.length);
      }
    }
  }
  
  public static void addOne(int[] x, int[] z)
  {
    int c = Nat.inc(12, x, z);
    if ((c != 0) || ((z[11] == -1) && (Nat.gte(12, z, P))))
    {
      addPInvTo(z);
    }
  }
  
  public static int[] fromBigInteger(BigInteger x)
  {
    int[] z = Nat.fromBigInteger(384, x);
    if ((z[11] == -1) && (Nat.gte(12, z, P)))
    {
      Nat.subFrom(12, P, z);
    }
    return z;
  }
  
  public static void half(int[] x, int[] z)
  {
    if ((x[0] & 0x1) == 0)
    {
      Nat.shiftDownBit(12, x, 0, z);
    }
    else
    {
      int c = Nat.add(12, x, P, z);
      Nat.shiftDownBit(12, z, c);
    }
  }
  
  public static void multiply(int[] x, int[] y, int[] z)
  {
    int[] tt = Nat.create(24);
    Nat384.mul(x, y, tt);
    reduce(tt, z);
  }
  
  public static void negate(int[] x, int[] z)
  {
    if (Nat.isZero(12, x))
    {
      Nat.zero(12, z);
    }
    else
    {
      Nat.sub(12, P, x, z);
    }
  }
  
  public static void reduce(int[] xx, int[] z)
  {
    long xx16 = xx[16] & 0xFFFFFFFF;long xx17 = xx[17] & 0xFFFFFFFF;long xx18 = xx[18] & 0xFFFFFFFF;long xx19 = xx[19] & 0xFFFFFFFF;
    long xx20 = xx[20] & 0xFFFFFFFF;long xx21 = xx[21] & 0xFFFFFFFF;long xx22 = xx[22] & 0xFFFFFFFF;long xx23 = xx[23] & 0xFFFFFFFF;
    
    long n = 1L;
    
    long t0 = (xx[12] & 0xFFFFFFFF) + xx20 - 1L;
    long t1 = (xx[13] & 0xFFFFFFFF) + xx22;
    long t2 = (xx[14] & 0xFFFFFFFF) + xx22 + xx23;
    long t3 = (xx[15] & 0xFFFFFFFF) + xx23;
    long t4 = xx17 + xx21;
    long t5 = xx21 - xx23;
    long t6 = xx22 - xx23;
    long t7 = t0 + t5;
    
    long cc = 0L;
    cc += (xx[0] & 0xFFFFFFFF) + t7;
    z[0] = ((int)cc);
    cc >>= 32;
    cc += (xx[1] & 0xFFFFFFFF) + xx23 - t0 + t1;
    z[1] = ((int)cc);
    cc >>= 32;
    cc += (xx[2] & 0xFFFFFFFF) - xx21 - t1 + t2;
    z[2] = ((int)cc);
    cc >>= 32;
    cc += (xx[3] & 0xFFFFFFFF) - t2 + t3 + t7;
    z[3] = ((int)cc);
    cc >>= 32;
    cc += (xx[4] & 0xFFFFFFFF) + xx16 + xx21 + t1 - t3 + t7;
    z[4] = ((int)cc);
    cc >>= 32;
    cc += (xx[5] & 0xFFFFFFFF) - xx16 + t1 + t2 + t4;
    z[5] = ((int)cc);
    cc >>= 32;
    cc += (xx[6] & 0xFFFFFFFF) + xx18 - xx17 + t2 + t3;
    z[6] = ((int)cc);
    cc >>= 32;
    cc += (xx[7] & 0xFFFFFFFF) + xx16 + xx19 - xx18 + t3;
    z[7] = ((int)cc);
    cc >>= 32;
    cc += (xx[8] & 0xFFFFFFFF) + xx16 + xx17 + xx20 - xx19;
    z[8] = ((int)cc);
    cc >>= 32;
    cc += (xx[9] & 0xFFFFFFFF) + xx18 - xx20 + t4;
    z[9] = ((int)cc);
    cc >>= 32;
    cc += (xx[10] & 0xFFFFFFFF) + xx18 + xx19 - t5 + t6;
    z[10] = ((int)cc);
    cc >>= 32;
    cc += (xx[11] & 0xFFFFFFFF) + xx19 + xx20 - t6;
    z[11] = ((int)cc);
    cc >>= 32;
    cc += 1L;
    


    reduce32((int)cc, z);
  }
  
  public static void reduce32(int x, int[] z)
  {
    long cc = 0L;
    
    if (x != 0)
    {
      long xx12 = x & 0xFFFFFFFF;
      
      cc += (z[0] & 0xFFFFFFFF) + xx12;
      z[0] = ((int)cc);
      cc >>= 32;
      cc += (z[1] & 0xFFFFFFFF) - xx12;
      z[1] = ((int)cc);
      cc >>= 32;
      if (cc != 0L)
      {
        cc += (z[2] & 0xFFFFFFFF);
        z[2] = ((int)cc);
        cc >>= 32;
      }
      cc += (z[3] & 0xFFFFFFFF) + xx12;
      z[3] = ((int)cc);
      cc >>= 32;
      cc += (z[4] & 0xFFFFFFFF) + xx12;
      z[4] = ((int)cc);
      cc >>= 32;
    }
    


    if (((cc != 0L) && (Nat.incAt(12, z, 5) != 0)) || ((z[11] == -1) && 
      (Nat.gte(12, z, P))))
    {
      addPInvTo(z);
    }
  }
  
  public static void square(int[] x, int[] z)
  {
    int[] tt = Nat.create(24);
    Nat384.square(x, tt);
    reduce(tt, z);
  }
  


  public static void squareN(int[] x, int n, int[] z)
  {
    int[] tt = Nat.create(24);
    Nat384.square(x, tt);
    reduce(tt, z);
    for (;;) {
      n--; if (n <= 0)
        break;
      Nat384.square(z, tt);
      reduce(tt, z);
    }
  }
  
  public static void subtract(int[] x, int[] y, int[] z)
  {
    int c = Nat.sub(12, x, y, z);
    if (c != 0)
    {
      subPInvFrom(z);
    }
  }
  
  public static void subtractExt(int[] xx, int[] yy, int[] zz)
  {
    int c = Nat.sub(24, xx, yy, zz);
    if (c != 0)
    {
      if (Nat.subFrom(PExtInv.length, PExtInv, zz) != 0)
      {
        Nat.decAt(24, zz, PExtInv.length);
      }
    }
  }
  
  public static void twice(int[] x, int[] z)
  {
    int c = Nat.shiftUpBit(12, x, 0, z);
    if ((c != 0) || ((z[11] == -1) && (Nat.gte(12, z, P))))
    {
      addPInvTo(z);
    }
  }
  
  private static void addPInvTo(int[] z)
  {
    long c = (z[0] & 0xFFFFFFFF) + 1L;
    z[0] = ((int)c);
    c >>= 32;
    c += (z[1] & 0xFFFFFFFF) - 1L;
    z[1] = ((int)c);
    c >>= 32;
    if (c != 0L)
    {
      c += (z[2] & 0xFFFFFFFF);
      z[2] = ((int)c);
      c >>= 32;
    }
    c += (z[3] & 0xFFFFFFFF) + 1L;
    z[3] = ((int)c);
    c >>= 32;
    c += (z[4] & 0xFFFFFFFF) + 1L;
    z[4] = ((int)c);
    c >>= 32;
    if (c != 0L)
    {
      Nat.incAt(12, z, 5);
    }
  }
  
  private static void subPInvFrom(int[] z)
  {
    long c = (z[0] & 0xFFFFFFFF) - 1L;
    z[0] = ((int)c);
    c >>= 32;
    c += (z[1] & 0xFFFFFFFF) + 1L;
    z[1] = ((int)c);
    c >>= 32;
    if (c != 0L)
    {
      c += (z[2] & 0xFFFFFFFF);
      z[2] = ((int)c);
      c >>= 32;
    }
    c += (z[3] & 0xFFFFFFFF) - 1L;
    z[3] = ((int)c);
    c >>= 32;
    c += (z[4] & 0xFFFFFFFF) - 1L;
    z[4] = ((int)c);
    c >>= 32;
    if (c != 0L)
    {
      Nat.decAt(12, z, 5);
    }
  }
}
