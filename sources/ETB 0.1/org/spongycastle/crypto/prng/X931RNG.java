package org.spongycastle.crypto.prng;

import org.spongycastle.crypto.BlockCipher;





public class X931RNG
{
  private static final long BLOCK64_RESEED_MAX = 32768L;
  private static final long BLOCK128_RESEED_MAX = 8388608L;
  private static final int BLOCK64_MAX_BITS_REQUEST = 4096;
  private static final int BLOCK128_MAX_BITS_REQUEST = 262144;
  private final BlockCipher engine;
  private final EntropySource entropySource;
  private final byte[] DT;
  private final byte[] I;
  private final byte[] R;
  private byte[] V;
  private long reseedCounter = 1L;
  





  public X931RNG(BlockCipher engine, byte[] dateTimeVector, EntropySource entropySource)
  {
    this.engine = engine;
    this.entropySource = entropySource;
    
    DT = new byte[engine.getBlockSize()];
    
    System.arraycopy(dateTimeVector, 0, DT, 0, DT.length);
    
    I = new byte[engine.getBlockSize()];
    R = new byte[engine.getBlockSize()];
  }
  








  int generate(byte[] output, boolean predictionResistant)
  {
    if (R.length == 8)
    {
      if (reseedCounter > 32768L)
      {
        return -1;
      }
      
      if (isTooLarge(output, 512))
      {
        throw new IllegalArgumentException("Number of bits per request limited to 4096");
      }
    }
    else
    {
      if (reseedCounter > 8388608L)
      {
        return -1;
      }
      
      if (isTooLarge(output, 32768))
      {
        throw new IllegalArgumentException("Number of bits per request limited to 262144");
      }
    }
    
    if ((predictionResistant) || (V == null))
    {
      V = entropySource.getEntropy();
      if (V.length != engine.getBlockSize())
      {
        throw new IllegalStateException("Insufficient entropy returned");
      }
    }
    
    int m = output.length / R.length;
    
    for (int i = 0; i < m; i++)
    {
      engine.processBlock(DT, 0, I, 0);
      process(R, I, V);
      process(V, R, I);
      
      System.arraycopy(R, 0, output, i * R.length, R.length);
      
      increment(DT);
    }
    
    int bytesToCopy = output.length - m * R.length;
    
    if (bytesToCopy > 0)
    {
      engine.processBlock(DT, 0, I, 0);
      process(R, I, V);
      process(V, R, I);
      
      System.arraycopy(R, 0, output, m * R.length, bytesToCopy);
      
      increment(DT);
    }
    
    reseedCounter += 1L;
    
    return output.length;
  }
  



  void reseed()
  {
    V = entropySource.getEntropy();
    if (V.length != engine.getBlockSize())
    {
      throw new IllegalStateException("Insufficient entropy returned");
    }
    reseedCounter = 1L;
  }
  
  EntropySource getEntropySource()
  {
    return entropySource;
  }
  
  private void process(byte[] res, byte[] a, byte[] b)
  {
    for (int i = 0; i != res.length; i++)
    {
      res[i] = ((byte)(a[i] ^ b[i]));
    }
    
    engine.processBlock(res, 0, res, 0);
  }
  
  private void increment(byte[] val)
  {
    for (int i = val.length - 1; i >= 0; i--)
    {
      int tmp11_10 = i;val;
      


















































































































































      if ((tmp11_9[tmp11_10] = (byte)(tmp11_9[tmp11_10] + 1)) != 0) {
        break;
      }
    }
  }
  

  private static boolean isTooLarge(byte[] bytes, int maxBytes)
  {
    return (bytes != null) && (bytes.length > maxBytes);
  }
}
