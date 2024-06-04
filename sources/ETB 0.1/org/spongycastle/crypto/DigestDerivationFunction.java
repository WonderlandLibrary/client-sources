package org.spongycastle.crypto;

public abstract interface DigestDerivationFunction
  extends DerivationFunction
{
  public abstract Digest getDigest();
}
