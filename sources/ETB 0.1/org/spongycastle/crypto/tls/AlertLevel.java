package org.spongycastle.crypto.tls;


public class AlertLevel
{
  public static final short warning = 1;
  public static final short fatal = 2;
  
  public AlertLevel() {}
  
  public static String getName(short alertDescription)
  {
    switch (alertDescription)
    {
    case 1: 
      return "warning";
    case 2: 
      return "fatal";
    }
    return "UNKNOWN";
  }
  

  public static String getText(short alertDescription)
  {
    return getName(alertDescription) + "(" + alertDescription + ")";
  }
}
