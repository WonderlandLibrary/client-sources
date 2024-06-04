package org.spongycastle.crypto;

public abstract interface MacDerivationFunction
  extends DerivationFunction
{
  public abstract Mac getMac();
}
