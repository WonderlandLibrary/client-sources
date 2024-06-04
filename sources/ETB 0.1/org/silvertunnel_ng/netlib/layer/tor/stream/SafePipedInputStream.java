package org.silvertunnel_ng.netlib.layer.tor.stream;

import java.io.IOException;
import java.io.PipedInputStream;


























class SafePipedInputStream
  extends PipedInputStream
{
  SafePipedInputStream() {}
  
  public int read()
    throws IOException
  {
    try
    {
      return super.read();

    }
    catch (IOException e)
    {

      String msg = e.getMessage();
      if ((msg != null) && (msg.equals("Write end dead")))
      {
        return -1;
      }
      

      throw e;
    }
  }
  

  public int read(byte[] b, int off, int len)
    throws IOException
  {
    try
    {
      return super.read(b, off, len);

    }
    catch (IOException e)
    {

      String msg = e.getMessage();
      if ((msg != null) && (
        (msg.equals("Write end dead")) || 
        (msg.equals("Pipe closed"))))
      {
        b = null;
        return 0;
      }
      

      throw e;
    }
  }
}
