package club.bluezenith.module.modules.fun;

import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.MoveEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.util.player.MovementUtil;

@SuppressWarnings("UnstableApiUsage")
public class Slow extends Module {
	public Slow() {
		super("Slow", ModuleCategory.FUN);
	}
	@Listener
	public void moveEvent(MoveEvent e){
		if (e.isPost()) return;
		mc.thePlayer.setSprinting(false);
		MovementUtil.setSpeed(0.01, e);
		mc.thePlayer.motionX = 0;
		mc.thePlayer.motionZ = 0;
	}
}
