package win.sightclient.module.movement.flight;

import io.netty.util.internal.ThreadLocalRandom;
import win.sightclient.event.Event;
import win.sightclient.event.events.player.EventMove;
import win.sightclient.module.Module;
import win.sightclient.module.ModuleMode;
import win.sightclient.module.settings.NumberSetting;
import win.sightclient.utils.minecraft.MoveUtils;

public class VanillaFlight extends ModuleMode {

	private NumberSetting speed;
	
	public VanillaFlight(Module parent, NumberSetting speed) {
		super(parent);
		this.speed = speed;
	}

	@Override
	public void onEvent(Event e) {
		if (e instanceof EventMove) {
			EventMove em = (EventMove)e;
			double speed = this.speed.getValue() * ThreadLocalRandom.current().nextDouble(0.95, 1);
			if (mc.gameSettings.keyBindJump.isKeyDown() && !mc.gameSettings.keyBindSneak.isKeyDown()) {
				em.setY(mc.thePlayer.motionY = speed);
			} else if (mc.gameSettings.keyBindSneak.isKeyDown() && !mc.gameSettings.keyBindJump.isKeyDown()) {
				em.setY(mc.thePlayer.motionY = -speed);
			} else {
				em.setY(mc.thePlayer.motionY = 0);
			}
			MoveUtils.setMotion(em, speed);
		}
	}
	
	@Override
	public void onDisable() {
		mc.thePlayer.motionX = 0;
		mc.thePlayer.motionY = 0;
		mc.thePlayer.motionY = 0;
	}
}
