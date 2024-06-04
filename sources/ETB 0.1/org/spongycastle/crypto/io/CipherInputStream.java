package org.spongycastle.crypto.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.SkippingCipher;
import org.spongycastle.crypto.StreamCipher;
import org.spongycastle.crypto.modes.AEADBlockCipher;
import org.spongycastle.util.Arrays;






















public class CipherInputStream
  extends FilterInputStream
{
  private static final int INPUT_BUF_SIZE = 2048;
  private SkippingCipher skippingCipher;
  private byte[] inBuf;
  private BufferedBlockCipher bufferedBlockCipher;
  private StreamCipher streamCipher;
  private AEADBlockCipher aeadBlockCipher;
  private byte[] buf;
  private byte[] markBuf;
  private int bufOff;
  private int maxBuf;
  private boolean finalized;
  private long markPosition;
  private int markBufOff;
  
  public CipherInputStream(InputStream is, BufferedBlockCipher cipher)
  {
    this(is, cipher, 2048);
  }
  





  public CipherInputStream(InputStream is, StreamCipher cipher)
  {
    this(is, cipher, 2048);
  }
  





  public CipherInputStream(InputStream is, AEADBlockCipher cipher)
  {
    this(is, cipher, 2048);
  }
  







  public CipherInputStream(InputStream is, BufferedBlockCipher cipher, int bufSize)
  {
    super(is);
    
    bufferedBlockCipher = cipher;
    inBuf = new byte[bufSize];
    skippingCipher = ((cipher instanceof SkippingCipher) ? (SkippingCipher)cipher : null);
  }
  






  public CipherInputStream(InputStream is, StreamCipher cipher, int bufSize)
  {
    super(is);
    
    streamCipher = cipher;
    inBuf = new byte[bufSize];
    skippingCipher = ((cipher instanceof SkippingCipher) ? (SkippingCipher)cipher : null);
  }
  






  public CipherInputStream(InputStream is, AEADBlockCipher cipher, int bufSize)
  {
    super(is);
    
    aeadBlockCipher = cipher;
    inBuf = new byte[bufSize];
    skippingCipher = ((cipher instanceof SkippingCipher) ? (SkippingCipher)cipher : null);
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
      int read = in.read(inBuf);
      if (read == -1)
      {
        finaliseCipher();
        if (maxBuf == 0)
        {
          return -1;
        }
        return maxBuf;
      }
      
      try
      {
        ensureCapacity(read, false);
        if (bufferedBlockCipher != null)
        {
          maxBuf = bufferedBlockCipher.processBytes(inBuf, 0, read, buf, 0);
        }
        else if (aeadBlockCipher != null)
        {
          maxBuf = aeadBlockCipher.processBytes(inBuf, 0, read, buf, 0);
        }
        else
        {
          streamCipher.processBytes(inBuf, 0, read, buf, 0);
          maxBuf = read;
        }
      }
      catch (Exception e)
      {
        throw new CipherIOException("Error processing stream ", e);
      }
    }
    return maxBuf;
  }
  
  private void finaliseCipher()
    throws IOException
  {
    try
    {
      finalized = true;
      ensureCapacity(0, true);
      if (bufferedBlockCipher != null)
      {
        maxBuf = bufferedBlockCipher.doFinal(buf, 0);
      }
      else if (aeadBlockCipher != null)
      {
        maxBuf = aeadBlockCipher.doFinal(buf, 0);
      }
      else
      {
        maxBuf = 0;
      }
    }
    catch (InvalidCipherTextException e)
    {
      throw new InvalidCipherTextIOException("Error finalising cipher", e);
    }
    catch (Exception e)
    {
      throw new IOException("Error finalising cipher " + e);
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
  














  public int read(byte[] b)
    throws IOException
  {
    return read(b, 0, b.length);
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
    
    if (skippingCipher != null)
    {
      int avail = available();
      if (n <= avail)
      {
        bufOff = ((int)(bufOff + n));
        
        return n;
      }
      
      bufOff = maxBuf;
      
      long skip = in.skip(n - avail);
      
      long cSkip = skippingCipher.skip(skip);
      
      if (skip != cSkip)
      {
        throw new IOException("Unable to skip cipher " + skip + " bytes.");
      }
      
      return skip + avail;
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
  






  private void ensureCapacity(int updateSize, boolean finalOutput)
  {
    int bufLen = updateSize;
    if (finalOutput)
    {
      if (bufferedBlockCipher != null)
      {
        bufLen = bufferedBlockCipher.getOutputSize(updateSize);
      }
      else if (aeadBlockCipher != null)
      {
        bufLen = aeadBlockCipher.getOutputSize(updateSize);
      }
      

    }
    else if (bufferedBlockCipher != null)
    {
      bufLen = bufferedBlockCipher.getUpdateOutputSize(updateSize);
    }
    else if (aeadBlockCipher != null)
    {
      bufLen = aeadBlockCipher.getUpdateOutputSize(updateSize);
    }
    

    if ((buf == null) || (buf.length < bufLen))
    {
      buf = new byte[bufLen];
    }
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
    

    markPosition = 0L;
    if (markBuf != null)
    {
      Arrays.fill(markBuf, (byte)0);
      markBuf = null;
    }
    if (buf != null)
    {
      Arrays.fill(buf, (byte)0);
      buf = null;
    }
    Arrays.fill(inBuf, (byte)0);
  }
  








  public void mark(int readlimit)
  {
    in.mark(readlimit);
    if (skippingCipher != null)
    {
      markPosition = skippingCipher.getPosition();
    }
    
    if (buf != null)
    {
      markBuf = new byte[buf.length];
      System.arraycopy(buf, 0, markBuf, 0, buf.length);
    }
    
    markBufOff = bufOff;
  }
  





  public void reset()
    throws IOException
  {
    if (skippingCipher == null)
    {
      throw new IOException("cipher must implement SkippingCipher to be used with reset()");
    }
    
    in.reset();
    
    skippingCipher.seekTo(markPosition);
    
    if (markBuf != null)
    {
      buf = markBuf;
    }
    
    bufOff = markBufOff;
  }
  






  public boolean markSupported()
  {
    if (skippingCipher != null)
    {
      return in.markSupported();
    }
    
    return false;
  }
}
