package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.KeyParameter;





public class DESEngine
  implements BlockCipher
{
  protected static final int BLOCK_SIZE = 8;
  private int[] workingKey = null;
  








  public DESEngine() {}
  







  public void init(boolean encrypting, CipherParameters params)
  {
    if ((params instanceof KeyParameter))
    {
      if (((KeyParameter)params).getKey().length > 8)
      {
        throw new IllegalArgumentException("DES key too long - should be 8 bytes");
      }
      
      workingKey = generateWorkingKey(encrypting, ((KeyParameter)params)
        .getKey());
      
      return;
    }
    
    throw new IllegalArgumentException("invalid parameter passed to DES init - " + params.getClass().getName());
  }
  
  public String getAlgorithmName()
  {
    return "DES";
  }
  
  public int getBlockSize()
  {
    return 8;
  }
  




  public int processBlock(byte[] in, int inOff, byte[] out, int outOff)
  {
    if (workingKey == null)
    {
      throw new IllegalStateException("DES engine not initialised");
    }
    
    if (inOff + 8 > in.length)
    {
      throw new DataLengthException("input buffer too short");
    }
    
    if (outOff + 8 > out.length)
    {
      throw new OutputLengthException("output buffer too short");
    }
    
    desFunc(workingKey, in, inOff, out, outOff);
    
    return 8;
  }
  

















  private static final short[] bytebit = { 128, 64, 32, 16, 8, 4, 2, 1 };
  



  private static final int[] bigbyte = { 8388608, 4194304, 2097152, 1048576, 524288, 262144, 131072, 65536, 32768, 16384, 8192, 4096, 2048, 1024, 512, 256, 128, 64, 32, 16, 8, 4, 2, 1 };
  












  private static final byte[] pc1 = { 56, 48, 40, 32, 24, 16, 8, 0, 57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 60, 52, 44, 36, 28, 20, 12, 4, 27, 19, 11, 3 };
  






  private static final byte[] totrot = { 1, 2, 4, 6, 8, 10, 12, 14, 15, 17, 19, 21, 23, 25, 27, 28 };
  




  private static final byte[] pc2 = { 13, 16, 10, 23, 0, 4, 2, 27, 14, 5, 20, 9, 22, 18, 11, 3, 25, 7, 15, 6, 26, 19, 12, 1, 40, 51, 30, 36, 46, 54, 29, 39, 50, 44, 32, 47, 43, 48, 38, 55, 33, 52, 45, 41, 49, 35, 28, 31 };
  






  private static final int[] SP1 = { 16843776, 0, 65536, 16843780, 16842756, 66564, 4, 65536, 1024, 16843776, 16843780, 1024, 16778244, 16842756, 16777216, 4, 1028, 16778240, 16778240, 66560, 66560, 16842752, 16842752, 16778244, 65540, 16777220, 16777220, 65540, 0, 1028, 66564, 16777216, 65536, 16843780, 4, 16842752, 16843776, 16777216, 16777216, 1024, 16842756, 65536, 66560, 16777220, 1024, 4, 16778244, 66564, 16843780, 65540, 16842752, 16778244, 16777220, 1028, 66564, 16843776, 1028, 16778240, 16778240, 0, 65540, 66560, 0, 16842756 };
  

















  private static final int[] SP2 = { -2146402272, -2147450880, 32768, 1081376, 1048576, 32, -2146435040, -2147450848, -2147483616, -2146402272, -2146402304, Integer.MIN_VALUE, -2147450880, 1048576, 32, -2146435040, 1081344, 1048608, -2147450848, 0, Integer.MIN_VALUE, 32768, 1081376, -2146435072, 1048608, -2147483616, 0, 1081344, 32800, -2146402304, -2146435072, 32800, 0, 1081376, -2146435040, 1048576, -2147450848, -2146435072, -2146402304, 32768, -2146435072, -2147450880, 32, -2146402272, 1081376, 32, 32768, Integer.MIN_VALUE, 32800, -2146402304, 1048576, -2147483616, 1048608, -2147450848, -2147483616, 1048608, 1081344, 0, -2147450880, 32800, Integer.MIN_VALUE, -2146435040, -2146402272, 1081344 };
  

















  private static final int[] SP3 = { 520, 134349312, 0, 134348808, 134218240, 0, 131592, 134218240, 131080, 134217736, 134217736, 131072, 134349320, 131080, 134348800, 520, 134217728, 8, 134349312, 512, 131584, 134348800, 134348808, 131592, 134218248, 131584, 131072, 134218248, 8, 134349320, 512, 134217728, 134349312, 134217728, 131080, 520, 131072, 134349312, 134218240, 0, 512, 131080, 134349320, 134218240, 134217736, 512, 0, 134348808, 134218248, 131072, 134217728, 134349320, 8, 131592, 131584, 134217736, 134348800, 134218248, 520, 134348800, 131592, 8, 134348808, 131584 };
  

















  private static final int[] SP4 = { 8396801, 8321, 8321, 128, 8396928, 8388737, 8388609, 8193, 0, 8396800, 8396800, 8396929, 129, 0, 8388736, 8388609, 1, 8192, 8388608, 8396801, 128, 8388608, 8193, 8320, 8388737, 1, 8320, 8388736, 8192, 8396928, 8396929, 129, 8388736, 8388609, 8396800, 8396929, 129, 0, 0, 8396800, 8320, 8388736, 8388737, 1, 8396801, 8321, 8321, 128, 8396929, 129, 1, 8192, 8388609, 8193, 8396928, 8388737, 8193, 8320, 8388608, 8396801, 128, 8388608, 8192, 8396928 };
  

















  private static final int[] SP5 = { 256, 34078976, 34078720, 1107296512, 524288, 256, 1073741824, 34078720, 1074266368, 524288, 33554688, 1074266368, 1107296512, 1107820544, 524544, 1073741824, 33554432, 1074266112, 1074266112, 0, 1073742080, 1107820800, 1107820800, 33554688, 1107820544, 1073742080, 0, 1107296256, 34078976, 33554432, 1107296256, 524544, 524288, 1107296512, 256, 33554432, 1073741824, 34078720, 1107296512, 1074266368, 33554688, 1073741824, 1107820544, 34078976, 1074266368, 256, 33554432, 1107820544, 1107820800, 524544, 1107296256, 1107820800, 34078720, 0, 1074266112, 1107296256, 524544, 33554688, 1073742080, 524288, 0, 1074266112, 34078976, 1073742080 };
  

















  private static final int[] SP6 = { 536870928, 541065216, 16384, 541081616, 541065216, 16, 541081616, 4194304, 536887296, 4210704, 4194304, 536870928, 4194320, 536887296, 536870912, 16400, 0, 4194320, 536887312, 16384, 4210688, 536887312, 16, 541065232, 541065232, 0, 4210704, 541081600, 16400, 4210688, 541081600, 536870912, 536887296, 16, 541065232, 4210688, 541081616, 4194304, 16400, 536870928, 4194304, 536887296, 536870912, 16400, 536870928, 541081616, 4210688, 541065216, 4210704, 541081600, 0, 541065232, 16, 16384, 541065216, 4210704, 16384, 4194320, 536887312, 0, 541081600, 536870912, 4194320, 536887312 };
  

















  private static final int[] SP7 = { 2097152, 69206018, 67110914, 0, 2048, 67110914, 2099202, 69208064, 69208066, 2097152, 0, 67108866, 2, 67108864, 69206018, 2050, 67110912, 2099202, 2097154, 67110912, 67108866, 69206016, 69208064, 2097154, 69206016, 2048, 2050, 69208066, 2099200, 2, 67108864, 2099200, 67108864, 2099200, 2097152, 67110914, 67110914, 69206018, 69206018, 2, 2097154, 67108864, 67110912, 2097152, 69208064, 2050, 2099202, 69208064, 2050, 67108866, 69208066, 69206016, 2099200, 0, 2, 69208066, 0, 2099202, 69206016, 2048, 67108866, 67110912, 2048, 2097154 };
  

















  private static final int[] SP8 = { 268439616, 4096, 262144, 268701760, 268435456, 268439616, 64, 268435456, 262208, 268697600, 268701760, 266240, 268701696, 266304, 4096, 64, 268697600, 268435520, 268439552, 4160, 266240, 262208, 268697664, 268701696, 4160, 0, 0, 268697664, 268435520, 268439552, 266304, 262144, 266304, 262144, 268701696, 4096, 64, 268697664, 4096, 266304, 268439552, 64, 268435520, 268697600, 268697664, 268435456, 262144, 268439616, 0, 268701760, 262208, 268435520, 268697600, 268439552, 268439616, 0, 268701760, 266240, 266240, 4160, 4160, 262208, 268435456, 268701696 };
  












  public void reset() {}
  












  protected int[] generateWorkingKey(boolean encrypting, byte[] key)
  {
    int[] newKey = new int[32];
    boolean[] pc1m = new boolean[56];
    boolean[] pcr = new boolean[56];
    
    for (int j = 0; j < 56; j++)
    {
      int l = pc1[j];
      
      pc1m[j] = ((key[(l >>> 3)] & bytebit[(l & 0x7)]) != 0 ? 1 : false);
    }
    
    for (int i = 0; i < 16; i++)
    {
      int m;
      int m;
      if (encrypting)
      {
        m = i << 1;
      }
      else
      {
        m = 15 - i << 1;
      }
      
      int n = m + 1; int 
        tmp115_114 = 0;newKey[n] = tmp115_114;newKey[m] = tmp115_114;
      
      for (int j = 0; j < 28; j++)
      {
        int l = j + totrot[i];
        if (l < 28)
        {
          pcr[j] = pc1m[l];
        }
        else
        {
          pcr[j] = pc1m[(l - 28)];
        }
      }
      
      for (int j = 28; j < 56; j++)
      {
        int l = j + totrot[i];
        if (l < 56)
        {
          pcr[j] = pc1m[l];
        }
        else
        {
          pcr[j] = pc1m[(l - 28)];
        }
      }
      
      for (int j = 0; j < 24; j++)
      {
        if (pcr[pc2[j]] != 0)
        {
          newKey[m] |= bigbyte[j];
        }
        
        if (pcr[pc2[(j + 24)]] != 0)
        {
          newKey[n] |= bigbyte[j];
        }
      }
    }
    



    for (int i = 0; i != 32; i += 2)
    {


      int i1 = newKey[i];
      int i2 = newKey[(i + 1)];
      
      newKey[i] = ((i1 & 0xFC0000) << 6 | (i1 & 0xFC0) << 10 | (i2 & 0xFC0000) >>> 10 | (i2 & 0xFC0) >>> 6);
      

      newKey[(i + 1)] = ((i1 & 0x3F000) << 12 | (i1 & 0x3F) << 16 | (i2 & 0x3F000) >>> 4 | i2 & 0x3F);
    }
    

    return newKey;
  }
  










  protected void desFunc(int[] wKey, byte[] in, int inOff, byte[] out, int outOff)
  {
    int left = (in[(inOff + 0)] & 0xFF) << 24;
    left |= (in[(inOff + 1)] & 0xFF) << 16;
    left |= (in[(inOff + 2)] & 0xFF) << 8;
    left |= in[(inOff + 3)] & 0xFF;
    
    int right = (in[(inOff + 4)] & 0xFF) << 24;
    right |= (in[(inOff + 5)] & 0xFF) << 16;
    right |= (in[(inOff + 6)] & 0xFF) << 8;
    right |= in[(inOff + 7)] & 0xFF;
    
    int work = (left >>> 4 ^ right) & 0xF0F0F0F;
    right ^= work;
    left ^= work << 4;
    work = (left >>> 16 ^ right) & 0xFFFF;
    right ^= work;
    left ^= work << 16;
    work = (right >>> 2 ^ left) & 0x33333333;
    left ^= work;
    right ^= work << 2;
    work = (right >>> 8 ^ left) & 0xFF00FF;
    left ^= work;
    right ^= work << 8;
    right = (right << 1 | right >>> 31 & 0x1) & 0xFFFFFFFF;
    work = (left ^ right) & 0xAAAAAAAA;
    left ^= work;
    right ^= work;
    left = (left << 1 | left >>> 31 & 0x1) & 0xFFFFFFFF;
    
    for (int round = 0; round < 8; round++)
    {


      work = right << 28 | right >>> 4;
      work ^= wKey[(round * 4 + 0)];
      int fval = SP7[(work & 0x3F)];
      fval |= SP5[(work >>> 8 & 0x3F)];
      fval |= SP3[(work >>> 16 & 0x3F)];
      fval |= SP1[(work >>> 24 & 0x3F)];
      work = right ^ wKey[(round * 4 + 1)];
      fval |= SP8[(work & 0x3F)];
      fval |= SP6[(work >>> 8 & 0x3F)];
      fval |= SP4[(work >>> 16 & 0x3F)];
      fval |= SP2[(work >>> 24 & 0x3F)];
      left ^= fval;
      work = left << 28 | left >>> 4;
      work ^= wKey[(round * 4 + 2)];
      fval = SP7[(work & 0x3F)];
      fval |= SP5[(work >>> 8 & 0x3F)];
      fval |= SP3[(work >>> 16 & 0x3F)];
      fval |= SP1[(work >>> 24 & 0x3F)];
      work = left ^ wKey[(round * 4 + 3)];
      fval |= SP8[(work & 0x3F)];
      fval |= SP6[(work >>> 8 & 0x3F)];
      fval |= SP4[(work >>> 16 & 0x3F)];
      fval |= SP2[(work >>> 24 & 0x3F)];
      right ^= fval;
    }
    
    right = right << 31 | right >>> 1;
    work = (left ^ right) & 0xAAAAAAAA;
    left ^= work;
    right ^= work;
    left = left << 31 | left >>> 1;
    work = (left >>> 8 ^ right) & 0xFF00FF;
    right ^= work;
    left ^= work << 8;
    work = (left >>> 2 ^ right) & 0x33333333;
    right ^= work;
    left ^= work << 2;
    work = (right >>> 16 ^ left) & 0xFFFF;
    left ^= work;
    right ^= work << 16;
    work = (right >>> 4 ^ left) & 0xF0F0F0F;
    left ^= work;
    right ^= work << 4;
    
    out[(outOff + 0)] = ((byte)(right >>> 24 & 0xFF));
    out[(outOff + 1)] = ((byte)(right >>> 16 & 0xFF));
    out[(outOff + 2)] = ((byte)(right >>> 8 & 0xFF));
    out[(outOff + 3)] = ((byte)(right & 0xFF));
    out[(outOff + 4)] = ((byte)(left >>> 24 & 0xFF));
    out[(outOff + 5)] = ((byte)(left >>> 16 & 0xFF));
    out[(outOff + 6)] = ((byte)(left >>> 8 & 0xFF));
    out[(outOff + 7)] = ((byte)(left & 0xFF));
  }
}
