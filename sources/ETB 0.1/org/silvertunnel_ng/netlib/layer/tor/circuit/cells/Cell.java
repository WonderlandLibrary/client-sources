package org.silvertunnel_ng.netlib.layer.tor.circuit.cells;

import java.io.IOException;
import java.io.InputStream;
import org.silvertunnel_ng.netlib.layer.tor.circuit.Circuit;
import org.silvertunnel_ng.netlib.layer.tor.util.Encoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



























public class Cell
{
  private static final Logger LOG = LoggerFactory.getLogger(Cell.class);
  
  public static final int CELL_PADDING = 0;
  
  public static final int CELL_CREATE = 1;
  
  public static final int CELL_CREATED = 2;
  
  public static final int CELL_RELAY = 3;
  
  public static final int CELL_DESTROY = 4;
  
  public static final int CELL_CREATE_FAST = 5;
  
  public static final int CELL_CREATED_FAST = 6;
  
  public static final int CELL_RELAY_EARLY = 9;
  
  static final int CELL_TOTAL_SIZE = 512;
  
  static final int CELL_CIRCID_SIZE = 2;
  static final int CELL_COMMAND_SIZE = 1;
  static final int CELL_PAYLOAD_SIZE = 509;
  static final int CELL_CIRCID_POS = 0;
  static final int CELL_COMMAND_POS = 2;
  static final int CELL_PAYLOAD_POS = 3;
  private static final String[] TYPE_TO_STRING = { "padding", "create", "created", "relay", "destroy", "create-fast", "created-fast", "", "", "relay-early" };
  


  private int circuitId;
  


  private byte command;
  


  protected byte[] payload;
  

  protected Circuit outCircuit;
  


  Cell(Circuit outCircuit, int command)
  {
    payload = new byte['ǽ'];
    
    circuitId = outCircuit.getId();
    this.command = Encoding.intToNByteArray(command, 1)[0];
    this.outCircuit = outCircuit;
  }
  






  Cell(byte[] data)
    throws NullPointerException
  {
    initFromData(data);
  }
  





  public Cell(InputStream in)
    throws IOException
  {
    if (in == null)
    {
      throw new IOException("null as input stream given");
    }
    byte[] data = new byte['Ȁ'];
    int filled = 0;
    while (filled < data.length)
    {
      int n = in.read(data, filled, data.length - filled);
      if (n < 0)
      {
        throw new IOException("Cell.<init>: reached EOF");
      }
      filled += n;
      
      Thread.yield();
    }
    initFromData(data);
  }
  






  private void initFromData(byte[] data)
    throws NullPointerException
  {
    if (data == null)
    {
      throw new NullPointerException("no data given");
    }
    payload = new byte['ǽ'];
    
    circuitId = Encoding.byteArrayToInt(data, 0, 2);
    command = data[2];
    System.arraycopy(data, 3, payload, 0, payload.length);
    
    if (LOG.isDebugEnabled())
    {
      LOG.debug("Cell.initFromData: " + toString("Received "));
    }
  }
  




  public byte[] toByteArray()
  {
    byte[] buff = new byte['Ȁ'];
    
    if (LOG.isDebugEnabled())
    {
      LOG.debug("Cell.toByteArray(): " + toString("Sending "));
    }
    
    System.arraycopy(
      Encoding.intToNByteArray(circuitId, 2), 0, buff, 0, 2);
    

    buff[2] = command;
    System.arraycopy(payload, 0, buff, 3, payload.length);
    
    return buff;
  }
  


  public String toString()
  {
    return toString("");
  }
  



  private String toString(String description)
  {
    return description + "cell for circuit " + getCircuitId() + " with command " + type() + ". Payload:\n" + Encoding.toHexString(payload, 100);
  }
  
  public static String type(int t)
  {
    if ((t >= 0) && (t < TYPE_TO_STRING.length))
    {
      return TYPE_TO_STRING[t];
    }
    

    return "[" + t + "]";
  }
  

  public String type()
  {
    return type(command);
  }
  




  public final boolean isTypePadding()
  {
    return command == 0;
  }
  

  public final boolean isTypeCreated()
  {
    return command == 2;
  }
  

  public final boolean isTypeRelay()
  {
    return command == 3;
  }
  

  public final boolean isTypeDestroy()
  {
    return command == 4;
  }
  
  public int getCircuitId()
  {
    return circuitId;
  }
  
  public byte getCommand()
  {
    return command;
  }
  
  public void setCommand(byte command)
  {
    this.command = command;
  }
  
  public byte[] getPayload()
  {
    return payload;
  }
  
  public void setPayload(byte[] payload)
  {
    this.payload = payload;
  }
  
  public Circuit getOutCircuit()
  {
    return outCircuit;
  }
  
  public void setOutCircuit(Circuit outCircuit)
  {
    this.outCircuit = outCircuit;
  }
}
