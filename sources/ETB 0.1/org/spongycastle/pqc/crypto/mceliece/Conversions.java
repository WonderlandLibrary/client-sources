package org.spongycastle.pqc.crypto.mceliece;

import java.math.BigInteger;
import org.spongycastle.pqc.math.linearalgebra.BigIntUtils;
import org.spongycastle.pqc.math.linearalgebra.GF2Vector;
import org.spongycastle.pqc.math.linearalgebra.IntegerFunctions;






final class Conversions
{
  private static final BigInteger ZERO = BigInteger.valueOf(0L);
  private static final BigInteger ONE = BigInteger.valueOf(1L);
  








  private Conversions() {}
  







  public static GF2Vector encode(int n, int t, byte[] m)
  {
    if (n < t)
    {
      throw new IllegalArgumentException("n < t");
    }
    

    BigInteger c = IntegerFunctions.binomial(n, t);
    
    BigInteger i = new BigInteger(1, m);
    
    if (i.compareTo(c) >= 0)
    {
      throw new IllegalArgumentException("Encoded number too large.");
    }
    
    GF2Vector result = new GF2Vector(n);
    
    int nn = n;
    int tt = t;
    for (int j = 0; j < n; j++)
    {
      c = c.multiply(BigInteger.valueOf(nn - tt)).divide(
        BigInteger.valueOf(nn));
      nn--;
      if (c.compareTo(i) <= 0)
      {
        result.setBit(j);
        i = i.subtract(c);
        tt--;
        if (nn == tt)
        {
          c = ONE;

        }
        else
        {
          c = c.multiply(BigInteger.valueOf(tt + 1)).divide(BigInteger.valueOf(nn - tt));
        }
      }
    }
    
    return result;
  }
  










  public static byte[] decode(int n, int t, GF2Vector vec)
  {
    if ((vec.getLength() != n) || (vec.getHammingWeight() != t))
    {
      throw new IllegalArgumentException("vector has wrong length or hamming weight");
    }
    
    int[] vecArray = vec.getVecArray();
    
    BigInteger bc = IntegerFunctions.binomial(n, t);
    BigInteger d = ZERO;
    int nn = n;
    int tt = t;
    for (int i = 0; i < n; i++)
    {
      bc = bc.multiply(BigInteger.valueOf(nn - tt)).divide(
        BigInteger.valueOf(nn));
      nn--;
      
      int q = i >> 5;
      int e = vecArray[q] & 1 << (i & 0x1F);
      if (e != 0)
      {
        d = d.add(bc);
        tt--;
        if (nn == tt)
        {
          bc = ONE;
        }
        else
        {
          bc = bc.multiply(BigInteger.valueOf(tt + 1)).divide(
            BigInteger.valueOf(nn - tt));
        }
      }
    }
    

    return BigIntUtils.toMinimalByteArray(d);
  }
  











  public static byte[] signConversion(int n, int t, byte[] m)
  {
    if (n < t)
    {
      throw new IllegalArgumentException("n < t");
    }
    
    BigInteger bc = IntegerFunctions.binomial(n, t);
    
    int s = bc.bitLength() - 1;
    
    int sq = s >> 3;
    int sr = s & 0x7;
    if (sr == 0)
    {
      sq--;
      sr = 8;
    }
    

    int nq = n >> 3;
    int nr = n & 0x7;
    if (nr == 0)
    {
      nq--;
      nr = 8;
    }
    
    byte[] data = new byte[nq + 1];
    if (m.length < data.length)
    {
      System.arraycopy(m, 0, data, 0, m.length);
      for (int i = m.length; i < data.length; i++)
      {
        data[i] = 0;
      }
    }
    else
    {
      System.arraycopy(m, 0, data, 0, nq);
      int h = (1 << nr) - 1;
      data[nq] = ((byte)(h & m[nq]));
    }
    
    BigInteger d = ZERO;
    int nn = n;
    int tt = t;
    for (int i = 0; i < n; i++)
    {

      bc = bc.multiply(new BigInteger(Integer.toString(nn - tt))).divide(new BigInteger(Integer.toString(nn)));
      nn--;
      
      int q = i >>> 3;
      int r = i & 0x7;
      r = 1 << r;
      byte e = (byte)(r & data[q]);
      if (e != 0)
      {
        d = d.add(bc);
        tt--;
        if (nn == tt)
        {
          bc = ONE;

        }
        else
        {

          bc = bc.multiply(new BigInteger(Integer.toString(tt + 1))).divide(new BigInteger(Integer.toString(nn - tt)));
        }
      }
    }
    
    byte[] result = new byte[sq + 1];
    byte[] help = d.toByteArray();
    if (help.length < result.length)
    {
      System.arraycopy(help, 0, result, 0, help.length);
      for (int i = help.length; i < result.length; i++)
      {
        result[i] = 0;
      }
    }
    else
    {
      System.arraycopy(help, 0, result, 0, sq);
      result[sq] = ((byte)((1 << sr) - 1 & help[sq]));
    }
    
    return result;
  }
}
