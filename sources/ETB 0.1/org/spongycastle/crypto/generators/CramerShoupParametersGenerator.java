package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.params.CramerShoupParameters;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.util.BigIntegers;


public class CramerShoupParametersGenerator
{
  private static final BigInteger ONE = BigInteger.valueOf(1L);
  

  private int size;
  

  private int certainty;
  

  private SecureRandom random;
  

  public CramerShoupParametersGenerator() {}
  

  public void init(int size, int certainty, SecureRandom random)
  {
    this.size = size;
    this.certainty = certainty;
    this.random = random;
  }
  










  public CramerShoupParameters generateParameters()
  {
    BigInteger[] safePrimes = ParametersHelper.generateSafePrimes(size, certainty, random);
    

    BigInteger q = safePrimes[1];
    BigInteger g1 = ParametersHelper.selectGenerator(q, random);
    BigInteger g2 = ParametersHelper.selectGenerator(q, random);
    while (g1.equals(g2))
    {
      g2 = ParametersHelper.selectGenerator(q, random);
    }
    
    return new CramerShoupParameters(q, g1, g2, new SHA256Digest());
  }
  
  public CramerShoupParameters generateParameters(DHParameters dhParams)
  {
    BigInteger p = dhParams.getP();
    BigInteger g1 = dhParams.getG();
    

    BigInteger g2 = ParametersHelper.selectGenerator(p, random);
    while (g1.equals(g2))
    {
      g2 = ParametersHelper.selectGenerator(p, random);
    }
    
    return new CramerShoupParameters(p, g1, g2, new SHA256Digest());
  }
  

  private static class ParametersHelper
  {
    private static final BigInteger TWO = BigInteger.valueOf(2L);
    


    private ParametersHelper() {}
    


    static BigInteger[] generateSafePrimes(int size, int certainty, SecureRandom random)
    {
      int qLength = size - 1;
      BigInteger q;
      BigInteger p;
      for (;;) {
        q = new BigInteger(qLength, 2, random);
        p = q.shiftLeft(1).add(CramerShoupParametersGenerator.ONE);
        if (p.isProbablePrime(certainty)) { if (certainty > 2) { if (q.isProbablePrime(certainty)) {
              break;
            }
          }
        }
      }
      return new BigInteger[] { p, q };
    }
    
    static BigInteger selectGenerator(BigInteger p, SecureRandom random)
    {
      BigInteger pMinusTwo = p.subtract(TWO);
      

      BigInteger g;
      

      do
      {
        BigInteger h = BigIntegers.createRandomInRange(TWO, pMinusTwo, random);
        
        g = h.modPow(TWO, p);
      }
      while (g.equals(CramerShoupParametersGenerator.ONE));
      
      return g;
    }
  }
}
