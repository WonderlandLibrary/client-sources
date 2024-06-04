package org.spongycastle.crypto;

public abstract interface Digest
{
  public abstract String getAlgorithmName();
  
  public abstract int getDigestSize();
  
  public abstract void update(byte paramByte);
  
  public abstract void update(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
  
  public abstract int doFinal(byte[] paramArrayOfByte, int paramInt);
  
  public abstract void reset();
}
