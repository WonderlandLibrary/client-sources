package org.spongycastle.math.ec;

import java.math.BigInteger;

public abstract class AbstractECMultiplier implements ECMultiplier {
  public AbstractECMultiplier() {}
  
  public ECPoint multiply(ECPoint p, BigInteger k) {
    int sign = k.signum();
    if ((sign == 0) || (p.isInfinity()))
    {
      return p.getCurve().getInfinity();
    }
    
    ECPoint positive = multiplyPositive(p, k.abs());
    ECPoint result = sign > 0 ? positive : positive.negate();
    




    return ECAlgorithms.validatePoint(result);
  }
  
  protected abstract ECPoint multiplyPositive(ECPoint paramECPoint, BigInteger paramBigInteger);
}
