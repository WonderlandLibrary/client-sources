package org.spongycastle.crypto.modes.gcm;

public abstract interface GCMMultiplier
{
  public abstract void init(byte[] paramArrayOfByte);
  
  public abstract void multiplyH(byte[] paramArrayOfByte);
}
