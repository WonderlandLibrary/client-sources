package me.xatzdevelopments.xatz.client.events.events2;

import net.minecraft.entity.Entity;
import me.xatzdevelopments.xatz.client.events.Event;

public class EventAttack extends Event {

	private Entity target;
	private boolean post;

	public Entity getTarget() {
		return target;
	}

	public void setTarget(Entity target) {
		this.target = target;
	}

	public EventAttack(Entity target, boolean post) {
		super();
		this.target = target;
		this.post = post;
	}
	
	public boolean isPre() {
		return !this.post;
	}
	
	public boolean isPost() {
		return this.post;
	}
}
