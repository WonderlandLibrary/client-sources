package me.valk.agway.modes.speed;

import me.valk.agway.modules.movement.SpeedMod;
import me.valk.event.EventListener;
import me.valk.event.events.player.EventMotion;
import me.valk.module.ModMode;


public class Guardian extends ModMode<SpeedMod>{

	public Guardian(SpeedMod parent) {
		super(parent, "Guardian");
	
	}
	  @EventListener
	  public void onMotion(EventMotion event){
	           if(p.isMoving() && p.onGround && !p.isEating()) {
	            p.setSpeed(p.ticksExisted % 2 == 0 ? 0.28f : 0.22f);
	         } 
	           if(!p.isMoving()) {
	 			  p.motionX = 0;
	 			  p.motionZ = 0;
	 		  }
	  }

}
