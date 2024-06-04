package org.spongycastle.crypto;

public abstract interface Xof
  extends ExtendedDigest
{
  public abstract int doFinal(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
  
  public abstract int doOutput(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
}
