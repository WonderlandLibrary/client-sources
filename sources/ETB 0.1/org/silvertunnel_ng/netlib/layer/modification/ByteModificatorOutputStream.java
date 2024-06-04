package org.silvertunnel_ng.netlib.layer.modification;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
























public class ByteModificatorOutputStream
  extends FilterOutputStream
{
  private final ByteModificator byteModificator;
  
  protected ByteModificatorOutputStream(OutputStream arg0, ByteModificator byteModificator)
  {
    super(arg0);
    this.byteModificator = byteModificator;
  }
  
  public void write(int b)
    throws IOException
  {
    out.write(byteModificator.modify((byte)b));
  }
}
