package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat224;



public class SecP224K1Field
{
  static final int[] P = { 58733, -2, -1, -1, -1, -1, -1 };
  
  static final int[] PExt = { 46280809, 13606, 1, 0, 0, 0, 0, 51930, -3, -1, -1, -1, -1, -1 };
  
  private static final int[] PExtInv = { -46280809, 51929, -2, -1, -1, -1, -1, 13605, 2 };
  private static final int P6 = -1;
  private static final int PExt13 = -1;
  private static final int PInv33 = 6803;
  
  public SecP224K1Field() {}
  
  public static void add(int[] x, int[] y, int[] z) {
    int c = Nat224.add(x, y, z);
    if ((c != 0) || ((z[6] == -1) && (Nat224.gte(z, P))))
    {
      Nat.add33To(7, 6803, z);
    }
  }
  
  public static void addExt(int[] xx, int[] yy, int[] zz)
  {
    int c = Nat.add(14, xx, yy, zz);
    if ((c != 0) || ((zz[13] == -1) && (Nat.gte(14, zz, PExt))))
    {
      if (Nat.addTo(PExtInv.length, PExtInv, zz) != 0)
      {
        Nat.incAt(14, zz, PExtInv.length);
      }
    }
  }
  
  public static void addOne(int[] x, int[] z)
  {
    int c = Nat.inc(7, x, z);
    if ((c != 0) || ((z[6] == -1) && (Nat224.gte(z, P))))
    {
      Nat.add33To(7, 6803, z);
    }
  }
  
  public static int[] fromBigInteger(BigInteger x)
  {
    int[] z = Nat224.fromBigInteger(x);
    if ((z[6] == -1) && (Nat224.gte(z, P)))
    {
      Nat.add33To(7, 6803, z);
    }
    return z;
  }
  
  public static void half(int[] x, int[] z)
  {
    if ((x[0] & 0x1) == 0)
    {
      Nat.shiftDownBit(7, x, 0, z);
    }
    else
    {
      int c = Nat224.add(x, P, z);
      Nat.shiftDownBit(7, z, c);
    }
  }
  
  public static void multiply(int[] x, int[] y, int[] z)
  {
    int[] tt = Nat224.createExt();
    Nat224.mul(x, y, tt);
    reduce(tt, z);
  }
  
  public static void multiplyAddToExt(int[] x, int[] y, int[] zz)
  {
    int c = Nat224.mulAddTo(x, y, zz);
    if ((c != 0) || ((zz[13] == -1) && (Nat.gte(14, zz, PExt))))
    {
      if (Nat.addTo(PExtInv.length, PExtInv, zz) != 0)
      {
        Nat.incAt(14, zz, PExtInv.length);
      }
    }
  }
  
  public static void negate(int[] x, int[] z)
  {
    if (Nat224.isZero(x))
    {
      Nat224.zero(z);
    }
    else
    {
      Nat224.sub(P, x, z);
    }
  }
  
  public static void reduce(int[] xx, int[] z)
  {
    long cc = Nat224.mul33Add(6803, xx, 7, xx, 0, z, 0);
    int c = Nat224.mul33DWordAdd(6803, cc, z, 0);
    


    if ((c != 0) || ((z[6] == -1) && (Nat224.gte(z, P))))
    {
      Nat.add33To(7, 6803, z);
    }
  }
  
  public static void reduce32(int x, int[] z)
  {
    if (((x != 0) && (Nat224.mul33WordAdd(6803, x, z, 0) != 0)) || ((z[6] == -1) && 
      (Nat224.gte(z, P))))
    {
      Nat.add33To(7, 6803, z);
    }
  }
  
  public static void square(int[] x, int[] z)
  {
    int[] tt = Nat224.createExt();
    Nat224.square(x, tt);
    reduce(tt, z);
  }
  


  public static void squareN(int[] x, int n, int[] z)
  {
    int[] tt = Nat224.createExt();
    Nat224.square(x, tt);
    reduce(tt, z);
    for (;;) {
      n--; if (n <= 0)
        break;
      Nat224.square(z, tt);
      reduce(tt, z);
    }
  }
  
  public static void subtract(int[] x, int[] y, int[] z)
  {
    int c = Nat224.sub(x, y, z);
    if (c != 0)
    {
      Nat.sub33From(7, 6803, z);
    }
  }
  
  public static void subtractExt(int[] xx, int[] yy, int[] zz)
  {
    int c = Nat.sub(14, xx, yy, zz);
    if (c != 0)
    {
      if (Nat.subFrom(PExtInv.length, PExtInv, zz) != 0)
      {
        Nat.decAt(14, zz, PExtInv.length);
      }
    }
  }
  
  public static void twice(int[] x, int[] z)
  {
    int c = Nat.shiftUpBit(7, x, 0, z);
    if ((c != 0) || ((z[6] == -1) && (Nat224.gte(z, P))))
    {
      Nat.add33To(7, 6803, z);
    }
  }
}
