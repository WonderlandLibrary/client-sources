package org.spongycastle.crypto.digests;

import org.spongycastle.util.Memoable;
import org.spongycastle.util.MemoableResetException;
import org.spongycastle.util.Pack;

public class SHA512tDigest
  extends LongDigest
{
  private int digestLength;
  private long H1t;
  private long H2t;
  private long H3t;
  private long H4t;
  private long H5t;
  private long H6t;
  private long H7t;
  private long H8t;
  
  public SHA512tDigest(int bitLength)
  {
    if (bitLength >= 512)
    {
      throw new IllegalArgumentException("bitLength cannot be >= 512");
    }
    
    if (bitLength % 8 != 0)
    {
      throw new IllegalArgumentException("bitLength needs to be a multiple of 8");
    }
    
    if (bitLength == 384)
    {
      throw new IllegalArgumentException("bitLength cannot be 384 use SHA384 instead");
    }
    
    digestLength = (bitLength / 8);
    
    tIvGenerate(digestLength * 8);
    
    reset();
  }
  




  public SHA512tDigest(SHA512tDigest t)
  {
    super(t);
    
    digestLength = digestLength;
    
    reset(t);
  }
  
  public SHA512tDigest(byte[] encodedState)
  {
    this(readDigestLength(encodedState));
    restoreState(encodedState);
  }
  
  private static int readDigestLength(byte[] encodedState)
  {
    return Pack.bigEndianToInt(encodedState, encodedState.length - 4);
  }
  
  public String getAlgorithmName()
  {
    return "SHA-512/" + Integer.toString(digestLength * 8);
  }
  
  public int getDigestSize()
  {
    return digestLength;
  }
  


  public int doFinal(byte[] out, int outOff)
  {
    finish();
    
    longToBigEndian(H1, out, outOff, digestLength);
    longToBigEndian(H2, out, outOff + 8, digestLength - 8);
    longToBigEndian(H3, out, outOff + 16, digestLength - 16);
    longToBigEndian(H4, out, outOff + 24, digestLength - 24);
    longToBigEndian(H5, out, outOff + 32, digestLength - 32);
    longToBigEndian(H6, out, outOff + 40, digestLength - 40);
    longToBigEndian(H7, out, outOff + 48, digestLength - 48);
    longToBigEndian(H8, out, outOff + 56, digestLength - 56);
    
    reset();
    
    return digestLength;
  }
  



  public void reset()
  {
    super.reset();
    



    H1 = H1t;
    H2 = H2t;
    H3 = H3t;
    H4 = H4t;
    H5 = H5t;
    H6 = H6t;
    H7 = H7t;
    H8 = H8t;
  }
  
  private void tIvGenerate(int bitLength)
  {
    H1 = -3482333909917012819L;
    H2 = 2216346199247487646L;
    H3 = -7364697282686394994L;
    H4 = 65953792586715988L;
    H5 = -816286391624063116L;
    H6 = 4512832404995164602L;
    H7 = -5033199132376557362L;
    H8 = -124578254951840548L;
    
    update((byte)83);
    update((byte)72);
    update((byte)65);
    update((byte)45);
    update((byte)53);
    update((byte)49);
    update((byte)50);
    update((byte)47);
    
    if (bitLength > 100)
    {
      update((byte)(bitLength / 100 + 48));
      bitLength %= 100;
      update((byte)(bitLength / 10 + 48));
      bitLength %= 10;
      update((byte)(bitLength + 48));
    }
    else if (bitLength > 10)
    {
      update((byte)(bitLength / 10 + 48));
      bitLength %= 10;
      update((byte)(bitLength + 48));
    }
    else
    {
      update((byte)(bitLength + 48));
    }
    
    finish();
    
    H1t = H1;
    H2t = H2;
    H3t = H3;
    H4t = H4;
    H5t = H5;
    H6t = H6;
    H7t = H7;
    H8t = H8;
  }
  
  private static void longToBigEndian(long n, byte[] bs, int off, int max)
  {
    if (max > 0)
    {
      intToBigEndian((int)(n >>> 32), bs, off, max);
      
      if (max > 4)
      {
        intToBigEndian((int)(n & 0xFFFFFFFF), bs, off + 4, max - 4);
      }
    }
  }
  
  private static void intToBigEndian(int n, byte[] bs, int off, int max)
  {
    int num = Math.min(4, max);
    for (;;) { num--; if (num < 0)
        break;
      int shift = 8 * (3 - num);
      bs[(off + num)] = ((byte)(n >>> shift));
    }
  }
  
  public Memoable copy()
  {
    return new SHA512tDigest(this);
  }
  
  public void reset(Memoable other)
  {
    SHA512tDigest t = (SHA512tDigest)other;
    
    if (digestLength != digestLength)
    {
      throw new MemoableResetException("digestLength inappropriate in other");
    }
    
    super.copyIn(t);
    
    H1t = H1t;
    H2t = H2t;
    H3t = H3t;
    H4t = H4t;
    H5t = H5t;
    H6t = H6t;
    H7t = H7t;
    H8t = H8t;
  }
  
  public byte[] getEncodedState()
  {
    int baseSize = getEncodedStateSize();
    byte[] encoded = new byte[baseSize + 4];
    populateState(encoded);
    Pack.intToBigEndian(digestLength * 8, encoded, baseSize);
    return encoded;
  }
}
