package net.java.games.input;

import java.io.IOException;

















































public abstract class AbstractComponent
  implements Component
{
  private final String name;
  private final Component.Identifier id;
  private boolean has_polled;
  private float value;
  private float event_value;
  
  protected AbstractComponent(String name, Component.Identifier id)
  {
    this.name = name;
    this.id = id;
  }
  


  public Component.Identifier getIdentifier()
  {
    return id;
  }
  



  public boolean isAnalog()
  {
    return false;
  }
  






  public float getDeadZone()
  {
    return 0.0F;
  }
  






  public final float getPollData()
  {
    if ((!has_polled) && (!isRelative())) {
      has_polled = true;
      try {
        setPollData(poll());
      } catch (IOException e) {
        ControllerEnvironment.log("Failed to poll component: " + e);
      }
    }
    return value;
  }
  
  final void resetHasPolled() {
    has_polled = false;
  }
  
  final void setPollData(float value) {
    this.value = value;
  }
  
  final float getEventValue() {
    return event_value;
  }
  
  final void setEventValue(float event_value) {
    this.event_value = event_value;
  }
  


  public String getName()
  {
    return name;
  }
  


  public String toString()
  {
    return name;
  }
  
  protected abstract float poll()
    throws IOException;
}
