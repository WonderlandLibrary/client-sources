package org.spongycastle.crypto.params;

import java.math.BigInteger;

public class SRP6GroupParameters
{
  private BigInteger N;
  private BigInteger g;
  
  public SRP6GroupParameters(BigInteger N, BigInteger g) {
    this.N = N;
    this.g = g;
  }
  
  public BigInteger getG()
  {
    return g;
  }
  
  public BigInteger getN()
  {
    return N;
  }
}
