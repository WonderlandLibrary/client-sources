package net.SliceClient.event;

import com.darkmagician6.eventapi.events.callables.EventCancellable;
import net.minecraft.entity.Entity;

public class EventPreAttack
  extends EventCancellable
{
  private Entity entity;
  
  public EventPreAttack(Entity entity)
  {
    this.entity = entity;
  }
  
  public Entity getEntity()
  {
    return entity;
  }
  
  public void setEntity(Entity entity)
  {
    this.entity = entity;
  }
}
