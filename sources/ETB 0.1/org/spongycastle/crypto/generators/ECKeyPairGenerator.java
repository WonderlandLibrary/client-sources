package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECKeyGenerationParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.math.ec.ECConstants;
import org.spongycastle.math.ec.ECMultiplier;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.ec.FixedPointCombMultiplier;
import org.spongycastle.math.ec.WNafUtil;

public class ECKeyPairGenerator
  implements AsymmetricCipherKeyPairGenerator, ECConstants
{
  ECDomainParameters params;
  SecureRandom random;
  
  public ECKeyPairGenerator() {}
  
  public void init(KeyGenerationParameters param)
  {
    ECKeyGenerationParameters ecP = (ECKeyGenerationParameters)param;
    
    random = ecP.getRandom();
    params = ecP.getDomainParameters();
    
    if (random == null)
    {
      random = new SecureRandom();
    }
  }
  




  public AsymmetricCipherKeyPair generateKeyPair()
  {
    BigInteger n = params.getN();
    int nBitLength = n.bitLength();
    int minWeight = nBitLength >>> 2;
    
    BigInteger d;
    do
    {
      d = new BigInteger(nBitLength, random);
    }
    while ((d.compareTo(TWO) < 0) || (d.compareTo(n) >= 0) || 
    



      (WNafUtil.getNafWeight(d) < minWeight));
    






    ECPoint Q = createBasePointMultiplier().multiply(params.getG(), d);
    
    return new AsymmetricCipherKeyPair(new ECPublicKeyParameters(Q, params), new ECPrivateKeyParameters(d, params));
  }
  


  protected ECMultiplier createBasePointMultiplier()
  {
    return new FixedPointCombMultiplier();
  }
}
