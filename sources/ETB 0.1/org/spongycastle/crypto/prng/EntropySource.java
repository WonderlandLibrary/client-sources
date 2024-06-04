package org.spongycastle.crypto.prng;

public abstract interface EntropySource
{
  public abstract boolean isPredictionResistant();
  
  public abstract byte[] getEntropy();
  
  public abstract int entropySize();
}
