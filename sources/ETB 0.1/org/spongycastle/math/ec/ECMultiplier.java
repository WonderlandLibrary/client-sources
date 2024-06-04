package org.spongycastle.math.ec;

import java.math.BigInteger;

public abstract interface ECMultiplier
{
  public abstract ECPoint multiply(ECPoint paramECPoint, BigInteger paramBigInteger);
}
