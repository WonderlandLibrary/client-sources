package org.spongycastle.crypto.tls;

import org.spongycastle.crypto.Digest;

public abstract interface TlsHandshakeHash
  extends Digest
{
  public abstract void init(TlsContext paramTlsContext);
  
  public abstract TlsHandshakeHash notifyPRFDetermined();
  
  public abstract void trackHashAlgorithm(short paramShort);
  
  public abstract void sealHashAlgorithms();
  
  public abstract TlsHandshakeHash stopTracking();
  
  public abstract Digest forkPRFHash();
  
  public abstract byte[] getFinalHash(short paramShort);
}
