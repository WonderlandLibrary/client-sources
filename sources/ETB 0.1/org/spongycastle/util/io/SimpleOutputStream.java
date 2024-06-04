package org.spongycastle.util.io;

import java.io.IOException;
import java.io.OutputStream;

public abstract class SimpleOutputStream
  extends OutputStream
{
  public SimpleOutputStream() {}
  
  public void close() {}
  
  public void flush() {}
  
  public void write(int b)
    throws IOException
  {
    byte[] buf = { (byte)b };
    write(buf, 0, 1);
  }
}
