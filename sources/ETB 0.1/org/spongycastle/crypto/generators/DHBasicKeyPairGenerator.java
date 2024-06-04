package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.params.DHKeyGenerationParameters;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.crypto.params.DHPrivateKeyParameters;
import org.spongycastle.crypto.params.DHPublicKeyParameters;







public class DHBasicKeyPairGenerator
  implements AsymmetricCipherKeyPairGenerator
{
  private DHKeyGenerationParameters param;
  
  public DHBasicKeyPairGenerator() {}
  
  public void init(KeyGenerationParameters param)
  {
    this.param = ((DHKeyGenerationParameters)param);
  }
  
  public AsymmetricCipherKeyPair generateKeyPair()
  {
    DHKeyGeneratorHelper helper = DHKeyGeneratorHelper.INSTANCE;
    DHParameters dhp = param.getParameters();
    
    BigInteger x = helper.calculatePrivate(dhp, param.getRandom());
    BigInteger y = helper.calculatePublic(dhp, x);
    
    return new AsymmetricCipherKeyPair(new DHPublicKeyParameters(y, dhp), new DHPrivateKeyParameters(x, dhp));
  }
}
