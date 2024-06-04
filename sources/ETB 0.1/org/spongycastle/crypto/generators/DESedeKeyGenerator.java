package org.spongycastle.crypto.generators;

import java.security.SecureRandom;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.params.DESedeParameters;








public class DESedeKeyGenerator
  extends DESKeyGenerator
{
  private static final int MAX_IT = 20;
  
  public DESedeKeyGenerator() {}
  
  public void init(KeyGenerationParameters param)
  {
    random = param.getRandom();
    strength = ((param.getStrength() + 7) / 8);
    
    if ((strength == 0) || (strength == 21))
    {
      strength = 24;
    }
    else if (strength == 14)
    {
      strength = 16;
    }
    else if ((strength != 24) && (strength != 16))
    {

      throw new IllegalArgumentException("DESede key must be 192 or 128 bits long.");
    }
  }
  



  public byte[] generateKey()
  {
    byte[] newKey = new byte[strength];
    int count = 0;
    
    do
    {
      random.nextBytes(newKey);
      
      DESedeParameters.setOddParity(newKey);
      
      count++; } while ((count < 20) && ((DESedeParameters.isWeakKey(newKey, 0, newKey.length)) || (!DESedeParameters.isRealEDEKey(newKey, 0))));
    
    if ((DESedeParameters.isWeakKey(newKey, 0, newKey.length)) || (!DESedeParameters.isRealEDEKey(newKey, 0)))
    {
      throw new IllegalStateException("Unable to generate DES-EDE key");
    }
    
    return newKey;
  }
}
