package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.math.ec.WNafUtil;
import org.spongycastle.util.BigIntegers;


class DHParametersHelper
{
  private static final BigInteger ONE = BigInteger.valueOf(1L);
  private static final BigInteger TWO = BigInteger.valueOf(2L);
  


  DHParametersHelper() {}
  


  static BigInteger[] generateSafePrimes(int size, int certainty, SecureRandom random)
  {
    int qLength = size - 1;
    int minWeight = size >>> 2;
    BigInteger q;
    BigInteger p;
    do {
      q = new BigInteger(qLength, 2, random);
      

      p = q.shiftLeft(1).add(ONE);
    }
    while ((!p.isProbablePrime(certainty)) || 
    



      ((certainty > 2) && (!q.isProbablePrime(certainty - 2))) || 
      









      (WNafUtil.getNafWeight(p) < minWeight));
    






    return new BigInteger[] { p, q };
  }
  





  static BigInteger selectGenerator(BigInteger p, BigInteger q, SecureRandom random)
  {
    BigInteger pMinusTwo = p.subtract(TWO);
    






    BigInteger g;
    






    do
    {
      BigInteger h = BigIntegers.createRandomInRange(TWO, pMinusTwo, random);
      
      g = h.modPow(TWO, p);
    }
    while (g.equals(ONE));
    

    return g;
  }
}
