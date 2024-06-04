package org.spongycastle.crypto;

public abstract interface Signer
{
  public abstract void init(boolean paramBoolean, CipherParameters paramCipherParameters);
  
  public abstract void update(byte paramByte);
  
  public abstract void update(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
  
  public abstract byte[] generateSignature()
    throws CryptoException, DataLengthException;
  
  public abstract boolean verifySignature(byte[] paramArrayOfByte);
  
  public abstract void reset();
}
