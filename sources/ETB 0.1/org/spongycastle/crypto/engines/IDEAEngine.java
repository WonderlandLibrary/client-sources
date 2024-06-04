package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.KeyParameter;













public class IDEAEngine
  implements BlockCipher
{
  protected static final int BLOCK_SIZE = 8;
  private int[] workingKey = null;
  



  private static final int MASK = 65535;
  


  private static final int BASE = 65537;
  



  public IDEAEngine() {}
  



  public void init(boolean forEncryption, CipherParameters params)
  {
    if ((params instanceof KeyParameter))
    {
      workingKey = generateWorkingKey(forEncryption, ((KeyParameter)params)
        .getKey());
      return;
    }
    
    throw new IllegalArgumentException("invalid parameter passed to IDEA init - " + params.getClass().getName());
  }
  
  public String getAlgorithmName()
  {
    return "IDEA";
  }
  
  public int getBlockSize()
  {
    return 8;
  }
  




  public int processBlock(byte[] in, int inOff, byte[] out, int outOff)
  {
    if (workingKey == null)
    {
      throw new IllegalStateException("IDEA engine not initialised");
    }
    
    if (inOff + 8 > in.length)
    {
      throw new DataLengthException("input buffer too short");
    }
    
    if (outOff + 8 > out.length)
    {
      throw new OutputLengthException("output buffer too short");
    }
    
    ideaFunc(workingKey, in, inOff, out, outOff);
    
    return 8;
  }
  




  public void reset() {}
  



  private int bytesToWord(byte[] in, int inOff)
  {
    return (in[inOff] << 8 & 0xFF00) + (in[(inOff + 1)] & 0xFF);
  }
  



  private void wordToBytes(int word, byte[] out, int outOff)
  {
    out[outOff] = ((byte)(word >>> 8));
    out[(outOff + 1)] = ((byte)word);
  }
  











  private int mul(int x, int y)
  {
    if (x == 0)
    {
      x = 65537 - y;
    }
    else if (y == 0)
    {
      x = 65537 - x;
    }
    else
    {
      int p = x * y;
      
      y = p & 0xFFFF;
      x = p >>> 16;
      x = y - x + (y < x ? 1 : 0);
    }
    
    return x & 0xFFFF;
  }
  






  private void ideaFunc(int[] workingKey, byte[] in, int inOff, byte[] out, int outOff)
  {
    int keyOff = 0;
    
    int x0 = bytesToWord(in, inOff);
    int x1 = bytesToWord(in, inOff + 2);
    int x2 = bytesToWord(in, inOff + 4);
    int x3 = bytesToWord(in, inOff + 6);
    
    for (int round = 0; round < 8; round++)
    {
      x0 = mul(x0, workingKey[(keyOff++)]);
      x1 += workingKey[(keyOff++)];
      x1 &= 0xFFFF;
      x2 += workingKey[(keyOff++)];
      x2 &= 0xFFFF;
      x3 = mul(x3, workingKey[(keyOff++)]);
      
      int t0 = x1;
      int t1 = x2;
      x2 ^= x0;
      x1 ^= x3;
      
      x2 = mul(x2, workingKey[(keyOff++)]);
      x1 += x2;
      x1 &= 0xFFFF;
      
      x1 = mul(x1, workingKey[(keyOff++)]);
      x2 += x1;
      x2 &= 0xFFFF;
      
      x0 ^= x1;
      x3 ^= x2;
      x1 ^= t1;
      x2 ^= t0;
    }
    
    wordToBytes(mul(x0, workingKey[(keyOff++)]), out, outOff);
    wordToBytes(x2 + workingKey[(keyOff++)], out, outOff + 2);
    wordToBytes(x1 + workingKey[(keyOff++)], out, outOff + 4);
    wordToBytes(mul(x3, workingKey[keyOff]), out, outOff + 6);
  }
  







  private int[] expandKey(byte[] uKey)
  {
    int[] key = new int[52];
    
    if (uKey.length < 16)
    {
      byte[] tmp = new byte[16];
      
      System.arraycopy(uKey, 0, tmp, tmp.length - uKey.length, uKey.length);
      
      uKey = tmp;
    }
    
    for (int i = 0; i < 8; i++)
    {
      key[i] = bytesToWord(uKey, i * 2);
    }
    
    for (int i = 8; i < 52; i++)
    {
      if ((i & 0x7) < 6)
      {
        key[i] = (((key[(i - 7)] & 0x7F) << 9 | key[(i - 6)] >> 7) & 0xFFFF);
      }
      else if ((i & 0x7) == 6)
      {
        key[i] = (((key[(i - 7)] & 0x7F) << 9 | key[(i - 14)] >> 7) & 0xFFFF);
      }
      else
      {
        key[i] = (((key[(i - 15)] & 0x7F) << 9 | key[(i - 14)] >> 7) & 0xFFFF);
      }
    }
    
    return key;
  }
  









  private int mulInv(int x)
  {
    if (x < 2)
    {
      return x;
    }
    
    int t0 = 1;
    int t1 = 65537 / x;
    int y = 65537 % x;
    
    while (y != 1)
    {
      int q = x / y;
      x %= y;
      t0 = t0 + t1 * q & 0xFFFF;
      if (x == 1)
      {
        return t0;
      }
      q = y / x;
      y %= x;
      t1 = t1 + t0 * q & 0xFFFF;
    }
    
    return 1 - t1 & 0xFFFF;
  }
  






  int addInv(int x)
  {
    return 0 - x & 0xFFFF;
  }
  






  private int[] invertKey(int[] inKey)
  {
    int p = 52;
    int[] key = new int[52];
    int inOff = 0;
    
    int t1 = mulInv(inKey[(inOff++)]);
    int t2 = addInv(inKey[(inOff++)]);
    int t3 = addInv(inKey[(inOff++)]);
    int t4 = mulInv(inKey[(inOff++)]);
    key[(--p)] = t4;
    key[(--p)] = t3;
    key[(--p)] = t2;
    key[(--p)] = t1;
    
    for (int round = 1; round < 8; round++)
    {
      t1 = inKey[(inOff++)];
      t2 = inKey[(inOff++)];
      key[(--p)] = t2;
      key[(--p)] = t1;
      
      t1 = mulInv(inKey[(inOff++)]);
      t2 = addInv(inKey[(inOff++)]);
      t3 = addInv(inKey[(inOff++)]);
      t4 = mulInv(inKey[(inOff++)]);
      key[(--p)] = t4;
      key[(--p)] = t2;
      key[(--p)] = t3;
      key[(--p)] = t1;
    }
    
    t1 = inKey[(inOff++)];
    t2 = inKey[(inOff++)];
    key[(--p)] = t2;
    key[(--p)] = t1;
    
    t1 = mulInv(inKey[(inOff++)]);
    t2 = addInv(inKey[(inOff++)]);
    t3 = addInv(inKey[(inOff++)]);
    t4 = mulInv(inKey[inOff]);
    key[(--p)] = t4;
    key[(--p)] = t3;
    key[(--p)] = t2;
    key[(--p)] = t1;
    
    return key;
  }
  


  private int[] generateWorkingKey(boolean forEncryption, byte[] userKey)
  {
    if (forEncryption)
    {
      return expandKey(userKey);
    }
    

    return invertKey(expandKey(userKey));
  }
}
