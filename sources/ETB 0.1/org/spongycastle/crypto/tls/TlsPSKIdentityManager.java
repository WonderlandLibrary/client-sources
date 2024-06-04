package org.spongycastle.crypto.tls;

public abstract interface TlsPSKIdentityManager
{
  public abstract byte[] getHint();
  
  public abstract byte[] getPSK(byte[] paramArrayOfByte);
}
