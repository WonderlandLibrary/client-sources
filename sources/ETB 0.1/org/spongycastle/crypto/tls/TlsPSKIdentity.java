package org.spongycastle.crypto.tls;

public abstract interface TlsPSKIdentity
{
  public abstract void skipIdentityHint();
  
  public abstract void notifyIdentityHint(byte[] paramArrayOfByte);
  
  public abstract byte[] getPSKIdentity();
  
  public abstract byte[] getPSK();
}
