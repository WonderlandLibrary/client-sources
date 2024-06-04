package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.params.DSAKeyGenerationParameters;
import org.spongycastle.crypto.params.DSAParameters;
import org.spongycastle.crypto.params.DSAPrivateKeyParameters;
import org.spongycastle.crypto.params.DSAPublicKeyParameters;
import org.spongycastle.math.ec.WNafUtil;
import org.spongycastle.util.BigIntegers;








public class DSAKeyPairGenerator
  implements AsymmetricCipherKeyPairGenerator
{
  private static final BigInteger ONE = BigInteger.valueOf(1L);
  private DSAKeyGenerationParameters param;
  
  public DSAKeyPairGenerator() {}
  
  public void init(KeyGenerationParameters param)
  {
    this.param = ((DSAKeyGenerationParameters)param);
  }
  
  public AsymmetricCipherKeyPair generateKeyPair()
  {
    DSAParameters dsaParams = param.getParameters();
    
    BigInteger x = generatePrivateKey(dsaParams.getQ(), param.getRandom());
    BigInteger y = calculatePublicKey(dsaParams.getP(), dsaParams.getG(), x);
    
    return new AsymmetricCipherKeyPair(new DSAPublicKeyParameters(y, dsaParams), new DSAPrivateKeyParameters(x, dsaParams));
  }
  



  private static BigInteger generatePrivateKey(BigInteger q, SecureRandom random)
  {
    int minWeight = q.bitLength() >>> 2;
    



    for (;;)
    {
      BigInteger x = BigIntegers.createRandomInRange(ONE, q.subtract(ONE), random);
      if (WNafUtil.getNafWeight(x) >= minWeight)
      {
        return x;
      }
    }
  }
  
  private static BigInteger calculatePublicKey(BigInteger p, BigInteger g, BigInteger x)
  {
    return g.modPow(x, p);
  }
}
