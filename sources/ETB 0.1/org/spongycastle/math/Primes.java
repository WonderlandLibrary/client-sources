package org.spongycastle.math;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.Digest;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.BigIntegers;






public abstract class Primes
{
  public static final int SMALL_FACTOR_LIMIT = 211;
  private static final BigInteger ONE = BigInteger.valueOf(1L);
  private static final BigInteger TWO = BigInteger.valueOf(2L);
  private static final BigInteger THREE = BigInteger.valueOf(3L);
  
  public Primes() {}
  
  public static class MROutput
  {
    private boolean provablyComposite;
    private BigInteger factor;
    
    private static MROutput probablyPrime()
    {
      return new MROutput(false, null);
    }
    
    private static MROutput provablyCompositeWithFactor(BigInteger factor)
    {
      return new MROutput(true, factor);
    }
    
    private static MROutput provablyCompositeNotPrimePower()
    {
      return new MROutput(true, null);
    }
    



    private MROutput(boolean provablyComposite, BigInteger factor)
    {
      this.provablyComposite = provablyComposite;
      this.factor = factor;
    }
    
    public BigInteger getFactor()
    {
      return factor;
    }
    
    public boolean isProvablyComposite()
    {
      return provablyComposite;
    }
    
    public boolean isNotPrimePower()
    {
      return (provablyComposite) && (factor == null);
    }
  }
  


  public static class STOutput
  {
    private BigInteger prime;
    
    private byte[] primeSeed;
    
    private int primeGenCounter;
    

    private STOutput(BigInteger prime, byte[] primeSeed, int primeGenCounter)
    {
      this.prime = prime;
      this.primeSeed = primeSeed;
      this.primeGenCounter = primeGenCounter;
    }
    
    public BigInteger getPrime()
    {
      return prime;
    }
    
    public byte[] getPrimeSeed()
    {
      return primeSeed;
    }
    
    public int getPrimeGenCounter()
    {
      return primeGenCounter;
    }
  }
  














  public static STOutput generateSTRandomPrime(Digest hash, int length, byte[] inputSeed)
  {
    if (hash == null)
    {
      throw new IllegalArgumentException("'hash' cannot be null");
    }
    if (length < 2)
    {
      throw new IllegalArgumentException("'length' must be >= 2");
    }
    if ((inputSeed == null) || (inputSeed.length == 0))
    {
      throw new IllegalArgumentException("'inputSeed' cannot be null or empty");
    }
    
    return implSTRandomPrime(hash, length, Arrays.clone(inputSeed));
  }
  
















  public static MROutput enhancedMRProbablePrimeTest(BigInteger candidate, SecureRandom random, int iterations)
  {
    checkCandidate(candidate, "candidate");
    
    if (random == null)
    {
      throw new IllegalArgumentException("'random' cannot be null");
    }
    if (iterations < 1)
    {
      throw new IllegalArgumentException("'iterations' must be > 0");
    }
    
    if (candidate.bitLength() == 2)
    {
      return MROutput.access$000();
    }
    if (!candidate.testBit(0))
    {
      return MROutput.provablyCompositeWithFactor(TWO);
    }
    
    BigInteger w = candidate;
    BigInteger wSubOne = candidate.subtract(ONE);
    BigInteger wSubTwo = candidate.subtract(TWO);
    
    int a = wSubOne.getLowestSetBit();
    BigInteger m = wSubOne.shiftRight(a);
    
    for (int i = 0; i < iterations; i++)
    {
      BigInteger b = BigIntegers.createRandomInRange(TWO, wSubTwo, random);
      BigInteger g = b.gcd(w);
      
      if (g.compareTo(ONE) > 0)
      {
        return MROutput.provablyCompositeWithFactor(g);
      }
      
      BigInteger z = b.modPow(m, w);
      
      if ((!z.equals(ONE)) && (!z.equals(wSubOne)))
      {



        boolean primeToBase = false;
        
        BigInteger x = z;
        for (int j = 1; j < a; j++)
        {
          z = z.modPow(TWO, w);
          
          if (z.equals(wSubOne))
          {
            primeToBase = true;
            break;
          }
          
          if (z.equals(ONE)) {
            break;
          }
          

          x = z;
        }
        
        if (!primeToBase)
        {
          if (!z.equals(ONE))
          {
            x = z;
            z = z.modPow(TWO, w);
            
            if (!z.equals(ONE))
            {
              x = z;
            }
          }
          
          g = x.subtract(ONE).gcd(w);
          
          if (g.compareTo(ONE) > 0)
          {
            return MROutput.provablyCompositeWithFactor(g);
          }
          
          return MROutput.access$200();
        }
      }
    }
    return MROutput.access$000();
  }
  









