package net.java.games.input;












public final class EventQueue
{
  private final Event[] queue;
  










  private int head;
  










  private int tail;
  










  public EventQueue(int size)
  {
    queue = new Event[size + 1];
    for (int i = 0; i < queue.length; i++) {
      queue[i] = new Event();
    }
  }
  

  final synchronized void add(Event event)
  {
    queue[tail].set(event);
    tail = increase(tail);
  }
  



  final synchronized boolean isFull()
  {
    return increase(tail) == head;
  }
  


  private final int increase(int x)
  {
    return (x + 1) % queue.length;
  }
  





  public final synchronized boolean getNextEvent(Event event)
  {
    if (head == tail)
      return false;
    event.set(queue[head]);
    head = increase(head);
    return true;
  }
}
