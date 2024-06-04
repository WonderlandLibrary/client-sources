package org.spongycastle.pqc.crypto;

import org.spongycastle.crypto.CipherParameters;

public abstract interface MessageSigner
{
  public abstract void init(boolean paramBoolean, CipherParameters paramCipherParameters);
  
  public abstract byte[] generateSignature(byte[] paramArrayOfByte);
  
  public abstract boolean verifySignature(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
}
