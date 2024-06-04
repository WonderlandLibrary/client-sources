package org.spongycastle.crypto.params;

import java.security.SecureRandom;
import org.spongycastle.crypto.KeyGenerationParameters;











public class NaccacheSternKeyGenerationParameters
  extends KeyGenerationParameters
{
  private int certainty;
  private int cntSmallPrimes;
  private boolean debug = false;
  













  public NaccacheSternKeyGenerationParameters(SecureRandom random, int strength, int certainty, int cntSmallPrimes)
  {
    this(random, strength, certainty, cntSmallPrimes, false);
  }
  

















  public NaccacheSternKeyGenerationParameters(SecureRandom random, int strength, int certainty, int cntSmallPrimes, boolean debug)
  {
    super(random, strength);
    
    this.certainty = certainty;
    if (cntSmallPrimes % 2 == 1)
    {
      throw new IllegalArgumentException("cntSmallPrimes must be a multiple of 2");
    }
    if (cntSmallPrimes < 30)
    {
      throw new IllegalArgumentException("cntSmallPrimes must be >= 30 for security reasons");
    }
    this.cntSmallPrimes = cntSmallPrimes;
    
    this.debug = debug;
  }
  



  public int getCertainty()
  {
    return certainty;
  }
  



  public int getCntSmallPrimes()
  {
    return cntSmallPrimes;
  }
  
  public boolean isDebug()
  {
    return debug;
  }
}
