package ooo.cpacket.ruby.module.mode.movement.fly;

import ooo.cpacket.ruby.api.event.EventImpl;
import ooo.cpacket.ruby.api.event.events.network.EventPacket;
import ooo.cpacket.ruby.api.event.events.player.EventMotionUpdate;
import ooo.cpacket.ruby.api.event.events.player.EventRawMove;
import ooo.cpacket.ruby.module.mode.Mode;
import ooo.cpacket.ruby.module.move.Fly;

public abstract class FlyMode extends Mode {

	public FlyMode(Fly module, String name) {
		super(module, name);
	}
	
	@EventImpl
	public abstract void move(EventRawMove e);
	
	@EventImpl
	public abstract void tick(EventMotionUpdate e);
	
	@EventImpl
	public abstract void packet(EventPacket e);
	
}
