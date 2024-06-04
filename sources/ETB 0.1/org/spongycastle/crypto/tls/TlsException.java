package org.spongycastle.crypto.tls;

import java.io.IOException;


public class TlsException
  extends IOException
{
  protected Throwable cause;
  
  public TlsException(String message, Throwable cause)
  {
    super(message);
    
    this.cause = cause;
  }
  
  public Throwable getCause()
  {
    return cause;
  }
}
