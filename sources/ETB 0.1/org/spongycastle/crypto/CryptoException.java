package org.spongycastle.crypto;






public class CryptoException
  extends Exception
{
  private Throwable cause;
  





  public CryptoException() {}
  




  public CryptoException(String message)
  {
    super(message);
  }
  








  public CryptoException(String message, Throwable cause)
  {
    super(message);
    
    this.cause = cause;
  }
  
  public Throwable getCause()
  {
    return cause;
  }
}
