package org.spongycastle.crypto.io;

import java.io.IOException;





public class CipherIOException
  extends IOException
{
  private static final long serialVersionUID = 1L;
  private final Throwable cause;
  
  public CipherIOException(String message, Throwable cause)
  {
    super(message);
    
    this.cause = cause;
  }
  
  public Throwable getCause()
  {
    return cause;
  }
}
