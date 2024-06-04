package org.spongycastle.crypto.generators;

import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.params.ECPublicKeyParameters;

public class DSTU4145KeyPairGenerator extends ECKeyPairGenerator
{
  public DSTU4145KeyPairGenerator() {}
  
  public AsymmetricCipherKeyPair generateKeyPair()
  {
    AsymmetricCipherKeyPair pair = super.generateKeyPair();
    
    ECPublicKeyParameters pub = (ECPublicKeyParameters)pair.getPublic();
    org.spongycastle.crypto.params.ECPrivateKeyParameters priv = (org.spongycastle.crypto.params.ECPrivateKeyParameters)pair.getPrivate();
    
    pub = new ECPublicKeyParameters(pub.getQ().negate(), pub.getParameters());
    
    return new AsymmetricCipherKeyPair(pub, priv);
  }
}
