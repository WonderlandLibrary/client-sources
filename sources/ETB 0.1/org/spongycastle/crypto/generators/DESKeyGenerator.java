package org.spongycastle.crypto.generators;

import java.security.SecureRandom;
import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.params.DESParameters;






public class DESKeyGenerator
  extends CipherKeyGenerator
{
  public DESKeyGenerator() {}
  
  public void init(KeyGenerationParameters param)
  {
    super.init(param);
    
    if ((strength == 0) || (strength == 7))
    {
      strength = 8;
    }
    else if (strength != 8)
    {
      throw new IllegalArgumentException("DES key must be 64 bits long.");
    }
  }
  


  public byte[] generateKey()
  {
    byte[] newKey = new byte[8];
    
    do
    {
      random.nextBytes(newKey);
      
      DESParameters.setOddParity(newKey);
    }
    while (DESParameters.isWeakKey(newKey, 0));
    
    return newKey;
  }
}
