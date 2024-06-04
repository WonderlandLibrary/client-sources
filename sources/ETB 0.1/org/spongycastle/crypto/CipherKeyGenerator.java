package org.spongycastle.crypto;

import java.security.SecureRandom;








public class CipherKeyGenerator
{
  protected SecureRandom random;
  protected int strength;
  
  public CipherKeyGenerator() {}
  
  public void init(KeyGenerationParameters param)
  {
    random = param.getRandom();
    strength = ((param.getStrength() + 7) / 8);
  }
  





  public byte[] generateKey()
  {
    byte[] key = new byte[strength];
    
    random.nextBytes(key);
    
    return key;
  }
}
