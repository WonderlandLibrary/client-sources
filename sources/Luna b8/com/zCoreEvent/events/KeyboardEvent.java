package com.zCoreEvent.events;

import com.zCoreEvent.Event;

public class KeyboardEvent
  extends Event
{
  public int key;
  
  public KeyboardEvent(int key)
  {
    this.key = key;
  }
}
