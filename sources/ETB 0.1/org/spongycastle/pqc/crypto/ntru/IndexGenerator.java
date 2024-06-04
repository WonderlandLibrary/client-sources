package org.spongycastle.pqc.crypto.ntru;

import org.spongycastle.crypto.Digest;
import org.spongycastle.util.Arrays;










public class IndexGenerator
{
  private byte[] seed;
  private int N;
  private int c;
  private int minCallsR;
  private int totLen;
  private int remLen;
  private BitString buf;
  private int counter;
  private boolean initialized;
  private Digest hashAlg;
  private int hLen;
  
  IndexGenerator(byte[] seed, NTRUEncryptionParameters params)
  {
    this.seed = seed;
    N = N;
    c = c;
    minCallsR = minCallsR;
    
    totLen = 0;
    remLen = 0;
    counter = 0;
    hashAlg = hashAlg;
    
    hLen = hashAlg.getDigestSize();
    initialized = false;
  }
  



  int nextIndex()
  {
    if (!initialized)
    {
      buf = new BitString();
      byte[] hash = new byte[hashAlg.getDigestSize()];
      while (counter < minCallsR)
      {
        appendHash(buf, hash);
        counter += 1;
      }
      totLen = (minCallsR * 8 * hLen);
      remLen = totLen;
      initialized = true;
    }
    
    for (;;)
    {
      totLen += c;
      BitString M = buf.getTrailing(remLen);
      if (remLen < c)
      {
        int tmpLen = c - remLen;
        int cThreshold = counter + (tmpLen + hLen - 1) / hLen;
        byte[] hash = new byte[hashAlg.getDigestSize()];
        while (counter < cThreshold)
        {
          appendHash(M, hash);
          counter += 1;
          if (tmpLen > 8 * hLen)
          {
            tmpLen -= 8 * hLen;
          }
        }
        remLen = (8 * hLen - tmpLen);
        buf = new BitString();
        buf.appendBits(hash);
      }
      else
      {
        remLen -= c;
      }
      
      int i = M.getLeadingAsInt(c);
      if (i < (1 << c) - (1 << c) % N)
      {
        return i % N;
      }
    }
  }
  
  private void appendHash(BitString m, byte[] hash)
  {
    hashAlg.update(seed, 0, seed.length);
    
    putInt(hashAlg, counter);
    
    hashAlg.doFinal(hash, 0);
    
    m.appendBits(hash);
  }
  
  private void putInt(Digest hashAlg, int counter)
  {
    hashAlg.update((byte)(counter >> 24));
    hashAlg.update((byte)(counter >> 16));
    hashAlg.update((byte)(counter >> 8));
    hashAlg.update((byte)counter);
  }
  



  public static class BitString
  {
    byte[] bytes = new byte[4];
    
    int numBytes;
    
    int lastByteBits;
    

    public BitString() {}
    
    void appendBits(byte[] bytes)
    {
      for (int i = 0; i != bytes.length; i++)
      {
        appendBits(bytes[i]);
      }
    }
    





    public void appendBits(byte b)
    {
      if (numBytes == bytes.length)
      {
        bytes = IndexGenerator.copyOf(bytes, 2 * bytes.length);
      }
      
      if (numBytes == 0)
      {
        numBytes = 1;
        bytes[0] = b;
        lastByteBits = 8;
      }
      else if (lastByteBits == 8)
      {
        bytes[(numBytes++)] = b;
      }
      else
      {
        int s = 8 - lastByteBits; int 
          tmp105_104 = (numBytes - 1); byte[] tmp105_96 = bytes;tmp105_96[tmp105_104] = ((byte)(tmp105_96[tmp105_104] | (b & 0xFF) << lastByteBits));
        bytes[(numBytes++)] = ((byte)((b & 0xFF) >> s));
      }
    }
    






    public BitString getTrailing(int numBits)
    {
      BitString newStr = new BitString();
      numBytes = ((numBits + 7) / 8);
      bytes = new byte[numBytes];
      for (int i = 0; i < numBytes; i++)
      {
        bytes[i] = bytes[i];
      }
      
      lastByteBits = (numBits % 8);
      if (lastByteBits == 0)
      {
        lastByteBits = 8;
      }
      else
      {
        int s = 32 - lastByteBits;
        bytes[(numBytes - 1)] = ((byte)(bytes[(numBytes - 1)] << s >>> s));
      }
      
      return newStr;
    }
    






    public int getLeadingAsInt(int numBits)
    {
      int startBit = (numBytes - 1) * 8 + lastByteBits - numBits;
      int startByte = startBit / 8;
      
      int startBitInStartByte = startBit % 8;
      int sum = (bytes[startByte] & 0xFF) >>> startBitInStartByte;
      int shift = 8 - startBitInStartByte;
      for (int i = startByte + 1; i < numBytes; i++)
      {
        sum |= (bytes[i] & 0xFF) << shift;
        shift += 8;
      }
      
      return sum;
    }
    
    public byte[] getBytes()
    {
      return Arrays.clone(bytes);
    }
  }
  
  private static byte[] copyOf(byte[] src, int len)
  {
    byte[] tmp = new byte[len];
    
    System.arraycopy(src, 0, tmp, 0, len < src.length ? len : src.length);
    
    return tmp;
  }
}
