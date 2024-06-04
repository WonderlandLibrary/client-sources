package org.spongycastle.crypto.encodings;

import java.math.BigInteger;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.params.RSAKeyParameters;









public class ISO9796d1Encoding
  implements AsymmetricBlockCipher
{
  private static final BigInteger SIXTEEN = BigInteger.valueOf(16L);
  private static final BigInteger SIX = BigInteger.valueOf(6L);
  
  private static byte[] shadows = { 14, 3, 5, 8, 9, 4, 2, 15, 0, 13, 11, 6, 7, 10, 12, 1 };
  
  private static byte[] inverse = { 8, 15, 6, 1, 5, 2, 11, 12, 3, 4, 13, 10, 14, 9, 0, 7 };
  
  private AsymmetricBlockCipher engine;
  
  private boolean forEncryption;
  private int bitSize;
  private int padBits = 0;
  
  private BigInteger modulus;
  
  public ISO9796d1Encoding(AsymmetricBlockCipher cipher)
  {
    engine = cipher;
  }
  
  public AsymmetricBlockCipher getUnderlyingCipher()
  {
    return engine;
  }
  


  public void init(boolean forEncryption, CipherParameters param)
  {
    RSAKeyParameters kParam = null;
    
    if ((param instanceof ParametersWithRandom))
    {
      ParametersWithRandom rParam = (ParametersWithRandom)param;
      
      kParam = (RSAKeyParameters)rParam.getParameters();
    }
    else
    {
      kParam = (RSAKeyParameters)param;
    }
    
    engine.init(forEncryption, param);
    
    modulus = kParam.getModulus();
    bitSize = modulus.bitLength();
    
    this.forEncryption = forEncryption;
  }
  





  public int getInputBlockSize()
  {
    int baseBlockSize = engine.getInputBlockSize();
    
    if (forEncryption)
    {
      return (baseBlockSize + 1) / 2;
    }
    

    return baseBlockSize;
  }
  




  public int getOutputBlockSize()
  {
    int baseBlockSize = engine.getOutputBlockSize();
    
    if (forEncryption)
    {
      return baseBlockSize;
    }
    

    return (baseBlockSize + 1) / 2;
  }
  






  public void setPadBits(int padBits)
  {
    if (padBits > 7)
    {
      throw new IllegalArgumentException("padBits > 7");
    }
    
    this.padBits = padBits;
  }
  



  public int getPadBits()
  {
    return padBits;
  }
  



  public byte[] processBlock(byte[] in, int inOff, int inLen)
    throws InvalidCipherTextException
  {
    if (forEncryption)
    {
      return encodeBlock(in, inOff, inLen);
    }
    

    return decodeBlock(in, inOff, inLen);
  }
  




  private byte[] encodeBlock(byte[] in, int inOff, int inLen)
    throws InvalidCipherTextException
  {
    byte[] block = new byte[(bitSize + 7) / 8];
    int r = padBits + 1;
    int z = inLen;
    int t = (bitSize + 13) / 16;
    
    for (int i = 0; i < t; i += z)
    {
      if (i > t - z)
      {
        System.arraycopy(in, inOff + inLen - (t - i), block, block.length - t, t - i);

      }
      else
      {
        System.arraycopy(in, inOff, block, block.length - (i + z), z);
      }
    }
    
    for (int i = block.length - 2 * t; i != block.length; i += 2)
    {
      byte val = block[(block.length - t + i / 2)];
      
      block[i] = ((byte)(shadows[((val & 0xFF) >>> 4)] << 4 | shadows[(val & 0xF)]));
      
      block[(i + 1)] = val;
    }
    
    int tmp203_202 = (block.length - 2 * z); byte[] tmp203_193 = block;tmp203_193[tmp203_202] = ((byte)(tmp203_193[tmp203_202] ^ r));
    block[(block.length - 1)] = ((byte)(block[(block.length - 1)] << 4 | 0x6));
    
    int maxBit = 8 - (bitSize - 1) % 8;
    int offSet = 0;
    
    if (maxBit != 8)
    {
      int tmp259_258 = 0; byte[] tmp259_256 = block;tmp259_256[tmp259_258] = ((byte)(tmp259_256[tmp259_258] & 255 >>> maxBit)); int 
        tmp273_272 = 0; byte[] tmp273_270 = block;tmp273_270[tmp273_272] = ((byte)(tmp273_270[tmp273_272] | 128 >>> maxBit));
    }
    else
    {
      block[0] = 0; int 
        tmp295_294 = 1; byte[] tmp295_292 = block;tmp295_292[tmp295_294] = ((byte)(tmp295_292[tmp295_294] | 0x80));
      offSet = 1;
    }
    
    return engine.processBlock(block, offSet, block.length - offSet);
  }
  






  private byte[] decodeBlock(byte[] in, int inOff, int inLen)
    throws InvalidCipherTextException
  {
    byte[] block = engine.processBlock(in, inOff, inLen);
    int r = 1;
    int t = (bitSize + 13) / 16;
    
    BigInteger iS = new BigInteger(1, block);
    BigInteger iR;
    if (iS.mod(SIXTEEN).equals(SIX))
    {
      iR = iS;
    } else { BigInteger iR;
      if (modulus.subtract(iS).mod(SIXTEEN).equals(SIX))
      {
        iR = modulus.subtract(iS);
      }
      else
      {
        throw new InvalidCipherTextException("resulting integer iS or (modulus - iS) is not congruent to 6 mod 16"); }
    }
    BigInteger iR;
    block = convertOutputDecryptOnly(iR);
    
    if ((block[(block.length - 1)] & 0xF) != 6)
    {
      throw new InvalidCipherTextException("invalid forcing byte in block");
    }
    
    block[(block.length - 1)] = ((byte)((block[(block.length - 1)] & 0xFF) >>> 4 | inverse[((block[(block.length - 2)] & 0xFF) >> 4)] << 4));
    block[0] = ((byte)(shadows[((block[1] & 0xFF) >>> 4)] << 4 | shadows[(block[1] & 0xF)]));
    

    boolean boundaryFound = false;
    int boundary = 0;
    
    for (int i = block.length - 1; i >= block.length - 2 * t; i -= 2)
    {
      int val = shadows[((block[i] & 0xFF) >>> 4)] << 4 | shadows[(block[i] & 0xF)];
      

      if (((block[(i - 1)] ^ val) & 0xFF) != 0)
      {
        if (!boundaryFound)
        {
          boundaryFound = true;
          r = (block[(i - 1)] ^ val) & 0xFF;
          boundary = i - 1;
        }
        else
        {
          throw new InvalidCipherTextException("invalid tsums in block");
        }
      }
    }
    
    block[boundary] = 0;
    
    byte[] nblock = new byte[(block.length - boundary) / 2];
    
    for (int i = 0; i < nblock.length; i++)
    {
      nblock[i] = block[(2 * i + boundary + 1)];
    }
    
    padBits = (r - 1);
    
    return nblock;
  }
  
  private static byte[] convertOutputDecryptOnly(BigInteger result)
  {
    byte[] output = result.toByteArray();
    if (output[0] == 0)
    {
      byte[] tmp = new byte[output.length - 1];
      System.arraycopy(output, 1, tmp, 0, tmp.length);
      return tmp;
    }
    return output;
  }
}
