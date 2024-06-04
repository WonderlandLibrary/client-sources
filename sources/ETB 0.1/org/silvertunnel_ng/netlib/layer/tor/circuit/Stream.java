package org.silvertunnel_ng.netlib.layer.tor.circuit;

import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.Cell;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;

public abstract interface Stream
{
  public abstract void close(boolean paramBoolean);
  
  public abstract void setId(int paramInt);
  
  public abstract int getId();
  
  public abstract boolean isClosed();
  
  public abstract long getLastCellSentDate();
  
  public abstract Circuit getCircuit();
  
  public abstract void sendCell(Cell paramCell)
    throws TorException;
  
  public abstract void processCell(Cell paramCell)
    throws TorException;
}
