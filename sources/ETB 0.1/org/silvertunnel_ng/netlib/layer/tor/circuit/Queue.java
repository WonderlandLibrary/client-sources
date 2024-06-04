package org.silvertunnel_ng.netlib.layer.tor.circuit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.Cell;
import org.silvertunnel_ng.netlib.layer.tor.circuit.cells.CellRelay;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;
import org.silvertunnel_ng.netlib.layer.tor.util.TorNoAnswerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

























public final class Queue
{
  private static final Logger LOG = LoggerFactory.getLogger(Queue.class);
  
  private static final int WAIT = 100;
  
  private volatile boolean closed = false;
  private volatile boolean addClosed = false;
  
  private int timeoutMs = 1000;
  private final List<Cell> queue = new ArrayList();
  private final List<QueueHandler> handler = new ArrayList();
  






  public Queue(int timeoutS)
  {
    timeoutMs = (timeoutS * 1000);
  }
  
  public Queue()
  {
    this(1000);
  }
  
  public synchronized void addHandler(QueueHandler qh)
  {
    handler.add(qh);
  }
  
  public synchronized boolean removeHandler(QueueHandler qh)
  {
    return handler.remove(qh);
  }
  

  public synchronized void add(Cell cell)
  {
    if (addClosed)
    {
      return;
    }
    
    try
    {
      for (QueueHandler qh : handler)
      {
        try
        {
          if (qh.handleCell(cell))
          {
            return;
          }
        }
        catch (TorException te)
        {
          LOG.debug("got TorException : {}", te.getMessage(), te);
        }
      }
    }
    catch (ClassCastException e)
    {
      LOG.debug("got ClassCastException : {}", e.getMessage(), e);
    }
    

    queue.add(cell);
    
    notify();
  }
  



  public synchronized void close()
  {
    addClosed = true;
    closed = true;
    
    for (QueueHandler qh : handler)
    {
      qh.close();
    }
    
    queue.clear();
    notify();
  }
  



  public synchronized void closeAdd()
  {
    addClosed = true;
    notify();
  }
  

  boolean isEmpty()
  {
    if (closed)
    {
      return true;
    }
    return queue.size() == 0;
  }
  
  public Cell get()
  {
    return get(timeoutMs);
  }
  








  public synchronized Cell get(int timeout)
  {
    boolean forever = false;
    if (timeout == -1)
    {
      forever = true;
    }
    
    int retries = timeout / 100;
    
    do
    {
      if (closed)
      {
        return null;
      }
      
      if (queue.size() > 0)
      {
        Cell cell = (Cell)queue.get(0);
        queue.remove(0);
        return cell;
      }
      if (addClosed)
      {
        closed = true;
        return null;
      }
      

      try
      {
        wait(100L);
      }
      catch (InterruptedException e)
      {
        LOG.debug("got InterruptedException : {}", e.getMessage(), e);
      }
      retries--;
    }
    while ((forever) || (retries > 0) || (queue.size() > 0));
    
    return null;
  }
  



  public Cell receiveCell(int type)
    throws IOException, TorException, TorNoAnswerException
  {
    Cell cell = get();
    if (cell == null)
    {
      throw new TorNoAnswerException("Queue.receiveCell: connection closed or no answer after " + timeoutMs / 1000 + " s");
    }
    

    if (cell.getCommand() != type)
    {

      throw new TorException("Queue.receiveCell: expected cell of type " + Cell.type(type) + " received type " + cell.type());
    }
    


    return cell;
  }
  








  public CellRelay receiveRelayCell(int type)
    throws IOException, TorException, TorNoAnswerException
  {
    CellRelay relay = (CellRelay)receiveCell(3);
    if (relay.getRelayCommand() != type)
    {

      if ((relay.getRelayCommand() == 3) && (relay.getData() != null))
      {




        throw new TorException("Queue.receiveRelayCell: expected relay-cell of type " + CellRelay.getRelayCommandAsString(type) + ", received END-CELL for reason: " + relay.getReasonForClosing());
      }
      

      if (relay.isTypeTruncated())
      {


        LOG.debug("Queue.receiveRelayCell: expected relay-cell of type " + 
          CellRelay.getRelayCommandAsString(type) + " received type " + relay
          
          .getRelayCommandAsString() + " reason : " + relay
          
          .getReasonForTruncated());
        






        throw new TorException("Queue.receiveRelayCell: expected relay-cell of type " + CellRelay.getRelayCommandAsString(type) + " received type " + relay.getRelayCommandAsString() + " reason : " + relay.getReasonForTruncated());
      }
      



      LOG.debug("Queue.receiveRelayCell: expected relay-cell of type " + 
        CellRelay.getRelayCommandAsString(type) + " received type " + relay
        
        .getRelayCommandAsString());
      




      throw new TorException("Queue.receiveRelayCell: expected relay-cell of type " + CellRelay.getRelayCommandAsString(type) + " received type " + relay.getRelayCommandAsString());
    }
    



    LOG.debug("got correct type.");
    
    return relay;
  }
  




  public boolean isClosed()
  {
    return closed;
  }
  
  public int getTimeoutMs()
  {
    return timeoutMs;
  }
  
  public void setTimeoutMs(int timeoutMs)
  {
    this.timeoutMs = timeoutMs;
  }
}
