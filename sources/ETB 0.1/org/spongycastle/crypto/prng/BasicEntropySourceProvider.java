package org.spongycastle.crypto.prng;

import java.security.SecureRandom;










public class BasicEntropySourceProvider
  implements EntropySourceProvider
{
  private final SecureRandom _sr;
  private final boolean _predictionResistant;
  
  public BasicEntropySourceProvider(SecureRandom random, boolean isPredictionResistant)
  {
    _sr = random;
    _predictionResistant = isPredictionResistant;
  }
  







  public EntropySource get(final int bitsRequired)
  {
    new EntropySource()
    {
      public boolean isPredictionResistant()
      {
        return _predictionResistant;
      }
      

      public byte[] getEntropy()
      {
        if (((_sr instanceof SP800SecureRandom)) || ((_sr instanceof X931SecureRandom)))
        {
          byte[] rv = new byte[(bitsRequired + 7) / 8];
          
          _sr.nextBytes(rv);
          
          return rv;
        }
        return _sr.generateSeed((bitsRequired + 7) / 8);
      }
      
      public int entropySize()
      {
        return bitsRequired;
      }
    };
  }
}
