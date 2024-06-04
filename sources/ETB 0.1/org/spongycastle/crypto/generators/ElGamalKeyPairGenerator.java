package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.crypto.params.ElGamalKeyGenerationParameters;
import org.spongycastle.crypto.params.ElGamalParameters;
import org.spongycastle.crypto.params.ElGamalPrivateKeyParameters;
import org.spongycastle.crypto.params.ElGamalPublicKeyParameters;







public class ElGamalKeyPairGenerator
  implements AsymmetricCipherKeyPairGenerator
{
  private ElGamalKeyGenerationParameters param;
  
  public ElGamalKeyPairGenerator() {}
  
  public void init(KeyGenerationParameters param)
  {
    this.param = ((ElGamalKeyGenerationParameters)param);
  }
  
  public AsymmetricCipherKeyPair generateKeyPair()
  {
    DHKeyGeneratorHelper helper = DHKeyGeneratorHelper.INSTANCE;
    ElGamalParameters egp = param.getParameters();
    DHParameters dhp = new DHParameters(egp.getP(), egp.getG(), null, egp.getL());
    
    BigInteger x = helper.calculatePrivate(dhp, param.getRandom());
    BigInteger y = helper.calculatePublic(dhp, x);
    
    return new AsymmetricCipherKeyPair(new ElGamalPublicKeyParameters(y, egp), new ElGamalPrivateKeyParameters(x, egp));
  }
}
