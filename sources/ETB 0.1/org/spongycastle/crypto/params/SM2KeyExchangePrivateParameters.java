package org.spongycastle.crypto.params;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.math.ec.ECPoint;







public class SM2KeyExchangePrivateParameters
  implements CipherParameters
{
  private final boolean initiator;
  private final ECPrivateKeyParameters staticPrivateKey;
  private final ECPoint staticPublicPoint;
  private final ECPrivateKeyParameters ephemeralPrivateKey;
  private final ECPoint ephemeralPublicPoint;
  
  public SM2KeyExchangePrivateParameters(boolean initiator, ECPrivateKeyParameters staticPrivateKey, ECPrivateKeyParameters ephemeralPrivateKey)
  {
    if (staticPrivateKey == null)
    {
      throw new NullPointerException("staticPrivateKey cannot be null");
    }
    if (ephemeralPrivateKey == null)
    {
      throw new NullPointerException("ephemeralPrivateKey cannot be null");
    }
    
    ECDomainParameters parameters = staticPrivateKey.getParameters();
    if (!parameters.equals(ephemeralPrivateKey.getParameters()))
    {
      throw new IllegalArgumentException("Static and ephemeral private keys have different domain parameters");
    }
    
    this.initiator = initiator;
    this.staticPrivateKey = staticPrivateKey;
    staticPublicPoint = parameters.getG().multiply(staticPrivateKey.getD()).normalize();
    this.ephemeralPrivateKey = ephemeralPrivateKey;
    ephemeralPublicPoint = parameters.getG().multiply(ephemeralPrivateKey.getD()).normalize();
  }
  
  public boolean isInitiator()
  {
    return initiator;
  }
  
  public ECPrivateKeyParameters getStaticPrivateKey() {
    return staticPrivateKey;
  }
  
  public ECPoint getStaticPublicPoint()
  {
    return staticPublicPoint;
  }
  
  public ECPrivateKeyParameters getEphemeralPrivateKey()
  {
    return ephemeralPrivateKey;
  }
  
  public ECPoint getEphemeralPublicPoint()
  {
    return ephemeralPublicPoint;
  }
}
