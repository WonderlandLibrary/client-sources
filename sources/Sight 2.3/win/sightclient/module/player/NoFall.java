package win.sightclient.module.player;

import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.network.play.client.C03PacketPlayer;
import win.sightclient.event.Event;
import win.sightclient.event.events.client.EventUpdate;
import win.sightclient.module.Category;
import win.sightclient.module.Module;

public class NoFall extends Module {

	public NoFall() {
		super("NoFall", Category.PLAYER);
	}

	@Override
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			EventUpdate eu = (EventUpdate) e;
			if (eu.isPre() && mc.thePlayer.fallDistance > 3.0F) {
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(mc.thePlayer.ticksExisted % ThreadLocalRandom.current().nextInt(45, 75) != 0));
			}	
		}
	}
}