  public static boolean hasAnySmallFactors(BigInteger candidate)
  {
    checkCandidate(candidate, "candidate");
    
    return implHasAnySmallFactors(candidate);
  }
  
















  public static boolean isMRProbablePrime(BigInteger candidate, SecureRandom random, int iterations)
  {
    checkCandidate(candidate, "candidate");
    
    if (random == null)
    {
      throw new IllegalArgumentException("'random' cannot be null");
    }
    if (iterations < 1)
    {
      throw new IllegalArgumentException("'iterations' must be > 0");
    }
    
    if (candidate.bitLength() == 2)
    {
      return true;
    }
    if (!candidate.testBit(0))
    {
      return false;
    }
    
    BigInteger w = candidate;
    BigInteger wSubOne = candidate.subtract(ONE);
    BigInteger wSubTwo = candidate.subtract(TWO);
    
    int a = wSubOne.getLowestSetBit();
    BigInteger m = wSubOne.shiftRight(a);
    
    for (int i = 0; i < iterations; i++)
    {
      BigInteger b = BigIntegers.createRandomInRange(TWO, wSubTwo, random);
      
      if (!implMRProbablePrimeToBase(w, wSubOne, m, a, b))
      {
        return false;
      }
    }
    
    return true;
  }
  












  public static boolean isMRProbablePrimeToBase(BigInteger candidate, BigInteger base)
  {
    checkCandidate(candidate, "candidate");
    checkCandidate(base, "base");
    
    if (base.compareTo(candidate.subtract(ONE)) >= 0)
    {
      throw new IllegalArgumentException("'base' must be < ('candidate' - 1)");
    }
    
    if (candidate.bitLength() == 2)
    {
      return true;
    }
    
    BigInteger w = candidate;
    BigInteger wSubOne = candidate.subtract(ONE);
    
    int a = wSubOne.getLowestSetBit();
    BigInteger m = wSubOne.shiftRight(a);
    
    return implMRProbablePrimeToBase(w, wSubOne, m, a, base);
  }
  
  private static void checkCandidate(BigInteger n, String name)
  {
    if ((n == null) || (n.signum() < 1) || (n.bitLength() < 2))
    {
      throw new IllegalArgumentException("'" + name + "' must be non-null and >= 2");
    }
  }
  



