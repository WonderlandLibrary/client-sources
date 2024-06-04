package org.spongycastle.pqc.crypto.mceliece;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.pqc.math.linearalgebra.PolynomialRingGF2;





























public class McElieceParameters
  implements CipherParameters
{
  public static final int DEFAULT_M = 11;
  public static final int DEFAULT_T = 50;
  private int m;
  private int t;
  private int n;
  private int fieldPoly;
  private Digest digest;
  
  public McElieceParameters()
  {
    this(11, 50);
  }
  
  public McElieceParameters(Digest digest)
  {
    this(11, 50, digest);
  }
  






  public McElieceParameters(int keysize)
  {
    this(keysize, null);
  }
  







  public McElieceParameters(int keysize, Digest digest)
  {
    if (keysize < 1)
    {
      throw new IllegalArgumentException("key size must be positive");
    }
    m = 0;
    n = 1;
    while (n < keysize)
    {
      n <<= 1;
      m += 1;
    }
    t = (n >>> 1);
    t /= m;
    fieldPoly = PolynomialRingGF2.getIrreduciblePolynomial(m);
    this.digest = digest;
  }
  








  public McElieceParameters(int m, int t)
  {
    this(m, t, null);
  }
  








  public McElieceParameters(int m, int t, Digest digest)
  {
    if (m < 1)
    {
      throw new IllegalArgumentException("m must be positive");
    }
    if (m > 32)
    {
      throw new IllegalArgumentException("m is too large");
    }
    this.m = m;
    n = (1 << m);
    if (t < 0)
    {
      throw new IllegalArgumentException("t must be positive");
    }
    if (t > n)
    {
      throw new IllegalArgumentException("t must be less than n = 2^m");
    }
    this.t = t;
    fieldPoly = PolynomialRingGF2.getIrreduciblePolynomial(m);
    this.digest = digest;
  }
  










  public McElieceParameters(int m, int t, int poly)
  {
    this(m, t, poly, null);
  }
  











  public McElieceParameters(int m, int t, int poly, Digest digest)
  {
    this.m = m;
    if (m < 1)
    {
      throw new IllegalArgumentException("m must be positive");
    }
    if (m > 32)
    {
      throw new IllegalArgumentException(" m is too large");
    }
    n = (1 << m);
    this.t = t;
    if (t < 0)
    {
      throw new IllegalArgumentException("t must be positive");
    }
    if (t > n)
    {
      throw new IllegalArgumentException("t must be less than n = 2^m");
    }
    if ((PolynomialRingGF2.degree(poly) == m) && 
      (PolynomialRingGF2.isIrreducible(poly)))
    {
      fieldPoly = poly;
    }
    else
    {
      throw new IllegalArgumentException("polynomial is not a field polynomial for GF(2^m)");
    }
    
    this.digest = digest;
  }
  



  public int getM()
  {
    return m;
  }
  



  public int getN()
  {
    return n;
  }
  



  public int getT()
  {
    return t;
  }
  



  public int getFieldPoly()
  {
    return fieldPoly;
  }
}
