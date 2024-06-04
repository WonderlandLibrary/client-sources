package org.spongycastle.jcajce.io;

import java.io.IOException;
import java.io.OutputStream;
import javax.crypto.Mac;











public final class MacOutputStream
  extends OutputStream
{
  private Mac mac;
  
  public MacOutputStream(Mac mac)
  {
    this.mac = mac;
  }
  






  public void write(int b)
    throws IOException
  {
    mac.update((byte)b);
  }
  












  public void write(byte[] bytes, int off, int len)
    throws IOException
  {
    mac.update(bytes, off, len);
  }
  





  public byte[] getMac()
  {
    return mac.doFinal();
  }
}
