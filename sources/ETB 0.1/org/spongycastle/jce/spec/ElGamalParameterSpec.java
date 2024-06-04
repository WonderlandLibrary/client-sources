package org.spongycastle.jce.spec;

import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;










public class ElGamalParameterSpec
  implements AlgorithmParameterSpec
{
  private BigInteger p;
  private BigInteger g;
  
  public ElGamalParameterSpec(BigInteger p, BigInteger g)
  {
    this.p = p;
    this.g = g;
  }
  





  public BigInteger getP()
  {
    return p;
  }
  





  public BigInteger getG()
  {
    return g;
  }
}
