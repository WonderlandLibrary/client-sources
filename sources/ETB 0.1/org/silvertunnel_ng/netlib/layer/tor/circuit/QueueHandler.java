package org.silvertunnel_ng.netlib.layer.tor.circuit;

import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.Cell;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;

public abstract interface QueueHandler
{
  public abstract boolean handleCell(Cell paramCell)
    throws TorException;
  
  public abstract void close();
}
