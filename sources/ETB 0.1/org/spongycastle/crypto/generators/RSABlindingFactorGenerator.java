package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.crypto.params.RSAPrivateCrtKeyParameters;







public class RSABlindingFactorGenerator
{
  private static BigInteger ZERO = BigInteger.valueOf(0L);
  private static BigInteger ONE = BigInteger.valueOf(1L);
  

  private RSAKeyParameters key;
  
  private SecureRandom random;
  

  public RSABlindingFactorGenerator() {}
  

  public void init(CipherParameters param)
  {
    if ((param instanceof ParametersWithRandom))
    {
      ParametersWithRandom rParam = (ParametersWithRandom)param;
      
      key = ((RSAKeyParameters)rParam.getParameters());
      random = rParam.getRandom();
    }
    else
    {
      key = ((RSAKeyParameters)param);
      random = new SecureRandom();
    }
    
    if ((key instanceof RSAPrivateCrtKeyParameters))
    {
      throw new IllegalArgumentException("generator requires RSA public key");
    }
  }
  





  public BigInteger generateBlindingFactor()
  {
    if (key == null)
    {
      throw new IllegalStateException("generator not initialised");
    }
    
    BigInteger m = key.getModulus();
    int length = m.bitLength() - 1;
    
    BigInteger factor;
    BigInteger gcd;
    do
    {
      factor = new BigInteger(length, random);
      gcd = factor.gcd(m);
    }
    while ((factor.equals(ZERO)) || (factor.equals(ONE)) || (!gcd.equals(ONE)));
    
    return factor;
  }
}
