package org.spongycastle.crypto;

public abstract interface AsymmetricBlockCipher
{
  public abstract void init(boolean paramBoolean, CipherParameters paramCipherParameters);
  
  public abstract int getInputBlockSize();
  
  public abstract int getOutputBlockSize();
  
  public abstract byte[] processBlock(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws InvalidCipherTextException;
}
