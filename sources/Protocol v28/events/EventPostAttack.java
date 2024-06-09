package events;

import net.minecraft.entity.Entity;
import darkmagician6.Event;

public class EventPostAttack
implements Event
{
private Entity attacker;
private Entity target;

public EventPostAttack(Entity attacker, Entity target)
{
  this.attacker = attacker;
  this.target = target;
}

public Entity getAttacker()
{
  return this.attacker;
}

public Entity getTarget()
{
  return this.target;
}
}	
