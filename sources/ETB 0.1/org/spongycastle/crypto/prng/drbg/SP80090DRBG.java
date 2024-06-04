package org.spongycastle.crypto.prng.drbg;

public abstract interface SP80090DRBG
{
  public abstract int getBlockSize();
  
  public abstract int generate(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, boolean paramBoolean);
  
  public abstract void reseed(byte[] paramArrayOfByte);
}
