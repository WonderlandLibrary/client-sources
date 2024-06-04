package org.spongycastle.crypto.ec;

import java.math.BigInteger;

public abstract interface ECPairFactorTransform
  extends ECPairTransform
{
  public abstract BigInteger getTransformValue();
}
