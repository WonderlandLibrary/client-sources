package org.spongycastle.crypto.modes.gcm;

public abstract interface GCMExponentiator
{
  public abstract void init(byte[] paramArrayOfByte);
  
  public abstract void exponentiateX(long paramLong, byte[] paramArrayOfByte);
}
