package win.sightclient.module.movement.speed;

import win.sightclient.event.Event;
import win.sightclient.event.events.client.EventUpdate;
import win.sightclient.module.Module;
import win.sightclient.module.ModuleMode;
import win.sightclient.module.combat.TargetStrafe;
import win.sightclient.utils.minecraft.MoveUtils;

public class CubecraftSpeed extends ModuleMode{
	
	public CubecraftSpeed(Module parent) {
		super(parent);
	}

	private TargetStrafe targetStrafe;
	
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
	        if(mc.thePlayer.onGround) {
	            if(mc.thePlayer.ticksExisted % 7 == 0) {
	                MoveUtils.setMotion(null, ((0.3) * -0.1) + 2.0);
	                mc.timer.timerSpeed = 1.10090382898581F;
	                mc.thePlayer.motionY = 0.2;
	            }
	        } else {
	            mc.timer.timerSpeed = 1.0F;
	            MoveUtils.setMotion(null, 0.2);
	        }
		}
	}
	
	@Override
	public void onDisable() {
        mc.timer.timerSpeed = 1.0F;
        mc.thePlayer.motionX = 0;
        mc.thePlayer.motionZ = 0;
	}
}
