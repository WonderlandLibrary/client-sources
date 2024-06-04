package org.spongycastle.jcajce.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import javax.crypto.Cipher;
import org.spongycastle.crypto.io.InvalidCipherTextIOException;



















public class CipherInputStream
  extends FilterInputStream
{
  private final Cipher cipher;
  private final byte[] inputBuffer = new byte['Ȁ'];
  private boolean finalized = false;
  
  private byte[] buf;
  
  private int maxBuf;
  
  private int bufOff;
  
  public CipherInputStream(InputStream input, Cipher cipher)
  {
    super(input);
    this.cipher = cipher;
  }
  






  private int nextChunk()
    throws IOException
  {
    if (finalized)
    {
      return -1;
    }
    
    bufOff = 0;
    maxBuf = 0;
    

    while (maxBuf == 0)
    {
      int read = in.read(inputBuffer);
      if (read == -1)
      {
        buf = finaliseCipher();
        if ((buf == null) || (buf.length == 0))
        {
          return -1;
        }
        maxBuf = buf.length;
        return maxBuf;
      }
      
      buf = cipher.update(inputBuffer, 0, read);
      if (buf != null)
      {
        maxBuf = buf.length;
      }
    }
    return maxBuf;
  }
  
  private byte[] finaliseCipher()
    throws InvalidCipherTextIOException
  {
    try
    {
      finalized = true;
      return cipher.doFinal();
    }
    catch (GeneralSecurityException e)
    {
      throw new InvalidCipherTextIOException("Error finalising cipher", e);
    }
  }
  










  public int read()
    throws IOException
  {
    if (bufOff >= maxBuf)
    {
      if (nextChunk() < 0)
      {
        return -1;
      }
    }
    
    return buf[(bufOff++)] & 0xFF;
  }
  















  public int read(byte[] b, int off, int len)
    throws IOException
  {
    if (bufOff >= maxBuf)
    {
      if (nextChunk() < 0)
      {
        return -1;
      }
    }
    
    int toSupply = Math.min(len, available());
    System.arraycopy(buf, bufOff, b, off, toSupply);
    bufOff += toSupply;
    return toSupply;
  }
  
  public long skip(long n)
    throws IOException
  {
    if (n <= 0L)
    {
      return 0L;
    }
    
    int skip = (int)Math.min(n, available());
    bufOff += skip;
    return skip;
  }
  
  public int available()
    throws IOException
  {
    return maxBuf - bufOff;
  }
  








  public void close()
    throws IOException
  {
    try
    {
      in.close();
      


      if (!finalized)
      {


        finaliseCipher();
      }
    }
    finally
    {
      if (!finalized)
      {


        finaliseCipher();
      }
    }
    maxBuf = (this.bufOff = 0);
  }
  

  public void mark(int readlimit) {}
  

  public void reset()
    throws IOException
  {}
  

  public boolean markSupported()
  {
    return false;
  }
}
