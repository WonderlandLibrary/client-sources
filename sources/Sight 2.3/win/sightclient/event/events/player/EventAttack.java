package win.sightclient.event.events.player;

import net.minecraft.entity.EntityLivingBase;
import win.sightclient.event.Event;

public class EventAttack extends Event {

	private EntityLivingBase target;
	
	public EventAttack(EntityLivingBase targ) {
		this.target = targ;
	}
	
	public EntityLivingBase getHit() {
		return this.target;
	}
} 
