package ooo.cpacket.ruby.module.attack;

import ooo.cpacket.ruby.api.event.EventImpl;
import ooo.cpacket.ruby.api.event.events.player.EventMotionUpdate;
import ooo.cpacket.ruby.module.Module;

public class Fastbow extends Module {

	public Fastbow() {
		super("Fastbow", 0, Category.VISUAL);
	}

	@Override
	public void onEnable() {}

	@Override
	public void onDisable() {}
	
	@EventImpl
	public void xd(EventMotionUpdate e) {
		
	}
	
}
