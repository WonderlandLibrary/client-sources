package org.spongycastle.crypto;




public class BufferedAsymmetricBlockCipher
{
  protected byte[] buf;
  


  protected int bufOff;
  


  private final AsymmetricBlockCipher cipher;
  


  public BufferedAsymmetricBlockCipher(AsymmetricBlockCipher cipher)
  {
    this.cipher = cipher;
  }
  





  public AsymmetricBlockCipher getUnderlyingCipher()
  {
    return cipher;
  }
  





  public int getBufferPosition()
  {
    return bufOff;
  }
  









  public void init(boolean forEncryption, CipherParameters params)
  {
    reset();
    
    cipher.init(forEncryption, params);
    




    buf = new byte[cipher.getInputBlockSize() + (forEncryption ? 1 : 0)];
    bufOff = 0;
  }
  





  public int getInputBlockSize()
  {
    return cipher.getInputBlockSize();
  }
  





  public int getOutputBlockSize()
  {
    return cipher.getOutputBlockSize();
  }
  






  public void processByte(byte in)
  {
    if (bufOff >= buf.length)
    {
      throw new DataLengthException("attempt to process message too long for cipher");
    }
    
    buf[(bufOff++)] = in;
  }
  










  public void processBytes(byte[] in, int inOff, int len)
  {
    if (len == 0)
    {
      return;
    }
    
    if (len < 0)
    {
      throw new IllegalArgumentException("Can't have a negative input length!");
    }
    
    if (bufOff + len > buf.length)
    {
      throw new DataLengthException("attempt to process message too long for cipher");
    }
    
    System.arraycopy(in, inOff, buf, bufOff, len);
    bufOff += len;
  }
  








  public byte[] doFinal()
    throws InvalidCipherTextException
  {
    byte[] out = cipher.processBlock(buf, 0, bufOff);
    
    reset();
    
    return out;
  }
  






  public void reset()
  {
    if (buf != null)
    {
      for (int i = 0; i < buf.length; i++)
      {
        buf[i] = 0;
      }
    }
    
    bufOff = 0;
  }
}
