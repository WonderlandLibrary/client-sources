package org.silvertunnel_ng.netlib.layer.tor.stream;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.CellRelayData;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

























class TCPStreamThreadJava2TorThread
  extends Thread
{
  private static final Logger LOG = LoggerFactory.getLogger(TCPStreamThreadJava2TorThread.class);
  
  private final TCPStream stream;
  
  private PipedOutputStream sout;
  
  private PipedInputStream fromjava;
  
  private boolean stopped;
  
  TCPStreamThreadJava2TorThread(TCPStream stream)
  {
    this.stream = stream;
    try
    {
      sout = new PipedOutputStream();
      fromjava = new PipedInputStream(sout);
    }
    catch (IOException e)
    {
      LOG.error("TCPStreamThreadJava2Tor: caught IOException " + e
        .getMessage());
    }
    start();
  }
  
  public void close()
  {
    stopped = true;
    interrupt();
  }
  

  public void run()
  {
    while ((!stream.isClosed()) && (!stopped))
    {
      try
      {
        int readBytes = fromjava.available();
        while ((readBytes > 0) && (!stopped))
        {
          LOG.debug("TCPStreamThreadJava2Tor.run(): read {} bytes from application", Integer.valueOf(readBytes));
          CellRelayData cell = new CellRelayData(stream);
          cell.setLength(readBytes);
          if (cell.getLength() > cell.getData().length)
          {
            cell.setLength(cell.getData().length);
          }
          int b0 = fromjava.read(cell.getData(), 0, cell
            .getLength());
          readBytes -= b0;
          if (b0 < cell.getLength())
          {
            LOG.warn("TCPStreamThreadJava2Tor.run(): read " + b0 + " bytes where " + cell
              .getLength() + " were advertised");
            
            cell.setLength(b0);
          }
          
          if (cell.getLength() > 0)
          {
            try
            {
              stream.sendCell(cell);
            }
            catch (TorException exception)
            {
              LOG.warn("got exception while tring to send RELAY_DATA cell", exception);
            }
          }
        }
        Thread.yield();

      }
      catch (IOException e)
      {
        LOG.warn("TCPStreamThreadJava2Tor.run(): caught IOException " + e
          .getMessage());
      }
    }
  }
}
