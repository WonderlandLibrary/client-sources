package ooo.cpacket.ruby.module.move;

import java.util.Arrays;

import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import ooo.cpacket.ruby.api.event.EventImpl;
import ooo.cpacket.ruby.api.event.EventManager;
import ooo.cpacket.ruby.api.event.events.player.EventMotionUpdate;
import ooo.cpacket.ruby.api.event.events.player.EventRawMove;
import ooo.cpacket.ruby.api.event.events.player.motion.State;
import ooo.cpacket.ruby.module.Module;
import ooo.cpacket.ruby.module.mode.movement.speed.AAC;
import ooo.cpacket.ruby.module.mode.movement.speed.Despacithoe;
import ooo.cpacket.ruby.module.mode.movement.speed.Hop;
import ooo.cpacket.ruby.util.SpeedUtils;
import ooo.cpacket.ruby.util.Timer;
import ooo.cpacket.ruby.util.motionstuff.SpeedValue;

public class Speed extends Module {

	public Timer timer = new Timer();

	public Speed(String name, int key, Category category) {
		super(name, key, category);
		this.addModes(Arrays.asList(new Despacithoe(this, "Ghostly"), new Hop(this, "Hop"), new AAC(this, "AAC")));
	}
	
	@Override
	public void onEnable() {
		EventManager.register(this.currentMode());
		this.currentMode().onEnable();
	}

	@Override
	public void onDisable() {
		EventManager.unregister(this.currentMode());
		this.currentMode().onDisable();
	}

}