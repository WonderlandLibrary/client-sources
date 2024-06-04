package org.spongycastle.crypto;

import java.security.SecureRandom;













public class KeyGenerationParameters
{
  private SecureRandom random;
  private int strength;
  
  public KeyGenerationParameters(SecureRandom random, int strength)
  {
    this.random = random;
    this.strength = strength;
  }
  






  public SecureRandom getRandom()
  {
    return random;
  }
  





  public int getStrength()
  {
    return strength;
  }
}
