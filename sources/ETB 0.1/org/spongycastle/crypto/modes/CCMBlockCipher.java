package org.spongycastle.crypto.modes;

import java.io.ByteArrayOutputStream;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.macs.CBCBlockCipherMac;
import org.spongycastle.crypto.params.AEADParameters;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.util.Arrays;








public class CCMBlockCipher
  implements AEADBlockCipher
{
  private BlockCipher cipher;
  private int blockSize;
  private boolean forEncryption;
  private byte[] nonce;
  private byte[] initialAssociatedText;
  private int macSize;
  private CipherParameters keyParam;
  private byte[] macBlock;
  private ExposedByteArrayOutputStream associatedText = new ExposedByteArrayOutputStream();
  private ExposedByteArrayOutputStream data = new ExposedByteArrayOutputStream();
  





  public CCMBlockCipher(BlockCipher c)
  {
    cipher = c;
    blockSize = c.getBlockSize();
    macBlock = new byte[blockSize];
    
    if (blockSize != 16)
    {
      throw new IllegalArgumentException("cipher required with a block size of 16.");
    }
  }
  





  public BlockCipher getUnderlyingCipher()
  {
    return cipher;
  }
  

  public void init(boolean forEncryption, CipherParameters params)
    throws IllegalArgumentException
  {
    this.forEncryption = forEncryption;
    
    CipherParameters cipherParameters;
    if ((params instanceof AEADParameters))
    {
      AEADParameters param = (AEADParameters)params;
      
      nonce = param.getNonce();
      initialAssociatedText = param.getAssociatedText();
      macSize = (param.getMacSize() / 8);
      cipherParameters = param.getKey();
    } else { CipherParameters cipherParameters;
      if ((params instanceof ParametersWithIV))
      {
        ParametersWithIV param = (ParametersWithIV)params;
        
        nonce = param.getIV();
        initialAssociatedText = null;
        macSize = (macBlock.length / 2);
        cipherParameters = param.getParameters();
      }
      else
      {
        throw new IllegalArgumentException("invalid parameters passed to CCM: " + params.getClass().getName());
      }
    }
    CipherParameters cipherParameters;
    if (cipherParameters != null)
    {
      keyParam = cipherParameters;
    }
    
    if ((nonce == null) || (nonce.length < 7) || (nonce.length > 13))
    {
      throw new IllegalArgumentException("nonce must have length from 7 to 13 octets");
    }
    
    reset();
  }
  
  public String getAlgorithmName()
  {
    return cipher.getAlgorithmName() + "/CCM";
  }
  
  public void processAADByte(byte in)
  {
    associatedText.write(in);
  }
  

  public void processAADBytes(byte[] in, int inOff, int len)
  {
    associatedText.write(in, inOff, len);
  }
  
  public int processByte(byte in, byte[] out, int outOff)
    throws DataLengthException, IllegalStateException
  {
    data.write(in);
    
    return 0;
  }
  
  public int processBytes(byte[] in, int inOff, int inLen, byte[] out, int outOff)
    throws DataLengthException, IllegalStateException
  {
    if (in.length < inOff + inLen)
    {
      throw new DataLengthException("Input buffer too short");
    }
    data.write(in, inOff, inLen);
    
    return 0;
  }
  
  public int doFinal(byte[] out, int outOff)
    throws IllegalStateException, InvalidCipherTextException
  {
    int len = processPacket(data.getBuffer(), 0, data.size(), out, outOff);
    
    reset();
    
    return len;
  }
  
  public void reset()
  {
    cipher.reset();
    associatedText.reset();
    data.reset();
  }
  






  public byte[] getMac()
  {
    byte[] mac = new byte[macSize];
    
    System.arraycopy(macBlock, 0, mac, 0, mac.length);
    
    return mac;
  }
  
  public int getUpdateOutputSize(int len)
  {
    return 0;
  }
  
  public int getOutputSize(int len)
  {
    int totalData = len + data.size();
    
    if (forEncryption)
    {
      return totalData + macSize;
    }
    
    return totalData < macSize ? 0 : totalData - macSize;
  }
  




  public byte[] processPacket(byte[] in, int inOff, int inLen)
    throws IllegalStateException, InvalidCipherTextException
  {
    byte[] output;
    


    byte[] output;
    


    if (forEncryption)
    {
      output = new byte[inLen + macSize];
    }
    else
    {
      if (inLen < macSize)
      {
        throw new InvalidCipherTextException("data too short");
      }
      output = new byte[inLen - macSize];
    }
    
    processPacket(in, inOff, inLen, output, 0);
    
    return output;
  }
  















  public int processPacket(byte[] in, int inOff, int inLen, byte[] output, int outOff)
    throws IllegalStateException, InvalidCipherTextException, DataLengthException
  {
    if (keyParam == null)
    {
      throw new IllegalStateException("CCM cipher unitialized.");
    }
    
    int n = nonce.length;
    int q = 15 - n;
    if (q < 4)
    {
      int limitLen = 1 << 8 * q;
      if (inLen >= limitLen)
      {
        throw new IllegalStateException("CCM packet too large for choice of q.");
      }
    }
    
    byte[] iv = new byte[blockSize];
    iv[0] = ((byte)(q - 1 & 0x7));
    System.arraycopy(nonce, 0, iv, 1, nonce.length);
    
    BlockCipher ctrCipher = new SICBlockCipher(cipher);
    ctrCipher.init(forEncryption, new ParametersWithIV(keyParam, iv));
    

    int inIndex = inOff;
    int outIndex = outOff;
    int outputLen;
    if (forEncryption)
    {
      int outputLen = inLen + macSize;
      if (output.length < outputLen + outOff)
      {
        throw new OutputLengthException("Output buffer too short.");
      }
      
      calculateMac(in, inOff, inLen, macBlock);
      
      byte[] encMac = new byte[blockSize];
      
      ctrCipher.processBlock(macBlock, 0, encMac, 0);
      
      while (inIndex < inOff + inLen - blockSize)
      {
        ctrCipher.processBlock(in, inIndex, output, outIndex);
        outIndex += blockSize;
        inIndex += blockSize;
      }
      
      byte[] block = new byte[blockSize];
      
      System.arraycopy(in, inIndex, block, 0, inLen + inOff - inIndex);
      
      ctrCipher.processBlock(block, 0, block, 0);
      
      System.arraycopy(block, 0, output, outIndex, inLen + inOff - inIndex);
      
      System.arraycopy(encMac, 0, output, outOff + inLen, macSize);
    }
    else
    {
      if (inLen < macSize)
      {
        throw new InvalidCipherTextException("data too short");
      }
      outputLen = inLen - macSize;
      if (output.length < outputLen + outOff)
      {
        throw new OutputLengthException("Output buffer too short.");
      }
      
      System.arraycopy(in, inOff + outputLen, macBlock, 0, macSize);
      
      ctrCipher.processBlock(macBlock, 0, macBlock, 0);
      
      for (int i = macSize; i != macBlock.length; i++)
      {
        macBlock[i] = 0;
      }
      
      while (inIndex < inOff + outputLen - blockSize)
      {
        ctrCipher.processBlock(in, inIndex, output, outIndex);
        outIndex += blockSize;
        inIndex += blockSize;
      }
      
      byte[] block = new byte[blockSize];
      
      System.arraycopy(in, inIndex, block, 0, outputLen - (inIndex - inOff));
      
      ctrCipher.processBlock(block, 0, block, 0);
      
      System.arraycopy(block, 0, output, outIndex, outputLen - (inIndex - inOff));
      
      byte[] calculatedMacBlock = new byte[blockSize];
      
      calculateMac(output, outOff, outputLen, calculatedMacBlock);
      
      if (!Arrays.constantTimeAreEqual(macBlock, calculatedMacBlock))
      {
        throw new InvalidCipherTextException("mac check in CCM failed");
      }
    }
    
    return outputLen;
  }
  
  private int calculateMac(byte[] data, int dataOff, int dataLen, byte[] macBlock)
  {
    Mac cMac = new CBCBlockCipherMac(cipher, macSize * 8);
    
    cMac.init(keyParam);
    



    byte[] b0 = new byte[16];
    
    if (hasAssociatedText())
    {
      int tmp47_46 = 0; byte[] tmp47_44 = b0;tmp47_44[tmp47_46] = ((byte)(tmp47_44[tmp47_46] | 0x40));
    }
    
    int tmp57_56 = 0; byte[] tmp57_54 = b0;tmp57_54[tmp57_56] = ((byte)(tmp57_54[tmp57_56] | ((cMac.getMacSize() - 2) / 2 & 0x7) << 3)); int 
    
      tmp81_80 = 0; byte[] tmp81_78 = b0;tmp81_78[tmp81_80] = ((byte)(tmp81_78[tmp81_80] | 15 - nonce.length - 1 & 0x7));
    
    System.arraycopy(nonce, 0, b0, 1, nonce.length);
    
    int q = dataLen;
    int count = 1;
    while (q > 0)
    {
      b0[(b0.length - count)] = ((byte)(q & 0xFF));
      q >>>= 8;
      count++;
    }
    
    cMac.update(b0, 0, b0.length);
    



    if (hasAssociatedText())
    {


      int textLength = getAssociatedTextLength();
      int extra; if (textLength < 65280)
      {
        cMac.update((byte)(textLength >> 8));
        cMac.update((byte)textLength);
        
        extra = 2;
      }
      else
      {
        cMac.update((byte)-1);
        cMac.update((byte)-2);
        cMac.update((byte)(textLength >> 24));
        cMac.update((byte)(textLength >> 16));
        cMac.update((byte)(textLength >> 8));
        cMac.update((byte)textLength);
        
        extra = 6;
      }
      
      if (initialAssociatedText != null)
      {
        cMac.update(initialAssociatedText, 0, initialAssociatedText.length);
      }
      if (associatedText.size() > 0)
      {
        cMac.update(associatedText.getBuffer(), 0, associatedText.size());
      }
      
      int extra = (extra + textLength) % 16;
      if (extra != 0)
      {
        for (int i = extra; i != 16; i++)
        {
          cMac.update((byte)0);
        }
      }
    }
    



    cMac.update(data, dataOff, dataLen);
    
    return cMac.doFinal(macBlock, 0);
  }
  
  private int getAssociatedTextLength()
  {
    return associatedText.size() + (initialAssociatedText == null ? 0 : initialAssociatedText.length);
  }
  
  private boolean hasAssociatedText()
  {
    return getAssociatedTextLength() > 0;
  }
  

  private class ExposedByteArrayOutputStream
    extends ByteArrayOutputStream
  {
    public ExposedByteArrayOutputStream() {}
    

    public byte[] getBuffer()
    {
      return buf;
    }
  }
}
