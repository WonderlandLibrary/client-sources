package org.spongycastle.crypto;

public abstract interface SkippingCipher
{
  public abstract long skip(long paramLong);
  
  public abstract long seekTo(long paramLong);
  
  public abstract long getPosition();
}
