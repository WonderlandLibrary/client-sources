package org.spongycastle.math.ec;

import java.math.BigInteger;

public class ReferenceMultiplier extends AbstractECMultiplier {
  public ReferenceMultiplier() {}
  
  protected ECPoint multiplyPositive(ECPoint p, BigInteger k) {
    return ECAlgorithms.referenceMultiply(p, k);
  }
}
