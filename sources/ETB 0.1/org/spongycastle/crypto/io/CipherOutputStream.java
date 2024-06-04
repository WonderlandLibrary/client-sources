package org.spongycastle.crypto.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.StreamCipher;
import org.spongycastle.crypto.modes.AEADBlockCipher;












public class CipherOutputStream
  extends FilterOutputStream
{
  private BufferedBlockCipher bufferedBlockCipher;
  private StreamCipher streamCipher;
  private AEADBlockCipher aeadBlockCipher;
  private final byte[] oneByte = new byte[1];
  


  private byte[] buf;
  



  public CipherOutputStream(OutputStream os, BufferedBlockCipher cipher)
  {
    super(os);
    bufferedBlockCipher = cipher;
  }
  






  public CipherOutputStream(OutputStream os, StreamCipher cipher)
  {
    super(os);
    streamCipher = cipher;
  }
  



  public CipherOutputStream(OutputStream os, AEADBlockCipher cipher)
  {
    super(os);
    aeadBlockCipher = cipher;
  }
  







  public void write(int b)
    throws IOException
  {
    oneByte[0] = ((byte)b);
    
    if (streamCipher != null)
    {
      out.write(streamCipher.returnByte((byte)b));
    }
    else
    {
      write(oneByte, 0, 1);
    }
  }
  














  public void write(byte[] b)
    throws IOException
  {
    write(b, 0, b.length);
  }
  












  public void write(byte[] b, int off, int len)
    throws IOException
  {
    ensureCapacity(len, false);
    
    if (bufferedBlockCipher != null)
    {
      int outLen = bufferedBlockCipher.processBytes(b, off, len, buf, 0);
      
      if (outLen != 0)
      {
        out.write(buf, 0, outLen);
      }
    }
    else if (aeadBlockCipher != null)
    {
      int outLen = aeadBlockCipher.processBytes(b, off, len, buf, 0);
      
      if (outLen != 0)
      {
        out.write(buf, 0, outLen);
      }
    }
    else
    {
      streamCipher.processBytes(b, off, len, buf, 0);
      
      out.write(buf, 0, len);
    }
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
  













  public void flush()
    throws IOException
  {
    out.flush();
  }
  

















  public void close()
    throws IOException
  {
    ensureCapacity(0, true);
    IOException error = null;
    try
    {
      if (bufferedBlockCipher != null)
      {
        int outLen = bufferedBlockCipher.doFinal(buf, 0);
        
        if (outLen != 0)
        {
          out.write(buf, 0, outLen);
        }
      }
      else if (aeadBlockCipher != null)
      {
        int outLen = aeadBlockCipher.doFinal(buf, 0);
        
        if (outLen != 0)
        {
          out.write(buf, 0, outLen);
        }
      }
      else if (streamCipher != null)
      {
        streamCipher.reset();
      }
    }
    catch (InvalidCipherTextException e)
    {
      error = new InvalidCipherTextIOException("Error finalising cipher data", e);
    }
    catch (Exception e)
    {
      error = new CipherIOException("Error closing stream: ", e);
    }
    
    try
    {
      flush();
      out.close();

    }
    catch (IOException e)
    {
      if (error == null)
      {
        error = e;
      }
    }
    if (error != null)
    {
      throw error;
    }
  }
}
