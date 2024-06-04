package org.spongycastle.crypto.macs;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.util.Pack;
















public class Poly1305
  implements Mac
{
  private static final int BLOCK_SIZE = 16;
  private final BlockCipher cipher;
  private final byte[] singleByte = new byte[1];
  
  private int r0;
  
  private int r1;
  private int r2;
  private int r3;
  private int r4;
  private int s1;
  private int s2;
  private int s3;
  private int s4;
  private int k0;
  private int k1;
  private int k2;
  private int k3;
  private final byte[] currentBlock = new byte[16];
  

  private int currentBlockOffset = 0;
  
  private int h0;
  private int h1;
  private int h2;
  private int h3;
  private int h4;
  
  public Poly1305()
  {
    cipher = null;
  }
  



  public Poly1305(BlockCipher cipher)
  {
    if (cipher.getBlockSize() != 16)
    {
      throw new IllegalArgumentException("Poly1305 requires a 128 bit block cipher.");
    }
    this.cipher = cipher;
  }
  








  public void init(CipherParameters params)
    throws IllegalArgumentException
  {
    byte[] nonce = null;
    
    if (cipher != null)
    {
      if (!(params instanceof ParametersWithIV))
      {
        throw new IllegalArgumentException("Poly1305 requires an IV when used with a block cipher.");
      }
      
      ParametersWithIV ivParams = (ParametersWithIV)params;
      nonce = ivParams.getIV();
      params = ivParams.getParameters();
    }
    
    if (!(params instanceof KeyParameter))
    {
      throw new IllegalArgumentException("Poly1305 requires a key.");
    }
    
    KeyParameter keyParams = (KeyParameter)params;
    
    setKey(keyParams.getKey(), nonce);
    
    reset();
  }
  
  private void setKey(byte[] key, byte[] nonce)
  {
    if (key.length != 32)
    {
      throw new IllegalArgumentException("Poly1305 key must be 256 bits.");
    }
    if ((cipher != null) && ((nonce == null) || (nonce.length != 16)))
    {
      throw new IllegalArgumentException("Poly1305 requires a 128 bit IV.");
    }
    

    int t0 = Pack.littleEndianToInt(key, 0);
    int t1 = Pack.littleEndianToInt(key, 4);
    int t2 = Pack.littleEndianToInt(key, 8);
    int t3 = Pack.littleEndianToInt(key, 12);
    

    r0 = (t0 & 0x3FFFFFF);
    r1 = ((t0 >>> 26 | t1 << 6) & 0x3FFFF03);
    r2 = ((t1 >>> 20 | t2 << 12) & 0x3FFC0FF);
    r3 = ((t2 >>> 14 | t3 << 18) & 0x3F03FFF);
    r4 = (t3 >>> 8 & 0xFFFFF);
    

    s1 = (r1 * 5);
    s2 = (r2 * 5);
    s3 = (r3 * 5);
    s4 = (r4 * 5);
    
    int kOff;
    byte[] kBytes;
    int kOff;
    if (cipher == null)
    {
      byte[] kBytes = key;
      kOff = 16;

    }
    else
    {
      kBytes = new byte[16];
      kOff = 0;
      
      cipher.init(true, new KeyParameter(key, 16, 16));
      cipher.processBlock(nonce, 0, kBytes, 0);
    }
    
    k0 = Pack.littleEndianToInt(kBytes, kOff + 0);
    k1 = Pack.littleEndianToInt(kBytes, kOff + 4);
    k2 = Pack.littleEndianToInt(kBytes, kOff + 8);
    k3 = Pack.littleEndianToInt(kBytes, kOff + 12);
  }
  
  public String getAlgorithmName()
  {
    return "Poly1305-" + cipher.getAlgorithmName();
  }
  
  public int getMacSize()
  {
    return 16;
  }
  
  public void update(byte in)
    throws IllegalStateException
  {
    singleByte[0] = in;
    update(singleByte, 0, 1);
  }
  

  public void update(byte[] in, int inOff, int len)
    throws DataLengthException, IllegalStateException
  {
    int copied = 0;
    while (len > copied)
    {
      if (currentBlockOffset == 16)
      {
        processBlock();
        currentBlockOffset = 0;
      }
      
      int toCopy = Math.min(len - copied, 16 - currentBlockOffset);
      System.arraycopy(in, copied + inOff, currentBlock, currentBlockOffset, toCopy);
      copied += toCopy;
      currentBlockOffset += toCopy;
    }
  }
  

  private void processBlock()
  {
    if (currentBlockOffset < 16)
    {
      currentBlock[currentBlockOffset] = 1;
      for (int i = currentBlockOffset + 1; i < 16; i++)
      {
        currentBlock[i] = 0;
      }
    }
    
    long t0 = 0xFFFFFFFF & Pack.littleEndianToInt(currentBlock, 0);
    long t1 = 0xFFFFFFFF & Pack.littleEndianToInt(currentBlock, 4);
    long t2 = 0xFFFFFFFF & Pack.littleEndianToInt(currentBlock, 8);
    long t3 = 0xFFFFFFFF & Pack.littleEndianToInt(currentBlock, 12);
    
    h0 = ((int)(h0 + (t0 & 0x3FFFFFF)));
    h1 = ((int)(h1 + ((t1 << 32 | t0) >>> 26 & 0x3FFFFFF)));
    h2 = ((int)(h2 + ((t2 << 32 | t1) >>> 20 & 0x3FFFFFF)));
    h3 = ((int)(h3 + ((t3 << 32 | t2) >>> 14 & 0x3FFFFFF)));
    h4 = ((int)(h4 + (t3 >>> 8)));
    
    if (currentBlockOffset == 16)
    {
      h4 += 16777216;
    }
    
    long tp0 = mul32x32_64(h0, r0) + mul32x32_64(h1, s4) + mul32x32_64(h2, s3) + mul32x32_64(h3, s2) + mul32x32_64(h4, s1);
    long tp1 = mul32x32_64(h0, r1) + mul32x32_64(h1, r0) + mul32x32_64(h2, s4) + mul32x32_64(h3, s3) + mul32x32_64(h4, s2);
    long tp2 = mul32x32_64(h0, r2) + mul32x32_64(h1, r1) + mul32x32_64(h2, r0) + mul32x32_64(h3, s4) + mul32x32_64(h4, s3);
    long tp3 = mul32x32_64(h0, r3) + mul32x32_64(h1, r2) + mul32x32_64(h2, r1) + mul32x32_64(h3, r0) + mul32x32_64(h4, s4);
    long tp4 = mul32x32_64(h0, r4) + mul32x32_64(h1, r3) + mul32x32_64(h2, r2) + mul32x32_64(h3, r1) + mul32x32_64(h4, r0);
    
    h0 = ((int)tp0 & 0x3FFFFFF);tp1 += (tp0 >>> 26);
    h1 = ((int)tp1 & 0x3FFFFFF);tp2 += (tp1 >>> 26);
    h2 = ((int)tp2 & 0x3FFFFFF);tp3 += (tp2 >>> 26);
    h3 = ((int)tp3 & 0x3FFFFFF);tp4 += (tp3 >>> 26);
    h4 = ((int)tp4 & 0x3FFFFFF);
    h0 += (int)(tp4 >>> 26) * 5;
    h1 += (h0 >>> 26);h0 &= 0x3FFFFFF;
  }
  

  public int doFinal(byte[] out, int outOff)
    throws DataLengthException, IllegalStateException
  {
    if (outOff + 16 > out.length)
    {
      throw new OutputLengthException("Output buffer is too short.");
    }
    
    if (currentBlockOffset > 0)
    {

      processBlock();
    }
    
    h1 += (h0 >>> 26);h0 &= 0x3FFFFFF;
    h2 += (h1 >>> 26);h1 &= 0x3FFFFFF;
    h3 += (h2 >>> 26);h2 &= 0x3FFFFFF;
    h4 += (h3 >>> 26);h3 &= 0x3FFFFFF;
    h0 += (h4 >>> 26) * 5;h4 &= 0x3FFFFFF;
    h1 += (h0 >>> 26);h0 &= 0x3FFFFFF;
    

    int g0 = h0 + 5;int b = g0 >>> 26;g0 &= 0x3FFFFFF;
    int g1 = h1 + b;b = g1 >>> 26;g1 &= 0x3FFFFFF;
    int g2 = h2 + b;b = g2 >>> 26;g2 &= 0x3FFFFFF;
    int g3 = h3 + b;b = g3 >>> 26;g3 &= 0x3FFFFFF;
    int g4 = h4 + b - 67108864;
    
    b = (g4 >>> 31) - 1;
    int nb = b ^ 0xFFFFFFFF;
    h0 = (h0 & nb | g0 & b);
    h1 = (h1 & nb | g1 & b);
    h2 = (h2 & nb | g2 & b);
    h3 = (h3 & nb | g3 & b);
    h4 = (h4 & nb | g4 & b);
    

    long f0 = ((h0 | h1 << 26) & 0xFFFFFFFF) + (0xFFFFFFFF & k0);
    long f1 = ((h1 >>> 6 | h2 << 20) & 0xFFFFFFFF) + (0xFFFFFFFF & k1);
    long f2 = ((h2 >>> 12 | h3 << 14) & 0xFFFFFFFF) + (0xFFFFFFFF & k2);
    long f3 = ((h3 >>> 18 | h4 << 8) & 0xFFFFFFFF) + (0xFFFFFFFF & k3);
    
    Pack.intToLittleEndian((int)f0, out, outOff);
    f1 += (f0 >>> 32);
    Pack.intToLittleEndian((int)f1, out, outOff + 4);
    f2 += (f1 >>> 32);
    Pack.intToLittleEndian((int)f2, out, outOff + 8);
    f3 += (f2 >>> 32);
    Pack.intToLittleEndian((int)f3, out, outOff + 12);
    
    reset();
    return 16;
  }
  
  public void reset()
  {
    currentBlockOffset = 0;
    
    h0 = (this.h1 = this.h2 = this.h3 = this.h4 = 0);
  }
  
  private static final long mul32x32_64(int i1, int i2)
  {
    return (i1 & 0xFFFFFFFF) * i2;
  }
}
