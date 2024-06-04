package org.spongycastle.crypto.macs;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.util.Pack;







public class SipHash
  implements Mac
{
  protected final int c;
  protected final int d;
  protected long k0;
  protected long k1;
  protected long v0;
  protected long v1;
  protected long v2;
  protected long v3;
  protected long m = 0L;
  protected int wordPos = 0;
  protected int wordCount = 0;
  




  public SipHash()
  {
    c = 2;
    d = 4;
  }
  






  public SipHash(int c, int d)
  {
    this.c = c;
    this.d = d;
  }
  
  public String getAlgorithmName()
  {
    return "SipHash-" + c + "-" + d;
  }
  
  public int getMacSize()
  {
    return 8;
  }
  
  public void init(CipherParameters params)
    throws IllegalArgumentException
  {
    if (!(params instanceof KeyParameter))
    {
      throw new IllegalArgumentException("'params' must be an instance of KeyParameter");
    }
    KeyParameter keyParameter = (KeyParameter)params;
    byte[] key = keyParameter.getKey();
    if (key.length != 16)
    {
      throw new IllegalArgumentException("'params' must be a 128-bit key");
    }
    
    k0 = Pack.littleEndianToLong(key, 0);
    k1 = Pack.littleEndianToLong(key, 8);
    
    reset();
  }
  
  public void update(byte input)
    throws IllegalStateException
  {
    m >>>= 8;
    m |= (input & 0xFF) << 56;
    
    if (++wordPos == 8)
    {
      processMessageWord();
      wordPos = 0;
    }
  }
  

  public void update(byte[] input, int offset, int length)
    throws DataLengthException, IllegalStateException
  {
    int i = 0;int fullWords = length & 0xFFFFFFF8;
    if (wordPos == 0)
    {
      for (; i < fullWords; i += 8)
      {
        m = Pack.littleEndianToLong(input, offset + i);
        processMessageWord();
      }
      for (; i < length; i++)
      {
        m >>>= 8;
        m |= (input[(offset + i)] & 0xFF) << 56;
      }
      wordPos = (length - fullWords);
    }
    else
    {
      int bits = wordPos << 3;
      for (; i < fullWords; i += 8)
      {
        long n = Pack.littleEndianToLong(input, offset + i);
        m = (n << bits | m >>> -bits);
        processMessageWord();
        m = n;
      }
      for (; i < length; i++)
      {
        m >>>= 8;
        m |= (input[(offset + i)] & 0xFF) << 56;
        
        if (++wordPos == 8)
        {
          processMessageWord();
          wordPos = 0;
        }
      }
    }
  }
  

  public long doFinal()
    throws DataLengthException, IllegalStateException
  {
    m >>>= 7 - wordPos << 3;
    m >>>= 8;
    m |= ((wordCount << 3) + wordPos & 0xFF) << 56;
    
    processMessageWord();
    
    v2 ^= 0xFF;
    
    applySipRounds(d);
    
    long result = v0 ^ v1 ^ v2 ^ v3;
    
    reset();
    
    return result;
  }
  
  public int doFinal(byte[] out, int outOff)
    throws DataLengthException, IllegalStateException
  {
    long result = doFinal();
    Pack.longToLittleEndian(result, out, outOff);
    return 8;
  }
  
  public void reset()
  {
    v0 = (k0 ^ 0x736F6D6570736575);
    v1 = (k1 ^ 0x646F72616E646F6D);
    v2 = (k0 ^ 0x6C7967656E657261);
    v3 = (k1 ^ 0x7465646279746573);
    
    m = 0L;
    wordPos = 0;
    wordCount = 0;
  }
  
  protected void processMessageWord()
  {
    wordCount += 1;
    v3 ^= m;
    applySipRounds(c);
    v0 ^= m;
  }
  
  protected void applySipRounds(int n)
  {
    long r0 = v0;long r1 = v1;long r2 = v2;long r3 = v3;
    
    for (int r = 0; r < n; r++)
    {
      r0 += r1;
      r2 += r3;
      r1 = rotateLeft(r1, 13);
      r3 = rotateLeft(r3, 16);
      r1 ^= r0;
      r3 ^= r2;
      r0 = rotateLeft(r0, 32);
      r2 += r1;
      r0 += r3;
      r1 = rotateLeft(r1, 17);
      r3 = rotateLeft(r3, 21);
      r1 ^= r2;
      r3 ^= r0;
      r2 = rotateLeft(r2, 32);
    }
    
    v0 = r0;v1 = r1;v2 = r2;v3 = r3;
  }
  
  protected static long rotateLeft(long x, int n)
  {
    return x << n | x >>> -n;
  }
}
