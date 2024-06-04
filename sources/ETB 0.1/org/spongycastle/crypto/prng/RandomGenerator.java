package org.spongycastle.crypto.prng;

public abstract interface RandomGenerator
{
  public abstract void addSeedMaterial(byte[] paramArrayOfByte);
  
  public abstract void addSeedMaterial(long paramLong);
  
  public abstract void nextBytes(byte[] paramArrayOfByte);
  
  public abstract void nextBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
}
