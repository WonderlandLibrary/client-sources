package org.spongycastle.pqc.crypto.xmss;

import java.security.SecureRandom;
import org.spongycastle.crypto.KeyGenerationParameters;










public final class XMSSMTKeyGenerationParameters
  extends KeyGenerationParameters
{
  private final XMSSMTParameters xmssmtParameters;
  
  public XMSSMTKeyGenerationParameters(XMSSMTParameters xmssmtParameters, SecureRandom prng)
  {
    super(prng, -1);
    
    this.xmssmtParameters = xmssmtParameters;
  }
  
  public XMSSMTParameters getParameters()
  {
    return xmssmtParameters;
  }
}
