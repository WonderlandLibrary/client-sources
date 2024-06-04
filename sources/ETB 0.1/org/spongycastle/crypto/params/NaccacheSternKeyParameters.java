package org.spongycastle.crypto.params;

import java.math.BigInteger;










public class NaccacheSternKeyParameters
  extends AsymmetricKeyParameter
{
  private BigInteger g;
  private BigInteger n;
  int lowerSigmaBound;
  
  public NaccacheSternKeyParameters(boolean privateKey, BigInteger g, BigInteger n, int lowerSigmaBound)
  {
    super(privateKey);
    this.g = g;
    this.n = n;
    this.lowerSigmaBound = lowerSigmaBound;
  }
  



  public BigInteger getG()
  {
    return g;
  }
  



  public int getLowerSigmaBound()
  {
    return lowerSigmaBound;
  }
  



  public BigInteger getModulus()
  {
    return n;
  }
}
