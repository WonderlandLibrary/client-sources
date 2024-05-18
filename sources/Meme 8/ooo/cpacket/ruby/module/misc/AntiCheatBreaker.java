package ooo.cpacket.ruby.module.misc;

import net.minecraft.network.play.client.C00PacketKeepAlive;
import ooo.cpacket.ruby.api.event.EventImpl;
import ooo.cpacket.ruby.api.event.events.network.EventPacket;
import ooo.cpacket.ruby.api.event.events.player.EventMotionUpdate;
import ooo.cpacket.ruby.module.Module;

public class AntiCheatBreaker extends Module {

	private boolean XD = false;

	public AntiCheatBreaker(String name, int key, Category category) {
		super(name, key, category);
		this.addModes("Janitor", "Guardian", "AGC");
	}

	@Override
	public void onEnable() {
		mc.thePlayer.motionY = 4.5;
	}
	@EventImpl
	public void nigger(EventPacket depresscito) {
		if (depresscito.getPacket() instanceof C00PacketKeepAlive) {
			depresscito.setSkip(true);
		}
	}
	@Override
	public void onDisable() {}
	
	@EventImpl
	public void onUpdate(EventMotionUpdate e) {
		if (this.XD) {
			
		}
	}
	
}
