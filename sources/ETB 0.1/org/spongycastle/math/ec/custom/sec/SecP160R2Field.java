package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat160;



public class SecP160R2Field
{
  static final int[] P = { 44147, -2, -1, -1, -1 };
  static final int[] PExt = { 457489321, 42778, 1, 0, 0, -42778, -3, -1, -1, -1 };
  
  private static final int[] PExtInv = { -457489321, -42779, -2, -1, -1, 42777, 2 };
  private static final int P4 = -1;
  private static final int PExt9 = -1;
  private static final int PInv33 = 21389;
  
  public SecP160R2Field() {}
  
  public static void add(int[] x, int[] y, int[] z) {
    int c = Nat160.add(x, y, z);
    if ((c != 0) || ((z[4] == -1) && (Nat160.gte(z, P))))
    {
      Nat.add33To(5, 21389, z);
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
      Nat.add33To(5, 21389, z);
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
    long cc = Nat160.mul33Add(21389, xx, 5, xx, 0, z, 0);
    int c = Nat160.mul33DWordAdd(21389, cc, z, 0);
    


    if ((c != 0) || ((z[4] == -1) && (Nat160.gte(z, P))))
    {
      Nat.add33To(5, 21389, z);
    }
  }
  
  public static void reduce32(int x, int[] z)
  {
    if (((x != 0) && (Nat160.mul33WordAdd(21389, x, z, 0) != 0)) || ((z[4] == -1) && 
      (Nat160.gte(z, P))))
    {
      Nat.add33To(5, 21389, z);
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
      Nat.sub33From(5, 21389, z);
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
      Nat.add33To(5, 21389, z);
    }
  }
}
