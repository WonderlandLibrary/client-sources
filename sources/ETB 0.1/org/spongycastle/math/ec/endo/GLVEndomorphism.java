package org.spongycastle.math.ec.endo;

import java.math.BigInteger;

public abstract interface GLVEndomorphism
  extends ECEndomorphism
{
  public abstract BigInteger[] decomposeScalar(BigInteger paramBigInteger);
}
