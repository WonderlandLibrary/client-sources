package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.params.DSAParameterGenerationParameters;
import org.spongycastle.crypto.params.DSAParameters;
import org.spongycastle.crypto.params.DSAValidationParameters;
import org.spongycastle.crypto.util.DigestFactory;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.BigIntegers;
import org.spongycastle.util.encoders.Hex;





public class DSAParametersGenerator
{
  private static final BigInteger ZERO = BigInteger.valueOf(0L);
  private static final BigInteger ONE = BigInteger.valueOf(1L);
  private static final BigInteger TWO = BigInteger.valueOf(2L);
  private Digest digest;
  private int L;
  private int N;
  private int certainty;
  private int iterations;
  private SecureRandom random;
  private boolean use186_3;
  private int usageIndex;
  
  public DSAParametersGenerator()
  {
    this(DigestFactory.createSHA1());
  }
  
  public DSAParametersGenerator(Digest digest)
  {
    this.digest = digest;
  }
  










  public void init(int size, int certainty, SecureRandom random)
  {
    L = size;
    N = getDefaultN(size);
    this.certainty = certainty;
    iterations = Math.max(getMinimumIterations(L), (certainty + 1) / 2);
    this.random = random;
    use186_3 = false;
    usageIndex = -1;
  }
  









  public void init(DSAParameterGenerationParameters params)
  {
    int L = params.getL();int N = params.getN();
    
    if ((L < 1024) || (L > 3072) || (L % 1024 != 0))
    {
      throw new IllegalArgumentException("L values must be between 1024 and 3072 and a multiple of 1024");
    }
    if ((L == 1024) && (N != 160))
    {
      throw new IllegalArgumentException("N must be 160 for L = 1024");
    }
    if ((L == 2048) && (N != 224) && (N != 256))
    {
      throw new IllegalArgumentException("N must be 224 or 256 for L = 2048");
    }
    if ((L == 3072) && (N != 256))
    {
      throw new IllegalArgumentException("N must be 256 for L = 3072");
    }
    
    if (digest.getDigestSize() * 8 < N)
    {
      throw new IllegalStateException("Digest output size too small for value of N");
    }
    
    this.L = L;
    this.N = N;
    certainty = params.getCertainty();
    iterations = Math.max(getMinimumIterations(L), (certainty + 1) / 2);
    random = params.getRandom();
    use186_3 = true;
    usageIndex = params.getUsageIndex();
  }
  






  public DSAParameters generateParameters()
  {
    return use186_3 ? 
      generateParameters_FIPS186_3() : 
      generateParameters_FIPS186_2();
  }
  
  private DSAParameters generateParameters_FIPS186_2()
  {
    byte[] seed = new byte[20];
    byte[] part1 = new byte[20];
    byte[] part2 = new byte[20];
    byte[] u = new byte[20];
    int n = (L - 1) / 160;
    byte[] w = new byte[L / 8];
    
    if (!(digest instanceof SHA1Digest))
    {
      throw new IllegalStateException("can only use SHA-1 for generating FIPS 186-2 parameters");
    }
    
    for (;;)
    {
      random.nextBytes(seed);
      
      hash(digest, seed, part1, 0);
      System.arraycopy(seed, 0, part2, 0, seed.length);
      inc(part2);
      hash(digest, part2, part2, 0);
      
      for (int i = 0; i != u.length; i++)
      {
        u[i] = ((byte)(part1[i] ^ part2[i]));
      }
      
      int tmp140_139 = 0; byte[] tmp140_137 = u;tmp140_137[tmp140_139] = ((byte)(tmp140_137[tmp140_139] | 0xFFFFFF80)); byte[] 
        tmp151_147 = u;tmp151_147[19] = ((byte)(tmp151_147[19] | 0x1));
      
      BigInteger q = new BigInteger(1, u);
      
      if (isProbablePrime(q))
      {



        byte[] offset = Arrays.clone(seed);
        inc(offset);
        
        for (int counter = 0; counter < 4096; counter++)
        {

          for (int k = 1; k <= n; k++)
          {
            inc(offset);
            hash(digest, offset, w, w.length - k * part1.length);
          }
          
          int remaining = w.length - n * part1.length;
          inc(offset);
          hash(digest, offset, part1, 0);
          System.arraycopy(part1, part1.length - remaining, w, 0, remaining); int 
          
            tmp288_287 = 0; byte[] tmp288_285 = w;tmp288_285[tmp288_287] = ((byte)(tmp288_285[tmp288_287] | 0xFFFFFF80));
          

          BigInteger x = new BigInteger(1, w);
          
          BigInteger c = x.mod(q.shiftLeft(1));
          
          BigInteger p = x.subtract(c.subtract(ONE));
          
          if (p.bitLength() == L)
          {



            if (isProbablePrime(p))
            {
              BigInteger g = calculateGenerator_FIPS186_2(p, q, random);
              
              return new DSAParameters(p, q, g, new DSAValidationParameters(seed, counter));
            } }
        }
      }
    }
  }
  
