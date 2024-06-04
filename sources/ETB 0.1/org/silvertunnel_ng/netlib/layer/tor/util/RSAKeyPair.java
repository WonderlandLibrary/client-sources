package org.silvertunnel_ng.netlib.layer.tor.util;

import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;


























public class RSAKeyPair
{
  private final RSAPublicKey publicKey;
  private final RSAPrivateCrtKey privateKey;
  
  public RSAKeyPair(RSAPublicKey publicKey, RSAPrivateCrtKey privateKey)
  {
    this.publicKey = publicKey;
    this.privateKey = privateKey;
  }
  

  public String toString()
  {
    return publicKey + "\n" + privateKey;
  }
  
  public RSAPublicKey getPublic()
  {
    return publicKey;
  }
  
  public RSAPrivateCrtKey getPrivate()
  {
    return privateKey;
  }
}
