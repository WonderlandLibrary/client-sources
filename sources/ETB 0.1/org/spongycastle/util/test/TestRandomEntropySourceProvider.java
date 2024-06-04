package org.spongycastle.util.test;

import java.security.SecureRandom;
import org.spongycastle.crypto.prng.EntropySource;
import org.spongycastle.crypto.prng.EntropySourceProvider;










public class TestRandomEntropySourceProvider
  implements EntropySourceProvider
{
  private final SecureRandom _sr;
  private final boolean _predictionResistant;
  
  public TestRandomEntropySourceProvider(boolean isPredictionResistant)
  {
    _sr = new SecureRandom();
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
        byte[] rv = new byte[(bitsRequired + 7) / 8];
        _sr.nextBytes(rv);
        return rv;
      }
      
      public int entropySize()
      {
        return bitsRequired;
      }
    };
  }
}