  private static BigInteger calculateGenerator_FIPS186_2(BigInteger p, BigInteger q, SecureRandom r) {
    BigInteger e = p.subtract(ONE).divide(q);
    BigInteger pSub2 = p.subtract(TWO);
    
    for (;;)
    {
      BigInteger h = BigIntegers.createRandomInRange(TWO, pSub2, r);
      BigInteger g = h.modPow(e, p);
      if (g.bitLength() > 1)
      {
        return g;
      }
    }
  }
  






  private DSAParameters generateParameters_FIPS186_3()
  {
    Digest d = digest;
    int outlen = d.getDigestSize() * 8;
    






    int seedlen = N;
    byte[] seed = new byte[seedlen / 8];
    

    int n = (L - 1) / outlen;
    

    int b = (L - 1) % outlen;
    
    byte[] w = new byte[L / 8];
    byte[] output = new byte[d.getDigestSize()];
    
    for (;;)
    {
      random.nextBytes(seed);
      

      hash(d, seed, output, 0);
      
      BigInteger U = new BigInteger(1, output).mod(ONE.shiftLeft(N - 1));
      

      BigInteger q = U.setBit(0).setBit(N - 1);
      

      if (isProbablePrime(q))
      {






        byte[] offset = Arrays.clone(seed);
        

        int counterLimit = 4 * L;
        for (int counter = 0; counter < counterLimit; counter++)
        {




          for (int j = 1; j <= n; j++)
          {
            inc(offset);
            hash(d, offset, w, w.length - j * output.length);
          }
          
          int remaining = w.length - n * output.length;
          inc(offset);
          hash(d, offset, output, 0);
          System.arraycopy(output, output.length - remaining, w, 0, remaining); int 
          

            tmp252_251 = 0; byte[] tmp252_249 = w;tmp252_249[tmp252_251] = ((byte)(tmp252_249[tmp252_251] | 0xFFFFFF80));
          

          BigInteger X = new BigInteger(1, w);
          

          BigInteger c = X.mod(q.shiftLeft(1));
          

          BigInteger p = X.subtract(c.subtract(ONE));
          

          if (p.bitLength() == L)
          {




            if (isProbablePrime(p))
            {


              if (usageIndex >= 0)
              {
                BigInteger g = calculateGenerator_FIPS186_3_Verifiable(d, p, q, seed, usageIndex);
                if (g != null)
                {
                  return new DSAParameters(p, q, g, new DSAValidationParameters(seed, counter, usageIndex));
                }
              }
              
              BigInteger g = calculateGenerator_FIPS186_3_Unverifiable(p, q, random);
              
              return new DSAParameters(p, q, g, new DSAValidationParameters(seed, counter));
            }
          }
        }
      }
    }
  }
  









  private boolean isProbablePrime(BigInteger x)
  {
    return x.isProbablePrime(certainty);
  }
  

  private static BigInteger calculateGenerator_FIPS186_3_Unverifiable(BigInteger p, BigInteger q, SecureRandom r)
  {
    return calculateGenerator_FIPS186_2(p, q, r);
  }
  


  private static BigInteger calculateGenerator_FIPS186_3_Verifiable(Digest d, BigInteger p, BigInteger q, byte[] seed, int index)
  {
    BigInteger e = p.subtract(ONE).divide(q);
    byte[] ggen = Hex.decode("6767656E");
    

    byte[] U = new byte[seed.length + ggen.length + 1 + 2];
    System.arraycopy(seed, 0, U, 0, seed.length);
    System.arraycopy(ggen, 0, U, seed.length, ggen.length);
    U[(U.length - 3)] = ((byte)index);
    
    byte[] w = new byte[d.getDigestSize()];
    for (int count = 1; count < 65536; count++)
    {
      inc(U);
      hash(d, U, w, 0);
      BigInteger W = new BigInteger(1, w);
      BigInteger g = W.modPow(e, p);
      if (g.compareTo(TWO) >= 0)
      {
        return g;
      }
    }
    
    return null;
  }
  
  private static void hash(Digest d, byte[] input, byte[] output, int outputPos)
  {
    d.update(input, 0, input.length);
    d.doFinal(output, outputPos);
  }
  
  private static int getDefaultN(int L)
  {
    return L > 1024 ? 256 : 160;
  }
  

  private static int getMinimumIterations(int L)
  {
    return L <= 1024 ? 40 : 48 + 8 * ((L - 1) / 1024);
  }
  
  private static void inc(byte[] buf)
  {
    for (int i = buf.length - 1; i >= 0; i--)
    {
      byte b = (byte)(buf[i] + 1 & 0xFF);
      buf[i] = b;
      
      if (b != 0) {
        break;
      }
    }
  }
}
