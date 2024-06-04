package org.spongycastle.crypto.prng.drbg;

import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECPoint;
















public class DualECPoints
{
  private final ECPoint p;
  private final ECPoint q;
  private final int securityStrength;
  private final int cofactor;
  
  public DualECPoints(int securityStrength, ECPoint p, ECPoint q, int cofactor)
  {
    if (!p.getCurve().equals(q.getCurve()))
    {
      throw new IllegalArgumentException("points need to be on the same curve");
    }
    
    this.securityStrength = securityStrength;
    this.p = p;
    this.q = q;
    this.cofactor = cofactor;
  }
  
  public int getSeedLen()
  {
    return p.getCurve().getFieldSize();
  }
  
  public int getMaxOutlen()
  {
    return (p.getCurve().getFieldSize() - (13 + log2(cofactor))) / 8 * 8;
  }
  
  public ECPoint getP()
  {
    return p;
  }
  
  public ECPoint getQ()
  {
    return q;
  }
  
  public int getSecurityStrength()
  {
    return securityStrength;
  }
  
  public int getCofactor()
  {
    return cofactor;
  }
  
  private static int log2(int value)
  {
    int log = 0;
    
    while (value >>= 1 != 0)
    {
      log++;
    }
    
    return log;
  }
}
