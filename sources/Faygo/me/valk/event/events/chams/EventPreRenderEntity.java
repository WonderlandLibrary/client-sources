package me.valk.event.events.chams;

import me.valk.event.Event;
import net.minecraft.entity.Entity;

public class EventPreRenderEntity extends Event {

	private Entity e;

	public EventPreRenderEntity(final Entity e) {
		this.e = null;
		this.setEntity(e);
	}
	 public Entity getEntity() {
	        return this.e;
	    }
	    
	public void setEntity(final Entity e) {
		this.e = e;
	}
}
