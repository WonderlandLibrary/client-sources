package org.spongycastle.crypto.params;

import org.spongycastle.crypto.CipherParameters;






public class SM2KeyExchangePublicParameters
  implements CipherParameters
{
  private ECPublicKeyParameters staticPublicKey;
  private ECPublicKeyParameters ephemeralPublicKey;
  
  public SM2KeyExchangePublicParameters(ECPublicKeyParameters staticPublicKey, ECPublicKeyParameters ephemeralPublicKey)
  {
    if (staticPublicKey == null)
    {
      throw new NullPointerException("staticPublicKey cannot be null");
    }
    if (ephemeralPublicKey == null)
    {
      throw new NullPointerException("ephemeralPublicKey cannot be null");
    }
    if (!staticPublicKey.getParameters().equals(ephemeralPublicKey.getParameters()))
    {
      throw new IllegalArgumentException("Static and ephemeral public keys have different domain parameters");
    }
    
    this.staticPublicKey = staticPublicKey;
    this.ephemeralPublicKey = ephemeralPublicKey;
  }
  
  public ECPublicKeyParameters getStaticPublicKey()
  {
    return staticPublicKey;
  }
  
  public ECPublicKeyParameters getEphemeralPublicKey()
  {
    return ephemeralPublicKey;
  }
}
