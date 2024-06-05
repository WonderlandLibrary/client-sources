package win.sightclient.module.movement.speed;

import win.sightclient.Sight;
import win.sightclient.event.Event;
import win.sightclient.event.events.player.EventMove;
import win.sightclient.module.Module;
import win.sightclient.module.ModuleMode;
import win.sightclient.module.combat.TargetStrafe;
import win.sightclient.module.settings.NumberSetting;
import win.sightclient.utils.minecraft.MoveUtils;

public class VanillaSpeed extends ModuleMode{

	private NumberSetting speed;
	
	public VanillaSpeed(Module parent, NumberSetting speed) {
		super(parent);
		this.speed = speed;
	}

	private TargetStrafe targetStrafe;
	
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventMove) {
			if (this.targetStrafe == null) {
				this.targetStrafe = (TargetStrafe) Sight.instance.mm.getModuleByName("TargetStrafe");
			}
			if (mc.thePlayer.onGround && MoveUtils.isMoving()) {
				((EventMove) e).setY(mc.thePlayer.motionY = MoveUtils.getJumpBoost(0.42));
			}
			
			if (this.targetStrafe.canStrafe()) {
				this.targetStrafe.strafe((EventMove) e, speed.getValue());
			} else {
				MoveUtils.setMotion(((EventMove) e), speed.getValue());
			}
		}
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
	}
}
