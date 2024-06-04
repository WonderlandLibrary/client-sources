package org.spongycastle.crypto.tls;

import java.io.IOException;

public abstract interface TlsPeer
{
  public abstract boolean shouldUseGMTUnixTime();
  
  public abstract void notifySecureRenegotiation(boolean paramBoolean)
    throws IOException;
  
  public abstract TlsCompression getCompression()
    throws IOException;
  
  public abstract TlsCipher getCipher()
    throws IOException;
  
  public abstract void notifyAlertRaised(short paramShort1, short paramShort2, String paramString, Throwable paramThrowable);
  
  public abstract void notifyAlertReceived(short paramShort1, short paramShort2);
  
  public abstract void notifyHandshakeComplete()
    throws IOException;
}
