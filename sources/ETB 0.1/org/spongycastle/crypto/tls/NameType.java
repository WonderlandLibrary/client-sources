package org.spongycastle.crypto.tls;


public class NameType
{
  public static final short host_name = 0;
  
  public NameType() {}
  
  public static boolean isValid(short nameType)
  {
    return nameType == 0;
  }
}
