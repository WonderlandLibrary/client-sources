package Squad.Events;

import com.darkmagician6.eventapi.events.Event;

public class EventPreMotion implements Event {
	
	private float pitch;
	private float yaw;
	
	public EventPreMotion(float yaw, float pitch){
		this.yaw = yaw;
		this.pitch = pitch;
	}
	
	public float getPitch(){
		  return pitch;
	  }
	  
	  public float getYaw(){
		  return yaw;
	  }
	  
	  public void setPitch(float pitch){
		  this.pitch = pitch;
	  }
	  
	  public void setYaw(float yaw){
		  this.yaw = yaw;
	  }

}
