package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat192;



public class SecP192K1Field
{
  static final int[] P = { 60983, -2, -1, -1, -1, -1 };
  static final int[] PExt = { 20729809, 9106, 1, 0, 0, 0, 56430, -3, -1, -1, -1, -1 };
  
  private static final int[] PExtInv = { -20729809, 56429, -2, -1, -1, -1, 9105, 2 };
  private static final int P5 = -1;
  private static final int PExt11 = -1;
  private static final int PInv33 = 4553;
  
  public SecP192K1Field() {}
  
  public static void add(int[] x, int[] y, int[] z) {
    int c = Nat192.add(x, y, z);
    if ((c != 0) || ((z[5] == -1) && (Nat192.gte(z, P))))
    {
      Nat.add33To(6, 4553, z);
    }
  }
  
  public static void addExt(int[] xx, int[] yy, int[] zz)
  {
    int c = Nat.add(12, xx, yy, zz);
    if ((c != 0) || ((zz[11] == -1) && (Nat.gte(12, zz, PExt))))
    {
      if (Nat.addTo(PExtInv.length, PExtInv, zz) != 0)
      {
        Nat.incAt(12, zz, PExtInv.length);
      }
    }
  }
  
  public static void addOne(int[] x, int[] z)
  {
    int c = Nat.inc(6, x, z);
    if ((c != 0) || ((z[5] == -1) && (Nat192.gte(z, P))))
    {
      Nat.add33To(6, 4553, z);
    }
  }
  
  public static int[] fromBigInteger(BigInteger x)
  {
    int[] z = Nat192.fromBigInteger(x);
    if ((z[5] == -1) && (Nat192.gte(z, P)))
    {
      Nat192.subFrom(P, z);
    }
    return z;
  }
  
  public static void half(int[] x, int[] z)
  {
    if ((x[0] & 0x1) == 0)
    {
      Nat.shiftDownBit(6, x, 0, z);
    }
    else
    {
      int c = Nat192.add(x, P, z);
      Nat.shiftDownBit(6, z, c);
    }
  }
  
  public static void multiply(int[] x, int[] y, int[] z)
  {
    int[] tt = Nat192.createExt();
    Nat192.mul(x, y, tt);
    reduce(tt, z);
  }
  
  public static void multiplyAddToExt(int[] x, int[] y, int[] zz)
  {
    int c = Nat192.mulAddTo(x, y, zz);
    if ((c != 0) || ((zz[11] == -1) && (Nat.gte(12, zz, PExt))))
    {
      if (Nat.addTo(PExtInv.length, PExtInv, zz) != 0)
      {
        Nat.incAt(12, zz, PExtInv.length);
      }
    }
  }
  
  public static void negate(int[] x, int[] z)
  {
    if (Nat192.isZero(x))
    {
      Nat192.zero(z);
    }
    else
    {
      Nat192.sub(P, x, z);
    }
  }
  
  public static void reduce(int[] xx, int[] z)
  {
    long cc = Nat192.mul33Add(4553, xx, 6, xx, 0, z, 0);
    int c = Nat192.mul33DWordAdd(4553, cc, z, 0);
    


    if ((c != 0) || ((z[5] == -1) && (Nat192.gte(z, P))))
    {
      Nat.add33To(6, 4553, z);
    }
  }
  
  public static void reduce32(int x, int[] z)
  {
    if (((x != 0) && (Nat192.mul33WordAdd(4553, x, z, 0) != 0)) || ((z[5] == -1) && 
      (Nat192.gte(z, P))))
    {
      Nat.add33To(6, 4553, z);
    }
  }
  
  public static void square(int[] x, int[] z)
  {
    int[] tt = Nat192.createExt();
    Nat192.square(x, tt);
    reduce(tt, z);
  }
  


  public static void squareN(int[] x, int n, int[] z)
  {
    int[] tt = Nat192.createExt();
    Nat192.square(x, tt);
    reduce(tt, z);
    for (;;) {
      n--; if (n <= 0)
        break;
      Nat192.square(z, tt);
      reduce(tt, z);
    }
  }
  
  public static void subtract(int[] x, int[] y, int[] z)
  {
    int c = Nat192.sub(x, y, z);
    if (c != 0)
    {
      Nat.sub33From(6, 4553, z);
    }
  }
  
  public static void subtractExt(int[] xx, int[] yy, int[] zz)
  {
    int c = Nat.sub(12, xx, yy, zz);
    if (c != 0)
    {
      if (Nat.subFrom(PExtInv.length, PExtInv, zz) != 0)
      {
        Nat.decAt(12, zz, PExtInv.length);
      }
    }
  }
  
  public static void twice(int[] x, int[] z)
  {
    int c = Nat.shiftUpBit(6, x, 0, z);
    if ((c != 0) || ((z[5] == -1) && (Nat192.gte(z, P))))
    {
      Nat.add33To(6, 4553, z);
    }
  }
}
