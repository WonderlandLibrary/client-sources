package org.spongycastle.crypto.tls;


public class CertChainType
{
  public static final short individual_certs = 0;
  public static final short pkipath = 1;
  
  public CertChainType() {}
  
  public static boolean isValid(short certChainType)
  {
    return (certChainType >= 0) && (certChainType <= 1);
  }
}
