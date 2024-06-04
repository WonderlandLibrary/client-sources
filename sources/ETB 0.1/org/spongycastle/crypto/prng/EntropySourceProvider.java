package org.spongycastle.crypto.prng;

public abstract interface EntropySourceProvider
{
  public abstract EntropySource get(int paramInt);
}
