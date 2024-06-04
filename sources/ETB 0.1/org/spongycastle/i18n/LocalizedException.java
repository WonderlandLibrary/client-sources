package org.spongycastle.i18n;

import java.util.Locale;








public class LocalizedException
  extends Exception
{
  protected ErrorBundle message;
  private Throwable cause;
  
  public LocalizedException(ErrorBundle message)
  {
    super(message.getText(Locale.getDefault()));
    this.message = message;
  }
  





  public LocalizedException(ErrorBundle message, Throwable throwable)
  {
    super(message.getText(Locale.getDefault()));
    this.message = message;
    cause = throwable;
  }
  




  public ErrorBundle getErrorMessage()
  {
    return message;
  }
  
  public Throwable getCause()
  {
    return cause;
  }
}
