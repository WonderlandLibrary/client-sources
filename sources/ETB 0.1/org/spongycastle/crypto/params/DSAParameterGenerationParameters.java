package org.spongycastle.crypto.params;

import java.security.SecureRandom;














public class DSAParameterGenerationParameters
{
  public static final int DIGITAL_SIGNATURE_USAGE = 1;
  public static final int KEY_ESTABLISHMENT_USAGE = 2;
  private final int l;
  private final int n;
  private final int usageIndex;
  private final int certainty;
  private final SecureRandom random;
  
  public DSAParameterGenerationParameters(int L, int N, int certainty, SecureRandom random)
  {
    this(L, N, certainty, random, -1);
  }
  














  public DSAParameterGenerationParameters(int L, int N, int certainty, SecureRandom random, int usageIndex)
  {
    l = L;
    n = N;
    this.certainty = certainty;
    this.usageIndex = usageIndex;
    this.random = random;
  }
  
  public int getL()
  {
    return l;
  }
  
  public int getN()
  {
    return n;
  }
  
  public int getCertainty()
  {
    return certainty;
  }
  
  public SecureRandom getRandom()
  {
    return random;
  }
  
  public int getUsageIndex()
  {
    return usageIndex;
  }
}
