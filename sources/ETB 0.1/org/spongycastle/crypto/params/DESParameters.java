package org.spongycastle.crypto.params;

public class DESParameters extends KeyParameter
{
  public static final int DES_KEY_LENGTH = 8;
  private static final int N_DES_WEAK_KEYS = 16;
  
  public DESParameters(byte[] key) {
    super(key);
    
    if (isWeakKey(key, 0))
    {
      throw new IllegalArgumentException("attempt to create weak DES key");
    }
  }
  










  private static byte[] DES_weak_keys = { 1, 1, 1, 1, 1, 1, 1, 1, 31, 31, 31, 31, 14, 14, 14, 14, -32, -32, -32, -32, -15, -15, -15, -15, -2, -2, -2, -2, -2, -2, -2, -2, 1, -2, 1, -2, 1, -2, 1, -2, 31, -32, 31, -32, 14, -15, 14, -15, 1, -32, 1, -32, 1, -15, 1, -15, 31, -2, 31, -2, 14, -2, 14, -2, 1, 31, 1, 31, 1, 14, 1, 14, -32, -2, -32, -2, -15, -2, -15, -2, -2, 1, -2, 1, -2, 1, -2, 1, -32, 31, -32, 31, -15, 14, -15, 14, -32, 1, -32, 1, -15, 1, -15, 1, -2, 31, -2, 31, -2, 14, -2, 14, 31, 1, 31, 1, 14, 1, 14, 1, -2, -32, -2, -32, -2, -15, -2, -15 };
  


































  public static boolean isWeakKey(byte[] key, int offset)
  {
    if (key.length - offset < 8)
    {
      throw new IllegalArgumentException("key material too short.");
    }
    label64:
    for (int i = 0; i < 16; i++)
    {
      for (int j = 0; j < 8; j++)
      {
        if (key[(j + offset)] != DES_weak_keys[(i * 8 + j)]) {
          break label64;
        }
      }
      

      return true;
    }
    return false;
  }
  







  public static void setOddParity(byte[] bytes)
  {
    for (int i = 0; i < bytes.length; i++)
    {
      int b = bytes[i];
      bytes[i] = ((byte)(b & 0xFE | (b >> 1 ^ b >> 2 ^ b >> 3 ^ b >> 4 ^ b >> 5 ^ b >> 6 ^ b >> 7 ^ 0x1) & 0x1));
    }
  }
}
