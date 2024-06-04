package org.spongycastle.crypto;

public abstract interface Wrapper
{
  public abstract void init(boolean paramBoolean, CipherParameters paramCipherParameters);
  
  public abstract String getAlgorithmName();
  
  public abstract byte[] wrap(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
  
  public abstract byte[] unwrap(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws InvalidCipherTextException;
}
