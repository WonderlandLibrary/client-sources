package org.spongycastle.crypto;

public abstract interface KeyEncapsulation
{
  public abstract void init(CipherParameters paramCipherParameters);
  
  public abstract CipherParameters encrypt(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
  
  public abstract CipherParameters decrypt(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3);
}
