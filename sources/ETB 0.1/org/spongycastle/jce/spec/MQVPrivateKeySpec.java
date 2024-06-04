package org.spongycastle.jce.spec;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import org.spongycastle.jce.interfaces.MQVPrivateKey;










/**
 * @deprecated
 */
public class MQVPrivateKeySpec
  implements KeySpec, MQVPrivateKey
{
  private PrivateKey staticPrivateKey;
  private PrivateKey ephemeralPrivateKey;
  private PublicKey ephemeralPublicKey;
  
  public MQVPrivateKeySpec(PrivateKey staticPrivateKey, PrivateKey ephemeralPrivateKey)
  {
    this(staticPrivateKey, ephemeralPrivateKey, null);
  }
  








  public MQVPrivateKeySpec(PrivateKey staticPrivateKey, PrivateKey ephemeralPrivateKey, PublicKey ephemeralPublicKey)
  {
    this.staticPrivateKey = staticPrivateKey;
    this.ephemeralPrivateKey = ephemeralPrivateKey;
    this.ephemeralPublicKey = ephemeralPublicKey;
  }
  



  public PrivateKey getStaticPrivateKey()
  {
    return staticPrivateKey;
  }
  



  public PrivateKey getEphemeralPrivateKey()
  {
    return ephemeralPrivateKey;
  }
  



  public PublicKey getEphemeralPublicKey()
  {
    return ephemeralPublicKey;
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
