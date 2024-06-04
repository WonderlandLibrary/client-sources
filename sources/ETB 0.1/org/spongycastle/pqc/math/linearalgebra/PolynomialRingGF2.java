package org.spongycastle.pqc.math.linearalgebra;

import java.io.PrintStream;
























public final class PolynomialRingGF2
{
  private PolynomialRingGF2() {}
  
  public static int add(int p, int q)
  {
    return p ^ q;
  }
  








  public static long multiply(int p, int q)
  {
    long result = 0L;
    if (q != 0)
    {
      long q1 = q & 0xFFFFFFFF;
      
      while (p != 0)
      {
        byte b = (byte)(p & 0x1);
        if (b == 1)
        {
          result ^= q1;
        }
        p >>>= 1;
        q1 <<= 1;
      }
    }
    
    return result;
  }
  








  public static int modMultiply(int a, int b, int r)
  {
    int result = 0;
    int p = remainder(a, r);
    int q = remainder(b, r);
    if (q != 0)
    {
      int d = 1 << degree(r);
      
      while (p != 0)
      {
        byte pMod2 = (byte)(p & 0x1);
        if (pMod2 == 1)
        {
          result ^= q;
        }
        p >>>= 1;
        q <<= 1;
        if (q >= d)
        {
          q ^= r;
        }
      }
    }
    return result;
  }
  







  public static int degree(int p)
  {
    int result = -1;
    while (p != 0)
    {
      result++;
      p >>>= 1;
    }
    return result;
  }
  







  public static int degree(long p)
  {
    int result = 0;
    while (p != 0L)
    {
      result++;
      p >>>= 1;
    }
    return result - 1;
  }
  







  public static int remainder(int p, int q)
  {
    int result = p;
    
    if (q == 0)
    {
      System.err.println("Error: to be divided by 0");
      return 0;
    }
    
    while (degree(result) >= degree(q))
    {
      result ^= q << degree(result) - degree(q);
    }
    
    return result;
  }
  








  public static int rest(long p, int q)
  {
    long p1 = p;
    if (q == 0)
    {
      System.err.println("Error: to be divided by 0");
      return 0;
    }
    long q1 = q & 0xFFFFFFFF;
    while (p1 >>> 32 != 0L)
    {
      p1 ^= q1 << degree(p1) - degree(q1);
    }
    
    int result = (int)(p1 & 0xFFFFFFFFFFFFFFFF);
    while (degree(result) >= degree(q))
    {
      result ^= q << degree(result) - degree(q);
    }
    
    return result;
  }
  









  public static int gcd(int p, int q)
  {
    int a = p;
    int b = q;
    while (b != 0)
    {
      int c = remainder(a, b);
      a = b;
      b = c;
    }
    
    return a;
  }
  







  public static boolean isIrreducible(int p)
  {
    if (p == 0)
    {
      return false;
    }
    int d = degree(p) >>> 1;
    int u = 2;
    for (int i = 0; i < d; i++)
    {
      u = modMultiply(u, u, p);
      if (gcd(u ^ 0x2, p) != 1)
      {
        return false;
      }
    }
    return true;
  }
  






  public static int getIrreduciblePolynomial(int deg)
  {
    if (deg < 0)
    {
      System.err.println("The Degree is negative");
      return 0;
    }
    if (deg > 31)
    {
      System.err.println("The Degree is more then 31");
      return 0;
    }
    if (deg == 0)
    {
      return 1;
    }
    int a = 1 << deg;
    a++;
    int b = 1 << deg + 1;
    for (int i = a; i < b; i += 2)
    {
      if (isIrreducible(i))
      {
        return i;
      }
    }
    return 0;
  }
}
