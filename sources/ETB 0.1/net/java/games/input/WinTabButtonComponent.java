package net.java.games.input;













public class WinTabButtonComponent
  extends WinTabComponent
{
  private int index;
  











  protected WinTabButtonComponent(WinTabContext context, int parentDevice, String name, Component.Identifier id, int index)
  {
    super(context, parentDevice, name, id);
    this.index = index;
  }
  
  public Event processPacket(WinTabPacket packet) {
    Event newEvent = null;
    
    float newValue = (PK_BUTTONS & (int)Math.pow(2.0D, index)) > 0 ? 1.0F : 0.0F;
    if (newValue != getPollData()) {
      lastKnownValue = newValue;
      

      newEvent = new Event();
      newEvent.set(this, newValue, PK_TIME * 1000L);
      return newEvent;
    }
    
    return newEvent;
  }
}
