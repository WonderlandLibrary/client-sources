package org.silvertunnel_ng.netlib.layer.modification;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
























public class ByteModificatorInputStream
  extends FilterInputStream
{
  private final ByteModificator byteModificator;
  
  protected ByteModificatorInputStream(InputStream arg0, ByteModificator byteModificator)
  {
    super(arg0);
    this.byteModificator = byteModificator;
  }
  
  public int read()
    throws IOException
  {
    return byteModificator.modify((byte)in.read());
  }
  
  public int read(byte[] b, int off, int len)
    throws IOException
  {
    int numOfBytes = in.read(b, off, len);
    for (int i = 0; i < numOfBytes; i++)
    {
      b[(off + i)] = byteModificator.modify(b[(off + i)]);
    }
    return numOfBytes;
  }
}
