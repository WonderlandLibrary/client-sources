package org.spongycastle.crypto.tls;

import java.io.IOException;





public abstract class AbstractTlsPeer
  implements TlsPeer
{
  public AbstractTlsPeer() {}
  
  public boolean shouldUseGMTUnixTime()
  {
    return false;
  }
  
  public void notifySecureRenegotiation(boolean secureRenegotiation) throws IOException
  {
    if (!secureRenegotiation)
    {




      throw new TlsFatalAlert((short)40);
    }
  }
  
  public void notifyAlertRaised(short alertLevel, short alertDescription, String message, Throwable cause) {}
  
  public void notifyAlertReceived(short alertLevel, short alertDescription) {}
  
  public void notifyHandshakeComplete()
    throws IOException
  {}
}
