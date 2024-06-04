package net.java.games.input;








public final class Event
{
  private Component component;
  






  private float value;
  






  private long nanos;
  







  public Event() {}
  






  public final void set(Event other)
  {
    set(other.getComponent(), other.getValue(), other.getNanos());
  }
  
  public final void set(Component component, float value, long nanos) {
    this.component = component;
    this.value = value;
    this.nanos = nanos;
  }
  
  public final Component getComponent() {
    return component;
  }
  
  public final float getValue() {
    return value;
  }
  




  public final long getNanos()
  {
    return nanos;
  }
  
  public final String toString() {
    return "Event: component = " + component + " | value = " + value;
  }
}
