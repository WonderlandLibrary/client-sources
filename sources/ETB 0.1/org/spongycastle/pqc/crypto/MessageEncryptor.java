package org.spongycastle.pqc.crypto;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.InvalidCipherTextException;

public abstract interface MessageEncryptor
{
  public abstract void init(boolean paramBoolean, CipherParameters paramCipherParameters);
  
  public abstract byte[] messageEncrypt(byte[] paramArrayOfByte);
  
  public abstract byte[] messageDecrypt(byte[] paramArrayOfByte)
    throws InvalidCipherTextException;
}
