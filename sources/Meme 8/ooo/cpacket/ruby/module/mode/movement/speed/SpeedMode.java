package ooo.cpacket.ruby.module.mode.movement.speed;

import ooo.cpacket.ruby.api.event.EventImpl;
import ooo.cpacket.ruby.api.event.events.network.EventPacket;
import ooo.cpacket.ruby.api.event.events.player.EventMotionUpdate;
import ooo.cpacket.ruby.api.event.events.player.EventRawMove;
import ooo.cpacket.ruby.module.Module;
import ooo.cpacket.ruby.module.mode.Mode;
import ooo.cpacket.ruby.module.move.Speed;

public abstract class SpeedMode extends Mode {
	
	public SpeedMode(Speed module, String name) {
		super(module, name);
	}

	public abstract void move(EventRawMove e);
	
	public abstract void tick(EventMotionUpdate e);
	
	public abstract void packet(EventPacket e);
	
}
