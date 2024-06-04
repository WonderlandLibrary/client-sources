package org.lwjgl.opengl;

import java.nio.ByteBuffer;





































class EventQueue
{
  private static final int QUEUE_SIZE = 200;
  private final int event_size;
  private final ByteBuffer queue;
  
  protected EventQueue(int event_size)
  {
    this.event_size = event_size;
    queue = ByteBuffer.allocate(200 * event_size);
  }
  
  protected synchronized void clearEvents() {
    queue.clear();
  }
  


  public synchronized void copyEvents(ByteBuffer dest)
  {
    queue.flip();
    int old_limit = queue.limit();
    if (dest.remaining() < queue.remaining())
      queue.limit(dest.remaining() + queue.position());
    dest.put(queue);
    queue.limit(old_limit);
    queue.compact();
  }
  



  public synchronized boolean putEvent(ByteBuffer event)
  {
    if (event.remaining() != event_size)
      throw new IllegalArgumentException("Internal error: event size " + event_size + " does not equal the given event size " + event.remaining());
    if (queue.remaining() >= event.remaining()) {
      queue.put(event);
      return true;
    }
    return false;
  }
}
