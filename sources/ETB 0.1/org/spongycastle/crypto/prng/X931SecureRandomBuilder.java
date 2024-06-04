package org.spongycastle.crypto.prng;

import java.security.SecureRandom;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.util.Pack;











public class X931SecureRandomBuilder
{
  private SecureRandom random;
  private EntropySourceProvider entropySourceProvider;
  private byte[] dateTimeVector;
  
  public X931SecureRandomBuilder()
  {
    this(new SecureRandom(), false);
  }
  










  public X931SecureRandomBuilder(SecureRandom entropySource, boolean predictionResistant)
  {
    random = entropySource;
    entropySourceProvider = new BasicEntropySourceProvider(random, predictionResistant);
  }
  







  public X931SecureRandomBuilder(EntropySourceProvider entropySourceProvider)
  {
    random = null;
    this.entropySourceProvider = entropySourceProvider;
  }
  
  public X931SecureRandomBuilder setDateTimeVector(byte[] dateTimeVector)
  {
    this.dateTimeVector = dateTimeVector;
    
    return this;
  }
  









  public X931SecureRandom build(BlockCipher engine, KeyParameter key, boolean predictionResistant)
  {
    if (dateTimeVector == null)
    {
      dateTimeVector = new byte[engine.getBlockSize()];
      Pack.longToBigEndian(System.currentTimeMillis(), dateTimeVector, 0);
    }
    
    engine.init(true, key);
    
    return new X931SecureRandom(random, new X931RNG(engine, dateTimeVector, entropySourceProvider.get(engine.getBlockSize() * 8)), predictionResistant);
  }
}
