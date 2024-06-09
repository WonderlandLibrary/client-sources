package lunadevs.luna.events;

import com.darkmagician6.eventapi.events.Event;

import net.minecraft.entity.Entity;

public class EventRenderNameTag implements Event{

	  public static boolean cancel;
	  private Entity entity;
	  
	  public EventRenderNameTag(Entity entity)
	  {
	    this.entity = entity;
	  }
	  
	  public Entity getEntity()
	  {
	    return this.entity;
	  }

}