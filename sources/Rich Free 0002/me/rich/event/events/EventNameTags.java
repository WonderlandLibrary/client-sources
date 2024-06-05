package me.rich.event.events;

import me.rich.event.Event;
import net.minecraft.entity.EntityLivingBase;

public class EventNameTags extends Event {
	private final EntityLivingBase entity;
	private String renderedName;

	public EventNameTags(EntityLivingBase entity, String renderedName) {
	      this.entity = entity;
	      this.renderedName = renderedName;
	   }

	public EntityLivingBase getEntity() {
		return this.entity;
	}

	public String getRenderedName() {
		return this.renderedName;
	}

	public void setRenderedName(String renderedName) {
		this.renderedName = renderedName;
	}

}
