package com.zCoreEvent.events;

import com.zCoreEvent.Event;

public class Event3D
  extends Event
{
  private float ticks;
  private boolean isUsingShaders;
  
  public Event3D(float ticks)
  {
    this.ticks = ticks;
  }
  
  public float getPartialTicks()
  {
    return this.ticks;
  }
  
  public boolean isUsingShaders()
  {
    return this.isUsingShaders;
  }
}
