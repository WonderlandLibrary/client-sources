package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.params.GOST3410KeyGenerationParameters;
import org.spongycastle.crypto.params.GOST3410Parameters;
import org.spongycastle.crypto.params.GOST3410PrivateKeyParameters;
import org.spongycastle.crypto.params.GOST3410PublicKeyParameters;
import org.spongycastle.math.ec.WNafUtil;






public class GOST3410KeyPairGenerator
  implements AsymmetricCipherKeyPairGenerator
{
  private GOST3410KeyGenerationParameters param;
  
  public GOST3410KeyPairGenerator() {}
  
  public void init(KeyGenerationParameters param)
  {
    this.param = ((GOST3410KeyGenerationParameters)param);
  }
  

  public AsymmetricCipherKeyPair generateKeyPair()
  {
    GOST3410Parameters GOST3410Params = param.getParameters();
    SecureRandom random = param.getRandom();
    
    BigInteger q = GOST3410Params.getQ();
    BigInteger p = GOST3410Params.getP();
    BigInteger a = GOST3410Params.getA();
    
    int minWeight = 64;
    BigInteger x;
    do {
      x = new BigInteger(256, random);
    }
    while ((x.signum() < 1) || (x.compareTo(q) >= 0) || 
    



      (WNafUtil.getNafWeight(x) < minWeight));
    









    BigInteger y = a.modPow(x, p);
    
    return new AsymmetricCipherKeyPair(new GOST3410PublicKeyParameters(y, GOST3410Params), new GOST3410PrivateKeyParameters(x, GOST3410Params));
  }
}
