package net.SliceClient.event;

import com.darkmagician6.eventapi.events.Event;
import net.minecraft.entity.Entity;

public class EventPostAttack
  implements Event
{
  private Entity attacker;
  private Entity target;
  
  public EventPostAttack()
  {
    attacker = attacker;
    target = target;
  }
  
  public Entity getAttacker()
  {
    return attacker;
  }
  
  public Entity getTarget()
  {
    return target;
  }
}
