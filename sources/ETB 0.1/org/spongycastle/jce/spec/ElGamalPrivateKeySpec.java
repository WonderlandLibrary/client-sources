package org.spongycastle.jce.spec;

import java.math.BigInteger;








public class ElGamalPrivateKeySpec
  extends ElGamalKeySpec
{
  private BigInteger x;
  
  public ElGamalPrivateKeySpec(BigInteger x, ElGamalParameterSpec spec)
  {
    super(spec);
    
    this.x = x;
  }
  





  public BigInteger getX()
  {
    return x;
  }
}
