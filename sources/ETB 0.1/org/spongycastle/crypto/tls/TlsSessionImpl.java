package org.spongycastle.crypto.tls;

import org.spongycastle.util.Arrays;

class TlsSessionImpl implements TlsSession
{
  final byte[] sessionID;
  SessionParameters sessionParameters;
  
  TlsSessionImpl(byte[] sessionID, SessionParameters sessionParameters)
  {
    if (sessionID == null)
    {
      throw new IllegalArgumentException("'sessionID' cannot be null");
    }
    if ((sessionID.length < 1) || (sessionID.length > 32))
    {
      throw new IllegalArgumentException("'sessionID' must have length between 1 and 32 bytes, inclusive");
    }
    
    this.sessionID = Arrays.clone(sessionID);
    this.sessionParameters = sessionParameters;
  }
  
  public synchronized SessionParameters exportSessionParameters()
  {
    return sessionParameters == null ? null : sessionParameters.copy();
  }
  
  public synchronized byte[] getSessionID()
  {
    return sessionID;
  }
  
  public synchronized void invalidate()
  {
    if (sessionParameters != null)
    {
      sessionParameters.clear();
      sessionParameters = null;
    }
  }
  
  public synchronized boolean isResumable()
  {
    return sessionParameters != null;
  }
}
