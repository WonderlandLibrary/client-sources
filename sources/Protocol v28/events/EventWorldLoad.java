package events;

import net.minecraft.client.multiplayer.WorldClient;
import darkmagician6.EventCancellable;

public final class EventWorldLoad
  extends EventCancellable
{
  private final WorldClient world;
  
  public EventWorldLoad(WorldClient world)
  {
    this.world = world;
  }
  
  public WorldClient getWorld()
  {
    return this.world;
  }
}
