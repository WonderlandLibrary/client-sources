package org.spongycastle.crypto.prng;

import java.security.SecureRandom;
import org.spongycastle.crypto.prng.drbg.SP80090DRBG;



public class SP800SecureRandom
  extends SecureRandom
{
  private final DRBGProvider drbgProvider;
  private final boolean predictionResistant;
  private final SecureRandom randomSource;
  private final EntropySource entropySource;
  private SP80090DRBG drbg;
  
  SP800SecureRandom(SecureRandom randomSource, EntropySource entropySource, DRBGProvider drbgProvider, boolean predictionResistant)
  {
    this.randomSource = randomSource;
    this.entropySource = entropySource;
    this.drbgProvider = drbgProvider;
    this.predictionResistant = predictionResistant;
  }
  
  public void setSeed(byte[] seed)
  {
    synchronized (this)
    {
      if (randomSource != null)
      {
        randomSource.setSeed(seed);
      }
    }
  }
  
  public void setSeed(long seed)
  {
    synchronized (this)
    {

      if (randomSource != null)
      {
        randomSource.setSeed(seed);
      }
    }
  }
  
  public void nextBytes(byte[] bytes)
  {
    synchronized (this)
    {
      if (drbg == null)
      {
        drbg = drbgProvider.get(entropySource);
      }
      

      if (drbg.generate(bytes, null, predictionResistant) < 0)
      {
        drbg.reseed(null);
        drbg.generate(bytes, null, predictionResistant);
      }
    }
  }
  
  public byte[] generateSeed(int numBytes)
  {
    return EntropyUtil.generateSeed(entropySource, numBytes);
  }
  





  public void reseed(byte[] additionalInput)
  {
    synchronized (this)
    {
      if (drbg == null)
      {
        drbg = drbgProvider.get(entropySource);
      }
      
      drbg.reseed(additionalInput);
    }
  }
}
