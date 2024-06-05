package zamorozka.event.events;

import net.minecraft.entity.Entity;
import zamorozka.event.Event;

public class EventEntityDamage extends Event {

	Entity entity;
	
	public EventEntityDamage(Entity entity) {
		this.entity = entity;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
}