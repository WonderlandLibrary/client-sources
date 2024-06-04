package org.spongycastle.jce.spec;

import java.math.BigInteger;








public class ElGamalPublicKeySpec
  extends ElGamalKeySpec
{
  private BigInteger y;
  
  public ElGamalPublicKeySpec(BigInteger y, ElGamalParameterSpec spec)
  {
    super(spec);
    
    this.y = y;
  }
  





  public BigInteger getY()
  {
    return y;
  }
}
