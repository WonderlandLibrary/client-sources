package org.spongycastle.util;






public class StoreException
  extends RuntimeException
{
  private Throwable _e;
  




  public StoreException(String msg, Throwable cause)
  {
    super(msg);
    _e = cause;
  }
  
  public Throwable getCause()
  {
    return _e;
  }
}
