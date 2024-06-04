package org.silvertunnel_ng.netlib.layer.tor.stream;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.CellRelayData;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;












































class TCPStreamOutputStream
  extends OutputStream
{
  private static final Logger LOG = LoggerFactory.getLogger(TCPStreamOutputStream.class);
  
  private final TCPStream stream;
  
  private PipedOutputStream sout;
  
  private PipedInputStream fromjava;
  
  private boolean stopped;
  private final byte[] buffer;
  private int bufferFilled;
  
  TCPStreamOutputStream(TCPStream stream)
  {
    this.stream = stream;
    buffer = new byte['ǲ'];
    bufferFilled = 0;
    try
    {
      sout = new PipedOutputStream();
      fromjava = new PipedInputStream(sout);
    }
    catch (IOException e)
    {
      LOG.error("TCPStreamThreadJava2Tor: caught IOException " + e
        .getMessage(), e);
    }
  }
  

  public void close()
  {
    stopped = true;
  }
  
  public void write(int b)
    throws IOException
  {
    byte[] bytes = new byte[1];
    bytes[0] = ((byte)b);
    write(bytes, 0, 1);
  }
  
  public synchronized void flush()
    throws IOException
  {
    if (stopped)
    {



      return;
    }
    if (bufferFilled < 1)
    {
      return;
    }
    if (bufferFilled > buffer.length)
    {
      throw new IOException("TCPStreamOutputStream.flush(): there must be an error somewhere else");
    }
    
    CellRelayData cell = new CellRelayData(stream);
    cell.setLength(bufferFilled);
    if (cell.getLength() > cell.getData().length)
    {
      cell.setLength(cell.getData().length);
    }
    System.arraycopy(buffer, 0, cell.getData(), 0, bufferFilled);
    try
    {
      stream.sendCell(cell);
    }
    catch (TorException exception)
    {
      throw new IOException(exception);
    }
    bufferFilled = 0;
  }
  

  public void write(byte[] b, int off, int len)
    throws IOException
  {
    if (len == 0)
    {
      return;
    }
    






    int bytesFree = buffer.length;
    if (bufferFilled == buffer.length)
    {
      flush();
    }
    else
    {
      bytesFree = buffer.length - bufferFilled;
    }
    
    if (len > bytesFree)
    {
      write(b, off, bytesFree);
      write(b, off + bytesFree, len - bytesFree);
    }
    else
    {
      System.arraycopy(b, off, buffer, bufferFilled, len);
      bufferFilled += len;
      

      flush();
    }
  }
  
  public void write(byte[] b)
    throws IOException
  {
    write(b, 0, b.length);
  }
}
