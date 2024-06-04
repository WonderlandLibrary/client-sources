package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.params.RSAKeyGenerationParameters;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.spongycastle.math.Primes;
import org.spongycastle.math.ec.WNafUtil;





public class RSAKeyPairGenerator
  implements AsymmetricCipherKeyPairGenerator
{
  private static final BigInteger ONE = BigInteger.valueOf(1L);
  private RSAKeyGenerationParameters param;
  private int iterations;
  
  public RSAKeyPairGenerator() {}
  
  public void init(KeyGenerationParameters param) {
    this.param = ((RSAKeyGenerationParameters)param);
    iterations = getNumberOfIterations(this.param.getStrength(), this.param.getCertainty());
  }
  
  public AsymmetricCipherKeyPair generateKeyPair()
  {
    AsymmetricCipherKeyPair result = null;
    boolean done = false;
    



    int strength = param.getStrength();
    int pbitlength = (strength + 1) / 2;
    int qbitlength = strength - pbitlength;
    int mindiffbits = strength / 2 - 100;
    
    if (mindiffbits < strength / 3)
    {
      mindiffbits = strength / 3;
    }
    
    int minWeight = strength >> 2;
    

    BigInteger dLowerBound = BigInteger.valueOf(2L).pow(strength / 2);
    
    BigInteger squaredBound = ONE.shiftLeft(strength - 1);
    
    BigInteger minDiff = ONE.shiftLeft(mindiffbits);
    
    while (!done)
    {


      BigInteger e = param.getPublicExponent();
      
      BigInteger p = chooseRandomPrime(pbitlength, e, squaredBound);
      
      BigInteger q;
      
      BigInteger n;
      for (;;)
      {
        q = chooseRandomPrime(qbitlength, e, squaredBound);
        

        BigInteger diff = q.subtract(p).abs();
        if ((diff.bitLength() >= mindiffbits) && (diff.compareTo(minDiff) > 0))
        {






          n = p.multiply(q);
          
          if (n.bitLength() != strength)
          {




            p = p.max(q);



          }
          else
          {



            if (WNafUtil.getNafWeight(n) >= minWeight)
              break;
            p = chooseRandomPrime(pbitlength, e, squaredBound);
          }
        }
      }
      


      if (p.compareTo(q) < 0)
      {
        BigInteger gcd = p;
        p = q;
        q = gcd;
      }
      
      BigInteger pSub1 = p.subtract(ONE);
      BigInteger qSub1 = q.subtract(ONE);
      BigInteger gcd = pSub1.gcd(qSub1);
      BigInteger lcm = pSub1.divide(gcd).multiply(qSub1);
      



      BigInteger d = e.modInverse(lcm);
      
      if (d.compareTo(dLowerBound) > 0)
      {




        done = true;
        






        BigInteger dP = d.remainder(pSub1);
        BigInteger dQ = d.remainder(qSub1);
        BigInteger qInv = q.modInverse(p);
        
        result = new AsymmetricCipherKeyPair(new RSAKeyParameters(false, n, e), new RSAPrivateCrtKeyParameters(n, e, d, p, q, dP, dQ, qInv));
      }
    }
    

    return result;
  }
  







  protected BigInteger chooseRandomPrime(int bitlength, BigInteger e, BigInteger sqrdBound)
  {
    for (int i = 0; i != 5 * bitlength; i++)
    {
      BigInteger p = new BigInteger(bitlength, 1, param.getRandom());
      
      if (!p.mod(e).equals(ONE))
      {



        if (p.multiply(p).compareTo(sqrdBound) >= 0)
        {



          if (isProbablePrime(p))
          {



            if (e.gcd(p.subtract(ONE)).equals(ONE))
            {



              return p; } } }
      }
    }
    throw new IllegalStateException("unable to generate prime number for RSA key");
  }
  



  protected boolean isProbablePrime(BigInteger x)
  {
    return (!Primes.hasAnySmallFactors(x)) && (Primes.isMRProbablePrime(x, param.getRandom(), iterations));
  }
  





  private static int getNumberOfIterations(int bits, int certainty)
  {
    if (bits >= 1536)
    {
      return certainty <= 128 ? 4 : certainty <= 100 ? 3 : 4 + (certainty - 128 + 1) / 2;
    }
    

    if (bits >= 1024)
    {
      return certainty <= 112 ? 5 : certainty <= 100 ? 4 : 5 + (certainty - 112 + 1) / 2;
    }
    

    if (bits >= 512)
    {
      return certainty <= 100 ? 7 : certainty <= 80 ? 5 : 7 + (certainty - 100 + 1) / 2;
    }
    



    return certainty <= 80 ? 40 : 40 + (certainty - 80 + 1) / 2;
  }
}
