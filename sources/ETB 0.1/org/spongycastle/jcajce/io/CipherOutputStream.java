package org.spongycastle.jcajce.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import javax.crypto.Cipher;
import org.spongycastle.crypto.io.InvalidCipherTextIOException;




















public class CipherOutputStream
  extends FilterOutputStream
{
  private final Cipher cipher;
  private final byte[] oneByte = new byte[1];
  



  public CipherOutputStream(OutputStream output, Cipher cipher)
  {
    super(output);
    this.cipher = cipher;
  }
  






  public void write(int b)
    throws IOException
  {
    oneByte[0] = ((byte)b);
    write(oneByte, 0, 1);
  }
  









  public void write(byte[] b, int off, int len)
    throws IOException
  {
    byte[] outData = cipher.update(b, off, len);
    if (outData != null)
    {
      out.write(outData);
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
    IOException error = null;
    try
    {
      byte[] outData = cipher.doFinal();
      if (outData != null)
      {
        out.write(outData);
      }
    }
    catch (GeneralSecurityException e)
    {
      error = new InvalidCipherTextIOException("Error during cipher finalisation", e);
    }
    catch (Exception e)
    {
      error = new IOException("Error closing stream: " + e);
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
