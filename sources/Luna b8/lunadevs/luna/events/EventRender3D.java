package lunadevs.luna.events;

import com.darkmagician6.eventapi.events.Event;

public class EventRender3D
  implements Event
{
  public static float partialTicks;
  
  public EventRender3D(float partialTicks)
  {
    partialTicks = EventRender3D.partialTicks;
  }
  
  public static float getPartialTicks()
  {
    return partialTicks;
  }
}
