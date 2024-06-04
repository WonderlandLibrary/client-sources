package org.spongycastle.crypto;

public abstract interface SignerWithRecovery
  extends Signer
{
  public abstract boolean hasFullMessage();
  
  public abstract byte[] getRecoveredMessage();
  
  public abstract void updateWithRecoveredMessage(byte[] paramArrayOfByte)
    throws InvalidCipherTextException;
}
