package org.spongycastle.crypto.generators;

import org.spongycastle.crypto.CipherKeyGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;

















public class Poly1305KeyGenerator
  extends CipherKeyGenerator
{
  private static final byte R_MASK_LOW_2 = -4;
  private static final byte R_MASK_HIGH_4 = 15;
  
  public Poly1305KeyGenerator() {}
  
  public void init(KeyGenerationParameters param)
  {
    super.init(new KeyGenerationParameters(param.getRandom(), 256));
  }
  





  public byte[] generateKey()
  {
    byte[] key = super.generateKey();
    clamp(key);
    return key;
  }
  














  public static void clamp(byte[] key)
  {
    if (key.length != 32)
    {
      throw new IllegalArgumentException("Poly1305 key must be 256 bits.");
    }
    



    int tmp19_18 = 3;key[tmp19_18] = ((byte)(key[tmp19_18] & 0xF));
    key[7] = ((byte)(key[7] & 0xF));
    key[11] = ((byte)(key[11] & 0xF));
    key[15] = ((byte)(key[15] & 0xF)); int 
    



      tmp58_57 = 4;key[tmp58_57] = ((byte)(key[tmp58_57] & 0xFFFFFFFC));
    key[8] = ((byte)(key[8] & 0xFFFFFFFC));
    key[12] = ((byte)(key[12] & 0xFFFFFFFC));
  }
  








  public static void checkKey(byte[] key)
  {
    if (key.length != 32)
    {
      throw new IllegalArgumentException("Poly1305 key must be 256 bits.");
    }
    
    checkMask(key[3], (byte)15);
    checkMask(key[7], (byte)15);
    checkMask(key[11], (byte)15);
    checkMask(key[15], (byte)15);
    
    checkMask(key[4], (byte)-4);
    checkMask(key[8], (byte)-4);
    checkMask(key[12], (byte)-4);
  }
  
  private static void checkMask(byte b, byte mask)
  {
    if ((b & (mask ^ 0xFFFFFFFF)) != 0)
    {
      throw new IllegalArgumentException("Invalid format for r portion of Poly1305 key.");
    }
  }
}
