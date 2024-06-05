package zamorozka.event.events;

import net.minecraft.entity.EntityLivingBase;
import zamorozka.event.Event;

public class EventNameTag extends Event {
	   private final EntityLivingBase entity;
	   private String renderedName;

	   public EventNameTag(EntityLivingBase entity, String renderedName) {
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
