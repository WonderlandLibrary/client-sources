package org.spongycastle.jce.spec;

import java.math.BigInteger;
import java.security.spec.KeySpec;














public class GOST3410PrivateKeySpec
  implements KeySpec
{
  private BigInteger x;
  private BigInteger p;
  private BigInteger q;
  private BigInteger a;
  
  public GOST3410PrivateKeySpec(BigInteger x, BigInteger p, BigInteger q, BigInteger a)
  {
    this.x = x;
    this.p = p;
    this.q = q;
    this.a = a;
  }
  




  public BigInteger getX()
  {
    return x;
  }
  




  public BigInteger getP()
  {
    return p;
  }
  




  public BigInteger getQ()
  {
    return q;
  }
  




  public BigInteger getA()
  {
    return a;
  }
}
