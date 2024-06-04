package org.spongycastle.crypto.tls;

public abstract interface TlsSession
{
  public abstract SessionParameters exportSessionParameters();
  
  public abstract byte[] getSessionID();
  
  public abstract void invalidate();
  
  public abstract boolean isResumable();
}
