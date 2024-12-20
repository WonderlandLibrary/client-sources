package org.spongycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RandomDSAKCalculator
  implements DSAKCalculator
{
  private static final BigInteger ZERO = BigInteger.valueOf(0L);
  private BigInteger q;
  private SecureRandom random;
  
  public RandomDSAKCalculator() {}
  
  public boolean isDeterministic() {
    return false;
  }
  
  public void init(BigInteger n, SecureRandom random)
  {
    q = n;
    this.random = random;
  }
  
  public void init(BigInteger n, BigInteger d, byte[] message)
  {
    throw new IllegalStateException("Operation not supported");
  }
  
  public BigInteger nextK()
  {
    int qBitLength = q.bitLength();
    
    BigInteger k;
    do
    {
      k = new BigInteger(qBitLength, random);
    }
    while ((k.equals(ZERO)) || (k.compareTo(q) >= 0));
    
    return k;
  }
}