  private static boolean implHasAnySmallFactors(BigInteger x)
  {
    int m = 223092870;
    int r = x.mod(BigInteger.valueOf(m)).intValue();
    if ((r % 2 == 0) || (r % 3 == 0) || (r % 5 == 0) || (r % 7 == 0) || (r % 11 == 0) || (r % 13 == 0) || (r % 17 == 0) || (r % 19 == 0) || (r % 23 == 0))
    {

      return true;
    }
    
    m = 58642669;
    r = x.mod(BigInteger.valueOf(m)).intValue();
    if ((r % 29 == 0) || (r % 31 == 0) || (r % 37 == 0) || (r % 41 == 0) || (r % 43 == 0))
    {
      return true;
    }
    
    m = 600662303;
    r = x.mod(BigInteger.valueOf(m)).intValue();
    if ((r % 47 == 0) || (r % 53 == 0) || (r % 59 == 0) || (r % 61 == 0) || (r % 67 == 0))
    {
      return true;
    }
    
    m = 33984931;
    r = x.mod(BigInteger.valueOf(m)).intValue();
    if ((r % 71 == 0) || (r % 73 == 0) || (r % 79 == 0) || (r % 83 == 0))
    {
      return true;
    }
    
    m = 89809099;
    r = x.mod(BigInteger.valueOf(m)).intValue();
    if ((r % 89 == 0) || (r % 97 == 0) || (r % 101 == 0) || (r % 103 == 0))
    {
      return true;
    }
    
    m = 167375713;
    r = x.mod(BigInteger.valueOf(m)).intValue();
    if ((r % 107 == 0) || (r % 109 == 0) || (r % 113 == 0) || (r % 127 == 0))
    {
      return true;
    }
    
    m = 371700317;
    r = x.mod(BigInteger.valueOf(m)).intValue();
    if ((r % 131 == 0) || (r % 137 == 0) || (r % 139 == 0) || (r % 149 == 0))
    {
      return true;
    }
    
    m = 645328247;
    r = x.mod(BigInteger.valueOf(m)).intValue();
    if ((r % 151 == 0) || (r % 157 == 0) || (r % 163 == 0) || (r % 167 == 0))
    {
      return true;
    }
    
    m = 1070560157;
    r = x.mod(BigInteger.valueOf(m)).intValue();
    if ((r % 173 == 0) || (r % 179 == 0) || (r % 181 == 0) || (r % 191 == 0))
    {
      return true;
    }
    
    m = 1596463769;
    r = x.mod(BigInteger.valueOf(m)).intValue();
    if ((r % 193 == 0) || (r % 197 == 0) || (r % 199 == 0) || (r % 211 == 0))
    {
      return true;
    }
    




    return false;
  }
  
  private static boolean implMRProbablePrimeToBase(BigInteger w, BigInteger wSubOne, BigInteger m, int a, BigInteger b)
  {
    BigInteger z = b.modPow(m, w);
    
    if ((z.equals(ONE)) || (z.equals(wSubOne)))
    {
      return true;
    }
    
    boolean result = false;
    
    for (int j = 1; j < a; j++)
    {
      z = z.modPow(TWO, w);
      
      if (z.equals(wSubOne))
      {
        result = true;
        break;
      }
      
      if (z.equals(ONE))
      {
        return false;
      }
    }
    
    return result;
  }
  
