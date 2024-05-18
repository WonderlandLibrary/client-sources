package me.protocol_client.modules;

import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPreMotionUpdates;

public class Frames extends Module{
	

	  public Frames() {
		super("Frames", "frames", 0, Category.MOVEMENT, new String[]{""});
	}

	boolean move;
	  boolean canChangeMotion;
	  
	  public void onEnable()
	  {
	   EventManager.register(this);
	    
	    this.move = true;
	    this.canChangeMotion = false;
	  }
	  public void onDisable(){
		  EventManager.unregister(this);
		  Wrapper.mc().timer.timerSpeed = 1f;
	  }
	  @EventTarget
	  public void onEvent(EventPreMotionUpdates event)
	  {
	    if (!this.mc.thePlayer.onGround)
	    {
	      if (this.canChangeMotion) {
	        if (!this.move)
	        {
	          this.mc.thePlayer.motionX *= 4.3D;
	          this.mc.thePlayer.motionZ *= 4.3D;
	          this.move = true;
	          this.canChangeMotion = false;
	        }
	        else
	        {
	          this.mc.thePlayer.motionX = 0.0D;
	          this.mc.thePlayer.motionZ = 0.0D;
	          this.move = false;
	          this.canChangeMotion = false;
	        }
	      }
	    }
	    else if ((this.mc.thePlayer.moveForward != 0 || this.mc.thePlayer.moveStrafing != 0) && (!this.mc.thePlayer.isCollidedHorizontally))
	    {
	      this.mc.thePlayer.jump();
	      this.canChangeMotion = true;
	      
	      {
	    }
	      
	      
	  }
	}
}
