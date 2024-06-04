package org.spongycastle.crypto.macs;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.paddings.ISO7816d4Padding;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.util.Pack;





























public class CMac
  implements Mac
{
  private byte[] poly;
  private byte[] ZEROES;
  private byte[] mac;
  private byte[] buf;
  private int bufOff;
  private BlockCipher cipher;
  private int macSize;
  private byte[] Lu;
  private byte[] Lu2;
  
  public CMac(BlockCipher cipher)
  {
    this(cipher, cipher.getBlockSize() * 8);
  }
  












  public CMac(BlockCipher cipher, int macSizeInBits)
  {
    if (macSizeInBits % 8 != 0)
    {
      throw new IllegalArgumentException("MAC size must be multiple of 8");
    }
    
    if (macSizeInBits > cipher.getBlockSize() * 8)
    {


      throw new IllegalArgumentException("MAC size must be less or equal to " + cipher.getBlockSize() * 8);
    }
    
    this.cipher = new CBCBlockCipher(cipher);
    macSize = (macSizeInBits / 8);
    poly = lookupPoly(cipher.getBlockSize());
    
    mac = new byte[cipher.getBlockSize()];
    
    buf = new byte[cipher.getBlockSize()];
    
    ZEROES = new byte[cipher.getBlockSize()];
    
    bufOff = 0;
  }
  
  public String getAlgorithmName()
  {
    return cipher.getAlgorithmName();
  }
  
  private static int shiftLeft(byte[] block, byte[] output)
  {
    int i = block.length;
    int bit = 0;
    for (;;) { i--; if (i < 0)
        break;
      int b = block[i] & 0xFF;
      output[i] = ((byte)(b << 1 | bit));
      bit = b >>> 7 & 0x1;
    }
    return bit;
  }
  
  private byte[] doubleLu(byte[] in)
  {
    byte[] ret = new byte[in.length];
    int carry = shiftLeft(in, ret);
    



    int mask = -carry & 0xFF; int 
      tmp24_23 = (in.length - 3); byte[] tmp24_19 = ret;tmp24_19[tmp24_23] = ((byte)(tmp24_19[tmp24_23] ^ poly[1] & mask)); int 
      tmp43_42 = (in.length - 2); byte[] tmp43_38 = ret;tmp43_38[tmp43_42] = ((byte)(tmp43_38[tmp43_42] ^ poly[2] & mask)); int 
      tmp62_61 = (in.length - 1); byte[] tmp62_57 = ret;tmp62_57[tmp62_61] = ((byte)(tmp62_57[tmp62_61] ^ poly[3] & mask));
    
    return ret; }
  
  private static byte[] lookupPoly(int blockSizeLength) { int xor;
    int xor;
    int xor;
    int xor;
    int xor; int xor; int xor; int xor; int xor; int xor; int xor; int xor; int xor; switch (blockSizeLength * 8)
    {
    case 64: 
      xor = 27;
      break;
    case 128: 
      xor = 135;
      break;
    case 160: 
      xor = 45;
      break;
    case 192: 
      xor = 135;
      break;
    case 224: 
      xor = 777;
      break;
    case 256: 
      xor = 1061;
      break;
    case 320: 
      xor = 27;
      break;
    case 384: 
      xor = 4109;
      break;
    case 448: 
      xor = 2129;
      break;
    case 512: 
      xor = 293;
      break;
    case 768: 
      xor = 655377;
      break;
    case 1024: 
      xor = 524355;
      break;
    case 2048: 
      xor = 548865;
      break;
    default: 
      throw new IllegalArgumentException("Unknown block size for CMAC: " + blockSizeLength * 8);
    }
    int xor;
    return Pack.intToBigEndian(xor);
  }
  
  public void init(CipherParameters params)
  {
    validate(params);
    
    cipher.init(true, params);
    

    byte[] L = new byte[ZEROES.length];
    cipher.processBlock(ZEROES, 0, L, 0);
    Lu = doubleLu(L);
    Lu2 = doubleLu(Lu);
    
    reset();
  }
  
  void validate(CipherParameters params)
  {
    if (params != null)
    {
      if (!(params instanceof KeyParameter))
      {

        throw new IllegalArgumentException("CMac mode only permits key to be set.");
      }
    }
  }
  
  public int getMacSize()
  {
    return macSize;
  }
  
  public void update(byte in)
  {
    if (bufOff == buf.length)
    {
      cipher.processBlock(buf, 0, mac, 0);
      bufOff = 0;
    }
    
    buf[(bufOff++)] = in;
  }
  
  public void update(byte[] in, int inOff, int len)
  {
    if (len < 0)
    {
      throw new IllegalArgumentException("Can't have a negative input length!");
    }
    

    int blockSize = cipher.getBlockSize();
    int gapLen = blockSize - bufOff;
    
    if (len > gapLen)
    {
      System.arraycopy(in, inOff, buf, bufOff, gapLen);
      
      cipher.processBlock(buf, 0, mac, 0);
      
      bufOff = 0;
      len -= gapLen;
      inOff += gapLen;
      
      while (len > blockSize)
      {
        cipher.processBlock(in, inOff, mac, 0);
        
        len -= blockSize;
        inOff += blockSize;
      }
    }
    
    System.arraycopy(in, inOff, buf, bufOff, len);
    
    bufOff += len;
  }
  
  public int doFinal(byte[] out, int outOff)
  {
    int blockSize = cipher.getBlockSize();
    byte[] lu;
    byte[] lu;
    if (bufOff == blockSize)
    {
      lu = Lu;
    }
    else
    {
      new ISO7816d4Padding().addPadding(buf, bufOff);
      lu = Lu2;
    }
    
    for (int i = 0; i < mac.length; i++)
    {
      int tmp71_69 = i; byte[] tmp71_66 = buf;tmp71_66[tmp71_69] = ((byte)(tmp71_66[tmp71_69] ^ lu[i]));
    }
    
    cipher.processBlock(buf, 0, mac, 0);
    
    System.arraycopy(mac, 0, out, outOff, macSize);
    
    reset();
    
    return macSize;
  }
  






  public void reset()
  {
    for (int i = 0; i < buf.length; i++)
    {
      buf[i] = 0;
    }
    
    bufOff = 0;
    



    cipher.reset();
  }
}
