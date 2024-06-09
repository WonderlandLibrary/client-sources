package lunadevs.luna.events;

import com.darkmagician6.eventapi.events.Event;

import net.minecraft.entity.Entity;

public class StepEvent implements Event{
	
	  private float stepHeight;
	  private Entity entity;
	  private EventType type;
	  
	  public StepEvent(Entity entity, float stepHeight, EventType type)
	  {
	    this.entity = entity;
	    this.stepHeight = stepHeight;
	    this.type = type;
	  }
	  
	  public float getStepHeight()
	  {
	    return this.stepHeight;
	  }
	  
	  public void setStepHeight(float stepHeight)
	  {
	    this.stepHeight = stepHeight;
	  }
	  
	  public Entity getEntity()
	  {
	    return this.entity;
	  }
	  
	  public EventType getType()
	  {
	    return this.type;
	  }

}
