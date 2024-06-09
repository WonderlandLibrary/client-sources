package com.darkmagician6.eventapi.events;

public abstract interface Cancellable
{
  public abstract boolean isCancelled();
  
  public abstract void setCancelled(boolean paramBoolean);
}
