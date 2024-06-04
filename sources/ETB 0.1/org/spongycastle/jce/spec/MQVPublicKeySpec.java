package org.spongycastle.jce.spec;

import java.security.PublicKey;
import java.security.spec.KeySpec;
import org.spongycastle.jce.interfaces.MQVPublicKey;









/**
 * @deprecated
 */
public class MQVPublicKeySpec
  implements KeySpec, MQVPublicKey
{
  private PublicKey staticKey;
  private PublicKey ephemeralKey;
  
  public MQVPublicKeySpec(PublicKey staticKey, PublicKey ephemeralKey)
  {
    this.staticKey = staticKey;
    this.ephemeralKey = ephemeralKey;
  }
  



  public PublicKey getStaticKey()
  {
    return staticKey;
  }
  



  public PublicKey getEphemeralKey()
  {
    return ephemeralKey;
  }
  



  public String getAlgorithm()
  {
    return "ECMQV";
  }
  



  public String getFormat()
  {
    return null;
  }
  



  public byte[] getEncoded()
  {
    return null;
  }
}