  private static STOutput implSTRandomPrime(Digest d, int length, byte[] primeSeed)
  {
    int dLen = d.getDigestSize();
    
    if (length < 33)
    {
      int primeGenCounter = 0;
      
      byte[] c0 = new byte[dLen];
      byte[] c1 = new byte[dLen];
      
      for (;;)
      {
        hash(d, primeSeed, c0, 0);
        inc(primeSeed, 1);
        
        hash(d, primeSeed, c1, 0);
        inc(primeSeed, 1);
        
        int c = extract32(c0) ^ extract32(c1);
        c &= -1 >>> 32 - length;
        c |= 1 << length - 1 | 0x1;
        
        primeGenCounter++;
        
        long c64 = c & 0xFFFFFFFF;
        if (isPrime32(c64))
        {
          return new STOutput(BigInteger.valueOf(c64), primeSeed, primeGenCounter, null);
        }
        
        if (primeGenCounter > 4 * length)
        {
          throw new IllegalStateException("Too many iterations in Shawe-Taylor Random_Prime Routine");
        }
      }
    }
    
    STOutput rec = implSTRandomPrime(d, (length + 3) / 2, primeSeed);
    
    BigInteger c0 = rec.getPrime();
    primeSeed = rec.getPrimeSeed();
    int primeGenCounter = rec.getPrimeGenCounter();
    
    int outlen = 8 * dLen;
    int iterations = (length - 1) / outlen;
    
    int oldCounter = primeGenCounter;
    
    BigInteger x = hashGen(d, primeSeed, iterations + 1);
    x = x.mod(ONE.shiftLeft(length - 1)).setBit(length - 1);
    
    BigInteger c0x2 = c0.shiftLeft(1);
    BigInteger tx2 = x.subtract(ONE).divide(c0x2).add(ONE).shiftLeft(1);
    int dt = 0;
    
    BigInteger c = tx2.multiply(c0).add(ONE);
    




    for (;;)
    {
      if (c.bitLength() > length)
      {
        tx2 = ONE.shiftLeft(length - 1).subtract(ONE).divide(c0x2).add(ONE).shiftLeft(1);
        c = tx2.multiply(c0).add(ONE);
      }
      
      primeGenCounter++;
      






      if (!implHasAnySmallFactors(c))
      {
        BigInteger a = hashGen(d, primeSeed, iterations + 1);
        a = a.mod(c.subtract(THREE)).add(TWO);
        
        tx2 = tx2.add(BigInteger.valueOf(dt));
        dt = 0;
        
        BigInteger z = a.modPow(tx2, c);
        
        if ((c.gcd(z.subtract(ONE)).equals(ONE)) && (z.modPow(c0, c).equals(ONE)))
        {
          return new STOutput(c, primeSeed, primeGenCounter, null);
        }
      }
      else
      {
        inc(primeSeed, iterations + 1);
      }
      
      if (primeGenCounter >= 4 * length + oldCounter)
      {
        throw new IllegalStateException("Too many iterations in Shawe-Taylor Random_Prime Routine");
      }
      
      dt += 2;
      c = c.add(c0x2);
    }
  }
  
  private static int extract32(byte[] bs)
  {
    int result = 0;
    
    int count = Math.min(4, bs.length);
    for (int i = 0; i < count; i++)
    {
      int b = bs[(bs.length - (i + 1))] & 0xFF;
      result |= b << 8 * i;
    }
    
    return result;
  }
  
  private static void hash(Digest d, byte[] input, byte[] output, int outPos)
  {
    d.update(input, 0, input.length);
    d.doFinal(output, outPos);
  }
  
  private static BigInteger hashGen(Digest d, byte[] seed, int count)
  {
    int dLen = d.getDigestSize();
    int pos = count * dLen;
    byte[] buf = new byte[pos];
    for (int i = 0; i < count; i++)
    {
      pos -= dLen;
      hash(d, seed, buf, pos);
      inc(seed, 1);
    }
    return new BigInteger(1, buf);
  }
  
  private static void inc(byte[] seed, int c)
  {
    int pos = seed.length;
    while (c > 0) { pos--; if (pos < 0)
        break;
      c += (seed[pos] & 0xFF);
      seed[pos] = ((byte)c);
      c >>>= 8;
    }
  }
  
  private static boolean isPrime32(long x)
  {
    if (x >>> 32 != 0L)
    {
      throw new IllegalArgumentException("Size limit exceeded");
    }
    




    if (x <= 5L)
    {
      return (x == 2L) || (x == 3L) || (x == 5L);
    }
    
    if (((x & 1L) == 0L) || (x % 3L == 0L) || (x % 5L == 0L))
    {
      return false;
    }
    
    long[] ds = { 1L, 7L, 11L, 13L, 17L, 19L, 23L, 29L };
    long base = 0L;
    int pos = 1;
    

    for (;;)
    {
      if (pos < ds.length)
      {
        long d = base + ds[pos];
        if (x % d == 0L)
        {
          return x < 30L;
        }
        pos++;
      }
      else {
        base += 30L;
        
        if (base * base >= x)
        {
          return true;
        }
        pos = 0;
      }
    }
  }
}
